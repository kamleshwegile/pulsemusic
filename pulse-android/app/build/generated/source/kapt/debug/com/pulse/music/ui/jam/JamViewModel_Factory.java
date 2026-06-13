package com.pulse.music.ui.jam;

import android.content.Context;
import com.pulse.music.data.repository.AuthRepository;
import com.pulse.music.data.repository.JamRepository;
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
public final class JamViewModel_Factory implements Factory<JamViewModel> {
  private final Provider<MusicPlayerManager> musicPlayerManagerProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<JamRepository> jamRepositoryProvider;

  private final Provider<Context> contextProvider;

  public JamViewModel_Factory(Provider<MusicPlayerManager> musicPlayerManagerProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<JamRepository> jamRepositoryProvider, Provider<Context> contextProvider) {
    this.musicPlayerManagerProvider = musicPlayerManagerProvider;
    this.authRepositoryProvider = authRepositoryProvider;
    this.jamRepositoryProvider = jamRepositoryProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public JamViewModel get() {
    return newInstance(musicPlayerManagerProvider.get(), authRepositoryProvider.get(), jamRepositoryProvider.get(), contextProvider.get());
  }

  public static JamViewModel_Factory create(Provider<MusicPlayerManager> musicPlayerManagerProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<JamRepository> jamRepositoryProvider, Provider<Context> contextProvider) {
    return new JamViewModel_Factory(musicPlayerManagerProvider, authRepositoryProvider, jamRepositoryProvider, contextProvider);
  }

  public static JamViewModel newInstance(MusicPlayerManager musicPlayerManager,
      AuthRepository authRepository, JamRepository jamRepository, Context context) {
    return new JamViewModel(musicPlayerManager, authRepository, jamRepository, context);
  }
}
