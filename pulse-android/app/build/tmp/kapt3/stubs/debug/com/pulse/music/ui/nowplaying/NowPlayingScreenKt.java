package com.pulse.music.ui.nowplaying;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\u001a8\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00060\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\rH\u0007\u001aD\u0010\u000e\u001a\u00020\u00062\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\b2\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\r2\u0016\b\u0002\u0010\u0013\u001a\u0010\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u000bH\u0007\u001a\u0088\u0002\u0010\u0015\u001a\u00020\u00062\b\b\u0002\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\u00142\u0006\u0010\"\u001a\u00020\u001d2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u001d2\u0006\u0010&\u001a\u00020\u001f2\u0012\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u00060\u000b2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00060\r2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00060\r2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00060\r2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00060\r2\u0012\u0010,\u001a\u000e\u0012\u0004\u0012\u00020-\u0012\u0004\u0012\u00020\u00060\u000b2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u00020-\u0012\u0004\u0012\u00020\u00060\u000b2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00060\r2\f\u00100\u001a\b\u0012\u0004\u0012\u0002010\b2\u0012\u00102\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020-0\u000bH\u0007\u001a^\u00103\u001a\u00020\u00062\b\b\u0002\u0010\u0018\u001a\u00020\u00192\u000e\b\u0002\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00060\r2\u0014\b\u0002\u0010,\u001a\u000e\u0012\u0004\u0012\u00020-\u0012\u0004\u0012\u00020\u00060\u000b2\u0014\b\u0002\u0010.\u001a\u000e\u0012\u0004\u0012\u00020-\u0012\u0004\u0012\u00020\u00060\u000b2\u000e\b\u0002\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00060\rH\u0007\u001a\u008a\u0001\u00104\u001a\u00020\u00062\f\u00105\u001a\b\u0012\u0004\u0012\u00020\u001b0\b2\f\u00106\u001a\b\u0012\u0004\u0012\u00020\u001b0\b2\u0006\u00107\u001a\u00020\u00122\u0012\u00108\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00060\u000b2\u0012\u00109\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00060\u000b2\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u00060\u000b2\u0012\u0010;\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u00060\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\rH\u0007\u001a&\u0010<\u001a\u00020\u00062\u0006\u0010\u001e\u001a\u00020\u001f2\u0014\b\u0002\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u00060\u000bH\u0007\"\u0013\u0010\u0000\u001a\u00020\u0001\u00a2\u0006\n\n\u0002\u0010\u0004\u001a\u0004\b\u0002\u0010\u0003\u00a8\u0006="}, d2 = {"PulseRed", "Landroidx/compose/ui/graphics/Color;", "getPulseRed", "()J", "J", "DevicePanel", "", "devices", "", "Landroid/media/AudioDeviceInfo;", "onSelectDevice", "Lkotlin/Function1;", "onClose", "Lkotlin/Function0;", "LyricsPanel", "lyrics", "Lcom/pulse/music/domain/LyricLine;", "highlightedIndex", "", "onSeek", "", "MainPlayerContent", "modifier", "Landroidx/compose/ui/Modifier;", "viewModel", "Lcom/pulse/music/ui/nowplaying/NowPlayingViewModel;", "currentSong", "Lcom/pulse/music/domain/Song;", "isPlaying", "", "progress", "", "currentPosition", "duration", "shuffleEnabled", "repeatMode", "Lcom/pulse/music/player/RepeatMode;", "isLiked", "volume", "onVolumeChange", "showQueue", "showDevices", "showLyrics", "onBack", "onNavigateToArtist", "", "onNavigateToAlbum", "onNavigateToJam", "playlists", "Lcom/pulse/music/data/local/PlaylistEntity;", "formatTime", "NowPlayingScreen", "QueuePanel", "queue", "recommendations", "currentIndex", "onSongClick", "onRemove", "onAddToQueue", "onPlayRecommendation", "WaveformSeekbar", "app_debug"})
public final class NowPlayingScreenKt {
    private static final long PulseRed = 0L;
    
    public static final long getPulseRed() {
        return 0L;
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void NowPlayingScreen(@org.jetbrains.annotations.NotNull()
    com.pulse.music.ui.nowplaying.NowPlayingViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToArtist, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToAlbum, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToJam) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void MainPlayerContent(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    com.pulse.music.ui.nowplaying.NowPlayingViewModel viewModel, @org.jetbrains.annotations.Nullable()
    com.pulse.music.domain.Song currentSong, boolean isPlaying, float progress, long currentPosition, long duration, boolean shuffleEnabled, @org.jetbrains.annotations.NotNull()
    com.pulse.music.player.RepeatMode repeatMode, boolean isLiked, float volume, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onVolumeChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> showQueue, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> showDevices, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> showLyrics, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToArtist, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToAlbum, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToJam, @org.jetbrains.annotations.NotNull()
    java.util.List<com.pulse.music.data.local.PlaylistEntity> playlists, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Long, java.lang.String> formatTime) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void DevicePanel(@org.jetbrains.annotations.NotNull()
    java.util.List<android.media.AudioDeviceInfo> devices, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super android.media.AudioDeviceInfo, kotlin.Unit> onSelectDevice, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClose) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void WaveformSeekbar(float progress, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onSeek) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void QueuePanel(@org.jetbrains.annotations.NotNull()
    java.util.List<com.pulse.music.domain.Song> queue, @org.jetbrains.annotations.NotNull()
    java.util.List<com.pulse.music.domain.Song> recommendations, int currentIndex, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onSongClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onRemove, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.pulse.music.domain.Song, kotlin.Unit> onAddToQueue, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.pulse.music.domain.Song, kotlin.Unit> onPlayRecommendation, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClose) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void LyricsPanel(@org.jetbrains.annotations.NotNull()
    java.util.List<com.pulse.music.domain.LyricLine> lyrics, int highlightedIndex, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClose, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onSeek) {
    }
}