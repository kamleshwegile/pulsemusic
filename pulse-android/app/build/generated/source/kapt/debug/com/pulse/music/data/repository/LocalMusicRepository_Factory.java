package com.pulse.music.data.repository;

import android.content.Context;
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
public final class LocalMusicRepository_Factory implements Factory<LocalMusicRepository> {
  private final Provider<Context> contextProvider;

  public LocalMusicRepository_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public LocalMusicRepository get() {
    return newInstance(contextProvider.get());
  }

  public static LocalMusicRepository_Factory create(Provider<Context> contextProvider) {
    return new LocalMusicRepository_Factory(contextProvider);
  }

  public static LocalMusicRepository newInstance(Context context) {
    return new LocalMusicRepository(context);
  }
}
