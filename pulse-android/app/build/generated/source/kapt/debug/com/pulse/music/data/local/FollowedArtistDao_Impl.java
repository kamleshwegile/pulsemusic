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
public final class FollowedArtistDao_Impl implements FollowedArtistDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FollowedArtistEntity> __insertionAdapterOfFollowedArtistEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteFollowedArtist;

  public FollowedArtistDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFollowedArtistEntity = new EntityInsertionAdapter<FollowedArtistEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `followed_artists` (`id`,`name`,`image`,`followedAt`) VALUES (?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FollowedArtistEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getImage() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getImage());
        }
        statement.bindLong(4, entity.getFollowedAt());
      }
    };
    this.__preparedStmtOfDeleteFollowedArtist = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM followed_artists WHERE id = ? OR name = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertFollowedArtist(final FollowedArtistEntity artist,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFollowedArtistEntity.insert(artist);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteFollowedArtist(final String artistIdOrName,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteFollowedArtist.acquire();
        int _argIndex = 1;
        if (artistIdOrName == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, artistIdOrName);
        }
        _argIndex = 2;
        if (artistIdOrName == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, artistIdOrName);
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
          __preparedStmtOfDeleteFollowedArtist.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<FollowedArtistEntity>> getAllFollowedArtists() {
    final String _sql = "SELECT * FROM followed_artists ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"followed_artists"}, new Callable<List<FollowedArtistEntity>>() {
      @Override
      @NonNull
      public List<FollowedArtistEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
          final int _cursorIndexOfFollowedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "followedAt");
          final List<FollowedArtistEntity> _result = new ArrayList<FollowedArtistEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FollowedArtistEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpImage;
            if (_cursor.isNull(_cursorIndexOfImage)) {
              _tmpImage = null;
            } else {
              _tmpImage = _cursor.getString(_cursorIndexOfImage);
            }
            final long _tmpFollowedAt;
            _tmpFollowedAt = _cursor.getLong(_cursorIndexOfFollowedAt);
            _item = new FollowedArtistEntity(_tmpId,_tmpName,_tmpImage,_tmpFollowedAt);
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
  public Flow<Boolean> isArtistFollowed(final String artistIdOrName) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM followed_artists WHERE id = ? OR name = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (artistIdOrName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, artistIdOrName);
    }
    _argIndex = 2;
    if (artistIdOrName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, artistIdOrName);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"followed_artists"}, new Callable<Boolean>() {
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
