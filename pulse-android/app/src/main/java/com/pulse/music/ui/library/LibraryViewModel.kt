package com.pulse.music.ui.library

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor() : ViewModel() {

    // Dummy flows mimicking Room database observing
    val playlists = flowOf(emptyList<Any>())
    val albums = flowOf(emptyList<Any>())
    val artists = flowOf(emptyList<Any>())
    val songs = flowOf(emptyList<Any>())

    private val _filter = MutableStateFlow("All")
    val filter: StateFlow<String> = _filter.asStateFlow()

    private val _downloadedOnly = MutableStateFlow(false)
    val downloadedOnly: StateFlow<Boolean> = _downloadedOnly.asStateFlow()

    fun setFilter(newFilter: String) {
        _filter.value = newFilter
    }

    fun toggleDownloadedOnly() {
        _downloadedOnly.value = !_downloadedOnly.value
    }
}
