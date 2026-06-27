package com.pulse.music;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\u001az\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\u0010\b\u0002\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u001aP\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u000b2\u0010\b\u0002\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u000b2\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u0007\u00a8\u0006\u0014"}, d2 = {"MiniPlayer", "", "queue", "", "Lcom/pulse/music/domain/Song;", "currentIndex", "", "isPlaying", "", "isShuffleEnabled", "onTogglePlay", "Lkotlin/Function0;", "onToggleShuffle", "onSkipNext", "onSkipPrev", "onClick", "MiniPlayerCard", "song", "modifier", "Landroidx/compose/ui/Modifier;", "app_debug"})
@kotlin.OptIn(markerClass = {androidx.compose.foundation.ExperimentalFoundationApi.class})
public final class MainActivityKt {
    
    @androidx.compose.runtime.Composable()
    public static final void MiniPlayer(@org.jetbrains.annotations.NotNull()
    java.util.List<com.pulse.music.domain.Song> queue, int currentIndex, boolean isPlaying, boolean isShuffleEnabled, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onTogglePlay, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onToggleShuffle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSkipNext, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSkipPrev, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void MiniPlayerCard(@org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Song song, boolean isPlaying, boolean isShuffleEnabled, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onTogglePlay, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onToggleShuffle, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
}