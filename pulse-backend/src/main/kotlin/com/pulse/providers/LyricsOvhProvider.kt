package com.pulse.providers

import com.pulse.models.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.*

/**
 * Fallback lyrics provider using lyrics.ovh API.
 * Returns plain (unsynced) lyrics when LRCLIB is unreachable.
 */
class LyricsOvhProvider : MusicProvider {
    override val name = "LyricsOvh"
    override val priority = 5  // Lower priority, used as fallback

    override suspend fun search(query: String): List<Song> = emptyList()
    override suspend fun getArtist(id: String): Artist? = null
    override suspend fun getAlbum(id: String): Album? = null
    override suspend fun getRecommendations(songId: String): List<Song> = emptyList()

    override suspend fun getLyrics(title: String, artist: String, songId: String?): Lyrics? {
        return null // API is currently dead and causing timeouts
    }

    override fun isHealthy(): Boolean = true
}
