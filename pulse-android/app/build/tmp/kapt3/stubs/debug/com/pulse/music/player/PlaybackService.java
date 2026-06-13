package com.pulse.music.player;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0010H\u0016J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0012\u0010\u0015\u001a\u00020\u00102\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u00020\b8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/pulse/music/player/PlaybackService;", "Landroidx/media3/session/MediaSessionService;", "()V", "jamSessionManager", "Lcom/pulse/music/ui/jam/JamSessionManager;", "mediaSession", "Landroidx/media3/session/MediaSession;", "musicPlayerManager", "Lcom/pulse/music/player/MusicPlayerManager;", "getMusicPlayerManager", "()Lcom/pulse/music/player/MusicPlayerManager;", "setMusicPlayerManager", "(Lcom/pulse/music/player/MusicPlayerManager;)V", "scope", "Lkotlinx/coroutines/CoroutineScope;", "onCreate", "", "onDestroy", "onGetSession", "controllerInfo", "Landroidx/media3/session/MediaSession$ControllerInfo;", "onTaskRemoved", "rootIntent", "Landroid/content/Intent;", "app_debug"})
public final class PlaybackService extends androidx.media3.session.MediaSessionService {
    @javax.inject.Inject()
    public com.pulse.music.player.MusicPlayerManager musicPlayerManager;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.ui.jam.JamSessionManager jamSessionManager = null;
    @org.jetbrains.annotations.Nullable()
    private androidx.media3.session.MediaSession mediaSession;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    
    public PlaybackService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pulse.music.player.MusicPlayerManager getMusicPlayerManager() {
        return null;
    }
    
    public final void setMusicPlayerManager(@org.jetbrains.annotations.NotNull()
    com.pulse.music.player.MusicPlayerManager p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public androidx.media3.session.MediaSession onGetSession(@org.jetbrains.annotations.NotNull()
    androidx.media3.session.MediaSession.ControllerInfo controllerInfo) {
        return null;
    }
    
    @java.lang.Override()
    public void onTaskRemoved(@org.jetbrains.annotations.Nullable()
    android.content.Intent rootIntent) {
    }
}