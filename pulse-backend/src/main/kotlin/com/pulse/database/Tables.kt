package com.pulse.database

import org.jetbrains.exposed.sql.Table

object CachedSongs : Table("cached_songs") {
    val id = varchar("id", 64) // provider:songId
    override val primaryKey = PrimaryKey(id)
    val title = varchar("title", 255)
    val artist = varchar("artist", 255)
    val albumArt = text("album_art").nullable()
    val provider = varchar("provider", 32)
    val data = text("data") // full JSON blob
    val cachedAt = long("cached_at")
    val ttlHours = integer("ttl_hours").default(24)
}

object CachedLyrics : Table("cached_lyrics") {
    val id = varchar("id", 64) // title:artist hash
    override val primaryKey = PrimaryKey(id)
    val syncedLyrics = text("synced_lyrics").nullable()
    val plainLyrics = text("plain_lyrics").nullable()
    val provider = varchar("provider", 32)
    val cachedAt = long("cached_at")
}

object ProviderHealthTable : Table("provider_health") {
    val name = varchar("name", 32)
    override val primaryKey = PrimaryKey(name)
    val failureCount = integer("failure_count").default(0)
    val lastFailure = long("last_failure").nullable()
    val disabled = bool("disabled").default(false)
}
