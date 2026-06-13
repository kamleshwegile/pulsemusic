package com.pulse.music.ui.playlist;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\rJ\u0014\u0010\u001f\u001a\u00020\u001c2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00100\u0019J\u001c\u0010!\u001a\u00020\u001c2\u0006\u0010\"\u001a\u00020\u00102\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00100\u0019J\u000e\u0010#\u001a\u00020\u001c2\u0006\u0010$\u001a\u00020%J\u000e\u0010&\u001a\u00020\u001c2\u0006\u0010\'\u001a\u00020%J\u0014\u0010(\u001a\u00020\u001c2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00100\u0019J\u0006\u0010)\u001a\u00020\u001cJ\u0006\u0010*\u001a\u00020\u001cR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00160\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00190\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0012\u00a8\u0006+"}, d2 = {"Lcom/pulse/music/ui/playlist/PlaylistViewModel;", "Landroidx/lifecycle/ViewModel;", "playlistRepository", "Lcom/pulse/music/data/repository/PlaylistRepository;", "likedSongsRepository", "Lcom/pulse/music/data/repository/LikedSongsRepository;", "musicPlayerManager", "Lcom/pulse/music/player/MusicPlayerManager;", "(Lcom/pulse/music/data/repository/PlaylistRepository;Lcom/pulse/music/data/repository/LikedSongsRepository;Lcom/pulse/music/player/MusicPlayerManager;)V", "_isFavorite", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_playlistId", "", "currentSong", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/pulse/music/domain/Song;", "getCurrentSong", "()Lkotlinx/coroutines/flow/StateFlow;", "isFavorite", "isPlaying", "playlistInfo", "Lcom/pulse/music/data/local/PlaylistEntity;", "getPlaylistInfo", "playlistSongs", "", "getPlaylistSongs", "deletePlaylist", "", "loadPlaylist", "id", "playAll", "contextSongs", "playSong", "song", "removeSong", "songId", "", "renamePlaylist", "newName", "shufflePlay", "toggleFavorite", "togglePlayPause", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class PlaylistViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.repository.PlaylistRepository playlistRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.repository.LikedSongsRepository likedSongsRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.player.MusicPlayerManager musicPlayerManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _playlistId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pulse.music.data.local.PlaylistEntity> playlistInfo = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.pulse.music.domain.Song>> playlistSongs = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pulse.music.domain.Song> currentSong = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPlaying = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isFavorite = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isFavorite = null;
    
    @javax.inject.Inject()
    public PlaylistViewModel(@org.jetbrains.annotations.NotNull()
    com.pulse.music.data.repository.PlaylistRepository playlistRepository, @org.jetbrains.annotations.NotNull()
    com.pulse.music.data.repository.LikedSongsRepository likedSongsRepository, @org.jetbrains.annotations.NotNull()
    com.pulse.music.player.MusicPlayerManager musicPlayerManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pulse.music.data.local.PlaylistEntity> getPlaylistInfo() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.pulse.music.domain.Song>> getPlaylistSongs() {
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
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isFavorite() {
        return null;
    }
    
    public final void loadPlaylist(int id) {
    }
    
    public final void playSong(@org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Song song, @org.jetbrains.annotations.NotNull()
    java.util.List<com.pulse.music.domain.Song> contextSongs) {
    }
    
    public final void playAll(@org.jetbrains.annotations.NotNull()
    java.util.List<com.pulse.music.domain.Song> contextSongs) {
    }
    
    public final void togglePlayPause() {
    }
    
    public final void shufflePlay(@org.jetbrains.annotations.NotNull()
    java.util.List<com.pulse.music.domain.Song> contextSongs) {
    }
    
    public final void toggleFavorite() {
    }
    
    public final void removeSong(@org.jetbrains.annotations.NotNull()
    java.lang.String songId) {
    }
    
    public final void renamePlaylist(@org.jetbrains.annotations.NotNull()
    java.lang.String newName) {
    }
    
    public final void deletePlaylist() {
    }
}