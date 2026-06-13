package com.pulse.music.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u0010J\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\b2\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0014H\u0086@\u00a2\u0006\u0002\u0010\u0016J\u000e\u0010\u0017\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0018R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0019"}, d2 = {"Lcom/pulse/music/data/repository/FollowedArtistRepository;", "", "followedArtistDao", "Lcom/pulse/music/data/local/FollowedArtistDao;", "apiService", "Lcom/pulse/music/data/network/PulseApiService;", "(Lcom/pulse/music/data/local/FollowedArtistDao;Lcom/pulse/music/data/network/PulseApiService;)V", "followedArtists", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/pulse/music/domain/Artist;", "getFollowedArtists", "()Lkotlinx/coroutines/flow/Flow;", "addFollowedArtist", "", "artist", "(Lcom/pulse/music/domain/Artist;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isArtistFollowed", "", "artistId", "", "removeFollowedArtist", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "syncFollowedArtists", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class FollowedArtistRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.local.FollowedArtistDao followedArtistDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.network.PulseApiService apiService = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.pulse.music.domain.Artist>> followedArtists = null;
    
    @javax.inject.Inject()
    public FollowedArtistRepository(@org.jetbrains.annotations.NotNull()
    com.pulse.music.data.local.FollowedArtistDao followedArtistDao, @org.jetbrains.annotations.NotNull()
    com.pulse.music.data.network.PulseApiService apiService) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.pulse.music.domain.Artist>> getFollowedArtists() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object syncFollowedArtists(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addFollowedArtist(@org.jetbrains.annotations.NotNull()
    com.pulse.music.domain.Artist artist, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object removeFollowedArtist(@org.jetbrains.annotations.NotNull()
    java.lang.String artistId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> isArtistFollowed(@org.jetbrains.annotations.NotNull()
    java.lang.String artistId) {
        return null;
    }
}