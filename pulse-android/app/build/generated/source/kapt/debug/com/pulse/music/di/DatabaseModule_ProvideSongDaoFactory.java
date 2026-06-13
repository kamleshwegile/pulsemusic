package com.pulse.music.di;

import com.pulse.music.data.local.AppDatabase;
import com.pulse.music.data.local.SongDao;
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
public final class DatabaseModule_ProvideSongDaoFactory implements Factory<SongDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideSongDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public SongDao get() {
    return provideSongDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideSongDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideSongDaoFactory(databaseProvider);
  }

  public static SongDao provideSongDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSongDao(database));
  }
}
