with open('app/src/main/java/com/pulse/music/di/NetworkModule.kt', 'r', encoding='utf-8') as f:
    code = f.read()

target = '''        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(failoverInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()'''

replacement = '''        val cacheSize = 500L * 1024L * 1024L // 500 MB
        val cache = okhttp3.Cache(java.io.File(context.cacheDir, "http_cache"), cacheSize)

        val forceCacheInterceptor = Interceptor { chain ->
            val response = chain.proceed(chain.request())
            val cacheControl = okhttp3.CacheControl.Builder()
                .maxAge(30, java.util.concurrent.TimeUnit.DAYS)
                .build()
            response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .removeHeader("Pragma")
                .build()
        }

        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(headerInterceptor)
            .addInterceptor(failoverInterceptor)
            .addNetworkInterceptor(forceCacheInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()'''

code = code.replace(target, replacement)

with open('app/src/main/java/com/pulse/music/di/NetworkModule.kt', 'w', encoding='utf-8') as f:
    f.write(code)
