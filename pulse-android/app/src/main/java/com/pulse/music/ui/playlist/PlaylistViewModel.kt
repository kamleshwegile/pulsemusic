package com.pulse.music.ui.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pulse.music.data.local.PlaylistEntity
import com.pulse.music.data.repository.PlaylistRepository
import com.pulse.music.domain.Song
import com.pulse.music.player.MusicPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val playlistRepository: PlaylistRepository,
    private val musicPlayerManager: MusicPlayerManager
) : ViewModel() {

    private val _playlistId = MutableStateFlow<Int?>(null)

    val playlistInfo: StateFlow<PlaylistEntity?> = _playlistId
        .filterNotNull()
        .flatMapLatest { id ->
            playlistRepository.getAllPlaylists().map { playlists -> 
                playlists.find { it.id == id } 
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val playlistSongs: StateFlow<List<Song>> = _playlistId
        .filterNotNull()
        .flatMapLatest { id ->
            playlistRepository.getSongsForPlaylist(id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val currentSong = musicPlayerManager.currentSong
    
    // Mocking favorite functionality since DB doesn't support it directly on PlaylistEntity
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    fun loadPlaylist(id: Int) {
        _playlistId.value = id
        // In a real app we'd load this from DB
        _isFavorite.value = false
    }

    fun playSong(song: Song, contextSongs: List<Song>) {
        musicPlayerManager.playSongFromList(song, contextSongs)
    }

    fun playAll(contextSongs: List<Song>) {
        if (contextSongs.isNotEmpty()) {
            musicPlayerManager.playSongFromList(contextSongs.first(), contextSongs)
        }
    }

    fun shufflePlay(contextSongs: List<Song>) {
        if (contextSongs.isNotEmpty()) {
            val shuffled = contextSongs.shuffled()
            musicPlayerManager.playSongFromList(shuffled.first(), shuffled)
        }
    }

    fun toggleFavorite() {
        _isFavorite.value = !_isFavorite.value
    }

    fun removeSong(songId: String) {
        val pId = _playlistId.value ?: return
        viewModelScope.launch {
            playlistRepository.removeSongFromPlaylist(pId, songId)
        }
    }

    fun renamePlaylist(newName: String) {
        // Implementation for renaming playlist
    }

    fun deletePlaylist() {
        val pId = _playlistId.value ?: return
        viewModelScope.launch {
            playlistRepository.deletePlaylist(pId)
        }
    }
}
