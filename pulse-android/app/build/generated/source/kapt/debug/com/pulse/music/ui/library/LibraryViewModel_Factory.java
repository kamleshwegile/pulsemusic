package com.pulse.music.ui.library;

import com.pulse.music.data.repository.FollowedArtistRepository;
import com.pulse.music.data.repository.LikedSongsRepository;
import com.pulse.music.data.repository.LocalMusicRepository;
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
public final class LibraryViewModel_Factory implements Factory<LibraryViewModel> {
  private final Provider<LocalMusicRepository> localMusicRepositoryProvider;

  private final Provider<MusicPlayerManager> musicPlayerManagerProvider;

  private final Provider<LikedSongsRepository> likedSongsRepositoryProvider;

  private final Provider<PlaylistRepository> playlistRepositoryProvider;

  private final Provider<FollowedArtistRepository> followedArtistRepositoryProvider;

  public LibraryViewModel_Factory(Provider<LocalMusicRepository> localMusicRepositoryProvider,
      Provider<MusicPlayerManager> musicPlayerManagerProvider,
      Provider<LikedSongsRepository> likedSongsRepositoryProvider,
      Provider<PlaylistRepository> playlistRepositoryProvider,
      Provider<FollowedArtistRepository> followedArtistRepositoryProvider) {
    this.localMusicRepositoryProvider = localMusicRepositoryProvider;
    this.musicPlayerManagerProvider = musicPlayerManagerProvider;
    this.likedSongsRepositoryProvider = likedSongsRepositoryProvider;
    this.playlistRepositoryProvider = playlistRepositoryProvider;
    this.followedArtistRepositoryProvider = followedArtistRepositoryProvider;
  }

  @Override
  public LibraryViewModel get() {
    return newInstance(localMusicRepositoryProvider.get(), musicPlayerManagerProvider.get(), likedSongsRepositoryProvider.get(), playlistRepositoryProvider.get(), followedArtistRepositoryProvider.get());
  }

  public static LibraryViewModel_Factory create(
      Provider<LocalMusicRepository> localMusicRepositoryProvider,
      Provider<MusicPlayerManager> musicPlayerManagerProvider,
      Provider<LikedSongsRepository> likedSongsRepositoryProvider,
      Provider<PlaylistRepository> playlistRepositoryProvider,
      Provider<FollowedArtistRepository> followedArtistRepositoryProvider) {
    return new LibraryViewModel_Factory(localMusicRepositoryProvider, musicPlayerManagerProvider, likedSongsRepositoryProvider, playlistRepositoryProvider, followedArtistRepositoryProvider);
  }

  public static LibraryViewModel newInstance(LocalMusicRepository localMusicRepository,
      MusicPlayerManager musicPlayerManager, LikedSongsRepository likedSongsRepository,
      PlaylistRepository playlistRepository, FollowedArtistRepository followedArtistRepository) {
    return new LibraryViewModel(localMusicRepository, musicPlayerManager, likedSongsRepository, playlistRepository, followedArtistRepository);
  }
}
