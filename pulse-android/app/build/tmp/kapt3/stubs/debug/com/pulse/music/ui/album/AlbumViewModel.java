package com.pulse.music.ui.album;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aJ\u0014\u0010\u001b\u001a\u00020\u00182\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00120\u001dJ\u0006\u0010\u001e\u001a\u00020\u0018J\u0006\u0010\u001f\u001a\u00020\u0018R\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0019\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/pulse/music/ui/album/AlbumViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/pulse/music/data/repository/MusicRepository;", "musicPlayerManager", "Lcom/pulse/music/player/MusicPlayerManager;", "(Lcom/pulse/music/data/repository/MusicRepository;Lcom/pulse/music/player/MusicPlayerManager;)V", "_albumInfo", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/pulse/music/domain/Album;", "_isFavorite", "", "_isLoading", "albumInfo", "Lkotlinx/coroutines/flow/StateFlow;", "getAlbumInfo", "()Lkotlinx/coroutines/flow/StateFlow;", "currentSong", "Lcom/pulse/music/domain/Song;", "getCurrentSong", "isFavorite", "isLoading", "isPlaying", "loadAlbum", "", "id", "", "shufflePlay", "songs", "", "toggleFavorite", "togglePlayPause", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AlbumViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.repository.MusicRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.player.MusicPlayerManager musicPlayerManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.pulse.music.domain.Album> _albumInfo = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pulse.music.domain.Album> albumInfo = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pulse.music.domain.Song> currentSong = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPlaying = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isFavorite = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isFavorite = null;
    
    @javax.inject.Inject()
    public AlbumViewModel(@org.jetbrains.annotations.NotNull()
    com.pulse.music.data.repository.MusicRepository repository, @org.jetbrains.annotations.NotNull()
    com.pulse.music.player.MusicPlayerManager musicPlayerManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pulse.music.domain.Album> getAlbumInfo() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pulse.music.domain.Song> getCurrentSong() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPlaying() {
        return null;
    }
    
    public final void loadAlbum(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isFavorite() {
        return null;
    }
    
    public final void toggleFavorite() {
    }
    
    public final void shufflePlay(@org.jetbrains.annotations.NotNull()
    java.util.List<com.pulse.music.domain.Song> songs) {
    }
    
    public final void togglePlayPause() {
    }
}