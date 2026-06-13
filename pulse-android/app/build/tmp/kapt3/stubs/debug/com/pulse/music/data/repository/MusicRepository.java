package com.pulse.music.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B!\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n2\u0006\u0010\f\u001a\u00020\rJ\u0016\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\n2\u0006\u0010\u0010\u001a\u00020\rJ*\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\n2\u0006\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\r2\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\rJ\"\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00170\n2\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\rJ\u0012\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00170\nJ\u001a\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u00170\n2\u0006\u0010\u001c\u001a\u00020\rR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/pulse/music/data/repository/MusicRepository;", "", "onlineRepo", "Lcom/pulse/music/data/repository/OnlineMusicRepository;", "songDao", "Lcom/pulse/music/data/local/SongDao;", "context", "Landroid/content/Context;", "(Lcom/pulse/music/data/repository/OnlineMusicRepository;Lcom/pulse/music/data/local/SongDao;Landroid/content/Context;)V", "getAlbumInfo", "Lkotlinx/coroutines/flow/Flow;", "Lcom/pulse/music/domain/Album;", "id", "", "getArtistInfo", "Lcom/pulse/music/domain/Artist;", "name", "getLyrics", "Lcom/pulse/music/domain/Lyrics;", "title", "artist", "songId", "getRecommendations", "", "Lcom/pulse/music/domain/Song;", "track", "getTrending", "search", "query", "app_debug"})
public final class MusicRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.repository.OnlineMusicRepository onlineRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.local.SongDao songDao = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    
    @javax.inject.Inject()
    public MusicRepository(@org.jetbrains.annotations.NotNull()
    com.pulse.music.data.repository.OnlineMusicRepository onlineRepo, @org.jetbrains.annotations.NotNull()
    com.pulse.music.data.local.SongDao songDao, @dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<java.lang.Object>> search(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.pulse.music.domain.Song>> getTrending() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.pulse.music.domain.Lyrics> getLyrics(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String artist, @org.jetbrains.annotations.Nullable()
    java.lang.String songId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.pulse.music.domain.Song>> getRecommendations(@org.jetbrains.annotations.NotNull()
    java.lang.String artist, @org.jetbrains.annotations.NotNull()
    java.lang.String track) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.pulse.music.domain.Artist> getArtistInfo(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.pulse.music.domain.Album> getAlbumInfo(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
        return null;
    }
}