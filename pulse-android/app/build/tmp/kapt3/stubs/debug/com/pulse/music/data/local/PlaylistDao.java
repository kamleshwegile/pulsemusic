package com.pulse.music.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bH\'J\u001c\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\t0\b2\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\u0013J\u001e\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u0016H\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u001e\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0016H\u00a7@\u00a2\u0006\u0002\u0010\u0017\u00a8\u0006\u001a"}, d2 = {"Lcom/pulse/music/data/local/PlaylistDao;", "", "deletePlaylist", "", "playlistId", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllPlaylists", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/pulse/music/data/local/PlaylistEntity;", "getSongsForPlaylist", "Lcom/pulse/music/data/local/PlaylistSongEntity;", "insertPlaylist", "", "playlist", "(Lcom/pulse/music/data/local/PlaylistEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertSongToPlaylist", "song", "(Lcom/pulse/music/data/local/PlaylistSongEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeSongFromPlaylist", "songId", "", "(ILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "renamePlaylist", "newName", "app_debug"})
@androidx.room.Dao()
public abstract interface PlaylistDao {
    
    @androidx.room.Query(value = "SELECT * FROM playlists ORDER BY createdAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.pulse.music.data.local.PlaylistEntity>> getAllPlaylists();
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertPlaylist(@org.jetbrains.annotations.NotNull()
    com.pulse.music.data.local.PlaylistEntity playlist, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "DELETE FROM playlists WHERE id = :playlistId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deletePlaylist(int playlistId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE playlists SET name = :newName WHERE id = :playlistId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object renamePlaylist(int playlistId, @org.jetbrains.annotations.NotNull()
    java.lang.String newName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM playlist_songs WHERE playlistId = :playlistId ORDER BY addedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.pulse.music.data.local.PlaylistSongEntity>> getSongsForPlaylist(int playlistId);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertSongToPlaylist(@org.jetbrains.annotations.NotNull()
    com.pulse.music.data.local.PlaylistSongEntity song, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM playlist_songs WHERE playlistId = :playlistId AND songId = :songId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object removeSongFromPlaylist(int playlistId, @org.jetbrains.annotations.NotNull()
    java.lang.String songId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}