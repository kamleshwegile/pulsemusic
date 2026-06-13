package com.pulse.music.ui.search;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\u0007\b\u0007\u0018\u0000 .2\u00020\u0001:\u0001.B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 J\u000e\u0010!\u001a\u00020\u001e2\u0006\u0010\"\u001a\u00020\u0010J\b\u0010#\u001a\u00020\u001eH\u0002J\u0010\u0010$\u001a\u00020\u001e2\u0006\u0010%\u001a\u00020\u0010H\u0002J\u001c\u0010&\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020 0(J\u000e\u0010)\u001a\u00020\u001e2\u0006\u0010%\u001a\u00020\u0010J\u000e\u0010*\u001a\u00020\u001e2\u0006\u0010%\u001a\u00020\u0010J\u000e\u0010+\u001a\u00020\u001e2\u0006\u0010,\u001a\u00020\u0010J\u000e\u0010-\u001a\u00020\u001e2\u0006\u0010%\u001a\u00020\u0010R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001f\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00150\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\r0\u001a\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001c\u00a8\u0006/"}, d2 = {"Lcom/pulse/music/ui/search/SearchViewModel;", "Landroidx/lifecycle/ViewModel;", "onlineRepo", "Lcom/pulse/music/data/repository/OnlineMusicRepository;", "songDao", "Lcom/pulse/music/data/local/SongDao;", "playlistDao", "Lcom/pulse/music/data/local/PlaylistDao;", "musicPlayerManager", "Lcom/pulse/music/player/MusicPlayerManager;", "(Lcom/pulse/music/data/repository/OnlineMusicRepository;Lcom/pulse/music/data/local/SongDao;Lcom/pulse/music/data/local/PlaylistDao;Lcom/pulse/music/player/MusicPlayerManager;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/pulse/music/ui/search/SearchUiState;", "categoryLoading", "Landroidx/compose/runtime/snapshots/SnapshotStateMap;", "", "", "getCategoryLoading", "()Landroidx/compose/runtime/snapshots/SnapshotStateMap;", "categoryPlaylists", "Lcom/pulse/music/domain/Playlist;", "getCategoryPlaylists", "searchJob", "Lkotlinx/coroutines/Job;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addToQueue", "", "song", "Lcom/pulse/music/domain/Song;", "fetchCategoryPlaylist", "categoryQuery", "fetchRecentSearches", "performSearch", "query", "playSong", "contextList", "", "removeRecentSearch", "saveRecentSearch", "setCategory", "category", "setQuery", "Companion", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SearchViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.repository.OnlineMusicRepository onlineRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.local.SongDao songDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.local.PlaylistDao playlistDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.player.MusicPlayerManager musicPlayerManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.pulse.music.ui.search.SearchUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pulse.music.ui.search.SearchUiState> uiState = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job searchJob;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.runtime.snapshots.SnapshotStateMap<java.lang.String, com.pulse.music.domain.Playlist> cachedCategoryPlaylists = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.runtime.snapshots.SnapshotStateMap<java.lang.String, java.lang.Boolean> cachedCategoryLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.snapshots.SnapshotStateMap<java.lang.String, com.pulse.music.domain.Playlist> categoryPlaylists = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.snapshots.SnapshotStateMap<java.lang.String, java.lang.Boolean> categoryLoading = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.pulse.music.ui.search.SearchViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public SearchViewModel(@org.jetbrains.annotations.NotNull()
    com.pulse.music.data.repository.OnlineMusicRepository onlineRepo, @org.jetbrains.annotations.NotNull()
    com.pulse.music.data.local.SongDao songDao, @org.jetbrains.annotations.NotNull()
    com.pulse.music.data.local.PlaylistDao playlistDao, @org.jetbrains.annotations.NotNull()
    com.pulse.music.player.MusicPlayerManager musicPlayerManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pulse.music.ui.search.SearchUiState> getUiState() {
        return null;
    }
    
    public final void setQuery(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    private final void fetchRecentSearches() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.runtime.snapshots.SnapshotStateMap<java.lang.String, com.pulse.music.domain.Playlist> getCategoryPlaylists() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.runtime.snapshots.SnapshotStateMap<java.lang.String, java.lang.Boolean> getCategoryLoading() {
        return null;
    }
    
    public final void fetchCategoryPlaylist(@org.jetbrains.annotations.NotNull()
    java.lang.String categoryQuery) {
    }
    
    public final void saveRecentSearch(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void removeRecentSearch(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void setCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String category) {
    }
    
    private final void performSearch(java.lang.String query) {
    }
    
    public final void playSong(@org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Song song, @org.jetbrains.annotations.NotNull()
    java.util.List<com.pulse.music.domain.Song> contextList) {
    }
    
    public final void addToQueue(@org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Song song) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001f\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\b\u00a8\u0006\f"}, d2 = {"Lcom/pulse/music/ui/search/SearchViewModel$Companion;", "", "()V", "cachedCategoryLoading", "Landroidx/compose/runtime/snapshots/SnapshotStateMap;", "", "", "getCachedCategoryLoading", "()Landroidx/compose/runtime/snapshots/SnapshotStateMap;", "cachedCategoryPlaylists", "Lcom/pulse/music/domain/Playlist;", "getCachedCategoryPlaylists", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.compose.runtime.snapshots.SnapshotStateMap<java.lang.String, com.pulse.music.domain.Playlist> getCachedCategoryPlaylists() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.compose.runtime.snapshots.SnapshotStateMap<java.lang.String, java.lang.Boolean> getCachedCategoryLoading() {
            return null;
        }
    }
}