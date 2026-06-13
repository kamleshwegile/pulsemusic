package com.pulse.music.ui.auth;

import com.pulse.music.data.repository.AuthRepository;
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
public final class AuthViewModel_Factory implements Factory<AuthViewModel> {
  private final Provider<AuthRepository> repositoryProvider;

  private final Provider<MusicPlayerManager> musicPlayerManagerProvider;

  public AuthViewModel_Factory(Provider<AuthRepository> repositoryProvider,
      Provider<MusicPlayerManager> musicPlayerManagerProvider) {
    this.repositoryProvider = repositoryProvider;
    this.musicPlayerManagerProvider = musicPlayerManagerProvider;
  }

  @Override
  public AuthViewModel get() {
    return newInstance(repositoryProvider.get(), musicPlayerManagerProvider.get());
  }

  public static AuthViewModel_Factory create(Provider<AuthRepository> repositoryProvider,
      Provider<MusicPlayerManager> musicPlayerManagerProvider) {
    return new AuthViewModel_Factory(repositoryProvider, musicPlayerManagerProvider);
  }

  public static AuthViewModel newInstance(AuthRepository repository,
      MusicPlayerManager musicPlayerManager) {
    return new AuthViewModel(repository, musicPlayerManager);
  }
}
