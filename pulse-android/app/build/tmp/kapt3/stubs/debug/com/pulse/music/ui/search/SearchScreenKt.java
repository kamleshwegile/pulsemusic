package com.pulse.music.ui.search;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000N\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\u001a(\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\n0\u000fH\u0007\u001aH\u0010\u0010\u001a\u00020\n2\b\b\u0002\u0010\u0011\u001a\u00020\u00122\u0014\b\u0002\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\n0\u00142\u0014\b\u0002\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\n0\u00142\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0007\u001a\u0010\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u001aH\u0007\u001a\b\u0010\u001b\u001a\u00020\nH\u0007\u001a\b\u0010\u001c\u001a\u00020\nH\u0007\u001a\b\u0010\u001d\u001a\u00020\nH\u0007\u001a\b\u0010\u001e\u001a\u00020\nH\u0007\u001a\u0012\u0010\u001f\u001a\u00020\n2\b\b\u0002\u0010 \u001a\u00020!H\u0007\u001a\u0013\u0010\"\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f\u00a2\u0006\u0002\u0010#\"\u0013\u0010\u0000\u001a\u00020\u0001\u00a2\u0006\n\n\u0002\u0010\u0004\u001a\u0004\b\u0002\u0010\u0003\"\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006$"}, d2 = {"PulseAccentSearch", "Landroidx/compose/ui/graphics/Color;", "getPulseAccentSearch", "()J", "J", "searchCategoryColors", "", "getSearchCategoryColors", "()Ljava/util/List;", "CategoryCard", "", "title", "", "imageUrl", "onClick", "Lkotlin/Function0;", "SearchScreen", "viewModel", "Lcom/pulse/music/ui/search/SearchViewModel;", "onNavigateToArtist", "Lkotlin/Function1;", "onNavigateToAlbum", "focusRequester", "Landroidx/compose/ui/focus/FocusRequester;", "SkeletonBox", "modifier", "Landroidx/compose/ui/Modifier;", "SkeletonCard", "SkeletonCategoryCard", "SkeletonCircle", "SkeletonExactArtistItem", "SkeletonSongRow", "isCircle", "", "getColorForTitle", "(Ljava/lang/String;)J", "app_debug"})
public final class SearchScreenKt {
    private static final long PulseAccentSearch = 0L;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<androidx.compose.ui.graphics.Color> searchCategoryColors = null;
    
    public static final long getPulseAccentSearch() {
        return 0L;
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void SearchScreen(@org.jetbrains.annotations.NotNull()
    com.pulse.music.ui.search.SearchViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToArtist, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToAlbum, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.focus.FocusRequester focusRequester) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SkeletonBox(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SkeletonExactArtistItem() {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SkeletonSongRow(boolean isCircle) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SkeletonCard() {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SkeletonCircle() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<androidx.compose.ui.graphics.Color> getSearchCategoryColors() {
        return null;
    }
    
    public static final long getColorForTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String title) {
        return 0L;
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CategoryCard(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.Nullable()
    java.lang.String imageUrl, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SkeletonCategoryCard() {
    }
}