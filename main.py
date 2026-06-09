from fastapi import FastAPI, Query, HTTPException, Depends, Header
from typing import List, Optional
from pydantic import BaseModel
import uvicorn
import requests
from pymongo import MongoClient
from bson.objectid import ObjectId
import bcrypt
import jwt
from datetime import datetime, timedelta
import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
import threading
import concurrent.futures
import re
import spotipy
from spotipy.oauth2 import SpotifyClientCredentials

import os
from dotenv import load_dotenv

load_dotenv()

SPOTIPY_CLIENT_ID = os.getenv("SPOTIFY_CLIENT_ID", "cccb6c89f4ac43f8a874a20b19e38fc5")
SPOTIPY_CLIENT_SECRET = os.getenv("SPOTIFY_CLIENT_SECRET", "d0b74333a8d24d51bdd58f4ed77179b1")

if SPOTIPY_CLIENT_ID and SPOTIPY_CLIENT_SECRET:
    sp = spotipy.Spotify(auth_manager=SpotifyClientCredentials(client_id=SPOTIPY_CLIENT_ID, client_secret=SPOTIPY_CLIENT_SECRET))
else:
    sp = None

app = FastAPI()

# Database Config
MONGO_URL = os.getenv("MONGO_URL", "mongodb+srv://user:pass@cluster.mongodb.net/")
client = MongoClient(MONGO_URL)
db = client["pulse"]
users_collection = db["users"]
liked_songs_collection = db["liked_songs"]
followed_artists_collection = db["followed_artists"]
playlists_collection = db["playlists"]
recent_searches_collection = db["recent_searches"]
recently_played_collection = db["recently_played"]

SECRET_KEY = os.getenv("SECRET_KEY", "your-secret-key-here")

def create_access_token(data: dict):
    to_encode = data.copy()
    expire = datetime.utcnow() + timedelta(days=30)
    to_encode.update({"exp": expire})
    return jwt.encode(to_encode, SECRET_KEY, algorithm="HS256")

def get_current_user(authorization: str = Header(None)):
    if not authorization or not authorization.startswith("Bearer "):
        raise HTTPException(status_code=401, detail="Unauthorized")
    token = authorization.split(" ")[1]
    try:
        payload = jwt.decode(token, SECRET_KEY, algorithms=["HS256"])
        return str(payload.get("sub")) # user_id
    except:
        raise HTTPException(status_code=401, detail="Invalid token")

# Models
class Song(BaseModel):
    id: str
    title: str
    artist: str
    album: Optional[str] = ""
    albumArt: str = ""
    durationMs: int = 0
    source: str = "jiosaavn"

class Playlist(BaseModel):
    id: str
    title: str
    image: str = ""
    songCount: Optional[int] = None
    source: str = "jiosaavn"

class SearchResponse(BaseModel):
    songs: List[Song] = []
    artists: List['Artist'] = []
    albums: List['Album'] = []
    playlists: List[Playlist] = []

class HomeResponse(BaseModel):
    featuredPlaylists: List[Playlist] = []
    topPlaylists: List[Playlist] = []
    englishHits: List[Playlist] = []
    hindiHits: List[Playlist] = []
    punjabiHits: List[Playlist] = []
    topHits: List[Playlist] = []
    popClassic: List[Playlist] = []
    artists: List['Artist'] = []
    kpop: List[Playlist] = []
    trendingEnglish: List[Playlist] = []

class Lyrics(BaseModel):
    plain: Optional[str] = None
    synced: Optional[List[dict]] = None
    source: str = "jiosaavn"

class Album(BaseModel):
    id: str
    title: str
    artist: str
    coverArt: str = ""
    year: int = 0
    tracks: List[Song] = []

class Artist(BaseModel):
    id: str
    name: str
    image: str = ""
    bio: str = ""
    genres: List[str] = []
    similar: List['Artist'] = []
    topTracks: List[Song] = []
    albums: List[Album] = []

class AuthUser(BaseModel):
    email: str
    password: str

class RegisterUser(BaseModel):
    username: str
    email: str
    password: str

# Email Setup
EMAIL_SENDER = os.getenv("EMAIL_SENDER", "") 
EMAIL_PASSWORD = os.getenv("EMAIL_PASSWORD", "")

def send_email_async(to_email: str, subject: str, body: str):
    def send():
        try:
            msg = MIMEMultipart()
            msg['From'] = EMAIL_SENDER
            msg['To'] = to_email
            msg['Subject'] = subject
            msg.attach(MIMEText(body, 'plain'))
            
            server = smtplib.SMTP('smtp.gmail.com', 587)
            server.starttls()
            server.login(EMAIL_SENDER, EMAIL_PASSWORD)
            server.send_message(msg)
            server.quit()
        except Exception as e:
            print("Failed to send email:", e)
            
    threading.Thread(target=send).start()

# Auth Routes
@app.post("/api/v1/auth/register")
def register(user: RegisterUser):
    if users_collection.find_one({"email": user.email}):
        raise HTTPException(status_code=400, detail="Email already registered")
        
    hashed_pw = bcrypt.hashpw(user.password.encode('utf-8'), bcrypt.gensalt()).decode('utf-8')
    result = users_collection.insert_one({"username": user.username, "email": user.email, "passwordHash": hashed_pw})
    user_id = str(result.inserted_id)
    token = create_access_token({"sub": user_id})
    send_email_async(user.email, "Welcome to Pulse Music!", "Thanks for signing up to Pulse Music! We're excited to have you.")
    return {"token": token, "username": user.username, "email": user.email}

@app.post("/api/v1/auth/login")
def login(user: AuthUser):
    db_user = users_collection.find_one({"email": user.email})
    if not db_user or not bcrypt.checkpw(user.password.encode('utf-8'), db_user['passwordHash'].encode('utf-8')):
        raise HTTPException(status_code=400, detail="Incorrect email or password")
    
    token = create_access_token({"sub": str(db_user['_id'])})
    send_email_async(user.email, "New Login Alert", "We noticed a new login to your Pulse Music account.")
    username = db_user.get("username", user.email.split("@")[0])
    return {"token": token, "username": username, "email": user.email}

class ForgotPasswordRequest(BaseModel):
    email: str

class VerifyCodeRequest(BaseModel):
    email: str
    code: str

class ResetPasswordRequest(BaseModel):
    email: str
    code: str
    new_password: str

import random

# Store reset codes temporarily in MongoDB
reset_codes_collection = db["reset_codes"]

@app.post("/api/v1/auth/forgot-password")
def forgot_password(req: ForgotPasswordRequest):
    db_user = users_collection.find_one({"email": req.email})
    
    if not db_user:
        return {"status": "error", "message": "user doesn't exist"}
    
    code = str(random.randint(100000, 999999))
    reset_codes_collection.update_one(
        {"email": req.email},
        {"$set": {"code": code, "created_at": datetime.utcnow()}},
        upsert=True
    )
    send_email_async(
        req.email,
        "Pulse Music - Password Reset Code",
        f"Your password reset code is: {code}\n\nThis code will expire in 10 minutes.\n\nIf you didn't request this, please ignore this email."
    )
    return {"status": "success", "message": "Code sent to your email"}

@app.post("/api/v1/auth/verify-code")
def verify_code(req: VerifyCodeRequest):
    record = reset_codes_collection.find_one({"email": req.email, "code": req.code})
    if not record:
        return {"status": "error", "message": "Invalid code"}
    
    created = record.get("created_at", datetime.utcnow())
    if (datetime.utcnow() - created).total_seconds() > 600:
        reset_codes_collection.delete_one({"email": req.email})
        return {"status": "error", "message": "Code expired"}
    
    return {"status": "success", "message": "Code verified"}

@app.post("/api/v1/auth/reset-password")
def reset_password(req: ResetPasswordRequest):
    record = reset_codes_collection.find_one({"email": req.email, "code": req.code})
    if not record:
        return {"status": "error", "message": "Invalid code"}
    
    hashed_pw = bcrypt.hashpw(req.new_password.encode('utf-8'), bcrypt.gensalt()).decode('utf-8')
    users_collection.update_one({"email": req.email}, {"$set": {"passwordHash": hashed_pw}})
    reset_codes_collection.delete_one({"email": req.email})
    return {"status": "success", "message": "Password changed successfully"}

# Liked Songs Routes
@app.get("/api/v1/user/liked", response_model=List[Song])
def get_liked_songs(user_id: str = Depends(get_current_user)):
    rows = liked_songs_collection.find({"user_id": user_id})
    return [Song(id=r['song_id'], title=r.get('title', ''), artist=r.get('artist', ''), albumArt=r.get('cover_art', '')) for r in rows]

@app.post("/api/v1/user/liked")
def add_liked_song(song: Song, user_id: str = Depends(get_current_user)):
    try:
        liked_songs_collection.update_one(
            {"user_id": user_id, "song_id": song.id},
            {"$set": {"title": song.title, "artist": song.artist, "cover_art": song.albumArt}},
            upsert=True
        )
        return {"status": "success"}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.delete("/api/v1/user/liked/{song_id}")
def remove_liked_song(song_id: str, user_id: str = Depends(get_current_user)):
    liked_songs_collection.delete_one({"user_id": user_id, "song_id": song_id})
    return {"status": "success"}

# Artist Follows Routes
@app.get("/api/v1/user/follows")
def get_followed_artists(user_id: str = Depends(get_current_user)):
    rows = followed_artists_collection.find({"user_id": user_id})
    return [{"id": r['artist_id'], "name": r.get('name', ''), "image": r.get('image', '')} for r in rows]

@app.post("/api/v1/user/follows")
def follow_artist(artist_id: str, name: str, image: str = "", user_id: str = Depends(get_current_user)):
    try:
        followed_artists_collection.update_one(
            {"user_id": user_id, "artist_id": artist_id},
            {"$set": {"name": name, "image": image}},
            upsert=True
        )
        return {"status": "success"}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

# Recent Searches Routes
class RecentSearch(BaseModel):
    query: str
    timestamp: float

@app.get("/api/v1/user/searches")
def get_recent_searches(user_id: str = Depends(get_current_user)):
    rows = recent_searches_collection.find({"user_id": user_id}).sort("timestamp", -1).limit(10)
    return [{"query": r["query"], "timestamp": r["timestamp"]} for r in rows]

@app.post("/api/v1/user/searches")
def add_recent_search(req: RecentSearch, user_id: str = Depends(get_current_user)):
    try:
        # Upsert search query so it updates timestamp if it exists, otherwise inserts it
        recent_searches_collection.update_one(
            {"user_id": user_id, "query": req.query},
            {"$set": {"timestamp": req.timestamp}},
            upsert=True
        )
        return {"status": "success"}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.delete("/api/v1/user/searches")
def clear_recent_searches(user_id: str = Depends(get_current_user)):
    recent_searches_collection.delete_many({"user_id": user_id})
    return {"status": "success"}

# Recently Played Routes
class RecentlyPlayedSong(BaseModel):
    id: str
    title: str
    artist: str
    album: str
    albumArt: str
    source: str = "jiosaavn"
    timestamp: float

@app.get("/api/v1/user/recent-songs")
def get_recent_songs(user_id: str = Depends(get_current_user)):
    rows = recently_played_collection.find({"user_id": user_id}).sort("timestamp", -1).limit(20)
    result = []
    for r in rows:
        result.append({
            "id": r["song_id"],
            "title": r.get("title", ""),
            "artist": r.get("artist", ""),
            "album": r.get("album", ""),
            "albumArt": r.get("albumArt", ""),
            "source": r.get("source", "jiosaavn"),
            "timestamp": r.get("timestamp", 0)
        })
    return result

@app.post("/api/v1/user/recent-songs")
def add_recent_song(req: RecentlyPlayedSong, user_id: str = Depends(get_current_user)):
    try:
        recently_played_collection.update_one(
            {"user_id": user_id, "song_id": req.id},
            {
                "$set": {
                    "title": req.title,
                    "artist": req.artist,
                    "album": req.album,
                    "albumArt": req.albumArt,
                    "source": req.source,
                    "timestamp": req.timestamp
                }
            },
            upsert=True
        )
        return {"status": "success"}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

# Playlist Routes
class SpotifyImportRequest(BaseModel):
    url: str

def search_jiosaavn(track_name, artist_name):
    try:
        query = f"{track_name} {artist_name}"
        res = _search_songs_internal(query, "song")
        songs = res.songs if hasattr(res, "songs") else res.get("songs", [])
        if songs and len(songs) > 0:
            song = songs[0]
            return {
                "id": song.id if hasattr(song, "id") else song["id"],
                "title": song.title if hasattr(song, "title") else song["title"],
                "artist": song.artist if hasattr(song, "artist") else song["artist"],
                "album": song.album if hasattr(song, "album") else song["album"],
                "albumArt": song.albumArt if hasattr(song, "albumArt") else song["albumArt"],
                "source": "jiosaavn"
            }
    except Exception:
        pass
    return None

@app.post("/api/v1/user/playlists/spotify-import")
def import_spotify_playlist(req: SpotifyImportRequest, user_id: str = Depends(get_current_user)):
    match = re.search(r"playlist/([a-zA-Z0-9]+)", req.url)
    if not match:
        raise HTTPException(status_code=400, detail="Invalid Spotify Playlist URL")
    
    playlist_id = match.group(1)
    
    # Scrape tracks from Spotify embed page (no API key needed)
    try:
        embed_url = f"https://open.spotify.com/embed/playlist/{playlist_id}"
        headers = {"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"}
        embed_res = requests.get(embed_url, headers=headers, timeout=15)
        if embed_res.status_code != 200:
            raise HTTPException(status_code=400, detail="Could not fetch Spotify playlist")
        
        import json as json_mod
        next_data_match = re.search(r'<script id="__NEXT_DATA__" type="application/json">(.*?)</script>', embed_res.text)
        if not next_data_match:
            raise HTTPException(status_code=400, detail="Could not parse Spotify playlist data")
        
        data = json_mod.loads(next_data_match.group(1))
        entity = data.get("props", {}).get("pageProps", {}).get("state", {}).get("data", {}).get("entity", {})
        playlist_name = entity.get("name") or entity.get("title") or "Imported Spotify Playlist"
        track_list = entity.get("trackList", [])
        
        if not track_list:
            raise HTTPException(status_code=400, detail="Playlist is empty or could not be read")
    except HTTPException:
        raise
    except Exception as e:
        raise HTTPException(status_code=400, detail=f"Error fetching playlist: {str(e)}")
        
    tracks_to_search = []
    for track in track_list:
        title = track.get("title", "")
        # subtitle contains artist names separated by non-breaking spaces and commas
        subtitle = track.get("subtitle", "").replace("\u00a0", " ").strip()
        # Take only the first artist
        artist = subtitle.split(",")[0].strip() if subtitle else ""
        if title:
            tracks_to_search.append((title, artist))
            
    matched_songs = []
    with concurrent.futures.ThreadPoolExecutor(max_workers=10) as executor:
        futures = []
        for name, artist in tracks_to_search:
            futures.append(executor.submit(search_jiosaavn, name, artist))
        for future in concurrent.futures.as_completed(futures):
            res = future.result()
            if res:
                matched_songs.append(res)
                
    if not matched_songs:
        raise HTTPException(status_code=404, detail="Could not match any songs from the playlist")
        
    # Create the playlist in DB
    res = playlists_collection.insert_one({
        "user_id": user_id, 
        "name": playlist_name, 
        "songs": matched_songs,
        "created_at": datetime.utcnow()
    })
    
    return {
        "status": "success",
        "playlist_id": str(res.inserted_id),
        "name": playlist_name,
        "matched_count": len(matched_songs),
        "total_count": len(tracks_to_search)
    }

@app.get("/api/v1/user/playlists")
def get_playlists(user_id: str = Depends(get_current_user)):
    rows = playlists_collection.find({"user_id": user_id})
    result = []
    for r in rows:
        result.append({
            "id": str(r["_id"]),
            "name": r.get("name", ""),
            "songs": r.get("songs", [])
        })
    return result

@app.post("/api/v1/user/playlists")
def create_playlist(name: str, user_id: str = Depends(get_current_user)):
    res = playlists_collection.insert_one({
        "user_id": user_id, 
        "name": name, 
        "songs": [],
        "created_at": datetime.utcnow()
    })
    return {"status": "success", "id": str(res.inserted_id)}

@app.delete("/api/v1/user/playlists/{playlist_id}")
def delete_playlist(playlist_id: str, user_id: str = Depends(get_current_user)):
    playlists_collection.delete_one({"_id": ObjectId(playlist_id), "user_id": user_id})
    return {"status": "success"}

@app.post("/api/v1/user/playlists/{playlist_id}/songs")
def add_song_to_playlist(playlist_id: str, song: Song, user_id: str = Depends(get_current_user)):
    playlists_collection.update_one(
        {"_id": ObjectId(playlist_id), "user_id": user_id},
        {"$push": {"songs": song.dict()}}
    )
    return {"status": "success"}

@app.delete("/api/v1/user/playlists/{playlist_id}/songs/{song_id}")
def remove_song_from_playlist(playlist_id: str, song_id: str, user_id: str = Depends(get_current_user)):
    playlists_collection.update_one(
        {"_id": ObjectId(playlist_id), "user_id": user_id},
        {"$pull": {"songs": {"id": song_id}}}
    )
    return {"status": "success"}

# YTMusic & ListenFree Routes
@app.get("/api/v1/search", response_model=SearchResponse)
def search_songs(q: str = Query(..., alias="q"), type: str = "song"):
    return _search_songs_internal(q, type)

def _search_songs_internal(q: str, type: str = "song"):
    try:
        res = requests.get("https://music-api.albatross0071.workers.dev/api/search", params={"query": q}, timeout=5)
        if res.status_code == 200:
            data = res.json()
            if data.get("success"):
                data_block = data.get("data", {})
                # Songs
                songs_obj = data_block.get("songs") or data_block.get("topQuery") or {}
                results = songs_obj.get("results", [])
                
                songs = []
                for item in results:
                    if item.get("type") and item.get("type") != "song":
                        continue
                    images = item.get("image", [])
                    cover = images[-1]["url"] if images else ""
                    artist = item.get("primaryArtists") or item.get("singers") or "Unknown"
                    songs.append(Song(
                        id=item["id"],
                        title=item["title"],
                        artist=artist,
                        album=item.get("album", ""),
                        albumArt=cover,
                        durationMs=int(item.get("duration", 0)) * 1000,
                        source="jiosaavn"
                    ))
                
                # Artists
                artists_results = data_block.get("artists", {}).get("results", [])
                artists = []
                for a in artists_results:
                    images = a.get("image", [])
                    cover = images[-1]["url"] if images else ""
                    artists.append(Artist(
                        id=a["id"],
                        name=a["title"],
                        image=cover
                    ))
                    
                # Albums
                albums_results = data_block.get("albums", {}).get("results", [])
                albums = []
                for alb in albums_results:
                    images = alb.get("image", [])
                    cover = images[-1]["url"] if images else ""
                    year_val = alb.get("year", 0)
                    if isinstance(year_val, str) and year_val.isdigit():
                        year_val = int(year_val)
                    elif not isinstance(year_val, int):
                        year_val = 0
                        
                    albums.append(Album(
                        id=alb["id"],
                        title=alb["title"],
                        artist=alb.get("artist", ""),
                        coverArt=cover,
                        year=year_val
                    ))
                    
                # Playlists
                playlists_results = data_block.get("playlists", {}).get("results", [])
                playlists = []
                for p in playlists_results:
                    images = p.get("image", [])
                    cover = images[-1]["url"] if images else ""
                    playlists.append(Playlist(
                        id=p["id"],
                        title=p["title"],
                        image=cover
                    ))
                    
                return SearchResponse(songs=songs, artists=artists, albums=albums, playlists=playlists)
    except Exception as e:
        print("ListenFree Search Error:", e)
    return SearchResponse(songs=[])

@app.get("/api/v1/trending", response_model=List[Song])
def get_trending(country: str = "IN"):
    # Using search as fallback since ListenFree doesn't have a direct trending endpoint documented here
    return search_songs("top hits 2026", "song").songs[:20]

@app.get("/api/v1/lyrics", response_model=Lyrics)
async def get_lyrics(title: str, artist: str, songId: str = None):
    import re
    import asyncio
    from concurrent.futures import ThreadPoolExecutor

    # Clean title and artist to improve matching
    clean_title = re.sub(r'\(.*?\)|\[.*?\]|- .*', '', title).strip()
    clean_artist = artist.split(',')[0].split('&')[0].strip()

    def fetch_lrclib():
        try:
            lrc_res = requests.get("https://lrclib.net/api/get", params={"artist_name": clean_artist, "track_name": clean_title}, headers={"User-Agent": "PulseMusic/1.0"}, timeout=5)
            if lrc_res.status_code == 200:
                lrc_data = lrc_res.json()
                synced_lrc = lrc_data.get("syncedLyrics")
                plain_lrc = lrc_data.get("plainLyrics")
                
                if synced_lrc:
                    parsed_synced = []
                    for line in synced_lrc.split('\n'):
                        match = re.match(r'\[(\d+):(\d+(?:\.\d+)?)\](.*)', line)
                        if match:
                            mins = int(match.group(1))
                            secs = float(match.group(2))
                            text = match.group(3).strip()
                            time_ms = int((mins * 60 + secs) * 1000)
                            parsed_synced.append({"timeMs": time_ms, "text": text})
                    
                    if parsed_synced:
                        return Lyrics(plain=plain_lrc, synced=parsed_synced, source="lrclib")
                elif plain_lrc:
                    return Lyrics(plain=plain_lrc, source="lrclib")
        except Exception as e:
            print("LRCLIB Error:", e)
        return None

    def fetch_ytmusic():
        try:
            from ytmusicapi import YTMusic
            yt = YTMusic()
            search_results = yt.search(f"{clean_title} {clean_artist}", filter="songs")
            if search_results:
                video_id = search_results[0]['videoId']
                watch_playlist = yt.get_watch_playlist(videoId=video_id)
                if 'lyrics' in watch_playlist and watch_playlist['lyrics']:
                    lyrics_dict = yt.get_lyrics(watch_playlist['lyrics'], timestamps=True)
                    if lyrics_dict:
                        if lyrics_dict.get('hasTimestamps'):
                            parsed_synced = [{"timeMs": l.start_time, "text": l.text} for l in lyrics_dict['lyrics']]
                            plain_text = "\n".join(l.text for l in lyrics_dict['lyrics'])
                            return Lyrics(plain=plain_text, synced=parsed_synced, source="ytmusic")
                        elif 'lyrics' in lyrics_dict:
                            return Lyrics(plain=lyrics_dict['lyrics'], source="ytmusic")
        except Exception as e:
            print("YTMusic Lyrics Error:", e)
        return None

    loop = asyncio.get_running_loop()
    with ThreadPoolExecutor() as pool:
        task1 = loop.run_in_executor(pool, fetch_lrclib)
        task2 = loop.run_in_executor(pool, fetch_ytmusic)
        
        for f in asyncio.as_completed([task1, task2]):
            result = await f
            if result is not None:
                return result

    return Lyrics(plain="Lyrics not found", source="unknown")

@app.get("/api/v1/recommendations", response_model=List[Song])
def get_recommendations(artist: str, track: str):
    try:
        search_res = requests.get("https://music-api.albatross0071.workers.dev/api/search", params={"query": f"{track} {artist}"}, timeout=5)
        if search_res.status_code == 200:
            s_data = search_res.json()
            if s_data.get("success"):
                results = (s_data.get("data", {}).get("songs") or s_data.get("data", {}).get("topQuery") or {}).get("results", [])
                if results:
                    song_id = results[0]["id"]
                    sugg_res = requests.get(f"https://music-api.albatross0071.workers.dev/api/songs/{song_id}/suggestions", timeout=5)
                    if sugg_res.status_code == 200:
                        sugg_data = sugg_res.json()
                        if sugg_data.get("success"):
                            songs = []
                            for item in sugg_data.get("data", []):
                                images = item.get("image", [])
                                cover = images[-1]["url"] if images else ""
                                p_artists = item.get("artists", {}).get("primary", [])
                                artist_names = ", ".join([a["name"] for a in p_artists]) if p_artists else "Unknown"
                                songs.append(Song(
                                    id=item["id"],
                                    title=item["name"],
                                    artist=artist_names,
                                    album=item.get("album", {}).get("name", ""),
                                    albumArt=cover,
                                    durationMs=int(item.get("duration", 0)) * 1000,
                                    source="jiosaavn"
                                ))
                            return songs
    except Exception as e:
        print("ListenFree Recommendations Error:", e)

    return []

@app.get("/api/v1/artist/{name}", response_model=Artist)
def get_artist(name: str):
    artist_image = ""
    artist_id = ""
    artist_bio = ""
    artist_name = name
    top_albums = []
    similar_artists = []
    top_tracks = []
    
    def parse_artist_data(data):
        nonlocal artist_id, artist_name, artist_image, artist_bio
        artist_id = str(data.get("id", artist_id))
        artist_name = data.get("name", artist_name)
        images = data.get("image", [])
        if images and isinstance(images, list):
            artist_image = images[-1].get("url", artist_image)
            
        for alb in data.get("topAlbums", []):
            alb_images = alb.get("image", [])
            alb_cover = alb_images[-1]["url"] if isinstance(alb_images, list) and alb_images else ""
            year_val = alb.get("year", 0)
            if isinstance(year_val, str) and year_val.isdigit():
                year_val = int(year_val)
            elif not isinstance(year_val, int):
                year_val = 0
            top_albums.append(Album(id=alb["id"], title=alb["name"], artist=artist_name, coverArt=alb_cover, year=year_val))
            
        for sim in data.get("similarArtists", []):
            if isinstance(sim, dict):
                sim_id = sim.get("id")
                sim_name = sim.get("name", "")
                sim_images = sim.get("image", [])
                sim_cover = sim_images[-1]["url"] if isinstance(sim_images, list) and sim_images else ""
                if sim_id and sim_name and str(sim_id) != sim_name:
                    similar_artists.append(Artist(id=str(sim_id), name=sim_name, image=sim_cover))
                    
        bio_data = data.get("bio")
        if isinstance(bio_data, list) and len(bio_data) > 0:
            artist_bio = bio_data[0].get("text", "")
        elif isinstance(bio_data, str):
            artist_bio = bio_data

    fetched_by_id = False
    try:
        art_details_res = requests.get(f"https://music-api.albatross0071.workers.dev/api/artists?id={name}", timeout=5)
        if art_details_res.status_code == 200:
            art_data = art_details_res.json()
            if art_data.get("success") and art_data.get("data", {}).get("name"):
                parse_artist_data(art_data.get("data", {}))
                fetched_by_id = True
    except Exception as e:
        print("Artist direct ID fetch error:", e)

    if not fetched_by_id:
        try:
            search_res = requests.get("https://music-api.albatross0071.workers.dev/api/search", params={"query": name}, timeout=5)
            if search_res.status_code == 200:
                s_data = search_res.json()
                if s_data.get("success"):
                    top_query_results = s_data.get("data", {}).get("topQuery", {}).get("results", [])
                    artist_results = s_data.get("data", {}).get("artists", {}).get("results", [])
                    artists = [q for q in top_query_results if q.get("type") == "artist"] + artist_results
                    
                    best_match = next((a for a in artists if a.get("title", "").lower() == name.lower()), artists[0] if artists else None)
                    if best_match:
                        artist_id = best_match["id"]
                        images = best_match.get("image", [])
                        artist_image = images[-1]["url"] if images else ""
                        
                        try:
                            art_details_res = requests.get(f"https://music-api.albatross0071.workers.dev/api/artists?id={artist_id}", timeout=5)
                            if art_details_res.status_code == 200 and art_details_res.json().get("success"):
                                parse_artist_data(art_details_res.json().get("data", {}))
                        except Exception as e:
                            print("Artist search ID fetch error:", e)
        except Exception as e:
            print("Artist search error:", e)

    try:
        top_tracks = search_songs(artist_name).songs[:10]
    except Exception:
        pass

    return Artist(
        id=artist_id,
        name=artist_name,
        image=artist_image,
        bio=artist_bio,
        albums=top_albums,
        topTracks=top_tracks,
        similar=similar_artists
    )

@app.get("/api/v1/album/{id}", response_model=Album)
def get_album(id: str):
    try:
        res = requests.get(f"https://music-api.albatross0071.workers.dev/api/albums?id={id}", timeout=5)
        data = res.json() if res.status_code == 200 else {}
        
        # If not successful or not found, try playlist endpoint
        alb_data = data.get("data", {})
        if not data.get("success") or not alb_data or not alb_data.get("id"):
            res = requests.get(f"https://music-api.albatross0071.workers.dev/api/playlists?id={id}", timeout=5)
            data = res.json() if res.status_code == 200 else {}

        if data.get("success") and data.get("data", {}).get("id"):
            alb = data.get("data", {})
            
            # parse songs
            songs = []
            for item in alb.get("songs", []):
                images = item.get("image", [])
                cover = images[-1]["url"] if images else ""
                
                p_artists = item.get("artists", {}).get("primary", [])
                artist_names = ", ".join([a["name"] for a in p_artists]) if p_artists else "Unknown"
                
                songs.append(Song(
                    id=item["id"],
                    title=item["name"] if "name" in item else item.get("title", ""),
                    artist=artist_names,
                    album=alb.get("name", ""),
                    albumArt=cover,
                    durationMs=int(item.get("duration", 0)) * 1000,
                    source="jiosaavn"
                ))
            
            images = alb.get("image", [])
            cover = images[-1]["url"] if images else ""
            
            year_val = alb.get("year", 0)
            if isinstance(year_val, str) and year_val.isdigit():
                year_val = int(year_val)
            elif not isinstance(year_val, int):
                year_val = 0
            
            return Album(
                id=id,
                title=alb.get("name", ""),
                artist=alb.get("primaryArtists", "Unknown") if "primaryArtists" in alb else alb.get("subtitle", "Playlist"),
                coverArt=cover,
                year=year_val,
                tracks=songs
            )
    except Exception as e:
        print("get_album error:", e)
    return Album(id=id, title="Unknown", artist="Unknown")

import time
import concurrent.futures

home_cache = None
home_cache_time = 0

@app.get("/api/v1/home", response_model=HomeResponse)
def get_home():
    global home_cache, home_cache_time
    if home_cache and time.time() - home_cache_time < 3600:
        return home_cache

    def fetch_playlists(query):
        res = _search_songs_internal(query)
        return res.playlists[:10]

    def fetch_artists(query):
        res = _search_songs_internal(query)
        return res.artists[:10]

    with concurrent.futures.ThreadPoolExecutor(max_workers=10) as executor:
        f_feat = executor.submit(fetch_playlists, "featured playlist")
        f_top_pl = executor.submit(fetch_playlists, "top playlist")
        f_eng = executor.submit(fetch_playlists, "english hits")
        f_hin = executor.submit(fetch_playlists, "hindi hits")
        f_pun = executor.submit(fetch_playlists, "punjabi hits")
        f_top = executor.submit(fetch_playlists, "top hits")
        f_pop = executor.submit(fetch_playlists, "pop classic")
        f_kpop = executor.submit(fetch_playlists, "k-pop")
        f_trend_eng = executor.submit(fetch_playlists, "trending english")
        f_art = executor.submit(fetch_artists, "top artists")

        home_cache = HomeResponse(
            featuredPlaylists=f_feat.result(),
            topPlaylists=f_top_pl.result(),
            englishHits=f_eng.result(),
            hindiHits=f_hin.result(),
            punjabiHits=f_pun.result(),
            topHits=f_top.result(),
            popClassic=f_pop.result(),
            artists=f_art.result(),
            kpop=f_kpop.result(),
            trendingEnglish=f_trend_eng.result()
        )
        home_cache_time = time.time()
        
    return home_cache

@app.get("/api/v1/health")
def health_v1():
    return {"status": "ok", "provider": "jiosaavn"}

if __name__ == "__main__":
    port = int(os.environ.get("PORT", 8080))
    uvicorn.run(app, host="0.0.0.0", port=port)
