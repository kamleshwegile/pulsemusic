package com.pulse.music.ui.home;

import android.content.Context;
import com.pulse.music.data.local.SongDao;
import com.pulse.music.data.repository.AuthRepository;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<OnlineMusicRepository> repositoryProvider;

  private final Provider<SongDao> songDaoProvider;

  private final Provider<MusicPlayerManager> musicPlayerManagerProvider;

  private final Provider<Context> contextProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public HomeViewModel_Factory(Provider<OnlineMusicRepository> repositoryProvider,
      Provider<SongDao> songDaoProvider, Provider<MusicPlayerManager> musicPlayerManagerProvider,
      Provider<Context> contextProvider, Provider<AuthRepository> authRepositoryProvider) {
    this.repositoryProvider = repositoryProvider;
    this.songDaoProvider = songDaoProvider;
    this.musicPlayerManagerProvider = musicPlayerManagerProvider;
    this.contextProvider = contextProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(repositoryProvider.get(), songDaoProvider.get(), musicPlayerManagerProvider.get(), contextProvider.get(), authRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<OnlineMusicRepository> repositoryProvider,
      Provider<SongDao> songDaoProvider, Provider<MusicPlayerManager> musicPlayerManagerProvider,
      Provider<Context> contextProvider, Provider<AuthRepository> authRepositoryProvider) {
    return new HomeViewModel_Factory(repositoryProvider, songDaoProvider, musicPlayerManagerProvider, contextProvider, authRepositoryProvider);
  }

  public static HomeViewModel newInstance(OnlineMusicRepository repository, SongDao songDao,
      MusicPlayerManager musicPlayerManager, Context context, AuthRepository authRepository) {
    return new HomeViewModel(repository, songDao, musicPlayerManager, context, authRepository);
  }
}
