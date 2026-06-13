package com.pulse.music.data.repository;

import android.content.Context;
import com.pulse.music.data.local.SongDao;
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
public final class MusicRepository_Factory implements Factory<MusicRepository> {
  private final Provider<OnlineMusicRepository> onlineRepoProvider;

  private final Provider<SongDao> songDaoProvider;

  private final Provider<Context> contextProvider;

  public MusicRepository_Factory(Provider<OnlineMusicRepository> onlineRepoProvider,
      Provider<SongDao> songDaoProvider, Provider<Context> contextProvider) {
    this.onlineRepoProvider = onlineRepoProvider;
    this.songDaoProvider = songDaoProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public MusicRepository get() {
    return newInstance(onlineRepoProvider.get(), songDaoProvider.get(), contextProvider.get());
  }

  public static MusicRepository_Factory create(Provider<OnlineMusicRepository> onlineRepoProvider,
      Provider<SongDao> songDaoProvider, Provider<Context> contextProvider) {
    return new MusicRepository_Factory(onlineRepoProvider, songDaoProvider, contextProvider);
  }

  public static MusicRepository newInstance(OnlineMusicRepository onlineRepo, SongDao songDao,
      Context context) {
    return new MusicRepository(onlineRepo, songDao, context);
  }
}
