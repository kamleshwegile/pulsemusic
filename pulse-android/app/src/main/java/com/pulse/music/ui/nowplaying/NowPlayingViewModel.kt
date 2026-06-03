package com.pulse.music.ui.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pulse.music.data.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LyricsState {
    object Loading : LyricsState()
    data class Synced(val lines: List<Any>) : LyricsState()
    data class Plain(val text: String) : LyricsState()
    object Unavailable : LyricsState()
}

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val repository: MusicRepository
) : ViewModel() {

    val currentSong = MutableStateFlow<Any?>(null)
    val progress = MutableStateFlow(0L) // From Media3

    private val _lyricsState = MutableStateFlow<LyricsState>(LyricsState.Unavailable)
    val lyricsState: StateFlow<LyricsState> = _lyricsState.asStateFlow()

    val highlightedLine = progress.map { currentProgress ->
        // Match progress to LRC timestamp logic
        0 // Return index of matched line
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun fetchLyrics(title: String, artist: String) {
        viewModelScope.launch {
            _lyricsState.value = LyricsState.Loading
            repository.getLyrics(title, artist).collect { lyrics ->
                if (lyrics != null) {
                    _lyricsState.value = LyricsState.Plain(lyrics.toString())
                } else {
                    _lyricsState.value = LyricsState.Unavailable
                }
            }
        }
    }
}
