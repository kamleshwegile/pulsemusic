package com.pulse.providers

import com.pulse.models.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.*
import org.slf4j.LoggerFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class ListenFreeProvider : MusicProvider {
    override val name = "ListenFree"
    override val priority = 0 // Highest priority so it is queried first
    private val logger = LoggerFactory.getLogger(ListenFreeProvider::class.java)

    override suspend fun search(query: String): List<Song> {
        return try {
            val encodedQuery = java.net.URLEncoder.encode(query, "UTF-8")
            val url = "https://www.jiosaavn.com/api.php?__call=search.getResults&q=$encodedQuery&n=20&p=1&_format=json&_marker=0&ctx=web6dot0"
            val response = defaultHttpClient.get(url)
            if (response.status.value != 200) return emptyList()
            
            val text = response.bodyAsText()
            val json = Json.parseToJsonElement(text).jsonObject
            val results = json["results"]?.jsonArray ?: return emptyList()
            
            results.mapNotNull { item ->
                val songObj = item.jsonObject
                val id = songObj["id"]?.jsonPrimitive?.content ?: return@mapNotNull null
                val titleStr = songObj["song"]?.jsonPrimitive?.content ?: songObj["title"]?.jsonPrimitive?.content ?: ""
                val title = titleStr.replace("&quot;", "\"")
                
                val artistStr = songObj["primary_artists"]?.jsonPrimitive?.content 
                    ?: songObj["singers"]?.jsonPrimitive?.content 
                    ?: "Unknown Artist"
                val artist = artistStr.replace("&quot;", "\"")
                
                val imageStr = songObj["image"]?.jsonPrimitive?.content ?: ""
                val image = imageStr.replace(Regex("\\d+x\\d+"), "500x500")
                
                Song(
                    id = id,
                    title = title,
                    artist = artist,
                    album = songObj["album"]?.jsonPrimitive?.content?.replace("&quot;", "\"") ?: "",
                    albumArt = image,
                    durationMs = songObj["duration"]?.jsonPrimitive?.longOrNull?.times(1000),
                    source = "ListenFree"
                )
            }
        } catch (e: Exception) {
            logger.error("ListenFree search failed for $query", e)
            emptyList()
        }
    }
    
    override suspend fun searchAll(query: String): SearchResponse? = coroutineScope {
        val encodedQuery = java.net.URLEncoder.encode(query, "UTF-8")
        
        val songsDeferred = async { search(query) }
        
        val artistsDeferred = async {
            val artists = mutableListOf<Artist>()
            try {
                val response = defaultHttpClient.get("https://www.jiosaavn.com/api.php?__call=search.getArtistResults&q=$encodedQuery&n=10&p=1&_format=json&_marker=0&ctx=web6dot0")
                if (response.status.value == 200) {
                    val json = Json.parseToJsonElement(response.bodyAsText()).jsonObject
                    json["results"]?.jsonArray?.forEach {
                        val obj = it.jsonObject
                        artists.add(Artist(
                            id = obj["id"]?.jsonPrimitive?.content ?: "",
                            name = obj["title"]?.jsonPrimitive?.content ?: obj["name"]?.jsonPrimitive?.content ?: "",
                            image = obj["image"]?.jsonPrimitive?.content?.replace(Regex("\\d+x\\d+"), "500x500")
                        ))
                    }
                }
            } catch (e: Exception) {
                logger.error("ListenFree searchAll artist failed", e)
            }
            artists
        }
        
        val albumsDeferred = async {
            val albums = mutableListOf<Album>()
            try {
                val response = defaultHttpClient.get("https://www.jiosaavn.com/api.php?__call=search.getAlbumResults&q=$encodedQuery&n=10&p=1&_format=json&_marker=0&ctx=web6dot0")
                if (response.status.value == 200) {
                    val json = Json.parseToJsonElement(response.bodyAsText()).jsonObject
                    json["results"]?.jsonArray?.forEach {
                        val obj = it.jsonObject
                        albums.add(Album(
                            id = obj["albumid"]?.jsonPrimitive?.content ?: obj["id"]?.jsonPrimitive?.content ?: "",
                            title = obj["title"]?.jsonPrimitive?.content ?: "",
                            artist = obj["music"]?.jsonPrimitive?.content ?: "",
                            coverArt = obj["image"]?.jsonPrimitive?.content?.replace(Regex("\\d+x\\d+"), "500x500")
                        ))
                    }
                }
            } catch (e: Exception) {
                logger.error("ListenFree searchAll album failed", e)
            }
            albums
        }
        
        val playlistsDeferred = async {
            val playlists = mutableListOf<Playlist>()
            try {
                val response = defaultHttpClient.get("https://www.jiosaavn.com/api.php?__call=search.getPlaylistResults&q=$encodedQuery&n=10&p=1&_format=json&_marker=0&ctx=web6dot0")
                if (response.status.value == 200) {
                    val json = Json.parseToJsonElement(response.bodyAsText()).jsonObject
                    json["results"]?.jsonArray?.forEach {
                        val obj = it.jsonObject
                        playlists.add(Playlist(
                            id = obj["listid"]?.jsonPrimitive?.content ?: obj["id"]?.jsonPrimitive?.content ?: "",
                            title = obj["listname"]?.jsonPrimitive?.content ?: obj["title"]?.jsonPrimitive?.content ?: "",
                            image = obj["image"]?.jsonPrimitive?.content?.replace(Regex("\\d+x\\d+"), "500x500"),
                            source = "ListenFree"
                        ))
                    }
                }
            } catch (e: Exception) {
                logger.error("ListenFree searchAll playlist failed", e)
            }
            playlists
        }
        
        val songs = songsDeferred.await()
        val artists = artistsDeferred.await()
        val albums = albumsDeferred.await()
        val playlists = playlistsDeferred.await()
        
        if (songs.isEmpty() && artists.isEmpty() && albums.isEmpty() && playlists.isEmpty()) return@coroutineScope null
        
        SearchResponse(
            songs = songs,
            artists = artists,
            albums = albums,
            playlists = playlists
        )
    }

    override suspend fun getArtist(id: String): Artist? {
        return try {
            val searchResponse = defaultHttpClient.get("https://www.jiosaavn.com/api.php") {
                parameter("__call", "search.getArtistResults")
                parameter("q", id)
                parameter("_format", "json")
                parameter("_marker", "0")
                parameter("ctx", "web6dot0")
            }
            val searchJson = if (searchResponse.status.value == 200) Json.parseToJsonElement(searchResponse.bodyAsText()).jsonObject else null
            val firstArtist = searchJson?.get("results")?.jsonArray?.firstOrNull()?.jsonObject
            val imageUrl = firstArtist?.get("image")?.jsonPrimitive?.content?.replace(Regex("\\d+x\\d+"), "500x500")
            val artistId = firstArtist?.get("id")?.jsonPrimitive?.content ?: "unknown"
            
            val topSongs = search(id).take(15)
            
            val albumSearchResponse = defaultHttpClient.get("https://www.jiosaavn.com/api.php") {
                parameter("__call", "search.getAlbumResults")
                parameter("q", id)
                parameter("_format", "json")
                parameter("_marker", "0")
                parameter("ctx", "web6dot0")
            }
            val albums = if (albumSearchResponse.status.value == 200) {
                val albumJson = Json.parseToJsonElement(albumSearchResponse.bodyAsText()).jsonObject
                albumJson["results"]?.jsonArray?.mapNotNull { item ->
                    val albumObj = item.jsonObject
                    val title = albumObj["title"]?.jsonPrimitive?.content ?: return@mapNotNull null
                    Album(
                        id = albumObj["albumid"]?.jsonPrimitive?.content ?: albumObj["id"]?.jsonPrimitive?.content ?: title,
                        title = title.replace("&quot;", "\""),
                        artist = id,
                        coverArt = albumObj["image"]?.jsonPrimitive?.content?.replace(Regex("\\d+x\\d+"), "500x500"),
                        year = albumObj["year"]?.jsonPrimitive?.intOrNull
                    )
                } ?: emptyList()
            } else emptyList()
            
            Artist(
                id = artistId,
                name = id,
                image = imageUrl,
                topTracks = topSongs,
                albums = albums
            )
        } catch (e: Exception) {
            logger.error("ListenFree getArtist failed for id: $id", e)
            null
        }
    }

    private suspend fun fetchPlaylistAsAlbum(listid: String): Album? {
        try {
            val response = defaultHttpClient.get("https://www.jiosaavn.com/api.php") {
                parameter("__call", "playlist.getDetails")
                parameter("listid", listid)
                parameter("_format", "json")
                parameter("_marker", "0")
                parameter("ctx", "web6dot0")
            }
            if (response.status.value != 200) return null
            val json = Json.parseToJsonElement(response.bodyAsText()).jsonObject
            
            val title = json["listname"]?.jsonPrimitive?.content ?: json["title"]?.jsonPrimitive?.content ?: return null
            val coverArt = json["image"]?.jsonPrimitive?.content?.replace(Regex("\\d+x\\d+"), "500x500") ?: ""
            val fanCount = json["fan_count"]?.jsonPrimitive?.content ?: "0"
            val artistName = "Playlist • $fanCount Fans"
            
            val songsArray = json["songs"]?.jsonArray ?: json["list"]?.jsonArray
            val tracks = songsArray?.mapNotNull { item ->
                val songObj = item.jsonObject
                val songTitle = songObj["song"]?.jsonPrimitive?.content ?: songObj["title"]?.jsonPrimitive?.content ?: return@mapNotNull null
                val songImageUrl = songObj["image"]?.jsonPrimitive?.content?.replace(Regex("\\d+x\\d+"), "500x500") ?: coverArt
                
                Song(
                    id = songObj["id"]?.jsonPrimitive?.content ?: "",
                    title = songTitle.replace("&quot;", "\""),
                    artist = (songObj["primary_artists"]?.jsonPrimitive?.content ?: songObj["singers"]?.jsonPrimitive?.content ?: "").replace("&quot;", "\""),
                    album = title,
                    albumArt = songImageUrl,
                    source = this.name,
                    durationMs = songObj["duration"]?.jsonPrimitive?.content?.toLongOrNull()?.times(1000L)
                )
            } ?: emptyList()
            
            return Album(
                id = listid,
                title = title,
                artist = artistName,
                coverArt = coverArt,
                year = null,
                tracks = tracks
            )
        } catch (e: Exception) {
            return null
        }
    }

    override suspend fun getAlbum(id: String): Album? {
        return try {
            var numericId = id
            val isNumeric = id.toLongOrNull() != null
            
            if (!isNumeric) {
                val searchResponse = defaultHttpClient.get("https://www.jiosaavn.com/api.php") {
                    parameter("__call", "search.getAlbumResults")
                    parameter("q", id)
                    parameter("_format", "json")
                    parameter("_marker", "0")
                    parameter("ctx", "web6dot0")
                }
                if (searchResponse.status.value == 200) {
                    val searchJson = Json.parseToJsonElement(searchResponse.bodyAsText()).jsonObject
                    val results = searchJson["results"]?.jsonArray
                    if (!results.isNullOrEmpty()) {
                        val firstResult = results[0].jsonObject
                        numericId = firstResult["albumid"]?.jsonPrimitive?.content ?: firstResult["id"]?.jsonPrimitive?.content ?: id
                    }
                }
            }

            // Try to fetch as Album first
            val detailResponse = defaultHttpClient.get("https://www.jiosaavn.com/api.php") {
                parameter("__call", "content.getAlbumDetails")
                parameter("albumid", numericId)
                parameter("_format", "json")
                parameter("_marker", "0")
                parameter("ctx", "web6dot0")
            }
            
            val detailJson = if (detailResponse.status.value == 200) {
                try {
                    Json.parseToJsonElement(detailResponse.bodyAsText()).jsonObject
                } catch (e: Exception) { null }
            } else null
            
            if (detailJson == null || (!detailJson.containsKey("title") && !detailJson.containsKey("name"))) {
                // If album fetch failed, try as playlist!
                val playlist = fetchPlaylistAsAlbum(numericId)
                if (playlist != null) return playlist
                return null
            }
            
            val title = detailJson["title"]?.jsonPrimitive?.content ?: detailJson["name"]?.jsonPrimitive?.content ?: id
            val year = detailJson["year"]?.jsonPrimitive?.intOrNull ?: detailJson["release_date"]?.jsonPrimitive?.content?.substringBefore("-")?.toIntOrNull()
            val coverArt = detailJson["image"]?.jsonPrimitive?.content?.replace(Regex("\\d+x\\d+"), "500x500")
            val artistNames = detailJson["primary_artists"]?.jsonPrimitive?.content ?: "Unknown Artist"
            
            val songsArray = detailJson["songs"]?.jsonArray ?: detailJson["list"]?.jsonArray
            val tracks = songsArray?.mapNotNull { item ->
                val songObj = item.jsonObject
                val songTitle = songObj["song"]?.jsonPrimitive?.content ?: songObj["title"]?.jsonPrimitive?.content ?: return@mapNotNull null
                val songImageUrl = songObj["image"]?.jsonPrimitive?.content?.replace(Regex("\\d+x\\d+"), "500x500") ?: coverArt
                
                Song(
                    id = songObj["id"]?.jsonPrimitive?.content ?: "",
                    title = songTitle.replace("&quot;", "\""),
                    artist = (songObj["primary_artists"]?.jsonPrimitive?.content ?: songObj["singers"]?.jsonPrimitive?.content ?: artistNames).replace("&quot;", "\""),
                    album = title,
                    albumArt = songImageUrl,
                    source = this.name,
                    durationMs = songObj["duration"]?.jsonPrimitive?.content?.toLongOrNull()?.times(1000L)
                )
            } ?: emptyList()
            
            Album(
                id = numericId,
                title = title.replace("&quot;", "\""),
                artist = artistNames.replace("&quot;", "\""),
                coverArt = coverArt,
                year = year,
                tracks = tracks
            )
        } catch (e: Exception) {
            logger.error("ListenFree getAlbum failed for id: $id", e)
            null
        }
    }

    override suspend fun getLyrics(title: String, artist: String, songId: String?): Lyrics? {
        if (songId == null) return null
        return try {
            val response = defaultHttpClient.get("https://www.jiosaavn.com/api.php") {
                parameter("__call", "lyrics.getLyrics")
                parameter("lyrics_id", songId)
                parameter("ctx", "web6dot0")
                parameter("api_version", "4")
                parameter("_format", "json")
                parameter("_marker", "0")
            }
            if (response.status.value != 200) return null
            val json = Json.parseToJsonElement(response.bodyAsText()).jsonObject
            
            val lyricsHtml = json["lyrics"]?.jsonPrimitive?.content ?: return null
            val plainLyrics = lyricsHtml.replace("<br>", "\n").replace(Regex("<[^>]*>"), "")
            
            Lyrics(
                synced = null,
                plain = plainLyrics,
                source = "JioSaavn"
            )
        } catch (e: Exception) {
            logger.error("JioSaavn getLyrics failed for id: $songId", e)
            null
        }
    }

    override suspend fun getRecommendations(songId: String): List<Song> {
        val parts = songId.split(":", limit = 2)
        val artist = parts.getOrNull(0)?.takeIf { it.isNotBlank() }
        val track = parts.getOrNull(1)?.takeIf { it.isNotBlank() } ?: songId
        
        // Search by artist to get other songs by them, avoiding identical covers
        val query = artist ?: track
        val songs = search(query)
        
        // Filter out the current song and return a few
        return songs.filter { !it.title.equals(track, ignoreCase = true) }
            .shuffled()
            .take(10)
    }

    override fun isHealthy(): Boolean = true
}
