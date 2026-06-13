package com.pulse.music.ui.search;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u001d\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u008f\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\b\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\b\u0012\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\b\u0012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\b\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\r\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\u0002\u0010\u0015J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\u0014H\u00c6\u0003J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006H\u00c6\u0003J\u000f\u0010(\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00c6\u0003J\u000f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u000b0\bH\u00c6\u0003J\u000f\u0010*\u001a\b\u0012\u0004\u0012\u00020\r0\bH\u00c6\u0003J\u000f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u000f0\bH\u00c6\u0003J\u000f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00110\bH\u00c6\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\rH\u00c6\u0003J\u0093\u0001\u0010.\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\b2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\b2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\b2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\b2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\r2\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u00c6\u0001J\u0013\u0010/\u001a\u00020\u00142\b\u00100\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00101\u001a\u000202H\u00d6\u0001J\t\u00103\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u001dR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0019R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0017R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0019\u00a8\u00064"}, d2 = {"Lcom/pulse/music/ui/search/SearchUiState;", "", "query", "", "activeCategory", "recentSearches", "", "songs", "Lcom/pulse/music/ui/search/SearchSectionState;", "Lcom/pulse/music/domain/Song;", "albums", "Lcom/pulse/music/domain/Album;", "artists", "Lcom/pulse/music/domain/Artist;", "playlists", "Lcom/pulse/music/data/local/PlaylistEntity;", "onlinePlaylists", "Lcom/pulse/music/domain/Playlist;", "exactArtist", "isExactArtistLoading", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Lcom/pulse/music/ui/search/SearchSectionState;Lcom/pulse/music/ui/search/SearchSectionState;Lcom/pulse/music/ui/search/SearchSectionState;Lcom/pulse/music/ui/search/SearchSectionState;Lcom/pulse/music/ui/search/SearchSectionState;Lcom/pulse/music/domain/Artist;Z)V", "getActiveCategory", "()Ljava/lang/String;", "getAlbums", "()Lcom/pulse/music/ui/search/SearchSectionState;", "getArtists", "getExactArtist", "()Lcom/pulse/music/domain/Artist;", "()Z", "getOnlinePlaylists", "getPlaylists", "getQuery", "getRecentSearches", "()Ljava/util/List;", "getSongs", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class SearchUiState {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String query = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String activeCategory = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> recentSearches = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.ui.search.SearchSectionState<com.pulse.music.domain.Song> songs = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.ui.search.SearchSectionState<com.pulse.music.domain.Album> albums = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.ui.search.SearchSectionState<com.pulse.music.domain.Artist> artists = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.ui.search.SearchSectionState<com.pulse.music.data.local.PlaylistEntity> playlists = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.ui.search.SearchSectionState<com.pulse.music.domain.Playlist> onlinePlaylists = null;
    @org.jetbrains.annotations.Nullable()
    private final com.pulse.music.domain.Artist exactArtist = null;
    private final boolean isExactArtistLoading = false;
    
    public SearchUiState(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    java.lang.String activeCategory, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> recentSearches, @org.jetbrains.annotations.NotNull()
    com.pulse.music.ui.search.SearchSectionState<com.pulse.music.domain.Song> songs, @org.jetbrains.annotations.NotNull()
    com.pulse.music.ui.search.SearchSectionState<com.pulse.music.domain.Album> albums, @org.jetbrains.annotations.NotNull()
    com.pulse.music.ui.search.SearchSectionState<com.pulse.music.domain.Artist> artists, @org.jetbrains.annotations.NotNull()
    com.pulse.music.ui.search.SearchSectionState<com.pulse.music.data.local.PlaylistEntity> playlists, @org.jetbrains.annotations.NotNull()
    com.pulse.music.ui.search.SearchSectionState<com.pulse.music.domain.Playlist> onlinePlaylists, @org.jetbrains.annotations.Nullable()
    com.pulse.music.domain.Artist exactArtist, boolean isExactArtistLoading) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getQuery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getActiveCategory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getRecentSearches() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pulse.music.ui.search.SearchSectionState<com.pulse.music.domain.Song> getSongs() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pulse.music.ui.search.SearchSectionState<com.pulse.music.domain.Album> getAlbums() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pulse.music.ui.search.SearchSectionState<com.pulse.music.domain.Artist> getArtists() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pulse.music.ui.search.SearchSectionState<com.pulse.music.data.local.PlaylistEntity> getPlaylists() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pulse.music.ui.search.SearchSectionState<com.pulse.music.domain.Playlist> getOnlinePlaylists() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.pulse.music.domain.Artist getExactArtist() {
        return null;
    }
    
    public final boolean isExactArtistLoading() {
        return false;
    }
    
    public SearchUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final boolean component10() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pulse.music.ui.search.SearchSectionState<com.pulse.music.domain.Song> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pulse.music.ui.search.SearchSectionState<com.pulse.music.domain.Album> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pulse.music.ui.search.SearchSectionState<com.pulse.music.domain.Artist> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pulse.music.ui.search.SearchSectionState<com.pulse.music.data.local.PlaylistEntity> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pulse.music.ui.search.SearchSectionState<com.pulse.music.domain.Playlist> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.pulse.music.domain.Artist component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pulse.music.ui.search.SearchUiState copy(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    java.lang.String activeCategory, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> recentSearches, @org.jetbrains.annotations.NotNull()
    com.pulse.music.ui.search.SearchSectionState<com.pulse.music.domain.Song> songs, @org.jetbrains.annotations.NotNull()
    com.pulse.music.ui.search.SearchSectionState<com.pulse.music.domain.Album> albums, @org.jetbrains.annotations.NotNull()
    com.pulse.music.ui.search.SearchSectionState<com.pulse.music.domain.Artist> artists, @org.jetbrains.annotations.NotNull()
    com.pulse.music.ui.search.SearchSectionState<com.pulse.music.data.local.PlaylistEntity> playlists, @org.jetbrains.annotations.NotNull()
    com.pulse.music.ui.search.SearchSectionState<com.pulse.music.domain.Playlist> onlinePlaylists, @org.jetbrains.annotations.Nullable()
    com.pulse.music.domain.Artist exactArtist, boolean isExactArtistLoading) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}