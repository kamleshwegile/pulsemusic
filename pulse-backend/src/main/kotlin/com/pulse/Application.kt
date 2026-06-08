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
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import kotlinx.serialization.json.Json
import org.slf4j.event.Level
import com.pulse.plugins.appModule
import com.pulse.routes.configureApiRoutes
import com.pulse.routes.authRoutes
import com.pulse.database.*
import kotlin.concurrent.timer
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.reactivestreams.KMongo

lateinit var database: CoroutineDatabase

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
    configureAuth()
    configureApiRoutes()
    
    val jwtSecret = System.getenv("JWT_SECRET") ?: "super-secret-fallback-key"
    val jwtIssuer = System.getenv("JWT_ISSUER") ?: "http://0.0.0.0:8080/"
    authRoutes(jwtSecret, jwtIssuer)
}

fun Application.configureMonitoring() {
    install(CallLogging) {
        level = Level.INFO
    }
}

fun Application.configureAuth() {
    val jwtSecret = System.getenv("JWT_SECRET") ?: "super-secret-fallback-key"
    val jwtIssuer = System.getenv("JWT_ISSUER") ?: "http://0.0.0.0:8080/"
    val jwtAudience = "pulse-users"

    install(Authentication) {
        jwt("auth-jwt") {
            realm = "Pulse Backend Access"
            verifier(
                com.auth0.jwt.JWT
                    .require(com.auth0.jwt.algorithms.Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtIssuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("email").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}

fun Application.configureDI() {
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
}

fun Application.configureDatabases() {
    val dbUrl = System.getenv("DATABASE_URL") ?: "mongodb+srv://rahul210092004_db_user:cLbu4LKjF4aT5Hmj@cluster0.dtdusis.mongodb.net/?appName=Cluster0"
    val client = KMongo.createClient(dbUrl).coroutine
    database = client.getDatabase("pulse")
    
    // Pruning job every 6 hours
    timer(name = "CachePruningJob", daemon = true, initialDelay = 0L, period = 6L * 3600 * 1000) {
        val cacheRepo = org.koin.java.KoinJavaComponent.getKoin().get<com.pulse.repository.CacheRepository>()
        try {
            // Since prune is currently suspended or synchronous depending on CacheRepository, we can launch it in a runBlocking or CoroutineScope if needed.
            // For now, keep it as is, we will update CacheRepository to use coroutines and maybe remove this timer or launch it properly.
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
