package com.pulse.music.ui.artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pulse.music.data.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val repository: MusicRepository
) : ViewModel() {

    private val _artistInfo = MutableStateFlow<Any?>(null)
    val artistInfo: StateFlow<Any?> = _artistInfo.asStateFlow()

    private val _topTracks = MutableStateFlow<List<Any>>(emptyList())
    val topTracks: StateFlow<List<Any>> = _topTracks.asStateFlow()

    fun loadArtist(name: String) {
        viewModelScope.launch {
            repository.getArtistInfo(name).collect { info ->
                _artistInfo.value = info
            }
        }
    }
}
