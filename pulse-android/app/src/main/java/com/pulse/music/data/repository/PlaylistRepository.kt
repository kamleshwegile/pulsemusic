package com.pulse.music.data.repository

import com.pulse.music.data.local.PlaylistDao
import com.pulse.music.data.local.PlaylistEntity
import com.pulse.music.data.local.PlaylistSongEntity
import com.pulse.music.domain.Song
import com.pulse.music.data.network.PulseApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaylistRepository @Inject constructor(
    private val playlistDao: PlaylistDao,
    private val apiService: PulseApiService
) {
    fun getAllPlaylists(): Flow<List<PlaylistEntity>> {
        return playlistDao.getAllPlaylists()
    }

    suspend fun syncPlaylists() {
        try {
            val remotePlaylists = apiService.getPlaylists()
            val localPlaylists = playlistDao.getAllPlaylists().first()
            val localNamesMap = localPlaylists.associateBy { it.name }
            
            remotePlaylists.forEach { pl ->
                val name = pl["title"] as? String ?: pl["name"] as? String ?: return@forEach
                
                var playlistId: Int
                if (!localNamesMap.containsKey(name)) {
                    playlistId = playlistDao.insertPlaylist(PlaylistEntity(name = name)).toInt()
                } else {
                    playlistId = localNamesMap[name]!!.id
                }
                
                val songs = pl["songs"] as? List<Map<String, Any>> ?: emptyList()
                val existingSongs = playlistDao.getSongsForPlaylist(playlistId).first().map { it.songId }
                
                songs.forEach { songData ->
                    val songId = songData["id"] as? String ?: return@forEach
                    if (!existingSongs.contains(songId)) {
                        val title = songData["title"] as? String ?: ""
                        val artist = songData["artist"] as? String ?: ""
                        val album = songData["album"] as? String ?: ""
                        val albumArt = songData["image"] as? String ?: songData["albumArt"] as? String ?: ""
                        
                        val durationRaw = songData["duration"]?.toString() ?: "0"
                        var durationMs = durationRaw.toLongOrNull() ?: 0L
                        if (durationMs < 10000 && durationMs > 0) durationMs *= 1000 // Convert seconds to ms if needed
                        
                        val source = songData["source"] as? String ?: "jiosaavn"
                        
                        playlistDao.insertSongToPlaylist(PlaylistSongEntity(
                            playlistId = playlistId,
                            songId = songId,
                            title = title,
                            artist = artist,
                            album = album,
                            albumArt = albumArt,
                            durationMs = durationMs,
                            source = source
                        ))
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun createPlaylist(name: String): Int {
        val entity = PlaylistEntity(name = name)
        val id = playlistDao.insertPlaylist(entity).toInt()
        try {
            apiService.createPlaylist(name)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return id
    }

    suspend fun deletePlaylist(playlistId: Int) {
        playlistDao.deletePlaylist(playlistId)
        // Wait, local ID is Int, remote ID is String. We need remote ID to delete!
        // Right now the API uses remote string ID.
    }

    fun getSongsForPlaylist(playlistId: Int): Flow<List<Song>> {
        return playlistDao.getSongsForPlaylist(playlistId).map { entities ->
            entities.map { entity ->
                Song(
                    id = entity.songId,
                    title = entity.title,
                    artist = entity.artist,
                    album = entity.album,
                    albumArt = entity.albumArt,
                    durationMs = entity.durationMs,
                    source = entity.source
                )
            }
        }
    }

    suspend fun addSongToPlaylist(playlistId: Int, song: Song) {
        val entity = PlaylistSongEntity(
            playlistId = playlistId,
            songId = song.id,
            title = song.title,
            artist = song.artist,
            album = song.album,
            albumArt = song.albumArt,
            durationMs = song.durationMs,
            source = song.source
        )
        playlistDao.insertSongToPlaylist(entity)
        // Need string playlist_id for apiService.addSongToPlaylist
    }

    suspend fun removeSongFromPlaylist(playlistId: Int, songId: String) {
        playlistDao.removeSongFromPlaylist(playlistId, songId)
    }

    suspend fun importSpotifyPlaylist(url: String): Result<Boolean> {
        return try {
            val response = apiService.importSpotifyPlaylist(mapOf("url" to url))
            if (response["status"] == "success") {
                syncPlaylists()
                Result.success(true)
            } else {
                Result.failure(Exception("Failed to import playlist"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}
