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
        val featuredPlaylists: List<com.pulse.music.domain.Playlist>,
        val topPlaylists: List<com.pulse.music.domain.Playlist>,
        val englishHits: List<com.pulse.music.domain.Playlist>,
        val hindiHits: List<com.pulse.music.domain.Playlist>,
        val punjabiHits: List<com.pulse.music.domain.Playlist>,
        val topHits: List<com.pulse.music.domain.Playlist>,
        val popClassic: List<com.pulse.music.domain.Playlist>,
        val artistsYouFollow: List<com.pulse.music.domain.Artist>,
        val kpop: List<com.pulse.music.domain.Playlist>,
        val trendingEnglish: List<com.pulse.music.domain.Playlist>
    ) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: com.pulse.music.data.repository.OnlineMusicRepository,
    private val songDao: com.pulse.music.data.local.SongDao,
    private val musicPlayerManager: MusicPlayerManager
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
        if (cachedUiState != null) {
            _uiState.value = cachedUiState!!
        } else {
            loadData()
        }
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                // Fetch recent song from DB for suggestions
                val localSongsEntity = songDao.getAllSongs().reversed() // Most recent first
                val recentSong = localSongsEntity.firstOrNull() // Pick the most recently saved song

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

                val suggestedDeferred = if (recentSong != null) {
                    async { repository.getRecommendations(recentSong.artist, recentSong.title) }
                } else {
                    async { repository.getTrending() } // Fallback
                }
                
                val trendingDeferred = async { repository.getTrending() }
                val homeDeferred = async { repository.getHome() }
                
                val rawSuggested = suggestedDeferred.await().getOrNull() ?: emptyList()
                val rawTrending = trendingDeferred.await().getOrNull() ?: emptyList()
                val suggested = (rawSuggested + rawTrending).distinctBy { it.id }.take(20)
                
                val homeData = homeDeferred.await().getOrNull()
                
                val successState = HomeUiState.Success(
                    recentPlays = localSongs,
                    suggested = suggested,
                    featuredPlaylists = homeData?.featuredPlaylists ?: emptyList(),
                    topPlaylists = homeData?.topPlaylists ?: emptyList(),
                    englishHits = homeData?.englishHits ?: emptyList(),
                    hindiHits = homeData?.hindiHits ?: emptyList(),
                    punjabiHits = homeData?.punjabiHits ?: emptyList(),
                    topHits = homeData?.topHits ?: emptyList(),
                    popClassic = homeData?.popClassic ?: emptyList(),
                    artistsYouFollow = homeData?.artists ?: emptyList(),
                    kpop = homeData?.kpop ?: emptyList(),
                    trendingEnglish = homeData?.trendingEnglish ?: emptyList()
                )
                cachedUiState = successState
                _uiState.value = successState
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}
