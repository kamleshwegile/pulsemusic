package com.pulse.providers

import com.pulse.models.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.*
import org.slf4j.LoggerFactory

class LastFmProvider : MusicProvider {
    override val name = "Last.fm"
    override val priority = 2
    private val logger = LoggerFactory.getLogger(LastFmProvider::class.java)

    private val apiKey = System.getenv("LASTFM_API_KEY")?.takeIf { it.isNotBlank() } ?: "d50c76ce17bb3a0d5c07cb81e8eb87eb"
    
    init {
        val maskedKey = if (apiKey.length > 4) "****${apiKey.takeLast(4)}" else "****"
        logger.info("Initialized Last.fm provider with LASTFM_KEY=$maskedKey")
    }

    override suspend fun search(query: String): List<Song> = emptyList()

    override suspend fun getArtist(id: String): Artist? {
        val response = defaultHttpClient.get("https://ws.audioscrobbler.com/2.0/") {
            parameter("method", "artist.getinfo")
            parameter("artist", id)
            parameter("api_key", apiKey)
            parameter("format", "json")
        }
        if (response.status.value != 200) return null
        val json = Json.parseToJsonElement(response.bodyAsText()).jsonObject
        val artist = json["artist"]?.jsonObject ?: return null
        val bio = artist["bio"]?.jsonObject?.get("summary")?.jsonPrimitive?.content
        val similar = artist["similar"]?.jsonObject?.get("artist")?.jsonArray?.mapNotNull { it.jsonObject["name"]?.jsonPrimitive?.content } ?: emptyList()
        val tags = artist["tags"]?.jsonObject?.get("tag")?.jsonArray?.mapNotNull { it.jsonObject["name"]?.jsonPrimitive?.content } ?: emptyList()

        val tracksResponse = defaultHttpClient.get("https://ws.audioscrobbler.com/2.0/") {
            parameter("method", "artist.gettoptracks")
            parameter("artist", id)
            parameter("api_key", apiKey)
            parameter("format", "json")
            parameter("limit", "15")
        }
        val tracks = if (tracksResponse.status.value == 200) {
            val tracksJson = Json.parseToJsonElement(tracksResponse.bodyAsText()).jsonObject
            val trackArray = tracksJson["toptracks"]?.jsonObject?.get("track")?.jsonArray ?: emptyList()
            trackArray.mapNotNull { 
                val t = it.jsonObject
                val title = t["name"]?.jsonPrimitive?.content ?: return@mapNotNull null
                val image = t["image"]?.jsonArray?.lastOrNull()?.jsonObject?.get("#text")?.jsonPrimitive?.content
                Song(id = title, title = title, artist = id, albumArt = if (image.isNullOrEmpty()) null else image, source = name)
            }
        } else emptyList()

        val albumsResponse = defaultHttpClient.get("https://ws.audioscrobbler.com/2.0/") {
            parameter("method", "artist.gettopalbums")
            parameter("artist", id)
            parameter("api_key", apiKey)
            parameter("format", "json")
            parameter("limit", "10")
        }
        val albums = if (albumsResponse.status.value == 200) {
            val albumsJson = Json.parseToJsonElement(albumsResponse.bodyAsText()).jsonObject
            val albumArray = albumsJson["topalbums"]?.jsonObject?.get("album")?.jsonArray ?: emptyList()
            albumArray.mapNotNull { 
                val a = it.jsonObject
                val title = a["name"]?.jsonPrimitive?.content ?: return@mapNotNull null
                val image = a["image"]?.jsonArray?.lastOrNull()?.jsonObject?.get("#text")?.jsonPrimitive?.content
                Album(id = title, title = title, artist = id, coverArt = if (image.isNullOrEmpty()) null else image)
            }
        } else emptyList()

        return Artist(id = id, name = id, bio = bio, genres = tags, similar = similar, topTracks = tracks, albums = albums)
    }

    override suspend fun getAlbum(id: String): Album? = null
    override suspend fun getLyrics(title: String, artist: String, songId: String?): Lyrics? = null

    override suspend fun getRecommendations(songId: String): List<Song> {
        val parts = songId.split(":", limit = 2)
        val artist = parts.getOrNull(0) ?: ""
        val track = parts.getOrNull(1) ?: ""
        val response = defaultHttpClient.get("https://ws.audioscrobbler.com/2.0/") {
            parameter("method", "track.getsimilar")
            parameter("artist", artist)
            parameter("track", track)
            parameter("api_key", apiKey)
            parameter("format", "json")
            parameter("limit", "10")
        }
        if (response.status.value != 200) return emptyList()
        val json = Json.parseToJsonElement(response.bodyAsText()).jsonObject
        val tracks = json["similartracks"]?.jsonObject?.get("track")?.jsonArray ?: return emptyList()
        
        return tracks.map {
            val trackObj = it.jsonObject
            Song(
                id = trackObj["name"]?.jsonPrimitive?.content ?: "",
                title = trackObj["name"]?.jsonPrimitive?.content ?: "",
                artist = trackObj["artist"]?.jsonObject?.get("name")?.jsonPrimitive?.content ?: "",
                source = name
            )
        }
    }
    
    suspend fun getTopTracks(country: String = "IN", limit: Int = 20): List<Song> {
        val response = defaultHttpClient.get("https://ws.audioscrobbler.com/2.0/") {
            parameter("method", "geo.gettoptracks")
            parameter("country", country)
            parameter("limit", limit.toString())
            parameter("api_key", apiKey)
            parameter("format", "json")
        }
        if (response.status.value != 200) return emptyList()
        val json = Json.parseToJsonElement(response.bodyAsText()).jsonObject
        val tracks = json["tracks"]?.jsonObject?.get("track")?.jsonArray ?: return emptyList()
        
        return tracks.map {
            val trackObj = it.jsonObject
            Song(
                id = trackObj["name"]?.jsonPrimitive?.content ?: "",
                title = trackObj["name"]?.jsonPrimitive?.content ?: "",
                artist = trackObj["artist"]?.jsonObject?.get("name")?.jsonPrimitive?.content ?: "",
                source = name
            )
        }
    }

    override fun isHealthy(): Boolean = apiKey.isNotEmpty()
}
