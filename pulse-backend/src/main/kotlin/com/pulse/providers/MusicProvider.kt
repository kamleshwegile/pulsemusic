package com.pulse.providers

import com.pulse.models.*

interface MusicProvider {
    val name: String
    val priority: Int
    suspend fun search(query: String): List<Song>
    suspend fun getArtist(id: String): Artist?
    suspend fun getAlbum(id: String): Album?
    suspend fun getLyrics(title: String, artist: String, songId: String? = null): Lyrics?
    suspend fun getRecommendations(songId: String): List<Song>
    suspend fun searchAll(query: String): SearchResponse? = null
    fun isHealthy(): Boolean
}
