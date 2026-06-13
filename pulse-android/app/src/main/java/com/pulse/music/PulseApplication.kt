package com.pulse.music

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache

@HiltAndroidApp
class PulseApplication : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
        com.pulse.music.ui.jam.JamSessionManager.applicationContext = this
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(this.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.05)
                    .build()
            }
            .okHttpClient {
                okhttp3.OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                            .build()
                        chain.proceed(request)
                    }
                    .build()
            }
            .crossfade(true)
            .build()
    }
}
