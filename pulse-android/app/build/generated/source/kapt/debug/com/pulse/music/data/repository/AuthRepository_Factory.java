package com.pulse.music.data.repository;

import android.content.Context;
import com.pulse.music.data.local.AppDatabase;
import com.pulse.music.data.network.PulseApiService;
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
public final class AuthRepository_Factory implements Factory<AuthRepository> {
  private final Provider<PulseApiService> apiProvider;

  private final Provider<Context> contextProvider;

  private final Provider<AppDatabase> databaseProvider;

  public AuthRepository_Factory(Provider<PulseApiService> apiProvider,
      Provider<Context> contextProvider, Provider<AppDatabase> databaseProvider) {
    this.apiProvider = apiProvider;
    this.contextProvider = contextProvider;
    this.databaseProvider = databaseProvider;
  }

  @Override
  public AuthRepository get() {
    return newInstance(apiProvider.get(), contextProvider.get(), databaseProvider.get());
  }

  public static AuthRepository_Factory create(Provider<PulseApiService> apiProvider,
      Provider<Context> contextProvider, Provider<AppDatabase> databaseProvider) {
    return new AuthRepository_Factory(apiProvider, contextProvider, databaseProvider);
  }

  public static AuthRepository newInstance(PulseApiService api, Context context,
      AppDatabase database) {
    return new AuthRepository(api, context, database);
  }
}
