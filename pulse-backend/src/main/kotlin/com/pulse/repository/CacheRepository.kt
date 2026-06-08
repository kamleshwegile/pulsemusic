package com.pulse.repository

import com.pulse.database.*
import com.pulse.database as appDatabase
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.*

class CacheRepository {
    
    private val songs = appDatabase.getCollection<CachedSong>("cached_songs")
    private val lyrics = appDatabase.getCollection<CachedLyric>("cached_lyrics")

    suspend fun getSongData(key: String): String? {
        val row = songs.findOneById(key) ?: return null
        if (isStale(row.cachedAt, row.ttlHours)) {
            return null
        }
        return row.data
    }

    suspend fun setSongData(key: String, title: String, artist: String, provider: String, data: String, ttlHours: Int = 24) {
        val song = CachedSong(
            id = key,
            title = title,
            artist = artist,
            provider = provider,
            data = data,
            cachedAt = System.currentTimeMillis(),
            ttlHours = ttlHours
        )
        songs.save(song)
    }
    
    suspend fun getLyrics(key: String): Pair<String?, String?>? {
        val row = lyrics.findOneById(key) ?: return null
        if (isStale(row.cachedAt, 24)) return null
        return Pair(row.syncedLyrics, row.plainLyrics)
    }

    suspend fun setLyrics(key: String, synced: String?, plain: String?, provider: String) {
        val lyric = CachedLyric(
            id = key,
            syncedLyrics = synced,
            plainLyrics = plain,
            provider = provider,
            cachedAt = System.currentTimeMillis()
        )
        lyrics.save(lyric)
    }

    fun isStale(cachedAt: Long, ttlHours: Int): Boolean {
        return (cachedAt + ttlHours * 3600_000L) < System.currentTimeMillis()
    }

    suspend fun pruneExpired() {
        val now = System.currentTimeMillis()
        // Delete songs where cachedAt + ttlHours * 3600000 < now
        // This is tricky with simple KMongo filters, so we can do it later.
        // Or simply delete anything older than 48 hours for now:
        val threshold = now - (48 * 3600000L)
        songs.deleteMany(CachedSong::cachedAt lt threshold)
        lyrics.deleteMany(CachedLyric::cachedAt lt threshold)
    }
}
