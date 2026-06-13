package com.pulse.music.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile SongDao _songDao;

  private volatile PlaylistDao _playlistDao;

  private volatile LikedSongDao _likedSongDao;

  private volatile FollowedArtistDao _followedArtistDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(6) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `songs` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `artist` TEXT NOT NULL, `album` TEXT, `albumArt` TEXT, `source` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `albums` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `artist` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `artists` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `playlists` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `playlist_songs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `playlistId` INTEGER NOT NULL, `songId` TEXT NOT NULL, `title` TEXT NOT NULL, `artist` TEXT NOT NULL, `album` TEXT, `albumArt` TEXT, `durationMs` INTEGER, `source` TEXT NOT NULL, `addedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `liked_songs` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `artist` TEXT NOT NULL, `album` TEXT, `albumArt` TEXT, `durationMs` INTEGER, `source` TEXT NOT NULL, `likedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `followed_artists` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `image` TEXT, `followedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '88f3fdc6883a33f11dad7bb27693ce47')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `songs`");
        db.execSQL("DROP TABLE IF EXISTS `albums`");
        db.execSQL("DROP TABLE IF EXISTS `artists`");
        db.execSQL("DROP TABLE IF EXISTS `playlists`");
        db.execSQL("DROP TABLE IF EXISTS `playlist_songs`");
        db.execSQL("DROP TABLE IF EXISTS `liked_songs`");
        db.execSQL("DROP TABLE IF EXISTS `followed_artists`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsSongs = new HashMap<String, TableInfo.Column>(6);
        _columnsSongs.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSongs.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSongs.put("artist", new TableInfo.Column("artist", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSongs.put("album", new TableInfo.Column("album", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSongs.put("albumArt", new TableInfo.Column("albumArt", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSongs.put("source", new TableInfo.Column("source", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSongs = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSongs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSongs = new TableInfo("songs", _columnsSongs, _foreignKeysSongs, _indicesSongs);
        final TableInfo _existingSongs = TableInfo.read(db, "songs");
        if (!_infoSongs.equals(_existingSongs)) {
          return new RoomOpenHelper.ValidationResult(false, "songs(com.pulse.music.data.local.SongEntity).\n"
                  + " Expected:\n" + _infoSongs + "\n"
                  + " Found:\n" + _existingSongs);
        }
        final HashMap<String, TableInfo.Column> _columnsAlbums = new HashMap<String, TableInfo.Column>(3);
        _columnsAlbums.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlbums.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlbums.put("artist", new TableInfo.Column("artist", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAlbums = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAlbums = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAlbums = new TableInfo("albums", _columnsAlbums, _foreignKeysAlbums, _indicesAlbums);
        final TableInfo _existingAlbums = TableInfo.read(db, "albums");
        if (!_infoAlbums.equals(_existingAlbums)) {
          return new RoomOpenHelper.ValidationResult(false, "albums(com.pulse.music.data.local.AlbumEntity).\n"
                  + " Expected:\n" + _infoAlbums + "\n"
                  + " Found:\n" + _existingAlbums);
        }
        final HashMap<String, TableInfo.Column> _columnsArtists = new HashMap<String, TableInfo.Column>(2);
        _columnsArtists.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArtists.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysArtists = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesArtists = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoArtists = new TableInfo("artists", _columnsArtists, _foreignKeysArtists, _indicesArtists);
        final TableInfo _existingArtists = TableInfo.read(db, "artists");
        if (!_infoArtists.equals(_existingArtists)) {
          return new RoomOpenHelper.ValidationResult(false, "artists(com.pulse.music.data.local.ArtistEntity).\n"
                  + " Expected:\n" + _infoArtists + "\n"
                  + " Found:\n" + _existingArtists);
        }
        final HashMap<String, TableInfo.Column> _columnsPlaylists = new HashMap<String, TableInfo.Column>(3);
        _columnsPlaylists.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlaylists = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPlaylists = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPlaylists = new TableInfo("playlists", _columnsPlaylists, _foreignKeysPlaylists, _indicesPlaylists);
        final TableInfo _existingPlaylists = TableInfo.read(db, "playlists");
        if (!_infoPlaylists.equals(_existingPlaylists)) {
          return new RoomOpenHelper.ValidationResult(false, "playlists(com.pulse.music.data.local.PlaylistEntity).\n"
                  + " Expected:\n" + _infoPlaylists + "\n"
                  + " Found:\n" + _existingPlaylists);
        }
        final HashMap<String, TableInfo.Column> _columnsPlaylistSongs = new HashMap<String, TableInfo.Column>(10);
        _columnsPlaylistSongs.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylistSongs.put("playlistId", new TableInfo.Column("playlistId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylistSongs.put("songId", new TableInfo.Column("songId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylistSongs.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylistSongs.put("artist", new TableInfo.Column("artist", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylistSongs.put("album", new TableInfo.Column("album", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylistSongs.put("albumArt", new TableInfo.Column("albumArt", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylistSongs.put("durationMs", new TableInfo.Column("durationMs", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylistSongs.put("source", new TableInfo.Column("source", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylistSongs.put("addedAt", new TableInfo.Column("addedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlaylistSongs = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPlaylistSongs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPlaylistSongs = new TableInfo("playlist_songs", _columnsPlaylistSongs, _foreignKeysPlaylistSongs, _indicesPlaylistSongs);
        final TableInfo _existingPlaylistSongs = TableInfo.read(db, "playlist_songs");
        if (!_infoPlaylistSongs.equals(_existingPlaylistSongs)) {
          return new RoomOpenHelper.ValidationResult(false, "playlist_songs(com.pulse.music.data.local.PlaylistSongEntity).\n"
                  + " Expected:\n" + _infoPlaylistSongs + "\n"
                  + " Found:\n" + _existingPlaylistSongs);
        }
        final HashMap<String, TableInfo.Column> _columnsLikedSongs = new HashMap<String, TableInfo.Column>(8);
        _columnsLikedSongs.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLikedSongs.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLikedSongs.put("artist", new TableInfo.Column("artist", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLikedSongs.put("album", new TableInfo.Column("album", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLikedSongs.put("albumArt", new TableInfo.Column("albumArt", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLikedSongs.put("durationMs", new TableInfo.Column("durationMs", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLikedSongs.put("source", new TableInfo.Column("source", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLikedSongs.put("likedAt", new TableInfo.Column("likedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLikedSongs = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLikedSongs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLikedSongs = new TableInfo("liked_songs", _columnsLikedSongs, _foreignKeysLikedSongs, _indicesLikedSongs);
        final TableInfo _existingLikedSongs = TableInfo.read(db, "liked_songs");
        if (!_infoLikedSongs.equals(_existingLikedSongs)) {
          return new RoomOpenHelper.ValidationResult(false, "liked_songs(com.pulse.music.data.local.LikedSongEntity).\n"
                  + " Expected:\n" + _infoLikedSongs + "\n"
                  + " Found:\n" + _existingLikedSongs);
        }
        final HashMap<String, TableInfo.Column> _columnsFollowedArtists = new HashMap<String, TableInfo.Column>(4);
        _columnsFollowedArtists.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFollowedArtists.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFollowedArtists.put("image", new TableInfo.Column("image", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFollowedArtists.put("followedAt", new TableInfo.Column("followedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFollowedArtists = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFollowedArtists = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFollowedArtists = new TableInfo("followed_artists", _columnsFollowedArtists, _foreignKeysFollowedArtists, _indicesFollowedArtists);
        final TableInfo _existingFollowedArtists = TableInfo.read(db, "followed_artists");
        if (!_infoFollowedArtists.equals(_existingFollowedArtists)) {
          return new RoomOpenHelper.ValidationResult(false, "followed_artists(com.pulse.music.data.local.FollowedArtistEntity).\n"
                  + " Expected:\n" + _infoFollowedArtists + "\n"
                  + " Found:\n" + _existingFollowedArtists);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "88f3fdc6883a33f11dad7bb27693ce47", "469260aa1a42cddd9ac9b2d46beaf90a");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "songs","albums","artists","playlists","playlist_songs","liked_songs","followed_artists");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `songs`");
      _db.execSQL("DELETE FROM `albums`");
      _db.execSQL("DELETE FROM `artists`");
      _db.execSQL("DELETE FROM `playlists`");
      _db.execSQL("DELETE FROM `playlist_songs`");
      _db.execSQL("DELETE FROM `liked_songs`");
      _db.execSQL("DELETE FROM `followed_artists`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(SongDao.class, SongDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PlaylistDao.class, PlaylistDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(LikedSongDao.class, LikedSongDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FollowedArtistDao.class, FollowedArtistDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public SongDao songDao() {
    if (_songDao != null) {
      return _songDao;
    } else {
      synchronized(this) {
        if(_songDao == null) {
          _songDao = new SongDao_Impl(this);
        }
        return _songDao;
      }
    }
  }

  @Override
  public PlaylistDao playlistDao() {
    if (_playlistDao != null) {
      return _playlistDao;
    } else {
      synchronized(this) {
        if(_playlistDao == null) {
          _playlistDao = new PlaylistDao_Impl(this);
        }
        return _playlistDao;
      }
    }
  }

  @Override
  public LikedSongDao likedSongDao() {
    if (_likedSongDao != null) {
      return _likedSongDao;
    } else {
      synchronized(this) {
        if(_likedSongDao == null) {
          _likedSongDao = new LikedSongDao_Impl(this);
        }
        return _likedSongDao;
      }
    }
  }

  @Override
  public FollowedArtistDao followedArtistDao() {
    if (_followedArtistDao != null) {
      return _followedArtistDao;
    } else {
      synchronized(this) {
        if(_followedArtistDao == null) {
          _followedArtistDao = new FollowedArtistDao_Impl(this);
        }
        return _followedArtistDao;
      }
    }
  }
}
