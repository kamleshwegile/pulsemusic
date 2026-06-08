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

    fun loadPlaylist(id: Int) {
        _playlistId.value = id
    }

    fun playSong(song: Song, contextSongs: List<Song>) {
        musicPlayerManager.playSongFromList(song, contextSongs)
    }

    fun removeSong(songId: String) {
        val pId = _playlistId.value ?: return
        viewModelScope.launch {
            playlistRepository.removeSongFromPlaylist(pId, songId)
        }
    }
}
