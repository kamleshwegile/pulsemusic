package com.pulse.database

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import kotlinx.serialization.Serializable

@Serializable
data class CachedSong(
    @BsonId val id: String, // provider:songId
    val title: String,
    val artist: String,
    val albumArt: String? = null,
    val provider: String,
    val data: String, // full JSON blob
    val cachedAt: Long,
    val ttlHours: Int = 24
)

@Serializable
data class CachedLyric(
    @BsonId val id: String, // title:artist hash
    val syncedLyrics: String? = null,
    val plainLyrics: String? = null,
    val provider: String,
    val cachedAt: Long
)

@Serializable
data class ProviderHealth(
    @BsonId val name: String,
    val failureCount: Int = 0,
    val lastFailure: Long? = null,
    val disabled: Boolean = false
)

@Serializable
data class User(
    @BsonId val id: String = ObjectId().toString(),
    val username: String,
    val email: String,
    val passwordHash: String,
    val createdAt: Long
)
