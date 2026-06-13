package com.pulse.music.ui.home;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\b\u0007\u0018\u0000 #2\u00020\u0001:\u0001#B1\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0001\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u0010\u0010\u0019\u001a\u00020\u001a2\b\b\u0002\u0010\u001b\u001a\u00020\u001cJ\u001c\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0!J\u000e\u0010\"\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u001fR\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0019\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014\u00a8\u0006$"}, d2 = {"Lcom/pulse/music/ui/home/HomeViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/pulse/music/data/repository/OnlineMusicRepository;", "songDao", "Lcom/pulse/music/data/local/SongDao;", "musicPlayerManager", "Lcom/pulse/music/player/MusicPlayerManager;", "context", "Landroid/content/Context;", "authRepository", "Lcom/pulse/music/data/repository/AuthRepository;", "(Lcom/pulse/music/data/repository/OnlineMusicRepository;Lcom/pulse/music/data/local/SongDao;Lcom/pulse/music/player/MusicPlayerManager;Landroid/content/Context;Lcom/pulse/music/data/repository/AuthRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/pulse/music/ui/home/HomeUiState;", "profilePicUri", "Lkotlinx/coroutines/flow/StateFlow;", "", "getProfilePicUri", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "getUiState", "username", "getUsername", "loadData", "", "background", "", "playSong", "song", "Lcom/pulse/music/domain/Song;", "contextSongs", "", "removeRecentSong", "Companion", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HomeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.repository.OnlineMusicRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.local.SongDao songDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.player.MusicPlayerManager musicPlayerManager = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.Nullable()
    private static com.pulse.music.ui.home.HomeUiState.Success cachedUiState;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.pulse.music.ui.home.HomeUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pulse.music.ui.home.HomeUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> username = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> profilePicUri = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.pulse.music.ui.home.HomeViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public HomeViewModel(@org.jetbrains.annotations.NotNull()
    com.pulse.music.data.repository.OnlineMusicRepository repository, @org.jetbrains.annotations.NotNull()
    com.pulse.music.data.local.SongDao songDao, @org.jetbrains.annotations.NotNull()
    com.pulse.music.player.MusicPlayerManager musicPlayerManager, @dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.pulse.music.data.repository.AuthRepository authRepository) {
        super();
    }
    
    public final void playSong(@org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Song song, @org.jetbrains.annotations.NotNull()
    java.util.List<com.pulse.music.domain.Song> contextSongs) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pulse.music.ui.home.HomeUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getUsername() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getProfilePicUri() {
        return null;
    }
    
    public final void loadData(boolean background) {
    }
    
    public final void removeRecentSong(@org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Song song) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2 = {"Lcom/pulse/music/ui/home/HomeViewModel$Companion;", "", "()V", "cachedUiState", "Lcom/pulse/music/ui/home/HomeUiState$Success;", "getCachedUiState", "()Lcom/pulse/music/ui/home/HomeUiState$Success;", "setCachedUiState", "(Lcom/pulse/music/ui/home/HomeUiState$Success;)V", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.pulse.music.ui.home.HomeUiState.Success getCachedUiState() {
            return null;
        }
        
        public final void setCachedUiState(@org.jetbrains.annotations.Nullable()
        com.pulse.music.ui.home.HomeUiState.Success p0) {
        }
    }
}