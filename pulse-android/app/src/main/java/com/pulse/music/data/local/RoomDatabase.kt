package com.pulse.music.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Database
import androidx.room.RoomDatabase

@Entity(tableName = "songs")
data class SongEntity(@PrimaryKey val id: String, val title: String, val artist: String)

@Entity(tableName = "albums")
data class AlbumEntity(@PrimaryKey val id: String, val title: String)

@Entity(tableName = "artists")
data class ArtistEntity(@PrimaryKey val id: String, val name: String)

@Entity(tableName = "playlists")
data class PlaylistEntity(@PrimaryKey val id: Int, val name: String)

@Entity(tableName = "lyrics_cache")
data class LyricsCacheEntity(@PrimaryKey val trackId: String, val lyrics: String)

@Entity(tableName = "search_cache")
data class SearchCacheEntity(@PrimaryKey val query: String, val resultJson: String)

@Dao
interface MusicDao {
    @Query("SELECT * FROM songs")
    suspend fun getAllSongs(): List<SongEntity>
}

@Database(
    entities = [SongEntity::class, AlbumEntity::class, ArtistEntity::class, PlaylistEntity::class, LyricsCacheEntity::class, SearchCacheEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun musicDao(): MusicDao
}
