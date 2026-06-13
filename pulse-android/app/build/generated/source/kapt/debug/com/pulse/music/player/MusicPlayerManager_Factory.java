package com.pulse.music.player;

import android.content.Context;
import com.pulse.music.data.repository.OnlineMusicRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class MusicPlayerManager_Factory implements Factory<MusicPlayerManager> {
  private final Provider<Context> contextProvider;

  private final Provider<OnlineMusicRepository> onlineRepoProvider;

  public MusicPlayerManager_Factory(Provider<Context> contextProvider,
      Provider<OnlineMusicRepository> onlineRepoProvider) {
    this.contextProvider = contextProvider;
    this.onlineRepoProvider = onlineRepoProvider;
  }

  @Override
  public MusicPlayerManager get() {
    return newInstance(contextProvider.get(), onlineRepoProvider.get());
  }

  public static MusicPlayerManager_Factory create(Provider<Context> contextProvider,
      Provider<OnlineMusicRepository> onlineRepoProvider) {
    return new MusicPlayerManager_Factory(contextProvider, onlineRepoProvider);
  }

  public static MusicPlayerManager newInstance(Context context, OnlineMusicRepository onlineRepo) {
    return new MusicPlayerManager(context, onlineRepo);
  }
}
