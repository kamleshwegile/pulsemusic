package com.pulse.music.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&\u00a8\u0006\u000b"}, d2 = {"Lcom/pulse/music/data/local/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "followedArtistDao", "Lcom/pulse/music/data/local/FollowedArtistDao;", "likedSongDao", "Lcom/pulse/music/data/local/LikedSongDao;", "playlistDao", "Lcom/pulse/music/data/local/PlaylistDao;", "songDao", "Lcom/pulse/music/data/local/SongDao;", "app_debug"})
@androidx.room.Database(entities = {com.pulse.music.data.local.SongEntity.class, com.pulse.music.data.local.AlbumEntity.class, com.pulse.music.data.local.ArtistEntity.class, com.pulse.music.data.local.PlaylistEntity.class, com.pulse.music.data.local.PlaylistSongEntity.class, com.pulse.music.data.local.LikedSongEntity.class, com.pulse.music.data.local.FollowedArtistEntity.class}, version = 6, exportSchema = false)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.pulse.music.data.local.SongDao songDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.pulse.music.data.local.PlaylistDao playlistDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.pulse.music.data.local.LikedSongDao likedSongDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.pulse.music.data.local.FollowedArtistDao followedArtistDao();
}