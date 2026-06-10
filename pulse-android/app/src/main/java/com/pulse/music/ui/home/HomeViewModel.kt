package com.pulse.music.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pulse.music.data.repository.MusicRepository
import com.pulse.music.domain.Song
import com.pulse.music.player.MusicPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import javax.inject.Inject

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(
        val recentPlays: List<Song>,
        val allRecentPlays: List<Song>,
        val suggested: List<Song>,
        val modules: List<com.pulse.music.data.network.HomeModule>
    ) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: com.pulse.music.data.repository.OnlineMusicRepository,
    private val songDao: com.pulse.music.data.local.SongDao,
    private val musicPlayerManager: MusicPlayerManager,
    @dagger.hilt.android.qualifiers.ApplicationContext private val context: android.content.Context
) : ViewModel() {

    fun playSong(song: Song, contextSongs: List<Song>) {
        musicPlayerManager.playSongFromList(song, contextSongs)
    }

    companion object {
        var cachedUiState: HomeUiState.Success? = null
    }

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        if (cachedUiState == null) {
            try {
                val cacheFile = java.io.File(context.cacheDir, "home_cache.json")
                if (cacheFile.exists()) {
                    val cachedJson = cacheFile.readText()
                    cachedUiState = com.google.gson.Gson().fromJson(cachedJson, HomeUiState.Success::class.java)
                }
            } catch (e: Exception) {
                // Ignore parsing error
            }
        }
        
        if (cachedUiState != null) {
            _uiState.value = cachedUiState!!
        }
        
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            if (cachedUiState == null) {
                _uiState.value = HomeUiState.Loading
            }
            try {
                // Fetch actual recently played songs from local SharedPreferences
                var localSongs: List<Song> = emptyList()
                try {
                    val prefs = context.getSharedPreferences("pulse_actual_recent_plays", android.content.Context.MODE_PRIVATE)
                    val json = prefs.getString("plays", "[]")
                    val array = com.google.gson.Gson().fromJson(json, Array<Song>::class.java)
                    localSongs = array?.toList() ?: emptyList()
                } catch (e: Exception) {}

                val actualRecentSongs = repository.getRecentSongs().getOrNull() ?: emptyList()
                val recentPlaysList = if (actualRecentSongs.isNotEmpty()) actualRecentSongs else localSongs
                val actualRecentSong = recentPlaysList.firstOrNull()
                
                val suggestedDeferred = if (actualRecentSong != null) {
                    async { repository.getRecommendations(actualRecentSong.artist, actualRecentSong.title) }
                } else {
                    async { repository.getTrending() } // Fallback
                }
                
                val trendingDeferred = async { repository.getTrending() }
                val homeDeferred = async { repository.getHome() }
                val limitedRecentPlays = recentPlaysList.take(5)
                
                val rawSuggested = suggestedDeferred.await().getOrNull() ?: emptyList()
                val rawTrending = trendingDeferred.await().getOrNull() ?: emptyList()
                val suggested = (rawSuggested + rawTrending).distinctBy { it.id }.take(4)
                
                val homeData = homeDeferred.await().getOrNull()
                
                val successState = HomeUiState.Success(
                    recentPlays = limitedRecentPlays,
                    allRecentPlays = recentPlaysList,
                    suggested = suggested,
                    modules = homeData?.modules ?: emptyList()
                )
                cachedUiState = successState
                _uiState.value = successState
                
                // Save to cache directory so it clears on Clear Cache
                try {
                    val cacheFile = java.io.File(context.cacheDir, "home_cache.json")
                    cacheFile.writeText(com.google.gson.Gson().toJson(successState))
                } catch (e: Exception) {
                    // Ignore save error
                }
                
            } catch (e: Exception) {
                android.util.Log.e("PulseAPI", "HomeViewModel error: ", e)
                _uiState.value = HomeUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}
