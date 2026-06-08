package com.pulse.providers

import com.pulse.models.*
import com.pulse.util.CircuitBreaker
import com.pulse.util.RateLimiter
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.*

class MusicBrainzProvider : MusicProvider {
    override val name = "MusicBrainz"
    override val priority = 1
    
    private val rateLimiter = RateLimiter(1) // 1 req/sec
    private val circuitBreaker = CircuitBreaker()
    
    override suspend fun search(query: String): List<Song> {
        return circuitBreaker.execute {
            rateLimiter.acquire()
            val response = defaultHttpClient.get("https://musicbrainz.org/ws/2/recording") {
                parameter("query", query)
                parameter("fmt", "json")
                header("User-Agent", "PulseMusic/1.0 (pulse@example.com)")
            }
            if (response.status.value != 200) return@execute emptyList()
            
            val json = Json.parseToJsonElement(response.bodyAsText()).jsonObject
            val recordings = json["recordings"]?.jsonArray ?: emptyList()
            
            recordings.take(20).map {
                val rec = it.jsonObject
                Song(
                    id = rec["id"]?.jsonPrimitive?.content ?: "",
                    title = rec["title"]?.jsonPrimitive?.content ?: "",
                    artist = rec["artist-credit"]?.jsonArray?.firstOrNull()?.jsonObject?.get("name")?.jsonPrimitive?.content ?: "Unknown",
                    source = name
                )
            }
        }
    }

    override suspend fun getArtist(id: String): Artist? {
        return circuitBreaker.execute {
            rateLimiter.acquire()
            val response = defaultHttpClient.get("https://musicbrainz.org/ws/2/artist") {
                parameter("query", id)
                parameter("fmt", "json")
                header("User-Agent", "PulseMusic/1.0 (pulse@example.com)")
            }
            if (response.status.value != 200) return@execute null
            val json = Json.parseToJsonElement(response.bodyAsText()).jsonObject
            val artists = json["artists"]?.jsonArray ?: return@execute null
            val firstArtist = artists.firstOrNull()?.jsonObject ?: return@execute null
            
            Artist(
                id = firstArtist["id"]?.jsonPrimitive?.content ?: "",
                name = firstArtist["name"]?.jsonPrimitive?.content ?: "Unknown",
                score = firstArtist["score"]?.jsonPrimitive?.content?.toIntOrNull()
            )
        }
    }

    override suspend fun getAlbum(id: String): Album? {
        // Assume id is MBID
        return circuitBreaker.execute {
            rateLimiter.acquire()
            val response = defaultHttpClient.get("https://musicbrainz.org/ws/2/release/$id") {
                parameter("fmt", "json")
                header("User-Agent", "PulseMusic/1.0 (pulse@example.com)")
            }
            if (response.status.value != 200) return@execute null
            val rec = Json.parseToJsonElement(response.bodyAsText()).jsonObject
            Album(
                id = rec["id"]?.jsonPrimitive?.content ?: "",
                title = rec["title"]?.jsonPrimitive?.content ?: "",
                artist = "Unknown"
            )
        }
    }

    override suspend fun getLyrics(title: String, artist: String, songId: String?): Lyrics? = null
    override suspend fun getRecommendations(songId: String): List<Song> = emptyList()
    
    override fun isHealthy(): Boolean = circuitBreaker.state != com.pulse.util.CircuitState.OPEN
}
