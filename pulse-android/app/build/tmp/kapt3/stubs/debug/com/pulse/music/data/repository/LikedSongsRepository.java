package com.pulse.music.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u0010J\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\b2\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0014H\u0086@\u00a2\u0006\u0002\u0010\u0016J\u000e\u0010\u0017\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u001a"}, d2 = {"Lcom/pulse/music/data/repository/LikedSongsRepository;", "", "likedSongDao", "Lcom/pulse/music/data/local/LikedSongDao;", "apiService", "Lcom/pulse/music/data/network/PulseApiService;", "(Lcom/pulse/music/data/local/LikedSongDao;Lcom/pulse/music/data/network/PulseApiService;)V", "likedSongs", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/pulse/music/domain/Song;", "getLikedSongs", "()Lkotlinx/coroutines/flow/Flow;", "addLikedSong", "", "song", "(Lcom/pulse/music/domain/Song;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isSongLiked", "", "songId", "", "removeLikedSong", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "syncLikedSongs", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toggleLiked", "app_debug"})
public final class LikedSongsRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.local.LikedSongDao likedSongDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.network.PulseApiService apiService = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.pulse.music.domain.Song>> likedSongs = null;
    
    @javax.inject.Inject()
    public LikedSongsRepository(@org.jetbrains.annotations.NotNull()
    com.pulse.music.data.local.LikedSongDao likedSongDao, @org.jetbrains.annotations.NotNull()
    com.pulse.music.data.network.PulseApiService apiService) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.pulse.music.domain.Song>> getLikedSongs() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object toggleLiked(@org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Song song, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object syncLikedSongs(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addLikedSong(@org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Song song, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object removeLikedSong(@org.jetbrains.annotations.NotNull()
    java.lang.String songId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> isSongLiked(@org.jetbrains.annotations.NotNull()
    java.lang.String songId) {
        return null;
    }
}