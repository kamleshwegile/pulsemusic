package com.pulse.music.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\n\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0012\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00160\u0015J\u001a\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00160\u00152\u0006\u0010\t\u001a\u00020\nJ$\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u0010H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001d\u0010\u0011J\u001e\u0010\u001e\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010 J\u001e\u0010!\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\"\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010 J\u000e\u0010#\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010$R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006%"}, d2 = {"Lcom/pulse/music/data/repository/PlaylistRepository;", "", "playlistDao", "Lcom/pulse/music/data/local/PlaylistDao;", "apiService", "Lcom/pulse/music/data/network/PulseApiService;", "(Lcom/pulse/music/data/local/PlaylistDao;Lcom/pulse/music/data/network/PulseApiService;)V", "addSongToPlaylist", "", "playlistId", "", "song", "Lcom/pulse/music/domain/Song;", "(ILcom/pulse/music/domain/Song;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createPlaylist", "name", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deletePlaylist", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllPlaylists", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/pulse/music/data/local/PlaylistEntity;", "getSongsForPlaylist", "importSpotifyPlaylist", "Lkotlin/Result;", "", "url", "importSpotifyPlaylist-gIAlu-s", "removeSongFromPlaylist", "songId", "(ILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "renamePlaylist", "newName", "syncPlaylists", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class PlaylistRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.local.PlaylistDao playlistDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.network.PulseApiService apiService = null;
    
    @javax.inject.Inject()
    public PlaylistRepository(@org.jetbrains.annotations.NotNull()
    com.pulse.music.data.local.PlaylistDao playlistDao, @org.jetbrains.annotations.NotNull()
    com.pulse.music.data.network.PulseApiService apiService) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.pulse.music.data.local.PlaylistEntity>> getAllPlaylists() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object syncPlaylists(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object createPlaylist(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deletePlaylist(int playlistId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object renamePlaylist(int playlistId, @org.jetbrains.annotations.NotNull()
    java.lang.String newName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.pulse.music.domain.Song>> getSongsForPlaylist(int playlistId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addSongToPlaylist(int playlistId, @org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Song song, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object removeSongFromPlaylist(int playlistId, @org.jetbrains.annotations.NotNull()
    java.lang.String songId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}