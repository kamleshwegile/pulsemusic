package com.pulse.music.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u001c\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\tJ\u001c\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\r\u00a8\u0006\u000e"}, d2 = {"Lcom/pulse/music/data/local/SongDao;", "", "getAllSongs", "", "Lcom/pulse/music/data/local/SongEntity;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertSongs", "", "songs", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchSongs", "query", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface SongDao {
    
    @androidx.room.Query(value = "SELECT * FROM songs")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllSongs(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.pulse.music.data.local.SongEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM songs WHERE title LIKE \'%\' || :query || \'%\' OR artist LIKE \'%\' || :query || \'%\'")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object searchSongs(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.pulse.music.data.local.SongEntity>> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertSongs(@org.jetbrains.annotations.NotNull()
    java.util.List<com.pulse.music.data.local.SongEntity> songs, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}