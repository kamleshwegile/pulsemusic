package com.pulse.providers

import com.pulse.models.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.*
import org.slf4j.LoggerFactory

class GeniusProvider : MusicProvider {
    override val name = "Genius"
    override val priority = 3
    private val logger = LoggerFactory.getLogger(GeniusProvider::class.java)

    private val accessToken = System.getenv("GENIUS_ACCESS_TOKEN") ?: ""
    
    init {
        val maskedToken = if (accessToken.length > 4) "****${accessToken.takeLast(4)}" else "****"
        logger.info("Initialized Genius provider with GENIUS_ACCESS_TOKEN=$maskedToken")
    }

    override suspend fun search(query: String): List<Song> = emptyList()
    override suspend fun getArtist(id: String): Artist? = null
    override suspend fun getAlbum(id: String): Album? = null

    override suspend fun getLyrics(title: String, artist: String, songId: String?): Lyrics? {
        val response = defaultHttpClient.get("https://api.genius.com/search") {
            parameter("q", "$title $artist")
            header("Authorization", "Bearer $accessToken")
        }
        if (response.status.value != 200) return null
        
        val json = Json.parseToJsonElement(response.bodyAsText()).jsonObject
        val hits = json["response"]?.jsonObject?.get("hits")?.jsonArray
        val firstHit = hits?.firstOrNull()?.jsonObject?.get("result")?.jsonObject
        val url = firstHit?.get("url")?.jsonPrimitive?.content
        
        if (url != null) {
            return Lyrics(plain = url, source = name)
        }
        return null
    }

    override suspend fun getRecommendations(songId: String): List<Song> = emptyList()
    override fun isHealthy(): Boolean = accessToken.isNotEmpty()
}
