package com.pulse.music.di;

import com.pulse.music.data.local.AppDatabase;
import com.pulse.music.data.local.LikedSongDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class DatabaseModule_ProvideLikedSongDaoFactory implements Factory<LikedSongDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideLikedSongDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public LikedSongDao get() {
    return provideLikedSongDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideLikedSongDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideLikedSongDaoFactory(databaseProvider);
  }

  public static LikedSongDao provideLikedSongDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideLikedSongDao(database));
  }
}
