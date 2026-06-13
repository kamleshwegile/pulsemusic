package com.pulse.music;

import com.pulse.music.data.repository.AuthRepository;
import com.pulse.music.player.MusicPlayerManager;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<MusicPlayerManager> musicPlayerManagerProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public MainActivity_MembersInjector(Provider<MusicPlayerManager> musicPlayerManagerProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.musicPlayerManagerProvider = musicPlayerManagerProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<MusicPlayerManager> musicPlayerManagerProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new MainActivity_MembersInjector(musicPlayerManagerProvider, authRepositoryProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectMusicPlayerManager(instance, musicPlayerManagerProvider.get());
    injectAuthRepository(instance, authRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.pulse.music.MainActivity.musicPlayerManager")
  public static void injectMusicPlayerManager(MainActivity instance,
      MusicPlayerManager musicPlayerManager) {
    instance.musicPlayerManager = musicPlayerManager;
  }

  @InjectedFieldSignature("com.pulse.music.MainActivity.authRepository")
  public static void injectAuthRepository(MainActivity instance, AuthRepository authRepository) {
    instance.authRepository = authRepository;
  }
}
