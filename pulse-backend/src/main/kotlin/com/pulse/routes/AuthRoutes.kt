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

@Serializable
data class RegisterRequest(val username: String, val email: String, val password: String)

@Serializable
data class LoginRequest(val email: String, val password: String)

@Serializable
data class AuthResponse(val token: String, val username: String, val email: String)

@Serializable
data class ForgotPasswordRequest(val email: String, val newPassword: String)

fun Application.authRoutes(secret: String, issuer: String) {
    val usersCollection = database.getCollection<User>("users")

    routing {
        route("/api/v1/auth") {
            post("/register") {
                val req = try {
                    call.receive<RegisterRequest>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid request body"))
                    return@post
                }

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

                val userRow = usersCollection.findOne(User::email eq req.email)

                if (userRow == null) {
                    call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Invalid credentials"))
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
