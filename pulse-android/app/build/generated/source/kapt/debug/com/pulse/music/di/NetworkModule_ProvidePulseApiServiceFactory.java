package com.pulse.music.di;

import com.pulse.music.data.network.PulseApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class NetworkModule_ProvidePulseApiServiceFactory implements Factory<PulseApiService> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvidePulseApiServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public PulseApiService get() {
    return providePulseApiService(retrofitProvider.get());
  }

  public static NetworkModule_ProvidePulseApiServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvidePulseApiServiceFactory(retrofitProvider);
  }

  public static PulseApiService providePulseApiService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.providePulseApiService(retrofit));
  }
}
