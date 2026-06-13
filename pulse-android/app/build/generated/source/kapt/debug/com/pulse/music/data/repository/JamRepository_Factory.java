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
public final class JamRepository_Factory implements Factory<JamRepository> {
  private final Provider<PulseApiService> apiServiceProvider;

  public JamRepository_Factory(Provider<PulseApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public JamRepository get() {
    return newInstance(apiServiceProvider.get());
  }

  public static JamRepository_Factory create(Provider<PulseApiService> apiServiceProvider) {
    return new JamRepository_Factory(apiServiceProvider);
  }

  public static JamRepository newInstance(PulseApiService apiService) {
    return new JamRepository(apiService);
  }
}
