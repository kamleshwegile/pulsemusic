package com.pulse.music.ui.home;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000F\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a^\u0010\u0006\u001a\u00020\u00012\b\b\u0002\u0010\u0007\u001a\u00020\b2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u000b2\u0014\b\u0002\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u000b2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a \u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00112\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001aL\u0010\u0012\u001a\u00020\u00012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00010\u000b2\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u001a\"\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\f2\u0010\b\u0002\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0005H\u0007\u001a\u0010\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u001eH\u0007\u001a2\u0010\u001f\u001a\u00020\u00012\u0006\u0010 \u001a\u00020\u00152\u0010\b\u0002\u0010!\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00052\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u001e\u0010\"\u001a\u00020\u00012\u0006\u0010 \u001a\u00020\u00152\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\b\u0010#\u001a\u00020\u001eH\u0007\u00a8\u0006$"}, d2 = {"ArtistCard", "", "artist", "Lcom/pulse/music/domain/Artist;", "onClick", "Lkotlin/Function0;", "HomeScreen", "viewModel", "Lcom/pulse/music/ui/home/HomeViewModel;", "onNavigateToNowPlaying", "onNavigateToAlbum", "Lkotlin/Function1;", "", "onNavigateToArtist", "onNavigateToProfile", "PlaylistCard", "playlist", "Lcom/pulse/music/domain/Playlist;", "RecentlyPlayedScreen", "songs", "", "Lcom/pulse/music/domain/Song;", "onBack", "onSongClick", "onRemoveSong", "SectionTitle", "title", "onSeeAll", "ShimmerShelfSkeletonRowItem", "shimmerBrush", "Landroidx/compose/ui/graphics/Brush;", "SongCard", "song", "onRemove", "SongRowItem", "rememberShimmerBrush", "app_debug"})
public final class HomeScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class, androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable()
    public static final void HomeScreen(@org.jetbrains.annotations.NotNull()
    com.pulse.music.ui.home.HomeViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToNowPlaying, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToAlbum, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToArtist, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToProfile) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SectionTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSeeAll) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SongCard(@org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Song song, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRemove, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.ui.graphics.Brush rememberShimmerBrush() {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ShimmerShelfSkeletonRowItem(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.Brush shimmerBrush) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SongRowItem(@org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Song song, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PlaylistCard(@org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Playlist playlist, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ArtistCard(@org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Artist artist, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void RecentlyPlayedScreen(@org.jetbrains.annotations.NotNull()
    java.util.List<com.pulse.music.domain.Song> songs, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.pulse.music.domain.Song, kotlin.Unit> onSongClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.pulse.music.domain.Song, kotlin.Unit> onRemoveSong) {
    }
}