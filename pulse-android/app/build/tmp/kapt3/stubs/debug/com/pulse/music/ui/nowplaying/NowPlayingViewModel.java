package com.pulse.music.ui.nowplaying;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0010\u0007\n\u0002\b\u0010\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0016\u00107\u001a\u0002082\u0006\u00109\u001a\u00020\u00152\u0006\u0010:\u001a\u00020\u0010J\u000e\u0010;\u001a\u0002082\u0006\u0010:\u001a\u00020\u0010J\u0016\u0010<\u001a\u0002082\u0006\u0010=\u001a\u00020\u00122\u0006\u0010:\u001a\u00020\u0010J\u0006\u0010>\u001a\u000208J\"\u0010?\u001a\u0002082\u0006\u0010@\u001a\u00020\u00122\u0006\u0010A\u001a\u00020\u00122\n\b\u0002\u0010B\u001a\u0004\u0018\u00010\u0012J\u0006\u0010C\u001a\u00020DJ\u000e\u0010E\u001a\u0002082\u0006\u0010F\u001a\u00020\u0015J\u000e\u0010G\u001a\u0002082\u0006\u0010:\u001a\u00020\u0010J\u000e\u0010H\u001a\u0002082\u0006\u0010F\u001a\u00020\u0015J\u000e\u0010I\u001a\u0002082\u0006\u0010J\u001a\u00020\u0019J\u000e\u0010K\u001a\u0002082\u0006\u0010L\u001a\u000203J\u000e\u0010M\u001a\u0002082\u0006\u0010N\u001a\u00020DJ\u0006\u0010O\u001a\u000208J\u0006\u0010P\u001a\u000208J\u0006\u0010Q\u001a\u000208J\u0006\u0010R\u001a\u000208J\u0006\u0010S\u001a\u000208R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0017R\u0019\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00190\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0017R\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0017R\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0017R\u0017\u0010#\u001a\b\u0012\u0004\u0012\u00020\"0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0017R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010$\u001a\b\u0012\u0004\u0012\u00020\r0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\'0\u000f0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0017R\u001d\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0017R\u001d\u0010+\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0017R\u0017\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u00100\u001a\b\u0012\u0004\u0012\u00020\"0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010\u0017R\u0017\u00102\u001a\b\u0012\u0004\u0012\u0002030\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u0010\u0017R\u0017\u00105\u001a\b\u0012\u0004\u0012\u00020\u00190\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010\u0017\u00a8\u0006T"}, d2 = {"Lcom/pulse/music/ui/nowplaying/NowPlayingViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/pulse/music/data/repository/MusicRepository;", "musicPlayerManager", "Lcom/pulse/music/player/MusicPlayerManager;", "likedSongsRepository", "Lcom/pulse/music/data/repository/LikedSongsRepository;", "playlistRepository", "Lcom/pulse/music/data/repository/PlaylistRepository;", "(Lcom/pulse/music/data/repository/MusicRepository;Lcom/pulse/music/player/MusicPlayerManager;Lcom/pulse/music/data/repository/LikedSongsRepository;Lcom/pulse/music/data/repository/PlaylistRepository;)V", "_lyricsState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/pulse/music/ui/nowplaying/LyricsState;", "_rawRecommendations", "", "Lcom/pulse/music/domain/Song;", "currentFetchingSongId", "", "currentIndex", "Lkotlinx/coroutines/flow/StateFlow;", "", "getCurrentIndex", "()Lkotlinx/coroutines/flow/StateFlow;", "currentPosition", "", "getCurrentPosition", "currentSong", "getCurrentSong", "duration", "getDuration", "highlightedLine", "getHighlightedLine", "isCurrentSongLiked", "", "isPlaying", "lyricsState", "getLyricsState", "playlists", "Lcom/pulse/music/data/local/PlaylistEntity;", "getPlaylists", "queue", "getQueue", "recommendations", "getRecommendations", "repeatMode", "Lcom/pulse/music/player/RepeatMode;", "getRepeatMode", "shuffleEnabled", "getShuffleEnabled", "sleepTimerMode", "Lcom/pulse/music/player/SleepTimerMode;", "getSleepTimerMode", "sleepTimerTimeLeft", "getSleepTimerTimeLeft", "addSongToPlaylist", "", "playlistId", "song", "addToQueue", "createPlaylistAndAddSong", "name", "cycleRepeatMode", "fetchLyricsAndRecommendations", "title", "artist", "songId", "getVolumeFraction", "", "playFromQueue", "index", "playSong", "removeFromQueue", "seekTo", "positionMs", "setSleepTimer", "mode", "setVolume", "fraction", "skipNext", "skipPrevious", "toggleLike", "togglePlayPause", "toggleShuffle", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class NowPlayingViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.repository.MusicRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.player.MusicPlayerManager musicPlayerManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.repository.LikedSongsRepository likedSongsRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.repository.PlaylistRepository playlistRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.pulse.music.data.local.PlaylistEntity>> playlists = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pulse.music.domain.Song> currentSong = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPlaying = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Long> currentPosition = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Long> duration = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> shuffleEnabled = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pulse.music.player.RepeatMode> repeatMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.pulse.music.domain.Song>> queue = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> currentIndex = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.pulse.music.ui.nowplaying.LyricsState> _lyricsState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pulse.music.ui.nowplaying.LyricsState> lyricsState = null;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String currentFetchingSongId;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isCurrentSongLiked = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> highlightedLine = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pulse.music.player.SleepTimerMode> sleepTimerMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Long> sleepTimerTimeLeft = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.pulse.music.domain.Song>> _rawRecommendations = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.pulse.music.domain.Song>> recommendations = null;
    
    @javax.inject.Inject()
    public NowPlayingViewModel(@org.jetbrains.annotations.NotNull()
    com.pulse.music.data.repository.MusicRepository repository, @org.jetbrains.annotations.NotNull()
    com.pulse.music.player.MusicPlayerManager musicPlayerManager, @org.jetbrains.annotations.NotNull()
    com.pulse.music.data.repository.LikedSongsRepository likedSongsRepository, @org.jetbrains.annotations.NotNull()
    com.pulse.music.data.repository.PlaylistRepository playlistRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.pulse.music.data.local.PlaylistEntity>> getPlaylists() {
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
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Long> getCurrentPosition() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Long> getDuration() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getShuffleEnabled() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pulse.music.player.RepeatMode> getRepeatMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.pulse.music.domain.Song>> getQueue() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getCurrentIndex() {
        return null;
    }
    
    public final void createPlaylistAndAddSong(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Song song) {
    }
    
    public final void addSongToPlaylist(int playlistId, @org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Song song) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pulse.music.ui.nowplaying.LyricsState> getLyricsState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isCurrentSongLiked() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getHighlightedLine() {
        return null;
    }
    
    public final void togglePlayPause() {
    }
    
    public final void seekTo(long positionMs) {
    }
    
    public final void skipNext() {
    }
    
    public final void skipPrevious() {
    }
    
    public final void toggleShuffle() {
    }
    
    public final void cycleRepeatMode() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pulse.music.player.SleepTimerMode> getSleepTimerMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Long> getSleepTimerTimeLeft() {
        return null;
    }
    
    public final void setSleepTimer(@org.jetbrains.annotations.NotNull()
    com.pulse.music.player.SleepTimerMode mode) {
    }
    
    public final float getVolumeFraction() {
        return 0.0F;
    }
    
    public final void setVolume(float fraction) {
    }
    
    public final void playFromQueue(int index) {
    }
    
    public final void removeFromQueue(int index) {
    }
    
    public final void addToQueue(@org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Song song) {
    }
    
    public final void toggleLike() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.pulse.music.domain.Song>> getRecommendations() {
        return null;
    }
    
    public final void playSong(@org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Song song) {
    }
    
    public final void fetchLyricsAndRecommendations(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String artist, @org.jetbrains.annotations.Nullable()
    java.lang.String songId) {
    }
}