package com.pulse.music.service;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class MusicServiceConnection_Factory implements Factory<MusicServiceConnection> {
  @Override
  public MusicServiceConnection get() {
    return newInstance();
  }

  public static MusicServiceConnection_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static MusicServiceConnection newInstance() {
    return new MusicServiceConnection();
  }

  private static final class InstanceHolder {
    private static final MusicServiceConnection_Factory INSTANCE = new MusicServiceConnection_Factory();
  }
}
