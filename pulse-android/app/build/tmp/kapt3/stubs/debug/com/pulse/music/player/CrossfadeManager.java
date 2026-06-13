package com.pulse.music.player;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000fJ\u000e\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000fJ\u0006\u0010\u0017\u001a\u00020\nJ.\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fJ\u0016\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020\u000fJ\u000e\u0010#\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u000fR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0011\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006$"}, d2 = {"Lcom/pulse/music/player/CrossfadeManager;", "", "()V", "crossfadeSecs", "", "getCrossfadeSecs", "()I", "setCrossfadeSecs", "(I)V", "<set-?>", "", "crossfadeStartTime", "getCrossfadeStartTime", "()J", "fadingPlayer", "Landroidx/media3/exoplayer/ExoPlayer;", "", "isCrossfading", "()Z", "abortCrossfade", "", "activePlayer", "endCrossfade", "getCrossfadeMs", "shouldStartCrossfade", "timeLeftMs", "currentPositionMs", "isLastTrack", "repeatMode", "Lcom/pulse/music/player/RepeatMode;", "sleepTimerMode", "Lcom/pulse/music/player/SleepTimerMode;", "startCrossfade", "current", "next", "updateCrossfade", "app_debug"})
public final class CrossfadeManager {
    private int crossfadeSecs = 0;
    private boolean isCrossfading = false;
    private long crossfadeStartTime = 0L;
    @org.jetbrains.annotations.Nullable()
    private androidx.media3.exoplayer.ExoPlayer fadingPlayer;
    
    public CrossfadeManager() {
        super();
    }
    
    public final int getCrossfadeSecs() {
        return 0;
    }
    
    public final void setCrossfadeSecs(int p0) {
    }
    
    public final boolean isCrossfading() {
        return false;
    }
    
    public final long getCrossfadeStartTime() {
        return 0L;
    }
    
    public final long getCrossfadeMs() {
        return 0L;
    }
    
    public final boolean shouldStartCrossfade(long timeLeftMs, long currentPositionMs, boolean isLastTrack, @org.jetbrains.annotations.NotNull()
    com.pulse.music.player.RepeatMode repeatMode, @org.jetbrains.annotations.NotNull()
    com.pulse.music.player.SleepTimerMode sleepTimerMode) {
        return false;
    }
    
    public final void startCrossfade(@org.jetbrains.annotations.NotNull()
    androidx.media3.exoplayer.ExoPlayer current, @org.jetbrains.annotations.NotNull()
    androidx.media3.exoplayer.ExoPlayer next) {
    }
    
    public final boolean updateCrossfade(@org.jetbrains.annotations.NotNull()
    androidx.media3.exoplayer.ExoPlayer activePlayer) {
        return false;
    }
    
    public final void endCrossfade(@org.jetbrains.annotations.NotNull()
    androidx.media3.exoplayer.ExoPlayer activePlayer) {
    }
    
    public final void abortCrossfade(@org.jetbrains.annotations.NotNull()
    androidx.media3.exoplayer.ExoPlayer activePlayer) {
    }
}