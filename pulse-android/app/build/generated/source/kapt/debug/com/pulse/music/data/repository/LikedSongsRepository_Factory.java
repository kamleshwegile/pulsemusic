package com.pulse.music.data.repository;

import com.pulse.music.data.local.LikedSongDao;
import com.pulse.music.data.network.PulseApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class LikedSongsRepository_Factory implements Factory<LikedSongsRepository> {
  private final Provider<LikedSongDao> likedSongDaoProvider;

  private final Provider<PulseApiService> apiServiceProvider;

  public LikedSongsRepository_Factory(Provider<LikedSongDao> likedSongDaoProvider,
      Provider<PulseApiService> apiServiceProvider) {
    this.likedSongDaoProvider = likedSongDaoProvider;
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public LikedSongsRepository get() {
    return newInstance(likedSongDaoProvider.get(), apiServiceProvider.get());
  }

  public static LikedSongsRepository_Factory create(Provider<LikedSongDao> likedSongDaoProvider,
      Provider<PulseApiService> apiServiceProvider) {
    return new LikedSongsRepository_Factory(likedSongDaoProvider, apiServiceProvider);
  }

  public static LikedSongsRepository newInstance(LikedSongDao likedSongDao,
      PulseApiService apiService) {
    return new LikedSongsRepository(likedSongDao, apiService);
  }
}
