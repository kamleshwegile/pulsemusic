package com.pulse.music.ui.lockscreen;

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
public final class LockScreenActivity_MembersInjector implements MembersInjector<LockScreenActivity> {
  private final Provider<MusicPlayerManager> musicPlayerManagerProvider;

  public LockScreenActivity_MembersInjector(
      Provider<MusicPlayerManager> musicPlayerManagerProvider) {
    this.musicPlayerManagerProvider = musicPlayerManagerProvider;
  }

  public static MembersInjector<LockScreenActivity> create(
      Provider<MusicPlayerManager> musicPlayerManagerProvider) {
    return new LockScreenActivity_MembersInjector(musicPlayerManagerProvider);
  }

  @Override
  public void injectMembers(LockScreenActivity instance) {
    injectMusicPlayerManager(instance, musicPlayerManagerProvider.get());
  }

  @InjectedFieldSignature("com.pulse.music.ui.lockscreen.LockScreenActivity.musicPlayerManager")
  public static void injectMusicPlayerManager(LockScreenActivity instance,
      MusicPlayerManager musicPlayerManager) {
    instance.musicPlayerManager = musicPlayerManager;
  }
}
