package com.pulse.music.ui.playlist;

import com.pulse.music.data.repository.LikedSongsRepository;
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
public final class PlaylistViewModel_Factory implements Factory<PlaylistViewModel> {
  private final Provider<PlaylistRepository> playlistRepositoryProvider;

  private final Provider<LikedSongsRepository> likedSongsRepositoryProvider;

  private final Provider<MusicPlayerManager> musicPlayerManagerProvider;

  public PlaylistViewModel_Factory(Provider<PlaylistRepository> playlistRepositoryProvider,
      Provider<LikedSongsRepository> likedSongsRepositoryProvider,
      Provider<MusicPlayerManager> musicPlayerManagerProvider) {
    this.playlistRepositoryProvider = playlistRepositoryProvider;
    this.likedSongsRepositoryProvider = likedSongsRepositoryProvider;
    this.musicPlayerManagerProvider = musicPlayerManagerProvider;
  }

  @Override
  public PlaylistViewModel get() {
    return newInstance(playlistRepositoryProvider.get(), likedSongsRepositoryProvider.get(), musicPlayerManagerProvider.get());
  }

  public static PlaylistViewModel_Factory create(
      Provider<PlaylistRepository> playlistRepositoryProvider,
      Provider<LikedSongsRepository> likedSongsRepositoryProvider,
      Provider<MusicPlayerManager> musicPlayerManagerProvider) {
    return new PlaylistViewModel_Factory(playlistRepositoryProvider, likedSongsRepositoryProvider, musicPlayerManagerProvider);
  }

  public static PlaylistViewModel newInstance(PlaylistRepository playlistRepository,
      LikedSongsRepository likedSongsRepository, MusicPlayerManager musicPlayerManager) {
    return new PlaylistViewModel(playlistRepository, likedSongsRepository, musicPlayerManager);
  }
}
