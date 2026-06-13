package com.pulse.music.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class LikedSongDao_Impl implements LikedSongDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<LikedSongEntity> __insertionAdapterOfLikedSongEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteLikedSong;

  public LikedSongDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLikedSongEntity = new EntityInsertionAdapter<LikedSongEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `liked_songs` (`id`,`title`,`artist`,`album`,`albumArt`,`durationMs`,`source`,`likedAt`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LikedSongEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getArtist() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getArtist());
        }
        if (entity.getAlbum() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getAlbum());
        }
        if (entity.getAlbumArt() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getAlbumArt());
        }
        if (entity.getDurationMs() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getDurationMs());
        }
        if (entity.getSource() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getSource());
        }
        statement.bindLong(8, entity.getLikedAt());
      }
    };
    this.__preparedStmtOfDeleteLikedSong = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM liked_songs WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertLikedSong(final LikedSongEntity song,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfLikedSongEntity.insert(song);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteLikedSong(final String songId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteLikedSong.acquire();
        int _argIndex = 1;
        if (songId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, songId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteLikedSong.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<LikedSongEntity>> getAllLikedSongs() {
    final String _sql = "SELECT * FROM liked_songs ORDER BY likedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"liked_songs"}, new Callable<List<LikedSongEntity>>() {
      @Override
      @NonNull
      public List<LikedSongEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfArtist = CursorUtil.getColumnIndexOrThrow(_cursor, "artist");
          final int _cursorIndexOfAlbum = CursorUtil.getColumnIndexOrThrow(_cursor, "album");
          final int _cursorIndexOfAlbumArt = CursorUtil.getColumnIndexOrThrow(_cursor, "albumArt");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfLikedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "likedAt");
          final List<LikedSongEntity> _result = new ArrayList<LikedSongEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LikedSongEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpArtist;
            if (_cursor.isNull(_cursorIndexOfArtist)) {
              _tmpArtist = null;
            } else {
              _tmpArtist = _cursor.getString(_cursorIndexOfArtist);
            }
            final String _tmpAlbum;
            if (_cursor.isNull(_cursorIndexOfAlbum)) {
              _tmpAlbum = null;
            } else {
              _tmpAlbum = _cursor.getString(_cursorIndexOfAlbum);
            }
            final String _tmpAlbumArt;
            if (_cursor.isNull(_cursorIndexOfAlbumArt)) {
              _tmpAlbumArt = null;
            } else {
              _tmpAlbumArt = _cursor.getString(_cursorIndexOfAlbumArt);
            }
            final Long _tmpDurationMs;
            if (_cursor.isNull(_cursorIndexOfDurationMs)) {
              _tmpDurationMs = null;
            } else {
              _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            }
            final String _tmpSource;
            if (_cursor.isNull(_cursorIndexOfSource)) {
              _tmpSource = null;
            } else {
              _tmpSource = _cursor.getString(_cursorIndexOfSource);
            }
            final long _tmpLikedAt;
            _tmpLikedAt = _cursor.getLong(_cursorIndexOfLikedAt);
            _item = new LikedSongEntity(_tmpId,_tmpTitle,_tmpArtist,_tmpAlbum,_tmpAlbumArt,_tmpDurationMs,_tmpSource,_tmpLikedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Boolean> isSongLiked(final String songId) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM liked_songs WHERE id = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (songId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, songId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"liked_songs"}, new Callable<Boolean>() {
      @Override
      @NonNull
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Boolean _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp == null ? null : _tmp != 0;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
