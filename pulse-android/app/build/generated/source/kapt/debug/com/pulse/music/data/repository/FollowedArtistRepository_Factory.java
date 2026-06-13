package com.pulse.music.data.repository;

import com.pulse.music.data.local.FollowedArtistDao;
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
public final class FollowedArtistRepository_Factory implements Factory<FollowedArtistRepository> {
  private final Provider<FollowedArtistDao> followedArtistDaoProvider;

  private final Provider<PulseApiService> apiServiceProvider;

  public FollowedArtistRepository_Factory(Provider<FollowedArtistDao> followedArtistDaoProvider,
      Provider<PulseApiService> apiServiceProvider) {
    this.followedArtistDaoProvider = followedArtistDaoProvider;
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public FollowedArtistRepository get() {
    return newInstance(followedArtistDaoProvider.get(), apiServiceProvider.get());
  }

  public static FollowedArtistRepository_Factory create(
      Provider<FollowedArtistDao> followedArtistDaoProvider,
      Provider<PulseApiService> apiServiceProvider) {
    return new FollowedArtistRepository_Factory(followedArtistDaoProvider, apiServiceProvider);
  }

  public static FollowedArtistRepository newInstance(FollowedArtistDao followedArtistDao,
      PulseApiService apiService) {
    return new FollowedArtistRepository(followedArtistDao, apiService);
  }
}
