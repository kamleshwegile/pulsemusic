package com.pulse.music.data.repository;

import com.pulse.music.data.local.PlaylistDao;
import com.pulse.music.data.network.PulseApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class PlaylistRepository_Factory implements Factory<PlaylistRepository> {
  private final Provider<PlaylistDao> playlistDaoProvider;

  private final Provider<PulseApiService> apiServiceProvider;

  public PlaylistRepository_Factory(Provider<PlaylistDao> playlistDaoProvider,
      Provider<PulseApiService> apiServiceProvider) {
    this.playlistDaoProvider = playlistDaoProvider;
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public PlaylistRepository get() {
    return newInstance(playlistDaoProvider.get(), apiServiceProvider.get());
  }

  public static PlaylistRepository_Factory create(Provider<PlaylistDao> playlistDaoProvider,
      Provider<PulseApiService> apiServiceProvider) {
    return new PlaylistRepository_Factory(playlistDaoProvider, apiServiceProvider);
  }

  public static PlaylistRepository newInstance(PlaylistDao playlistDao,
      PulseApiService apiService) {
    return new PlaylistRepository(playlistDao, apiService);
  }
}
