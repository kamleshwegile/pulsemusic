package com.pulse.routes

import com.pulse.repository.ProviderManager
import com.pulse.repository.CacheRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.HttpStatusCode
import org.koin.ktor.ext.inject

fun Application.configureApiRoutes() {
    val providerManager by inject<ProviderManager>()
    val cacheRepository by inject<CacheRepository>()
    val lastFm by inject<com.pulse.providers.LastFmProvider>() // For trending specifically

    routing {
        route("/api/v1") {
            get("/search") {
                val q = call.request.queryParameters["q"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing query")
                val type = call.request.queryParameters["type"] ?: "song"
                val cacheKey = "search:$type:$q"
                
                val (result, providerName) = providerManager.executeWithFailover("search", cacheKey) { provider ->
                    provider.search(q)
                }
                
                if (result != null) {
                    call.response.header("X-Provider", providerName)
                    call.response.header("Cache-Control", "max-age=3600")
                    call.respond(mapOf("songs" to result))
                } else {
                    call.respond(HttpStatusCode.ServiceUnavailable, "All providers failed")
                }
            }

            get("/lyrics") {
                val title = call.request.queryParameters["title"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing title")
                val artist = call.request.queryParameters["artist"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing artist")
                
                val cacheKey = "lyrics:$title:$artist"
                val (result, providerName) = providerManager.executeWithFailover("lyrics", cacheKey) { provider ->
                    provider.getLyrics(title, artist)
                }
                
                if (result != null) {
                    call.response.header("X-Provider", providerName)
                    call.response.header("Cache-Control", "max-age=3600")
                    call.respond(result)
                } else {
                    call.respond(HttpStatusCode.ServiceUnavailable, "All providers failed")
                }
            }

            get("/artist/{name}") {
                val name = call.parameters["name"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                val (result, providerName) = providerManager.executeWithFailover("artist", "artist:$name") { provider ->
                    provider.getArtist(name)
                }
                
                if (result != null) {
                    call.response.header("X-Provider", providerName)
                    call.response.header("Cache-Control", "max-age=3600")
                    call.respond(result)
                } else {
                    call.respond(HttpStatusCode.ServiceUnavailable, "All providers failed")
                }
            }
            
            get("/art/{mbid}") {
                val mbid = call.parameters["mbid"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                val (result, providerName) = providerManager.executeWithFailover("art", "art:$mbid") { provider ->
                    provider.getAlbum(mbid)
                }
                
                if (result != null) {
                    call.response.header("X-Provider", providerName)
                    call.response.header("Cache-Control", "max-age=3600")
                    call.respond(mapOf("url" to result.coverArt, "thumbnail" to result.coverArt, "source" to providerName))
                } else {
                    call.respond(HttpStatusCode.ServiceUnavailable, "All providers failed")
                }
            }
            
            get("/recommendations") {
                val artist = call.request.queryParameters["artist"] ?: ""
                val track = call.request.queryParameters["track"] ?: ""
                
                val (result, providerName) = providerManager.executeWithFailover("recommendations", "recommendations:$artist:$track") { provider ->
                    provider.getRecommendations("$artist:$track")
                }
                if (result != null) {
                    call.response.header("X-Provider", providerName)
                    call.response.header("Cache-Control", "max-age=3600")
                    call.respond(result)
                } else {
                    call.respond(HttpStatusCode.ServiceUnavailable, "All providers failed")
                }
            }
            
            get("/trending") {
                val country = call.request.queryParameters["country"] ?: "IN"
                val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 20
                
                try {
                    val tracks = lastFm.getTopTracks(country, limit)
                    call.response.header("X-Provider", "Last.fm")
                    call.response.header("Cache-Control", "max-age=3600")
                    call.respond(tracks)
                } catch(e: Exception) {
                    call.respond(HttpStatusCode.ServiceUnavailable, "Last.fm request failed")
                }
            }

            get("/health") {
                call.respond(providerManager.getHealth().associateBy { it.name })
            }
        }
    }
}
