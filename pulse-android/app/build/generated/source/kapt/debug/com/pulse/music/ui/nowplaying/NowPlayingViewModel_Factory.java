package com.pulse.music.ui.nowplaying;

import com.pulse.music.data.repository.LikedSongsRepository;
import com.pulse.music.data.repository.MusicRepository;
import com.pulse.music.data.repository.PlaylistRepository;
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
public final class NowPlayingViewModel_Factory implements Factory<NowPlayingViewModel> {
  private final Provider<MusicRepository> repositoryProvider;

  private final Provider<MusicPlayerManager> musicPlayerManagerProvider;

  private final Provider<LikedSongsRepository> likedSongsRepositoryProvider;

  private final Provider<PlaylistRepository> playlistRepositoryProvider;

  public NowPlayingViewModel_Factory(Provider<MusicRepository> repositoryProvider,
      Provider<MusicPlayerManager> musicPlayerManagerProvider,
      Provider<LikedSongsRepository> likedSongsRepositoryProvider,
      Provider<PlaylistRepository> playlistRepositoryProvider) {
    this.repositoryProvider = repositoryProvider;
    this.musicPlayerManagerProvider = musicPlayerManagerProvider;
    this.likedSongsRepositoryProvider = likedSongsRepositoryProvider;
    this.playlistRepositoryProvider = playlistRepositoryProvider;
  }

  @Override
  public NowPlayingViewModel get() {
    return newInstance(repositoryProvider.get(), musicPlayerManagerProvider.get(), likedSongsRepositoryProvider.get(), playlistRepositoryProvider.get());
  }

  public static NowPlayingViewModel_Factory create(Provider<MusicRepository> repositoryProvider,
      Provider<MusicPlayerManager> musicPlayerManagerProvider,
      Provider<LikedSongsRepository> likedSongsRepositoryProvider,
      Provider<PlaylistRepository> playlistRepositoryProvider) {
    return new NowPlayingViewModel_Factory(repositoryProvider, musicPlayerManagerProvider, likedSongsRepositoryProvider, playlistRepositoryProvider);
  }

  public static NowPlayingViewModel newInstance(MusicRepository repository,
      MusicPlayerManager musicPlayerManager, LikedSongsRepository likedSongsRepository,
      PlaylistRepository playlistRepository) {
    return new NowPlayingViewModel(repository, musicPlayerManager, likedSongsRepository, playlistRepository);
  }
}
