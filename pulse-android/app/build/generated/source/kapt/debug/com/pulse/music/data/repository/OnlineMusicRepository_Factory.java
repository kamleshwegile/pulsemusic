package com.pulse.music.data.repository;

import com.pulse.music.data.network.PulseApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class OnlineMusicRepository_Factory implements Factory<OnlineMusicRepository> {
  private final Provider<PulseApiService> apiServiceProvider;

  public OnlineMusicRepository_Factory(Provider<PulseApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public OnlineMusicRepository get() {
    return newInstance(apiServiceProvider.get());
  }

  public static OnlineMusicRepository_Factory create(Provider<PulseApiService> apiServiceProvider) {
    return new OnlineMusicRepository_Factory(apiServiceProvider);
  }

  public static OnlineMusicRepository newInstance(PulseApiService apiService) {
    return new OnlineMusicRepository(apiService);
  }
}
