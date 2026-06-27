package com.pulse.music.player;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00be\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b \b\u0007\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010^\u001a\u00020_H\u0002J\u0010\u0010`\u001a\u00020a2\u0006\u0010b\u001a\u00020\u000fH\u0002J\b\u0010c\u001a\u00020\tH\u0003J\u0010\u0010d\u001a\u00020e2\u0006\u0010f\u001a\u00020\tH\u0002J\u0006\u0010g\u001a\u00020_J\u000e\u0010h\u001a\u00020_2\u0006\u0010b\u001a\u00020\u000fJ\u0006\u0010i\u001a\u00020jJ$\u0010k\u001a\u00020_2\u0006\u0010b\u001a\u00020\u000f2\b\b\u0002\u0010l\u001a\u00020\r2\b\b\u0002\u0010m\u001a\u00020\u0012H\u0007J\u001c\u0010n\u001a\u00020_2\u0006\u0010b\u001a\u00020\u000f2\f\u0010o\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0014J\b\u0010p\u001a\u00020_H\u0002J\u0006\u0010q\u001a\u00020_J\u000e\u0010r\u001a\u00020_2\u0006\u0010s\u001a\u00020\u000bJ\u0006\u0010t\u001a\u00020_J\u0016\u0010u\u001a\u00020M2\u0006\u0010b\u001a\u00020\u000fH\u0082@\u00a2\u0006\u0002\u0010vJ\u0010\u0010w\u001a\u00020_2\u0006\u0010b\u001a\u00020\u000fH\u0002J\u000e\u0010x\u001a\u00020_2\u0006\u0010y\u001a\u00020\rJ\u000e\u0010z\u001a\u00020_2\u0006\u0010{\u001a\u00020\u0012J\u000e\u0010|\u001a\u00020_2\u0006\u0010}\u001a\u00020\u0019J\u000e\u0010~\u001a\u00020_2\u0006\u0010\u007f\u001a\u00020jJ\u0007\u0010\u0080\u0001\u001a\u00020_J\u0007\u0010\u0081\u0001\u001a\u00020_J\t\u0010\u0082\u0001\u001a\u00020_H\u0002J\u0007\u0010\u0083\u0001\u001a\u00020_J\t\u0010\u0084\u0001\u001a\u00020_H\u0002J\t\u0010\u0085\u0001\u001a\u00020_H\u0002J\u0007\u0010\u0086\u0001\u001a\u00020_J\u0007\u0010\u0087\u0001\u001a\u00020_J\"\u0010\u0088\u0001\u001a\u00020_2\u0006\u0010b\u001a\u00020\u000f2\u0006\u0010s\u001a\u00020\u000b2\u0007\u0010\u0089\u0001\u001a\u00020\tH\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00140\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00120\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\r0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\t0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\'\u001a\u00020\u000b8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b(\u0010)R\u0017\u0010*\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001eR\u0017\u0010,\u001a\b\u0012\u0004\u0012\u00020\r0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\u001eR\u0019\u0010.\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001eR\u0017\u00100\u001a\b\u0012\u0004\u0012\u00020\r0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010\u001eR\u0010\u00102\u001a\u0004\u0018\u000103X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u00104\u001a\u00020\u00128BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b4\u00105R\u0017\u00106\u001a\b\u0012\u0004\u0012\u00020\u00120\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010\u001eR\u0011\u00107\u001a\u00020\u000b8F\u00a2\u0006\u0006\u001a\u0004\b8\u0010)R\u0016\u00109\u001a\n\u0012\u0004\u0012\u00020;\u0018\u00010:X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010<\u001a\u00020\t8F\u00a2\u0006\u0006\u001a\u0004\b=\u0010>R\u001b\u0010?\u001a\u00020\t8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\bA\u0010B\u001a\u0004\b@\u0010>R\u001b\u0010C\u001a\u00020\t8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\bE\u0010B\u001a\u0004\bD\u0010>R\u0010\u0010F\u001a\u0004\u0018\u00010 X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001d\u0010G\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00140\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\bH\u0010\u001eR\u0017\u0010I\u001a\b\u0012\u0004\u0012\u00020\u00160\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\bJ\u0010\u001eR\u001a\u0010K\u001a\u000e\u0012\u0004\u0012\u00020M\u0012\u0004\u0012\u00020M0LX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010N\u001a\u00020OX\u0082\u0004\u00a2\u0006\u0002\n\u0000R!\u0010P\u001a\u00020Q8BX\u0083\u0084\u0002\u00a2\u0006\u0012\n\u0004\bV\u0010B\u0012\u0004\bR\u0010S\u001a\u0004\bT\u0010UR\u0017\u0010W\u001a\b\u0012\u0004\u0012\u00020\u00120\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\bX\u0010\u001eR\u0010\u0010Y\u001a\u0004\u0018\u00010 X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010Z\u001a\b\u0012\u0004\u0012\u00020\u00190\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b[\u0010\u001eR\u0017\u0010\\\u001a\b\u0012\u0004\u0012\u00020\r0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b]\u0010\u001e\u00a8\u0006\u008a\u0001"}, d2 = {"Lcom/pulse/music/player/MusicPlayerManager;", "", "context", "Landroid/content/Context;", "onlineRepo", "Lcom/pulse/music/data/repository/OnlineMusicRepository;", "(Landroid/content/Context;Lcom/pulse/music/data/repository/OnlineMusicRepository;)V", "_activePlayerFlow", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Landroidx/media3/exoplayer/ExoPlayer;", "_currentIndex", "", "_currentPosition", "", "_currentSong", "Lcom/pulse/music/domain/Song;", "_duration", "_isPlaying", "", "_queue", "", "_repeatMode", "Lcom/pulse/music/player/RepeatMode;", "_shuffleEnabled", "_sleepTimerMode", "Lcom/pulse/music/player/SleepTimerMode;", "_sleepTimerTimeLeft", "activePlayerFlow", "Lkotlinx/coroutines/flow/StateFlow;", "getActivePlayerFlow", "()Lkotlinx/coroutines/flow/StateFlow;", "artworkJob", "Lkotlinx/coroutines/Job;", "audioFocusChangeListener", "Landroid/media/AudioManager$OnAudioFocusChangeListener;", "audioManager", "Landroid/media/AudioManager;", "crossfadeManager", "Lcom/pulse/music/player/CrossfadeManager;", "crossfadeSecs", "getCrossfadeSecs", "()I", "currentIndex", "getCurrentIndex", "currentPosition", "getCurrentPosition", "currentSong", "getCurrentSong", "duration", "getDuration", "focusRequest", "Landroid/media/AudioFocusRequest;", "isCrossfading", "()Z", "isPlaying", "maxVolume", "getMaxVolume", "mediaControllerFuture", "Lcom/google/common/util/concurrent/ListenableFuture;", "Landroidx/media3/session/MediaController;", "player", "getPlayer", "()Landroidx/media3/exoplayer/ExoPlayer;", "player1", "getPlayer1", "player1$delegate", "Lkotlin/Lazy;", "player2", "getPlayer2", "player2$delegate", "positionPollingJob", "queue", "getQueue", "repeatMode", "getRepeatMode", "resolvedUrlCache", "Ljava/util/concurrent/ConcurrentHashMap;", "", "scope", "Lkotlinx/coroutines/CoroutineScope;", "sharedDataSourceFactory", "Landroidx/media3/datasource/ResolvingDataSource$Factory;", "getSharedDataSourceFactory$annotations", "()V", "getSharedDataSourceFactory", "()Landroidx/media3/datasource/ResolvingDataSource$Factory;", "sharedDataSourceFactory$delegate", "shuffleEnabled", "getShuffleEnabled", "sleepTimerJob", "sleepTimerMode", "getSleepTimerMode", "sleepTimerTimeLeft", "getSleepTimerTimeLeft", "abortCrossfade", "", "buildMediaItem", "Landroidx/media3/common/MediaItem;", "song", "createExoPlayer", "createPlayerListener", "Landroidx/media3/common/Player$Listener;", "targetPlayer", "cycleRepeatMode", "enqueue", "getVolumeFraction", "", "playSong", "startPositionMs", "playWhenReady", "playSongFromList", "allSongs", "preBufferAlternatePlayer", "release", "removeFromQueue", "index", "reset", "resolveSongSource", "(Lcom/pulse/music/domain/Song;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveRecentPlay", "seekTo", "positionMs", "setShuffleEnabled", "enabled", "setSleepTimer", "mode", "setVolumeFraction", "fraction", "skipNext", "skipPrevious", "startPositionPolling", "stopAndClear", "stopPositionPolling", "syncRepeatMode", "togglePlayPause", "toggleShuffle", "updateArtworkData", "playerToUpdate", "app_debug"})
public final class MusicPlayerManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.repository.OnlineMusicRepository onlineRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull()
    private final android.media.AudioManager audioManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy sharedDataSourceFactory$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy player1$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy player2$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<androidx.media3.exoplayer.ExoPlayer> _activePlayerFlow = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<androidx.media3.exoplayer.ExoPlayer> activePlayerFlow = null;
    @org.jetbrains.annotations.Nullable()
    private com.google.common.util.concurrent.ListenableFuture<androidx.media3.session.MediaController> mediaControllerFuture;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.player.CrossfadeManager crossfadeManager = null;
    @org.jetbrains.annotations.NotNull()
    private final android.media.AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = null;
    @org.jetbrains.annotations.Nullable()
    private final android.media.AudioFocusRequest focusRequest = null;
    @org.jetbrains.annotations.NotNull()
    private kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.pulse.music.domain.Song>> _queue;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.pulse.music.domain.Song>> queue = null;
    @org.jetbrains.annotations.NotNull()
    private kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _currentIndex;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> currentIndex = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.String> resolvedUrlCache = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.pulse.music.domain.Song> _currentSong = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pulse.music.domain.Song> currentSong = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isPlaying = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPlaying = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> _currentPosition = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Long> currentPosition = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> _duration = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Long> duration = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _shuffleEnabled = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> shuffleEnabled = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.pulse.music.player.RepeatMode> _repeatMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pulse.music.player.RepeatMode> repeatMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.pulse.music.player.SleepTimerMode> _sleepTimerMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pulse.music.player.SleepTimerMode> sleepTimerMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> _sleepTimerTimeLeft = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Long> sleepTimerTimeLeft = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job sleepTimerJob;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job positionPollingJob;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job artworkJob;
    
    @javax.inject.Inject()
    public MusicPlayerManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.pulse.music.data.repository.OnlineMusicRepository onlineRepo) {
        super();
    }
    
    private final androidx.media3.datasource.ResolvingDataSource.Factory getSharedDataSourceFactory() {
        return null;
    }
    
    @androidx.annotation.OptIn(markerClass = {androidx.media3.common.util.UnstableApi.class})
    @java.lang.Deprecated()
    private static void getSharedDataSourceFactory$annotations() {
    }
    
    @androidx.annotation.OptIn(markerClass = {androidx.media3.common.util.UnstableApi.class})
    private final androidx.media3.exoplayer.ExoPlayer createExoPlayer() {
        return null;
    }
    
    private final androidx.media3.exoplayer.ExoPlayer getPlayer1() {
        return null;
    }
    
    private final androidx.media3.exoplayer.ExoPlayer getPlayer2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<androidx.media3.exoplayer.ExoPlayer> getActivePlayerFlow() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.media3.exoplayer.ExoPlayer getPlayer() {
        return null;
    }
    
    private final int getCrossfadeSecs() {
        return 0;
    }
    
    private final boolean isCrossfading() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.pulse.music.domain.Song>> getQueue() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getCurrentIndex() {
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
    public final kotlinx.coroutines.flow.StateFlow<com.pulse.music.player.SleepTimerMode> getSleepTimerMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Long> getSleepTimerTimeLeft() {
        return null;
    }
    
    public final int getMaxVolume() {
        return 0;
    }
    
    public final float getVolumeFraction() {
        return 0.0F;
    }
    
    public final void setVolumeFraction(float fraction) {
    }
    
    private final void updateArtworkData(com.pulse.music.domain.Song song, int index, androidx.media3.exoplayer.ExoPlayer playerToUpdate) {
    }
    
    private final void startPositionPolling() {
    }
    
    private final void stopPositionPolling() {
    }
    
    private final void preBufferAlternatePlayer() {
    }
    
    public final void reset() {
    }
    
    private final java.lang.Object resolveSongSource(com.pulse.music.domain.Song song, kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    private final androidx.media3.common.Player.Listener createPlayerListener(androidx.media3.exoplayer.ExoPlayer targetPlayer) {
        return null;
    }
    
    /**
     * Play a song and set entire list as queue.
     */
    public final void playSongFromList(@org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Song song, @org.jetbrains.annotations.NotNull()
    java.util.List<com.pulse.music.domain.Song> allSongs) {
    }
    
    /**
     * Play a single song.
     */
    @androidx.annotation.OptIn(markerClass = {androidx.media3.common.util.UnstableApi.class})
    public final void playSong(@org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Song song, long startPositionMs, boolean playWhenReady) {
    }
    
    public final void enqueue(@org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Song song) {
    }
    
    private final androidx.media3.common.MediaItem buildMediaItem(com.pulse.music.domain.Song song) {
        return null;
    }
    
    private final void abortCrossfade() {
    }
    
    /**
     * Toggle between play and pause.
     */
    public final void togglePlayPause() {
    }
    
    /**
     * Seek to [positionMs] within the current track.
     */
    public final void seekTo(long positionMs) {
    }
    
    /**
     * Skip to the next track in the queue.
     */
    public final void skipNext() {
    }
    
    /**
     * Skip to the previous track, or restart if > 3s in.
     */
    public final void skipPrevious() {
    }
    
    /**
     * Toggle shuffle on/off.
     */
    public final void toggleShuffle() {
    }
    
    public final void setShuffleEnabled(boolean enabled) {
    }
    
    private final void syncRepeatMode() {
    }
    
    /**
     * Cycle repeat mode: OFF → ALL → ONE → OFF.
     */
    public final void cycleRepeatMode() {
    }
    
    /**
     * Remove a song from queue by index.
     */
    public final void removeFromQueue(int index) {
    }
    
    public final void setSleepTimer(@org.jetbrains.annotations.NotNull()
    com.pulse.music.player.SleepTimerMode mode) {
    }
    
    public final void stopAndClear() {
    }
    
    /**
     * Release the underlying [ExoPlayer] and cancel coroutines.
     */
    public final void release() {
    }
    
    private final void saveRecentPlay(com.pulse.music.domain.Song song) {
    }
}