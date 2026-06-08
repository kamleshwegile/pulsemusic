package com.pulse.music.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pulse.music.data.local.PlaylistDao
import com.pulse.music.data.local.PlaylistEntity
import com.pulse.music.data.local.SongDao
import com.pulse.music.data.local.SongEntity
import com.pulse.music.data.repository.OnlineMusicRepository
import com.pulse.music.domain.Album
import com.pulse.music.domain.Artist
import com.pulse.music.domain.Song
import com.pulse.music.player.MusicPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchSectionState<T>(
    val data: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

data class SearchUiState(
    val query: String = "",
    val activeCategory: String = "All",
    val recentSearches: List<String> = emptyList(),
    val songs: SearchSectionState<Song> = SearchSectionState(),
    val albums: SearchSectionState<Album> = SearchSectionState(),
    val artists: SearchSectionState<Artist> = SearchSectionState(),
    val playlists: SearchSectionState<PlaylistEntity> = SearchSectionState(),
    val onlinePlaylists: SearchSectionState<com.pulse.music.domain.Playlist> = SearchSectionState(),
    val exactArtist: Artist? = null,
    val isExactArtistLoading: Boolean = false
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val onlineRepo: OnlineMusicRepository,
    private val songDao: SongDao,
    private val playlistDao: PlaylistDao,
    private val musicPlayerManager: MusicPlayerManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        fetchRecentSearches()
        viewModelScope.launch {
            _uiState.map { it.query }
                .debounce(400)
                .distinctUntilChanged()
                .collectLatest { query ->
                    performSearch(query)
                }
        }
    }

    fun setQuery(query: String) {
        _uiState.update { it.copy(query = query) }
    }
    
    private fun fetchRecentSearches() {
        viewModelScope.launch(kotlinx.coroutines.Dispatchers.IO) {
            val res = onlineRepo.getRecentSearches().getOrNull()
            if (res != null) {
                val searches = res.mapNotNull { it["query"] as? String }
                _uiState.update { it.copy(recentSearches = searches) }
            }
        }
    }

    fun saveRecentSearch(query: String) {
        android.util.Log.e("SearchHistory", "saveRecentSearch called with query: $query")
        if (query.isBlank()) {
            android.util.Log.e("SearchHistory", "query is blank, aborting")
            return
        }
        viewModelScope.launch(kotlinx.coroutines.Dispatchers.IO) {
            android.util.Log.e("SearchHistory", "Launching API call for addRecentSearch")
            val result = onlineRepo.addRecentSearch(query)
            if (result.isSuccess) {
                android.util.Log.e("SearchHistory", "API call success: ${result.getOrNull()}")
            } else {
                android.util.Log.e("SearchHistory", "API call failed", result.exceptionOrNull())
            }
            fetchRecentSearches()
        }
    }

    fun setCategory(category: String) {
        _uiState.update { it.copy(activeCategory = category) }
    }

    private fun performSearch(query: String) {
        if (query.isBlank()) {
            _uiState.update {
                it.copy(
                    songs = SearchSectionState(),
                    albums = SearchSectionState(),
                    artists = SearchSectionState(),
                    playlists = SearchSectionState(),
                    exactArtist = null,
                    isExactArtistLoading = false
                )
            }
            return
        }

        searchJob?.cancel()

        _uiState.update { state ->
            state.copy(
                songs = state.songs.copy(isLoading = true, isError = false),
                albums = state.albums.copy(isLoading = true, isError = false),
                artists = state.artists.copy(isLoading = true, isError = false),
                playlists = state.playlists.copy(isLoading = true, isError = false),
                onlinePlaylists = state.onlinePlaylists.copy(isLoading = true, isError = false),
                isExactArtistLoading = true,
                exactArtist = null
            )
        }

        searchJob = viewModelScope.launch {
            // Fetch Playlists locally
            launch {
                try {
                    val p = playlistDao.getAllPlaylists().first()
                    val filteredPlaylists = p.filter { it.name.contains(query, ignoreCase = true) }
                    _uiState.update { it.copy(playlists = SearchSectionState(data = filteredPlaylists, isLoading = false)) }
                } catch (e: Exception) {
                    _uiState.update { it.copy(playlists = it.playlists.copy(isLoading = false, isError = true)) }
                }
            }

            // Fetch Exact Artist match independently
            launch {
                try {
                    val artistRes = onlineRepo.getArtistInfo(query)
                    val artist = artistRes.getOrNull()
                    if (artist != null && artist.name.isNotEmpty()) {
                        _uiState.update { it.copy(exactArtist = artist, isExactArtistLoading = false) }
                    } else {
                        _uiState.update { it.copy(isExactArtistLoading = false) }
                    }
                } catch (e: Exception) {
                    _uiState.update { it.copy(isExactArtistLoading = false) }
                }
            }

            // Fetch Online Search
            launch {
                try {
                    val localSongs = songDao.searchSongs(query).map { Song(it.id, it.title, it.artist, it.album, it.albumArt, source = it.source) }
                    if (localSongs.isNotEmpty()) {
                        _uiState.update { it.copy(songs = SearchSectionState(data = localSongs, isLoading = true)) }
                    }

                    val res = onlineRepo.searchOnline(query)
                    res.onSuccess { response ->
                        val combinedSongs = (response.songs + localSongs).distinctBy { "${it.title.lowercase()}_${it.artist.lowercase()}" }
                        if (response.songs.isNotEmpty()) {
                            songDao.insertSongs(response.songs.map { SongEntity(it.id, it.title, it.artist, it.album, it.albumArt, it.source) })
                        }
                        _uiState.update {
                            it.copy(
                                songs = SearchSectionState(data = combinedSongs, isLoading = false),
                                albums = SearchSectionState(data = response.albums, isLoading = false),
                                artists = SearchSectionState(data = response.artists, isLoading = false),
                                onlinePlaylists = SearchSectionState(data = response.playlists, isLoading = false)
                            )
                        }
                    }.onFailure {
                        _uiState.update {
                            it.copy(
                                songs = it.songs.copy(isLoading = false, isError = localSongs.isEmpty()),
                                albums = it.albums.copy(isLoading = false, isError = true),
                                artists = it.artists.copy(isLoading = false, isError = true),
                                onlinePlaylists = it.onlinePlaylists.copy(isLoading = false, isError = true)
                            )
                        }
                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            songs = it.songs.copy(isLoading = false, isError = true),
                            albums = it.albums.copy(isLoading = false, isError = true),
                            artists = it.artists.copy(isLoading = false, isError = true),
                            onlinePlaylists = it.onlinePlaylists.copy(isLoading = false, isError = true)
                        )
                    }
                }
            }
        }
    }

    fun playSong(song: Song, contextSongs: List<Song>) {
        saveRecentSearch(_uiState.value.query)
        musicPlayerManager.playSongFromList(song, contextSongs)
    }
}
