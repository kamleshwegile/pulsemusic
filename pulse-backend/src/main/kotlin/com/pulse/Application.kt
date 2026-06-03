package com.pulse

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import kotlinx.serialization.json.Json
import org.slf4j.event.Level
import com.pulse.plugins.appModule
import com.pulse.routes.configureApiRoutes
import org.jetbrains.exposed.sql.Database
import com.pulse.database.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.concurrent.timer

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8080
    embeddedServer(Netty, port = port, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureMonitoring()
    configureDI()
    configureDatabases()
    configureSerialization()
    configureCORS()
    configureApiRoutes()
}

fun Application.configureMonitoring() {
    install(CallLogging) {
        level = Level.INFO
    }
}

fun Application.configureDI() {
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
}

fun Application.configureDatabases() {
    val dbUrl = System.getenv("DATABASE_URL") ?: "jdbc:postgresql://localhost:5432/postgres"
    val user = System.getenv("DB_USER") ?: "postgres"
    val password = System.getenv("DB_PASSWORD") ?: "postgres"
    
    Database.connect(url = dbUrl, driver = "org.postgresql.Driver", user = user, password = password)
    
    transaction {
        SchemaUtils.createMissingTablesAndColumns(CachedSongs, CachedLyrics, ProviderHealthTable)
    }
    
    // Pruning job every 6 hours
    timer(name = "CachePruningJob", daemon = true, initialDelay = 0L, period = 6L * 3600 * 1000) {
        val cacheRepo = org.koin.java.KoinJavaComponent.getKoin().get<com.pulse.repository.CacheRepository>()
        try {
            cacheRepo.pruneExpired()
            this@configureDatabases.log.info("Pruned expired cache entries.")
        } catch(e: Exception) {
            this@configureDatabases.log.error("Failed to prune cache", e)
        }
    }
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        })
    }
}

fun Application.configureCORS() {
    install(CORS) {
        anyHost()
        allowHeader("Authorization")
        allowHeader("Content-Type")
    }
}
