package com.pulse.music.ui.search;

import com.pulse.music.data.local.PlaylistDao;
import com.pulse.music.data.local.SongDao;
import com.pulse.music.data.repository.OnlineMusicRepository;
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
public final class SearchViewModel_Factory implements Factory<SearchViewModel> {
  private final Provider<OnlineMusicRepository> onlineRepoProvider;

  private final Provider<SongDao> songDaoProvider;

  private final Provider<PlaylistDao> playlistDaoProvider;

  private final Provider<MusicPlayerManager> musicPlayerManagerProvider;

  public SearchViewModel_Factory(Provider<OnlineMusicRepository> onlineRepoProvider,
      Provider<SongDao> songDaoProvider, Provider<PlaylistDao> playlistDaoProvider,
      Provider<MusicPlayerManager> musicPlayerManagerProvider) {
    this.onlineRepoProvider = onlineRepoProvider;
    this.songDaoProvider = songDaoProvider;
    this.playlistDaoProvider = playlistDaoProvider;
    this.musicPlayerManagerProvider = musicPlayerManagerProvider;
  }

  @Override
  public SearchViewModel get() {
    return newInstance(onlineRepoProvider.get(), songDaoProvider.get(), playlistDaoProvider.get(), musicPlayerManagerProvider.get());
  }

  public static SearchViewModel_Factory create(Provider<OnlineMusicRepository> onlineRepoProvider,
      Provider<SongDao> songDaoProvider, Provider<PlaylistDao> playlistDaoProvider,
      Provider<MusicPlayerManager> musicPlayerManagerProvider) {
    return new SearchViewModel_Factory(onlineRepoProvider, songDaoProvider, playlistDaoProvider, musicPlayerManagerProvider);
  }

  public static SearchViewModel newInstance(OnlineMusicRepository onlineRepo, SongDao songDao,
      PlaylistDao playlistDao, MusicPlayerManager musicPlayerManager) {
    return new SearchViewModel(onlineRepo, songDao, playlistDao, musicPlayerManager);
  }
}
