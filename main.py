from fastapi import FastAPI, Query, HTTPException, Depends, Header, File, UploadFile
from typing import List, Optional
from pydantic import BaseModel
from fastapi import WebSocket, WebSocketDisconnect
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
import json
import spotipy
from spotipy.oauth2 import SpotifyClientCredentials


def filter_jio_image(url):
    if url and isinstance(url, str) and "default" in url and (".png" in url or ".jpg" in url):
        return ""
    return url

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
# Ensure email uniqueness at DB level
try:
    users_collection.create_index("email", unique=True)
except Exception:
    pass  # Index may already exist
liked_songs_collection = db["liked_songs"]
followed_artists_collection = db["followed_artists"]
playlists_collection = db["playlists"]
recent_searches_collection = db["recent_searches"]
recently_played_collection = db["recently_played"]
jams_collection = db["jams"]
jam_members_collection = db["jam_members"]
jam_messages_collection = db["jam_messages"]

SECRET_KEY = os.getenv("SECRET_KEY", os.getenv("JWT_SECRET", "super-secret-fallback-key"))

def create_access_token(data: dict):
    to_encode = data.copy()
    expire = datetime.utcnow() + timedelta(days=30)
    to_encode.update({"exp": expire})
    return jwt.encode(to_encode, SECRET_KEY, algorithm="HS256")

# ------------------- Jam Suggestions Endpoint -------------------
@app.get("/api/v1/jam/suggestions/{room_id}")
async def jam_suggestions(room_id: str, token: str = Header(None, alias="Authorization")):
    """Return up‑to‑5 Spotify recommendations based on the current queue.
    The token header must be a Bearer JWT (same as other secured routes)."""
    # Verify token (reuse get_current_user logic)
    if not token or not token.startswith("Bearer "):
        raise HTTPException(status_code=401, detail="Unauthorized")
    try:
        payload = jwt.decode(token.split()[1], SECRET_KEY, algorithms=["HS256"])
        user_id = str(payload.get("sub"))
    except Exception:
        raise HTTPException(status_code=401, detail="Invalid token")

    room = jam_rooms.get(room_id)
    if not room or not room.queue:
        return {"suggestions": []}
    # Use the first track as seed (fallback to first track's artist)
    seed_track_id = room.queue[0].get("id")
    try:
        recs = sp.recommendations(seed_tracks=[seed_track_id], limit=5) if sp else {"tracks": []}
        suggestions = [{
            "id": t["id"],
            "title": t["name"],
            "artist": t["artists"][0]["name"] if t["artists"] else "",
            "albumArt": t["album"]["images"][0]["url"] if t.get("album") else ""
        } for t in recs.get("tracks", [])]
        return {"suggestions": suggestions}
    except Exception as e:
        return {"suggestions": [], "error": str(e)}

def get_current_user(authorization: str = Header(None)):
    if not authorization or not authorization.startswith("Bearer "):
        raise HTTPException(status_code=401, detail="Unauthorized")
    token = authorization.split(" ")[1]
    try:
        payload = jwt.decode(token, SECRET_KEY, algorithms=["HS256"])
        return str(payload.get("sub")) # user_id
    except:
        raise HTTPException(status_code=401, detail="Invalid token")

# ------------------- Jam Session (Real-Time Group) -------------------
class JamRoom:
    def __init__(self, room_id: str):
        self.room_id = room_id
        from typing import Dict, Any
        self.connections: Dict[WebSocket, Dict[str, Any]] = {}
        import time
        
        # Load from DB
        jam_doc = jams_collection.find_one({"room_id": room_id})
        if jam_doc:
            self.queue = jam_doc.get("queue", [])
            self.is_playing = jam_doc.get("is_playing", False)
            self.position_ms = jam_doc.get("position_ms", 0)
            self.started_at_ms = jam_doc.get("started_at_ms", 0)
            self.current_song = jam_doc.get("current_song", None)
            self.created_at = jam_doc.get("created_at", time.time())
            self.last_active = jam_doc.get("last_active", time.time())
        else:
            self.queue = []
            self.is_playing = False
            self.position_ms = 0
            self.started_at_ms = 0
            self.current_song = None
            self.created_at = time.time()
            self.last_active = time.time()
            jams_collection.insert_one({
                "room_id": room_id,
                "queue": self.queue,
                "is_playing": self.is_playing,
                "position_ms": self.position_ms,
                "started_at_ms": self.started_at_ms,
                "current_song": self.current_song,
                "created_at": self.created_at,
                "last_active": self.last_active
            })

    def sync_to_db(self):
        import time
        self.last_active = time.time()
        jams_collection.update_one(
            {"room_id": self.room_id},
            {"$set": {
                "queue": self.queue,
                "is_playing": self.is_playing,
                "position_ms": self.position_ms,
                "started_at_ms": self.started_at_ms,
                "current_song": self.current_song,
                "last_active": self.last_active
            }}
        )

    async def broadcast(self, message: dict, sender: WebSocket = None):
        import json
        msg_str = json.dumps(message)
        for conn in list(self.connections.keys()):
            if conn != sender:
                try:
                    await conn.send_text(msg_str)
                except:
                    pass

jam_rooms: dict[str, JamRoom] = {}

import time
import asyncio

@app.get("/api/v1/jam/my-jams")
async def get_my_jams(user_id: str = Depends(get_current_user)):
    memberships = list(jam_members_collection.find({"user_id": user_id}))
    room_ids = [m["room_id"] for m in memberships]
    jams = list(jams_collection.find({"room_id": {"$in": room_ids}}, {"_id": 0}))
    
    result = []
    for j in jams:
        members_count = jam_members_collection.count_documents({"room_id": j["room_id"]})
        song_title = j.get("current_song", {}).get("title") if j.get("current_song") else None
        result.append({
            "jamId": j["room_id"],
            "roomCode": j["room_id"],
            "name": j.get("name", "My Jam"),
            "hostId": j.get("host_id", ""),
            "memberCount": members_count,
            "isActive": j["room_id"] in jam_rooms,
            "currentSongTitle": song_title
        })
    return result

@app.post("/api/v1/jam/create")
async def create_jam(name: str = Query("New Jam"), user_id: str = Depends(get_current_user)):
    jam_count = jams_collection.count_documents({"host_id": user_id})
    if jam_count >= 5:
        raise HTTPException(status_code=400, detail="You can only create up to 5 jams.")

    import uuid
    room_id = str(uuid.uuid4()).replace("-", "")[:6].upper()
    
    # Pre-insert jam details so JamRoom picks it up, or just let JamRoom update it.
    jams_collection.insert_one({
        "room_id": room_id,
        "name": name,
        "host_id": user_id,
        "queue": [],
        "is_playing": False,
        "position_ms": 0,
        "started_at_ms": 0,
        "current_song": None,
        "created_at": time.time(),
        "last_active": time.time()
    })
    
    room = JamRoom(room_id)
    jam_rooms[room_id] = room
    jam_members_collection.insert_one({
        "room_id": room_id,
        "user_id": user_id,
        "role": "HOST",
        "joined_at": time.time()
    })
    return {
        "jamId": room_id,
        "roomCode": room_id,
        "name": name,
        "hostId": user_id,
        "memberCount": 1,
        "isActive": True,
        "currentSongTitle": None
    }

@app.delete("/api/v1/jam/{room_id}")
async def delete_jam(room_id: str, user_id: str = Depends(get_current_user)):
    jam = jams_collection.find_one({"room_id": room_id})
    if not jam or jam.get("host_id") != user_id:
        raise HTTPException(status_code=403, detail="Only host can delete jam")
    
    jams_collection.delete_one({"room_id": room_id})
    jam_members_collection.delete_many({"room_id": room_id})
    jam_messages_collection.delete_many({"room_id": room_id})
    
    if room_id in jam_rooms:
        room = jam_rooms[room_id]
        for ws in list(room.connections.keys()):
            try:
                await ws.close(code=1000, reason="Jam deleted by host")
            except:
                pass
        del jam_rooms[room_id]
        
    return {"message": "Jam deleted successfully"}

@app.post("/api/v1/jam/{room_id}/kick")
async def kick_user(room_id: str, payload: dict, user_id: str = Depends(get_current_user)):
    target_user_id = payload.get("target_user_id")
    if not target_user_id:
        raise HTTPException(status_code=400, detail="Missing target_user_id")
        
    member = jam_members_collection.find_one({"room_id": room_id, "user_id": user_id})
    if not member or member.get("role") != "HOST":
        raise HTTPException(status_code=403, detail="Only host can kick users")
        
    jam_members_collection.delete_one({"room_id": room_id, "user_id": target_user_id})
    
    if room_id in jam_rooms:
        room = jam_rooms[room_id]
        target_ws = None
        for ws, info in list(room.connections.items()):
            if info["user_id"] == target_user_id:
                target_ws = ws
                break
        if target_ws:
            try:
                await target_ws.send_text(json.dumps({"event": "removed"}))
                await target_ws.close(code=1000, reason="Kicked by host")
            except:
                pass
            if target_ws in room.connections:
                del room.connections[target_ws]
            
            await room.broadcast({
                "event": "user_left",
                "participants": {info["user_id"]: info for info in room.connections.values()}
            })
            
    return {"message": "User kicked successfully"}

@app.websocket("/api/v1/jam/ws/{room_id}/{username}")
async def jam_websocket(websocket: WebSocket, room_id: str, username: str, action: str = Query(None), token: str = Query(None)):
    # Try to extract real user_id from token if available
    auth_header = websocket.headers.get("authorization")
    print(f"WS connect: username={username}, auth_header={auth_header}, token_query={token}")
    user_id = username
    
    # Priority 1: Query param token
    # Priority 2: Header token
    real_token = token
    if not real_token and auth_header and auth_header.startswith("Bearer "):
        real_token = auth_header.split(" ")[1]
        
    if real_token:
        try:
            payload = jwt.decode(real_token, SECRET_KEY, algorithms=["HS256"])
            user_id = str(payload.get("sub"))
            print(f"Extracted user_id from token: {user_id}")
        except Exception as e:
            print(f"JWT Decode error: {e}")
            pass

    # Enforce current_active_session_id
    if ObjectId.is_valid(user_id):
        users_collection.update_one({"_id": ObjectId(user_id)}, {"$set": {"current_active_session_id": room_id}})
    
    # Disconnect from any other jams in memory
    for r_id, r in list(jam_rooms.items()):
        if r_id != room_id:
            for ws, info in list(r.connections.items()):
                if info.get("user_id") == user_id:
                    try:
                        await ws.close(code=1000, reason="Joined another jam")
                    except:
                        pass
                    if ws in r.connections:
                        del r.connections[ws]
                    
    room = jam_rooms.get(room_id)
    if not room:
        room = JamRoom(room_id)
        jam_rooms[room_id] = room

    await websocket.accept()
    
    member_doc = jam_members_collection.find_one({"room_id": room_id, "user_id": user_id})
    is_host = False
    role = "PENDING"
    if member_doc:
        role = member_doc.get("role", "GUEST")
        is_host = (role == "HOST")
    else:
        count = jam_members_collection.count_documents({"room_id": room_id})
        if count == 0:
            role = "HOST"
            is_host = True
        
        jam_members_collection.insert_one({
            "room_id": room_id,
            "user_id": user_id,
            "role": role,
            "joined_at": time.time(),
            "online": True
        })

    participant_info = {
        "user_id": user_id,
        "role": role,
        "online": True
    }
    room.connections[websocket] = participant_info

    if is_host or role == "GUEST":
        session_state = {
            "event": "session_state",
            "session": {
                "room_id": room_id,
                "playback_state": "PLAYING" if room.is_playing else "PAUSED",
                "position_ms": room.position_ms,
                "started_at_ms": room.started_at_ms,
                "queue": room.queue,
                "current_song": room.current_song,
                "participants": {info["user_id"]: info for info in room.connections.values() if info["role"] != "PENDING"}
            }
        }
        await websocket.send_text(json.dumps(session_state))
        
        if role == "GUEST":
            await room.broadcast({
                "event": "user_joined",
                "participants": {info["user_id"]: info for info in room.connections.values() if info["role"] != "PENDING"}
            }, sender=websocket)
    else:
        await websocket.send_text(json.dumps({"event": "pending_approval"}))
        for conn, info in room.connections.items():
            if info["role"] == "HOST":
                await conn.send_text(json.dumps({
                    "event": "join_request",
                    "user_id": user_id
                }))

    try:
        while True:
            data = await websocket.receive_text()
            msg = json.loads(data)
            event = msg.get("event")
            
            if event == "approve_join":
                target_user_id = msg.get("target_user_id")
                jam_members_collection.update_one(
                    {"room_id": room_id, "user_id": target_user_id},
                    {"$set": {"role": "GUEST"}}
                )
                target_ws = None
                for conn, info in room.connections.items():
                    if info["user_id"] == target_user_id:
                        info["role"] = "GUEST"
                        target_ws = conn
                        break
                
                if target_ws:
                    session_state = {
                        "event": "session_state",
                        "session": {
                            "room_id": room_id,
                            "playback_state": "PLAYING" if room.is_playing else "PAUSED",
                            "position_ms": room.position_ms,
                            "started_at_ms": room.started_at_ms,
                            "queue": room.queue,
                            "current_song": room.current_song,
                            "participants": {info["user_id"]: info for info in room.connections.values() if info["role"] != "PENDING"}
                        }
                    }
                    await target_ws.send_text(json.dumps(session_state))
                    await room.broadcast({
                        "event": "user_joined",
                        "participants": {info["user_id"]: info for info in room.connections.values() if info["role"] != "PENDING"}
                    }, sender=target_ws)
                    
            elif event == "reject_join":
                target_user_id = msg.get("target_user_id")
                jam_members_collection.delete_one({"room_id": room_id, "user_id": target_user_id})
                target_ws = None
                for conn, info in room.connections.items():
                    if info["user_id"] == target_user_id:
                        target_ws = conn
                        break
                if target_ws:
                    await target_ws.send_text(json.dumps({"event": "join_rejected"}))
                    
            elif event == "remove_participant":
                target_user_id = msg.get("target_user_id")
                jam_members_collection.delete_one({"room_id": room_id, "user_id": target_user_id})
                target_ws = None
                for conn, info in room.connections.items():
                    if info["user_id"] == target_user_id:
                        target_ws = conn
                        break
                if target_ws:
                    await target_ws.send_text(json.dumps({"event": "removed"}))

            if room.connections[websocket]["role"] == "PENDING":
                continue

            if event == "play":
                room.is_playing = True
                room.position_ms = msg.get("position_ms", room.position_ms)
                room.started_at_ms = int(time.time() * 1000)
                room.sync_to_db()
                await room.broadcast({
                    "event": "playback_synced",
                    "state": "PLAYING",
                    "position_ms": room.position_ms,
                    "started_at_ms": room.started_at_ms
                }, sender=websocket)
            
            elif event == "pause":
                room.is_playing = False
                room.position_ms = msg.get("position_ms", room.position_ms)
                room.started_at_ms = 0
                room.sync_to_db()
                await room.broadcast({
                    "event": "playback_synced",
                    "state": "PAUSED",
                    "position_ms": room.position_ms,
                    "started_at_ms": 0
                }, sender=websocket)
                
            elif event == "playback_sync":
                state = msg.get("state")
                room.is_playing = state == "PLAYING"
                room.position_ms = msg.get("position_ms", room.position_ms)
                room.started_at_ms = int(time.time() * 1000) if room.is_playing else 0
                room.sync_to_db()
                await room.broadcast({
                    "event": "playback_synced",
                    "state": "PLAYING" if room.is_playing else "PAUSED",
                    "position_ms": room.position_ms,
                    "started_at_ms": room.started_at_ms
                }, sender=websocket)
                
            elif event == "play_song":
                room.current_song = msg.get("song")
                room.sync_to_db()
                await room.broadcast(msg, sender=websocket)

            elif event == "add_song":
                song = msg.get("song")
                if song:
                    room.queue.append(song)
                    room.sync_to_db()
                    await room.broadcast({
                        "event": "queue_updated",
                        "queue": room.queue
                    })
                    await websocket.send_text(json.dumps({
                        "event": "queue_updated",
                        "queue": room.queue
                    }))
                    
            elif event == "chat":
                chat_msg = {
                    "room_id": room_id,
                    "sender_id": user_id,
                    "text": msg.get("text", ""),
                    "timestamp": time.time()
                }
                jam_messages_collection.insert_one(chat_msg)
                
                chat_payload = {
                    "event": "chat_received",
                    "message": {
                        "sender_id": user_id,
                        "sender": username,
                        "text": msg.get("text", "")
                    }
                }
                await websocket.send_text(json.dumps(chat_payload))
                await room.broadcast(chat_payload, sender=websocket)

    except WebSocketDisconnect:
        if websocket in room.connections:
            del room.connections[websocket]
        
        if not room.connections:
            if room_id in jam_rooms:
                del jam_rooms[room_id]
        else:
            await room.broadcast({
                "event": "user_left",
                "participants": {info["user_id"]: info for info in room.connections.values()}
            })

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

# ------------------- JamViewModel – Player Listener Hook -------------------
# // Add a Flow to receive sync events from the backend and forward them to the local player.
# private val _incomingSync = MutableStateFlow<Pair<Boolean, Long>?>(null)
# val incomingSync: StateFlow<Pair<Boolean, Long>?> = _incomingSync.asStateFlow()
#
# // Call this from the UI when a sync message arrives via the WebSocket listener.
# fun handleIncomingSync(isPlaying: Boolean, positionMs: Long) {
#     _incomingSync.value = Pair(isPlaying, positionMs)
# }
#
# // Existing onMessage implementation should forward PLAYBACK_SYNC messages:
# // val json = JSONObject(text)
# // if (json.getString("type") == "PLAYBACK_SYNC") {
# //     handleIncomingSync(json.getBoolean("isPlaying"), json.getLong("positionMs"))
# // }

class HomeModule(BaseModel):
    title: str
    items: List[Playlist]

class HomeResponse(BaseModel):
    modules: List[HomeModule]

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
            server.login(EMAIL_SENDER, EMAIL_PASSWORD)
            server.send_message(msg)
            server.quit()
        except Exception as e:
            print("Failed to send email:", e)
            
    threading.Thread(target=send).start()

# Auth Routes
@app.post("/api/v1/auth/register")
def register(user: RegisterUser):
    # Check for existing email; also handle race condition via unique index
    if users_collection.find_one({"email": {"$regex": f"^{user.email.strip()}$", "$options": "i"}}):
        raise HTTPException(status_code=400, detail="Email already registered")
    hashed_pw = bcrypt.hashpw(user.password.encode('utf-8'), bcrypt.gensalt()).decode('utf-8')
    try:
        result = users_collection.insert_one({"username": user.username, "email": user.email.strip(), "passwordHash": hashed_pw})
    except Exception as e:
        # DuplicateKeyError or other DB errors
        raise HTTPException(status_code=400, detail="Email already registered")
    user_id = str(result.inserted_id)
    token = create_access_token({"sub": user_id})
    send_email_async(user.email, "Welcome to Pulse Music!", "Thanks for signing up to Pulse Music! We're excited to have you.")
    return {"token": token, "username": user.username, "email": user.email, "profilePic": ""}

@app.post("/api/v1/auth/login")
def login(user: AuthUser):
    db_user = users_collection.find_one({"email": {"$regex": f"^{user.email.strip()}$", "$options": "i"}})
    if not db_user or not bcrypt.checkpw(user.password.encode('utf-8'), db_user['passwordHash'].encode('utf-8')):
        raise HTTPException(status_code=400, detail="Incorrect email or password")
    
    token = create_access_token({"sub": str(db_user['_id'])})
    send_email_async(user.email, "New Login Alert", "We noticed a new login to your Pulse Music account.")
    username = db_user.get("username", user.email.split("@")[0])
    return {"token": token, "username": username, "email": user.email, "profilePic": db_user.get("profilePic", "")}

class SocialLoginRequest(BaseModel):
    token: str

@app.post("/api/v1/auth/google")
def google_login(req: SocialLoginRequest):
    try:
        response = requests.get(f"https://oauth2.googleapis.com/tokeninfo?id_token={req.token}")
        if response.status_code != 200:
            raise HTTPException(status_code=401, detail="Invalid Google token")
        
        token_info = response.json()
        email = token_info.get("email")
        if not email:
            raise HTTPException(status_code=401, detail="Invalid Google token payload")
        
        db_user = users_collection.find_one({"email": email})
        if not db_user:
            username = token_info.get("name", email.split("@")[0])
            profile_pic = token_info.get("picture", "")
            result = users_collection.insert_one({"username": username, "email": email, "passwordHash": "", "profilePic": profile_pic})
            user_id = str(result.inserted_id)
        else:
            username = db_user.get("username", email.split("@")[0])
            user_id = str(db_user["_id"])
            profile_pic = db_user.get("profilePic", "")
            if not profile_pic:
                profile_pic = token_info.get("picture", "")
                users_collection.update_one({"_id": db_user["_id"]}, {"$set": {"profilePic": profile_pic}})
            
        token = create_access_token({"sub": user_id})
        return {"token": token, "username": username, "email": email, "profilePic": profile_pic}
    except Exception as e:
        raise HTTPException(status_code=401, detail=f"Google verification failed: {str(e)}")

@app.post("/api/v1/auth/facebook")
def facebook_login(req: SocialLoginRequest):
    try:
        response = requests.get(f"https://graph.facebook.com/me?fields=id,name,email&access_token={req.token}")
        if response.status_code != 200:
            raise HTTPException(status_code=401, detail="Invalid Facebook token")
            
        token_info = response.json()
        email = token_info.get("email")
        if not email:
            email = f"{token_info.get('id')}@facebook.com"
            
        db_user = users_collection.find_one({"email": email})
        if not db_user:
            username = token_info.get("name", email.split("@")[0])
            result = users_collection.insert_one({"username": username, "email": email, "passwordHash": ""})
            user_id = str(result.inserted_id)
            profile_pic = ""
        else:
            username = db_user.get("username", email.split("@")[0])
            user_id = str(db_user["_id"])
            profile_pic = db_user.get("profilePic", "")
            
        token = create_access_token({"sub": user_id})
        return {"token": token, "username": username, "email": email, "profilePic": profile_pic}
    except Exception as e:
        raise HTTPException(status_code=401, detail=f"Facebook verification failed: {str(e)}")

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
    db_user = users_collection.find_one({"email": {"$regex": f"^{req.email.strip()}$", "$options": "i"}})
    
    if not db_user:
        return {"status": "error", "message": "user doesn't exist"}
    
    code = str(random.randint(100000, 999999))
    reset_codes_collection.update_one(
        {"email": req.email.strip().lower()},
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
    record = reset_codes_collection.find_one({"email": req.email.strip().lower(), "code": req.code})
    if not record:
        return {"status": "error", "message": "Invalid code"}
    
    created = record.get("created_at", datetime.utcnow())
    if (datetime.utcnow() - created).total_seconds() > 600:
        reset_codes_collection.delete_one({"email": req.email.strip().lower()})
        return {"status": "error", "message": "Code expired"}
    
    return {"status": "success", "message": "Code verified"}

@app.post("/api/v1/auth/reset-password")
def reset_password(req: ResetPasswordRequest):
    record = reset_codes_collection.find_one({"email": req.email.strip().lower(), "code": req.code})
    if not record:
        return {"status": "error", "message": "Invalid code"}
    
    hashed_pw = bcrypt.hashpw(req.new_password.encode('utf-8'), bcrypt.gensalt()).decode('utf-8')
    users_collection.update_one({"email": {"$regex": f"^{req.email.strip()}$", "$options": "i"}}, {"$set": {"passwordHash": hashed_pw}})
    reset_codes_collection.delete_one({"email": req.email.strip().lower()})
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

@app.delete("/api/v1/user/searches/{query}")
def remove_recent_search(query: str, user_id: str = Depends(get_current_user)):
    try:
        recent_searches_collection.delete_many({"user_id": user_id, "query": query})
        return {"status": "success"}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

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

@app.delete("/api/v1/user/recent-songs/{song_id}")
def remove_recent_song(song_id: str, user_id: str = Depends(get_current_user)):
    try:
        recently_played_collection.delete_one({"user_id": user_id, "song_id": song_id})
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
                "album": song.album if hasattr(song, "album") else song.get("album", ""),
                "albumArt": song.albumArt if hasattr(song, "albumArt") else song.get("albumArt", ""),
                "durationMs": song.durationMs if hasattr(song, "durationMs") else song.get("durationMs", 0),
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
        subtitle = track.get("subtitle", "").replace("\u00a0", " ").strip()
        artist = subtitle.split(",")[0].strip() if subtitle else ""
        duration_ms = int(track.get("duration", track.get("duration_ms", 0))) 
        if title:
            tracks_to_search.append((title, artist, duration_ms))
            
    matched_songs = []
    with concurrent.futures.ThreadPoolExecutor(max_workers=10) as executor:
        futures = {}
        for name, artist, duration_ms in tracks_to_search:
            futures[executor.submit(search_jiosaavn, name, artist)] = duration_ms
            
        for future in concurrent.futures.as_completed(futures):
            res = future.result()
            if res:
                # Override duration with Spotify's duration if JioSaavn returned 0
                spotify_duration = futures[future]
                if not res.get("durationMs") and spotify_duration:
                    res["durationMs"] = spotify_duration
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

@app.put("/api/v1/user/playlists/{playlist_id}")
def rename_playlist(playlist_id: str, name: str, user_id: str = Depends(get_current_user)):
    playlists_collection.update_one(
        {"_id": ObjectId(playlist_id), "user_id": user_id},
        {"$set": {"name": name}}
    )
    return {"status": "success"}

@app.post("/api/v1/user/profile-pic")
def upload_profile_pic(file: UploadFile = File(...), user_id: str = Depends(get_current_user)):
    cloud_name = os.getenv("CLOUDINARY_CLOUD_NAME")
    api_key = os.getenv("CLOUDINARY_API_KEY")
    api_secret = os.getenv("CLOUDINARY_API_SECRET")
    
    if not cloud_name or not api_key or not api_secret:
        raise HTTPException(
            status_code=500, 
            detail="Cloudinary credentials are not configured in backend environment"
        )
        
    try:
        import time
        import hashlib
        
        file_content = file.file.read()
        
        timestamp = int(time.time())
        params_to_sign = f"timestamp={timestamp}"
        signature_str = f"{params_to_sign}{api_secret}"
        signature = hashlib.sha1(signature_str.encode('utf-8')).hexdigest()
        
        files = {"file": (file.filename, file_content, file.content_type)}
        data = {
            "api_key": api_key,
            "timestamp": timestamp,
            "signature": signature
        }
        
        response = requests.post(
            f"https://api.cloudinary.com/v1_1/{cloud_name}/image/upload",
            files=files,
            data=data
        )
        
        if response.status_code != 200:
            raise HTTPException(status_code=response.status_code, detail=f"Cloudinary upload failed: {response.text}")
            
        res_data = response.json()
        secure_url = res_data.get("secure_url")
        
        if not secure_url:
            raise HTTPException(status_code=500, detail="Did not receive secure_url from Cloudinary response")
            
        users_collection.update_one(
            {"_id": ObjectId(user_id)},
            {"$set": {"profilePic": secure_url}}
        )
        
        return {"status": "success", "profilePic": secure_url}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


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
                    cover = filter_jio_image(images[-1]["url"] if images else "")
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
                
                artists_results = data_block.get("artists", {}).get("results", [])
                
                # Prepend topQuery artist if exists
                top_query_results = data_block.get("topQuery", {}).get("results", [])
                for tq in top_query_results:
                    if tq.get("type") == "artist":
                        # Check if already in list to avoid duplicates
                        if not any(a.get("id") == tq.get("id") for a in artists_results):
                            artists_results.insert(0, tq)
                
                artists = []
                for a in artists_results:
                    images = a.get("image", [])
                    cover = filter_jio_image(images[-1]["url"] if images else "")
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
                    cover = filter_jio_image(images[-1]["url"] if images else "")
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
                    cover = filter_jio_image(images[-1]["url"] if images else "")
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
    seen_ids = set()
    songs = []
    queries = ["top hits 2026", "best bollywood 2025", "new hindi songs 2026", "popular songs india"]
    for q in queries:
        try:
            batch = search_songs(q, "song").songs
            for s in batch:
                if s.id not in seen_ids:
                    seen_ids.add(s.id)
                    songs.append(s)
            if len(songs) >= 20:
                break
        except Exception:
            pass
    return songs[:20]

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
    def is_similar(t1, t2):
        s1 = re.sub(r'[^a-zA-Z0-9]', '', t1.lower())
        s2 = re.sub(r'[^a-zA-Z0-9]', '', t2.lower())
        return s1 in s2 or s2 in s1

    # // ------------------- Deep Link Handling for Jam Sessions -------------------
    # // In AndroidManifest.xml you would add an intent filter like:
    # // <action android:name="android.intent.action.VIEW" />
    # // <category android:name="android.intent.category.DEFAULT" />
    # // <category android:name="android.intent.category.BROWSABLE" />
    # // <data android:scheme="pulse" android:host="jam" />
    # // Then in MainActivity's onCreate you can capture the URI:
    # // val data = intent?.data
    # // if (data?.scheme == "pulse" && data.host == "jam") {
    # //     val roomId = data.getQueryParameter("room") ?: ""
    # //     // Navigate to JamScreen with the roomId (using NavHost)
    # // }

    try:
        if sp:
            try:
                sp_res = sp.search(q=f"track:{track} artist:{artist}", type="track", limit=1)
                if sp_res and sp_res['tracks']['items']:
                    seed_track = sp_res['tracks']['items'][0]
                    t_id = seed_track['id']
                    a_id = seed_track['artists'][0]['id']
                    
                    recs = sp.recommendations(seed_tracks=[t_id], seed_artists=[a_id], limit=15)
                    
                    spotify_queries = []
                    for t in recs['tracks']:
                        t_name = t['name']
                        t_artist = t['artists'][0]['name']
                        if not is_similar(t_name, track):
                            spotify_queries.append(f"{t_name} {t_artist}")
                            
                    songs = []
                    def fetch_jiosaavn(q):
                        try:
                            res = requests.get("https://music-api.albatross0071.workers.dev/api/search/songs", params={"query": q}, timeout=5)
                            if res.status_code == 200:
                                data = res.json()
                                if data.get("success") and data.get("data", {}).get("results"):
                                    return data["data"]["results"][0]
                        except:
                            pass
                        return None

                    with concurrent.futures.ThreadPoolExecutor(max_workers=5) as executor:
                        results = executor.map(fetch_jiosaavn, spotify_queries)
                        
                    for item in results:
                        if item:
                            title = item.get("name", "") or item.get("title", "")
                            if is_similar(title, track):
                                continue
                            images = item.get("image", [])
                            cover = filter_jio_image(images[-1]["url"] if images else "")
                            p_artists = item.get("artists", {}).get("primary", []) if "artists" in item else [{"name": item.get("subtitle", "")}]
                            artist_names = ", ".join([a["name"] for a in p_artists]) if p_artists else "Unknown"
                            songs.append(Song(
                                id=item["id"],
                                title=title,
                                artist=artist_names,
                                album=item.get("album", {}).get("name", "") if isinstance(item.get("album"), dict) else item.get("album", ""),
                                albumArt=cover,
                                durationMs=int(item.get("duration", 0)) * 1000,
                                source="jiosaavn"
                            ))
                            if len(songs) >= 20:
                                break
                    if songs:
                        return songs
            except Exception as e:
                print("Spotify recs error:", e)

        # Fallback to JioSaavn
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
                                title = item.get("name", "")
                                if is_similar(title, track):
                                    continue
                                images = item.get("image", [])
                                cover = filter_jio_image(images[-1]["url"] if images else "")
                                p_artists = item.get("artists", {}).get("primary", [])
                                artist_names = ", ".join([a["name"] for a in p_artists]) if p_artists else "Unknown"
                                songs.append(Song(
                                    id=item["id"],
                                    title=title,
                                    artist=artist_names,
                                    album=item.get("album", {}).get("name", ""),
                                    albumArt=cover,
                                    durationMs=int(item.get("duration", 0)) * 1000,
                                    source="jiosaavn"
                                ))
                                if len(songs) >= 20:
                                    break
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
            artist_image = filter_jio_image(images[-1].get("url", artist_image))
            
        for alb in data.get("topAlbums", []):
            alb_images = alb.get("image", [])
            alb_cover = filter_jio_image(alb_images[-1]["url"] if isinstance(alb_images, list) and alb_images else "")
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
                sim_cover = filter_jio_image(sim_images[-1]["url"] if isinstance(sim_images, list) and sim_images else "")
                if sim_id and sim_name and str(sim_id) != sim_name:
                    similar_artists.append(Artist(id=str(sim_id), name=sim_name, image=sim_cover))
                    
        bio_data = data.get("bio")
        if isinstance(bio_data, list) and len(bio_data) > 0:
            artist_bio = bio_data[0].get("text", "")
        elif isinstance(bio_data, str):
            artist_bio = bio_data
            
        for ts in data.get("topSongs", []):
            if isinstance(ts, dict):
                ts_images = ts.get("image", [])
                ts_cover = filter_jio_image(ts_images[-1]["url"] if isinstance(ts_images, list) and ts_images else "")
                p_artists = ts.get("artists", {}).get("primary", []) if "artists" in ts else []
                ts_artist_names = ", ".join([a["name"] for a in p_artists]) if p_artists else artist_name
                top_tracks.append(Song(
                    id=ts["id"],
                    title=ts.get("name", "") or ts.get("title", ""),
                    artist=ts_artist_names,
                    album=ts.get("album", {}).get("name", "") if isinstance(ts.get("album"), dict) else ts.get("album", ""),
                    albumArt=ts_cover,
                    durationMs=int(ts.get("duration", 0)) * 1000,
                    source="jiosaavn"
                ))

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
                        artist_image = filter_jio_image(images[-1]["url"] if images else "")
                        
                        try:
                            art_details_res = requests.get(f"https://music-api.albatross0071.workers.dev/api/artists?id={artist_id}", timeout=5)
                            if art_details_res.status_code == 200 and art_details_res.json().get("success"):
                                parse_artist_data(art_details_res.json().get("data", {}))
                        except Exception as e:
                            print("Artist search ID fetch error:", e)
        except Exception as e:
            print("Artist search error:", e)

    if not top_tracks:
        try:
            top_tracks = search_songs(artist_name).songs[:20]
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
            alb_data = data.get("data", {})

        # If STILL not found, check if the ID is a SONG ID and get its album ID
        if not data.get("success") or not alb_data or not alb_data.get("id"):
            song_res = requests.get(f"https://music-api.albatross0071.workers.dev/api/songs?ids={id}", timeout=5)
            song_data = song_res.json() if song_res.status_code == 200 else {}
            if song_data.get("success") and song_data.get("data"):
                # Grab the exact album ID from the song
                song_obj = song_data["data"][0]
                if "album" in song_obj and "id" in song_obj["album"]:
                    real_album_id = song_obj["album"]["id"]
                    res = requests.get(f"https://music-api.albatross0071.workers.dev/api/albums?id={real_album_id}", timeout=5)
                    data = res.json() if res.status_code == 200 else {}
                else:
                    # Fallback to searching by album name if song has no album ID
                    search_res = requests.get(f"https://music-api.albatross0071.workers.dev/api/search/albums?query={id}", timeout=5)
                    search_data = search_res.json() if search_res.status_code == 200 else {}
                    if search_data.get("success") and search_data.get("data", {}).get("results"):
                        real_id = search_data["data"]["results"][0]["id"]
                        res = requests.get(f"https://music-api.albatross0071.workers.dev/api/albums?id={real_id}", timeout=5)
                        data = res.json() if res.status_code == 200 else {}
        # If STILL not found, try searching the ID as an album name directly
        final_alb_data = data.get("data", {})
        if not data.get("success") or not final_alb_data or not final_alb_data.get("id"):
            search_res = requests.get(f"https://music-api.albatross0071.workers.dev/api/search/albums?query={id}", timeout=5)
            search_data = search_res.json() if search_res.status_code == 200 else {}
            if search_data.get("success") and search_data.get("data", {}).get("results"):
                real_id = search_data["data"]["results"][0]["id"]
                res = requests.get(f"https://music-api.albatross0071.workers.dev/api/albums?id={real_id}", timeout=5)
                data = res.json() if res.status_code == 200 else {}

        if data.get("success") and ("songs" in data.get("data", {}) or data.get("data", {}).get("id")):
            alb = data.get("data", {})
            
            # parse songs
            songs = []
            for item in alb.get("songs", []):
                images = item.get("image", [])
                cover = filter_jio_image(images[-1]["url"] if images else "")
                
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
            cover = filter_jio_image(images[-1]["url"] if images else "")
            
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
        f_ishq = executor.submit(fetch_playlists, "romance hindi")
        f_chill = executor.submit(fetch_playlists, "chill hindi")
        f_filmy = executor.submit(fetch_playlists, "filmy tadka")
        f_hiphop = executor.submit(fetch_playlists, "desi hip hop")
        f_community = executor.submit(fetch_playlists, "trending community")
        f_new = executor.submit(fetch_playlists, "new releases")
        f_mood = executor.submit(fetch_playlists, "mood hindi")
        f_artist_station = executor.submit(fetch_playlists, "best of artists")
        f_safar = executor.submit(fetch_playlists, "safarnama")
        f_fresh = executor.submit(fetch_playlists, "fresh hits")
        f_genre = executor.submit(fetch_playlists, "top genres")
        f_pop_hindi = executor.submit(fetch_playlists, "pop hindi")
        f_editors = executor.submit(fetch_playlists, "editors choice hindi")
        f_charts = executor.submit(fetch_playlists, "top charts")

        home_cache = HomeResponse(
            modules=[
                HomeModule(title="Ishq Wala Love", items=f_ishq.result()),
                HomeModule(title="Aao Chill Kare", items=f_chill.result()),
                HomeModule(title="Filmy Tadka", items=f_filmy.result()),
                HomeModule(title="Ek Number Hip Hop", items=f_hiphop.result()),
                HomeModule(title="From Community", items=f_community.result()),
                HomeModule(title="New Releases", items=f_new.result()),
                HomeModule(title="Made For Your Mood", items=f_mood.result()),
                HomeModule(title="Recommended Artist Station", items=f_artist_station.result()),
                HomeModule(title="Safarnama", items=f_safar.result()),
                HomeModule(title="Fresh Hits", items=f_fresh.result()),
                HomeModule(title="Top Genre & Moods", items=f_genre.result()),
                HomeModule(title="New Releases Pop-Hindi", items=f_pop_hindi.result()),
                HomeModule(title="Editor's Choice", items=f_editors.result()),
                HomeModule(title="Top Charts", items=f_charts.result())
            ]
        )
        home_cache_time = time.time()
        
    return home_cache

@app.head("/api/v1/health")
@app.get("/api/v1/health")
def health_v1():
    return {"status": "ok", "provider": "jiosaavn"}

if __name__ == "__main__":
    port = int(os.environ.get("PORT", 8080))
    uvicorn.run(app, host="0.0.0.0", port=port)
