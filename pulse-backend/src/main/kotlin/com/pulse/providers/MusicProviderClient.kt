package com.pulse.providers

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json

val defaultHttpClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true; isLenient = true })
    }
    install(HttpTimeout) {
        connectTimeoutMillis = 8000
        requestTimeoutMillis = 10000
    }
    install(HttpRequestRetry) {
        maxRetries = 2
        retryIf { request, response ->
            !response.status.isSuccess()
        }
        retryOnExceptionIf { request, cause ->
            cause is java.net.SocketTimeoutException || cause is java.net.ConnectException
        }
        delayMillis { retry -> retry * 1000L }
    }
}
