package com.pulse.music.ui.search;

import android.content.Context;
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
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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

  private final Provider<Context> contextProvider;

  public SearchViewModel_Factory(Provider<OnlineMusicRepository> onlineRepoProvider,
      Provider<SongDao> songDaoProvider, Provider<PlaylistDao> playlistDaoProvider,
      Provider<MusicPlayerManager> musicPlayerManagerProvider, Provider<Context> contextProvider) {
    this.onlineRepoProvider = onlineRepoProvider;
    this.songDaoProvider = songDaoProvider;
    this.playlistDaoProvider = playlistDaoProvider;
    this.musicPlayerManagerProvider = musicPlayerManagerProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public SearchViewModel get() {
    return newInstance(onlineRepoProvider.get(), songDaoProvider.get(), playlistDaoProvider.get(), musicPlayerManagerProvider.get(), contextProvider.get());
  }

  public static SearchViewModel_Factory create(Provider<OnlineMusicRepository> onlineRepoProvider,
      Provider<SongDao> songDaoProvider, Provider<PlaylistDao> playlistDaoProvider,
      Provider<MusicPlayerManager> musicPlayerManagerProvider, Provider<Context> contextProvider) {
    return new SearchViewModel_Factory(onlineRepoProvider, songDaoProvider, playlistDaoProvider, musicPlayerManagerProvider, contextProvider);
  }

  public static SearchViewModel newInstance(OnlineMusicRepository onlineRepo, SongDao songDao,
      PlaylistDao playlistDao, MusicPlayerManager musicPlayerManager, Context context) {
    return new SearchViewModel(onlineRepo, songDao, playlistDao, musicPlayerManager, context);
  }
}
