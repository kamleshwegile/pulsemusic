package com.pulse.repository

import com.pulse.database.CachedSongs
import com.pulse.database.CachedLyrics
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

class CacheRepository {
    fun getSongData(key: String): String? = transaction {
        val row = CachedSongs.select { CachedSongs.id eq key }.singleOrNull() ?: return@transaction null
        if (isStale(row[CachedSongs.cachedAt], row[CachedSongs.ttlHours])) {
            return@transaction null
        }
        row[CachedSongs.data]
    }

    fun setSongData(key: String, title: String, artist: String, provider: String, data: String, ttlHours: Int = 24) = transaction {
        val updated = CachedSongs.update({ CachedSongs.id eq key }) {
            it[CachedSongs.title] = title
            it[CachedSongs.artist] = artist
            it[CachedSongs.provider] = provider
            it[CachedSongs.data] = data
            it[cachedAt] = System.currentTimeMillis()
            it[CachedSongs.ttlHours] = ttlHours
        }
        if (updated == 0) {
            CachedSongs.insert {
                it[id] = key
                it[CachedSongs.title] = title
                it[CachedSongs.artist] = artist
                it[CachedSongs.provider] = provider
                it[CachedSongs.data] = data
                it[cachedAt] = System.currentTimeMillis()
                it[CachedSongs.ttlHours] = ttlHours
            }
        }
    }
    
    fun getLyrics(key: String): Pair<String?, String?>? = transaction {
        val row = CachedLyrics.select { CachedLyrics.id eq key }.singleOrNull() ?: return@transaction null
        if (isStale(row[CachedLyrics.cachedAt], 24)) return@transaction null
        Pair(row[CachedLyrics.syncedLyrics], row[CachedLyrics.plainLyrics])
    }

    fun setLyrics(key: String, synced: String?, plain: String?, provider: String) = transaction {
        val updated = CachedLyrics.update({ CachedLyrics.id eq key }) {
            it[syncedLyrics] = synced
            it[plainLyrics] = plain
            it[CachedLyrics.provider] = provider
            it[cachedAt] = System.currentTimeMillis()
        }
        if (updated == 0) {
            CachedLyrics.insert {
                it[id] = key
                it[syncedLyrics] = synced
                it[plainLyrics] = plain
                it[CachedLyrics.provider] = provider
                it[cachedAt] = System.currentTimeMillis()
            }
        }
    }

    fun isStale(cachedAt: Long, ttlHours: Int): Boolean {
        return (cachedAt + ttlHours * 3600_000L) < System.currentTimeMillis()
    }

    fun pruneExpired() = transaction {
        val now = System.currentTimeMillis()
        exec("DELETE FROM cached_songs WHERE cached_at + (ttl_hours * 3600000) < $now")
        exec("DELETE FROM cached_lyrics WHERE cached_at + (24 * 3600000) < $now")
    }
}
