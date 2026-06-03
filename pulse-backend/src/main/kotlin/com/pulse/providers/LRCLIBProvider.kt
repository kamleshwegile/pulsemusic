package com.pulse.providers

import com.pulse.models.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.*

class LRCLIBProvider : MusicProvider {
    override val name = "LRCLIB"
    override val priority = 1

    override suspend fun search(query: String): List<Song> = emptyList()
    override suspend fun getArtist(id: String): Artist? = null
    override suspend fun getAlbum(id: String): Album? = null

    override suspend fun getLyrics(title: String, artist: String): Lyrics? {
        val response = defaultHttpClient.get("https://lrclib.net/api/get") {
            parameter("track_name", title)
            parameter("artist_name", artist)
        }
        
        if (response.status.value != 200) return null
        val json = Json.parseToJsonElement(response.bodyAsText()).jsonObject
        
        val plainLyrics = json["plainLyrics"]?.jsonPrimitive?.content
        val syncedLrc = json["syncedLyrics"]?.jsonPrimitive?.content
        
        val synced = syncedLrc?.lines()?.mapNotNull { line ->
            val match = Regex("\\[(\\d{2}):(\\d{2})\\.(\\d{2})\\](.*)").find(line)
            if (match != null) {
                val (m, s, ms, text) = match.destructured
                val time = m.toLong() * 60000 + s.toLong() * 1000 + ms.toLong() * 10
                LyricLine(time, text.trim())
            } else null
        }
        
        return Lyrics(synced = synced, plain = plainLyrics, source = name)
    }

    override suspend fun getRecommendations(songId: String): List<Song> = emptyList()
    override fun isHealthy(): Boolean = true
}
