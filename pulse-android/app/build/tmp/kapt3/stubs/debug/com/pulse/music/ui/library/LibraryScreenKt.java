package com.pulse.music.ui.library;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000@\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u001aK\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\u0011\u0010\u000f\u001a\r\u0012\u0004\u0012\u00020\u00060\u000e\u00a2\u0006\u0002\b\u0010H\u0007\u001aN\u0010\u0011\u001a\u00020\u00062\b\b\u0002\u0010\u0012\u001a\u00020\u00132\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\u0014\b\u0002\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00060\u00162\u0014\b\u0002\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00060\u0016H\u0007\"\u0013\u0010\u0000\u001a\u00020\u0001\u00a2\u0006\n\n\u0002\u0010\u0004\u001a\u0004\b\u0002\u0010\u0003\u00a8\u0006\u0019"}, d2 = {"PulseRed", "Landroidx/compose/ui/graphics/Color;", "getPulseRed", "()J", "J", "LibraryItem", "", "isGridView", "", "title", "", "subtitle", "isCircle", "onClick", "Lkotlin/Function0;", "imageContent", "Landroidx/compose/runtime/Composable;", "LibraryScreen", "viewModel", "Lcom/pulse/music/ui/library/LibraryViewModel;", "onNavigateToNowPlaying", "onNavigateToPlaylist", "Lkotlin/Function1;", "", "onNavigateToArtist", "app_debug"})
public final class LibraryScreenKt {
    private static final long PulseRed = 0L;
    
    public static final long getPulseRed() {
        return 0L;
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void LibraryScreen(@org.jetbrains.annotations.NotNull()
    com.pulse.music.ui.library.LibraryViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToNowPlaying, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onNavigateToPlaylist, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToArtist) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void LibraryItem(boolean isGridView, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String subtitle, boolean isCircle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> imageContent) {
    }
}