package com.pulse.music.ui.album;

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
public final class AlbumViewModel_Factory implements Factory<AlbumViewModel> {
  private final Provider<MusicRepository> repositoryProvider;

  private final Provider<MusicPlayerManager> musicPlayerManagerProvider;

  public AlbumViewModel_Factory(Provider<MusicRepository> repositoryProvider,
      Provider<MusicPlayerManager> musicPlayerManagerProvider) {
    this.repositoryProvider = repositoryProvider;
    this.musicPlayerManagerProvider = musicPlayerManagerProvider;
  }

  @Override
  public AlbumViewModel get() {
    return newInstance(repositoryProvider.get(), musicPlayerManagerProvider.get());
  }

  public static AlbumViewModel_Factory create(Provider<MusicRepository> repositoryProvider,
      Provider<MusicPlayerManager> musicPlayerManagerProvider) {
    return new AlbumViewModel_Factory(repositoryProvider, musicPlayerManagerProvider);
  }

  public static AlbumViewModel newInstance(MusicRepository repository,
      MusicPlayerManager musicPlayerManager) {
    return new AlbumViewModel(repository, musicPlayerManager);
  }
}
