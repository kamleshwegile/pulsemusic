package com.pulse.music.ui.lockscreen;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000J\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u001aZ\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0007\u001at\u0010\r\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u0006\u0010\u0013\u001a\u00020\u0014H\u0007\u001a\u001e\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u000fH\u0002\u00a8\u0006\u001c"}, d2 = {"CompactLockScreenCard", "", "song", "Lcom/pulse/music/domain/Song;", "isPlaying", "", "onPlayPause", "Lkotlin/Function0;", "onNext", "onPrev", "onClick", "modifier", "Landroidx/compose/ui/Modifier;", "ExpandedLockScreenPlayer", "progress", "", "onSeek", "Lkotlin/Function1;", "onCollapse", "expansionProgress", "", "LockScreenPlayer", "musicPlayerManager", "Lcom/pulse/music/player/MusicPlayerManager;", "onUnlock", "formatTime", "", "ms", "app_debug"})
public final class LockScreenPlayerKt {
    
    @androidx.compose.runtime.Composable()
    public static final void LockScreenPlayer(@org.jetbrains.annotations.NotNull()
    com.pulse.music.player.MusicPlayerManager musicPlayerManager, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onUnlock) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CompactLockScreenCard(@org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Song song, boolean isPlaying, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPlayPause, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNext, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPrev, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ExpandedLockScreenPlayer(@org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Song song, boolean isPlaying, long progress, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPlayPause, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNext, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPrev, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onSeek, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCollapse, float expansionProgress) {
    }
    
    private static final java.lang.String formatTime(long ms) {
        return null;
    }
}