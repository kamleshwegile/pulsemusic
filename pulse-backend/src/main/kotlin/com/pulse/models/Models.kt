package com.pulse.models

import kotlinx.serialization.Serializable

@Serializable
data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val album: String? = null,
    val albumArt: String? = null,
    val durationMs: Long? = null,
    val source: String
)

@Serializable
data class Artist(
    val id: String,
    val name: String,
    val bio: String? = null,
    val image: String? = null,
    val genres: List<String> = emptyList(),
    val similar: List<String> = emptyList(),
    val topTracks: List<Song> = emptyList(),
    val albums: List<Album> = emptyList(),
    val score: Int? = null
)

@Serializable
data class Album(
    val id: String,
    val title: String,
    val artist: String,
    val coverArt: String? = null,
    val year: Int? = null,
    val tracks: List<Song> = emptyList()
)

@Serializable
data class LyricLine(
    val timeMs: Long,
    val text: String
)

@Serializable
data class Lyrics(
    val synced: List<LyricLine>? = null,
    val plain: String? = null,
    val source: String,
    val cached: Boolean = false
)

@Serializable
data class Playlist(
    val id: String,
    val title: String,
    val image: String? = null,
    val songCount: Int? = null,
    val source: String
)

@Serializable
data class SearchResponse(
    val songs: List<Song> = emptyList(),
    val artists: List<Artist> = emptyList(),
    val albums: List<Album> = emptyList(),
    val playlists: List<Playlist> = emptyList()
)

@Serializable
data class ArtResponse(
    val url: String?,
    val thumbnail: String?,
    val source: String
)

@Serializable
data class ProviderStatus(
    val name: String,
    val healthy: Boolean,
    val failureRate: Float,
    val lastCheck: Long?,
    val disabled: Boolean
)
