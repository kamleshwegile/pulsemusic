package com.pulse.music.di;

import com.pulse.music.data.local.AppDatabase;
import com.pulse.music.data.local.FollowedArtistDao;
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
public final class DatabaseModule_ProvideFollowedArtistDaoFactory implements Factory<FollowedArtistDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideFollowedArtistDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public FollowedArtistDao get() {
    return provideFollowedArtistDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideFollowedArtistDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideFollowedArtistDaoFactory(databaseProvider);
  }

  public static FollowedArtistDao provideFollowedArtistDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideFollowedArtistDao(database));
  }
}
