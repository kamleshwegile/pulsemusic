package com.pulse.music.ui.artist;

import com.pulse.music.data.repository.FollowedArtistRepository;
import com.pulse.music.data.repository.MusicRepository;
import com.pulse.music.player.MusicPlayerManager;
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
public final class ArtistViewModel_Factory implements Factory<ArtistViewModel> {
  private final Provider<MusicRepository> repositoryProvider;

  private final Provider<FollowedArtistRepository> followedArtistRepositoryProvider;

  private final Provider<MusicPlayerManager> musicPlayerManagerProvider;

  public ArtistViewModel_Factory(Provider<MusicRepository> repositoryProvider,
      Provider<FollowedArtistRepository> followedArtistRepositoryProvider,
      Provider<MusicPlayerManager> musicPlayerManagerProvider) {
    this.repositoryProvider = repositoryProvider;
    this.followedArtistRepositoryProvider = followedArtistRepositoryProvider;
    this.musicPlayerManagerProvider = musicPlayerManagerProvider;
  }

  @Override
  public ArtistViewModel get() {
    return newInstance(repositoryProvider.get(), followedArtistRepositoryProvider.get(), musicPlayerManagerProvider.get());
  }

  public static ArtistViewModel_Factory create(Provider<MusicRepository> repositoryProvider,
      Provider<FollowedArtistRepository> followedArtistRepositoryProvider,
      Provider<MusicPlayerManager> musicPlayerManagerProvider) {
    return new ArtistViewModel_Factory(repositoryProvider, followedArtistRepositoryProvider, musicPlayerManagerProvider);
  }

  public static ArtistViewModel newInstance(MusicRepository repository,
      FollowedArtistRepository followedArtistRepository, MusicPlayerManager musicPlayerManager) {
    return new ArtistViewModel(repository, followedArtistRepository, musicPlayerManager);
  }
}
