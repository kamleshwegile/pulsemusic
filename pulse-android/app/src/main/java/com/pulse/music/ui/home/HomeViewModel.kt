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
            val prefs = context.getSharedPreferences("pulse_home_cache", android.content.Context.MODE_PRIVATE)
            val cachedJson = prefs.getString("home_state", null)
            if (cachedJson != null) {
                try {
                    cachedUiState = com.google.gson.Gson().fromJson(cachedJson, HomeUiState.Success::class.java)
                } catch (e: Exception) {
                    // Ignore parsing error
                }
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
                // Fetch recent song from DB for suggestions
                val localSongsEntity = songDao.getAllSongs().reversed() // Most recent first

                val localSongs = localSongsEntity.map { entity ->
                    Song(
                        id = entity.id,
                        title = entity.title,
                        artist = entity.artist,
                        album = entity.album,
                        albumArt = entity.albumArt,
                        durationMs = null,
                        source = entity.source
                    )
                }

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
                
                val limitedRecentPlays = recentPlaysList.take(2)
                
                val rawSuggested = suggestedDeferred.await().getOrNull() ?: emptyList()
                val rawTrending = trendingDeferred.await().getOrNull() ?: emptyList()
                val suggested = (rawSuggested + rawTrending).distinctBy { it.id }.take(4)
                
                val homeData = homeDeferred.await().getOrNull()
                
                val successState = HomeUiState.Success(
                    recentPlays = limitedRecentPlays,
                    suggested = suggested,
                    modules = homeData?.modules ?: emptyList()
                )
                cachedUiState = successState
                _uiState.value = successState
                
                // Save to SharedPreferences for next cold boot
                try {
                    val prefs = context.getSharedPreferences("pulse_home_cache", android.content.Context.MODE_PRIVATE)
                    prefs.edit().putString("home_state", com.google.gson.Gson().toJson(successState)).apply()
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
