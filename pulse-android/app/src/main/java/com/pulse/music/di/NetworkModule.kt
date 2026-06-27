package com.pulse.music.di

import com.pulse.music.data.network.PulseApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import androidx.datastore.preferences.core.stringPreferencesKey
import com.pulse.music.data.repository.dataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.runBlocking
import com.pulse.music.BuildConfig // Automatically generated if configured in build.gradle

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        val headerInterceptor = Interceptor { chain ->
            val original = chain.request()
            val tokenKey = stringPreferencesKey("jwt_token")
            // NonCancellable prevents JobCancelledException when the calling
            // coroutine/request is cancelled (e.g. back press, screen rotation)
            val token = try {
                runBlocking(NonCancellable) {
                    context.dataStore.data.map { it[tokenKey] }.first()
                }
            } catch (e: Exception) {
                null
            }
            val builder = original.newBuilder()
                .header("X-App-Version", BuildConfig.VERSION_NAME)
                
            if (!token.isNullOrEmpty()) {
                builder.header("Authorization", "Bearer $token")
            }
            
            val request = builder.method(original.method, original.body).build()
            chain.proceed(request)
        }

        val retryInterceptor = Interceptor { chain ->
            val request = chain.request()
            var response = chain.proceed(request)
            var tryCount = 0
            val maxRetries = 1
            
            while (!response.isSuccessful && response.code == 503 && tryCount < maxRetries) {
                tryCount++
                response.close()
                Thread.sleep(1000L * tryCount) // Back off before retry
                response = chain.proceed(request)
            }
            response
        }

        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(retryInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()) // Adjust to Moshi or KotlinxSerialization if needed
            .build()
    }

    @Provides
    @Singleton
    fun providePulseApiService(retrofit: Retrofit): PulseApiService {
        return retrofit.create(PulseApiService::class.java)
    }
}

