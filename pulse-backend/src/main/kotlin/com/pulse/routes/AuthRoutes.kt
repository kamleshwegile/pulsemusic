package com.pulse.routes

import com.pulse.database
import com.pulse.database.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*
import org.litote.kmongo.eq
import org.litote.kmongo.setValue
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ClientContentNegotiation
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

@Serializable
data class RegisterRequest(val username: String, val email: String, val password: String)

@Serializable
data class LoginRequest(val email: String, val password: String)

@Serializable
data class AuthResponse(val token: String, val username: String, val email: String)

@Serializable
data class SocialLoginRequest(val token: String)

@Serializable
data class GoogleTokenInfo(val email: String? = null, val name: String? = null, val sub: String? = null, val error: String? = null)

@Serializable
data class FacebookTokenInfo(val id: String? = null, val name: String? = null, val email: String? = null, val error: kotlinx.serialization.json.JsonObject? = null)

@Serializable
data class ForgotPasswordRequest(val email: String, val newPassword: String)

fun Application.authRoutes(secret: String, issuer: String) {
    val usersCollection = database.getCollection<User>("users")

    routing {
        route("/pulse-java-api/api/v1/auth") {
            // Require API Key to prevent bot registrations
            intercept(io.ktor.server.application.ApplicationCallPipeline.Plugins) {
                val apiKey = call.request.headers["X-Pulse-App-Key"]
                if (apiKey != "pulse-frontend-prod-key-9f8a7b6c5d4e") {
                    call.respond(HttpStatusCode.Unauthorized, "Missing or Invalid API Key")
                    finish()
                }
            }

            post("/register") {
                val req = try {
                    call.receive<RegisterRequest>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid request body"))
                    return@post
                }

                println("INFO [Auth]: New user registering: ${req.email}")

                if (req.username.isBlank() || req.email.isBlank() || req.password.isBlank()) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Fields cannot be empty"))
                    return@post
                }

                val exists = usersCollection.findOne(User::email eq req.email) != null
                if (exists) {
                    call.respond(HttpStatusCode.Conflict, mapOf("error" to "Email already exists"))
                    return@post
                }

                val hashedPw = BCrypt.hashpw(req.password, BCrypt.gensalt())
                
                val user = User(
                    username = req.username,
                    email = req.email,
                    passwordHash = hashedPw,
                    createdAt = System.currentTimeMillis()
                )
                usersCollection.insertOne(user)

                val token = JWT.create()
                    .withAudience("pulse-users")
                    .withIssuer(issuer)
                    .withClaim("email", req.email)
                    .withExpiresAt(Date(System.currentTimeMillis() + 604800000L)) // 7 days
                    .sign(Algorithm.HMAC256(secret))

                call.respond(HttpStatusCode.Created, AuthResponse(token, req.username, req.email))
            }

            post("/login") {
                val req = try {
                    call.receive<LoginRequest>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid request body"))
                    return@post
                }

                println("INFO [Auth]: User attempting to login: ${req.email}")

                val userRow = usersCollection.findOne(User::email eq req.email)

                if (userRow == null) {
                    call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Invalid credentials"))
                    return@post
                }
                
                if (userRow.passwordHash.isEmpty()) {
                    call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Please sign in with Google or Facebook"))
                    return@post
                }

                if (BCrypt.checkpw(req.password, userRow.passwordHash)) {
                    val token = JWT.create()
                        .withAudience("pulse-users")
                        .withIssuer(issuer)
                        .withClaim("email", req.email)
                        .withExpiresAt(Date(System.currentTimeMillis() + 604800000L)) // 7 days
                        .sign(Algorithm.HMAC256(secret))

                    call.respond(HttpStatusCode.OK, AuthResponse(token, userRow.username, req.email))
                } else {
                    call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Invalid credentials"))
                }
            }

            post("/google") {
                val req = try { call.receive<SocialLoginRequest>() } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid request body")); return@post
                }
                
                val client = HttpClient(CIO) { install(ClientContentNegotiation) { json(Json { ignoreUnknownKeys = true }) } }
                val response: HttpResponse = client.get("https://oauth2.googleapis.com/tokeninfo?id_token=${req.token}")
                
                if (response.status != HttpStatusCode.OK) {
                    call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Invalid Google token")); return@post
                }
                
                val tokenInfo = response.body<GoogleTokenInfo>()
                if (tokenInfo.email == null) {
                    call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Invalid Google token payload")); return@post
                }
                
                // Find or create user
                val user = usersCollection.findOne(User::email eq tokenInfo.email) ?: User(
                    username = tokenInfo.name ?: tokenInfo.email.substringBefore("@"),
                    email = tokenInfo.email,
                    passwordHash = "", // Social login, no password
                    createdAt = System.currentTimeMillis()
                ).also { usersCollection.insertOne(it) }
                
                val jwtToken = JWT.create().withAudience("pulse-users").withIssuer(issuer).withClaim("email", user.email).withExpiresAt(Date(System.currentTimeMillis() + 604800000L)).sign(Algorithm.HMAC256(secret))
                call.respond(HttpStatusCode.OK, AuthResponse(jwtToken, user.username, user.email))
            }

            post("/facebook") {
                val req = try { call.receive<SocialLoginRequest>() } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid request body")); return@post
                }
                
                val client = HttpClient(CIO) { install(ClientContentNegotiation) { json(Json { ignoreUnknownKeys = true }) } }
                val response: HttpResponse = client.get("https://graph.facebook.com/me?fields=id,name,email&access_token=${req.token}")
                
                if (response.status != HttpStatusCode.OK) {
                    call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Invalid Facebook token")); return@post
                }
                
                val tokenInfo = response.body<FacebookTokenInfo>()
                if (tokenInfo.id == null) {
                    call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Invalid Facebook token payload")); return@post
                }
                
                val email = tokenInfo.email ?: "${tokenInfo.id}@facebook.com"
                
                // Find or create user
                val user = usersCollection.findOne(User::email eq email) ?: User(
                    username = tokenInfo.name ?: "Facebook User",
                    email = email,
                    passwordHash = "", // Social login, no password
                    createdAt = System.currentTimeMillis()
                ).also { usersCollection.insertOne(it) }
                
                val jwtToken = JWT.create().withAudience("pulse-users").withIssuer(issuer).withClaim("email", user.email).withExpiresAt(Date(System.currentTimeMillis() + 604800000L)).sign(Algorithm.HMAC256(secret))
                call.respond(HttpStatusCode.OK, AuthResponse(jwtToken, user.username, user.email))
            }

            post("/forgot-password") {
                val req = try {
                    call.receive<ForgotPasswordRequest>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid request body"))
                    return@post
                }

                if (req.email.isBlank() || req.newPassword.isBlank()) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Fields cannot be empty"))
                    return@post
                }

                val user = usersCollection.findOne(User::email eq req.email)

                if (user == null) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "User not found"))
                    return@post
                }

                val hashedPw = BCrypt.hashpw(req.newPassword, BCrypt.gensalt())
                
                usersCollection.updateOne(User::email eq req.email, setValue(User::passwordHash, hashedPw))

                call.respond(HttpStatusCode.OK, mapOf("message" to "Password updated successfully"))
            }
        }
    }
}
