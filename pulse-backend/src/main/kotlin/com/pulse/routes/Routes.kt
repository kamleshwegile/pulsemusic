package com.pulse.routes

import com.pulse.repository.ProviderManager
import com.pulse.repository.CacheRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.HttpStatusCode
import io.ktor.http.ContentType
import org.koin.ktor.ext.inject
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.pulse.models.*
import kotlinx.coroutines.async
import io.ktor.server.auth.*

import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.*
import com.pulse.providers.defaultHttpClient

fun Application.configureApiRoutes() {
    val providerManager by inject<ProviderManager>()
    val cacheRepository by inject<CacheRepository>()
    val lastFm by inject<com.pulse.providers.LastFmProvider>()
    
    val json = Json { encodeDefaults = true; ignoreUnknownKeys = true }

    routing {
        route("/pulse-java-api/api/v1") {
            // Layer A: Require API Key for all routes under this path (except /health)
            intercept(io.ktor.server.application.ApplicationCallPipeline.Plugins) {
                if (call.request.local.uri != "/pulse-java-api/api/v1/health") {
                    val apiKey = call.request.headers["X-Pulse-App-Key"]
                    if (apiKey != "pulse-frontend-prod-key-9f8a7b6c5d4e") {
                        call.respond(HttpStatusCode.Unauthorized, "Missing or Invalid API Key")
                        finish()
                    }
                }
            }

            get("/health") {
                call.respond(providerManager.getHealth().associateBy { it.name })
            }

            // Layer C: Require JWT Token for music routes
            authenticate("auth-jwt") {
                get("/search") {
                val q = call.request.queryParameters["q"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing query")
                val type = call.request.queryParameters["type"] ?: "song"
                val cacheKey = "search:all:v2:$q"
                
                val cached = cacheRepository.getSongData(cacheKey)
                if (cached != null) {
                    call.response.header("X-Provider", "Cache")
                    call.response.header("Cache-Control", "max-age=3600")
                    return@get call.respondText(cached, ContentType.Application.Json)
                }
                
                val (result, providerName) = providerManager.executeWithFailover("search", cacheKey) { provider ->
                    provider.searchAll(q) ?: SearchResponse(songs = provider.search(q))
                }
                
                if (result != null) {
                    val responseJson = json.encodeToString(result as SearchResponse)
                    cacheRepository.setSongData(cacheKey, q, "Search", providerName, responseJson)
                    call.response.header("X-Provider", providerName)
                    call.response.header("Cache-Control", "max-age=3600")
                    call.respondText(responseJson, ContentType.Application.Json)
                } else {
                    call.respond(HttpStatusCode.ServiceUnavailable, "All providers failed")
                }
            }



            get("/lyrics") {
                val title = call.request.queryParameters["title"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing title")
                val artist = call.request.queryParameters["artist"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing artist")
                val songId = call.request.queryParameters["songId"]
                
                val cacheKey = "lyrics:$title:$artist"
                val cached = cacheRepository.getLyrics(cacheKey)
                if (cached != null) {
                    val syncedList = cached.first?.let { json.decodeFromString<List<LyricLine>>(it) }
                    val lyricsResp = Lyrics(synced = syncedList, plain = cached.second, source = "Cache", cached = true)
                    call.response.header("X-Provider", "Cache")
                    call.response.header("Cache-Control", "max-age=3600")
                    return@get call.respond(lyricsResp)
                }
                
                var result: Any? = null
                var providerName = ""
                try {
                    kotlinx.coroutines.withTimeout(4000) {
                        kotlinx.coroutines.coroutineScope {
                        val jioSaavnProvider = providerManager.providers.find { it.name == "ListenFree" }
                        val lrclibProvider = providerManager.providers.find { it.name == "LRCLIB" }
                        
                        val jioDeferred = async { jioSaavnProvider?.getLyrics(title, artist, songId) }
                        val lrcDeferred = async { lrclibProvider?.getLyrics(title, artist, songId) }
                        
                        // Wait up to 1.5 seconds for synced lyrics from LRCLIB
                        val lrcFast = kotlinx.coroutines.withTimeoutOrNull(1500) { lrcDeferred.await() }
                        if (lrcFast != null && lrcFast.synced != null) {
                            result = lrcFast
                            providerName = "LRCLIB"
                            jioDeferred.cancel()
                        } else {
                            // Fallback to JioSaavn plain lyrics if LRCLIB is slow
                            val jioResult = kotlinx.coroutines.withTimeoutOrNull(2000) { jioDeferred.await() }
                            if (jioResult != null) {
                                result = jioResult
                                providerName = "ListenFree"
                                lrcDeferred.cancel()
                            } else {
                                // Wait completely for LRCLIB if JioSaavn failed
                                val lrcSlow = kotlinx.coroutines.withTimeoutOrNull(2000) { lrcDeferred.await() }
                                if (lrcSlow != null) {
                                    result = lrcSlow
                                    providerName = "LRCLIB"
                                }
                            }
                        }
                        
                        jioDeferred.cancel()
                        lrcDeferred.cancel()
                        }
                    }
                } catch (e: Exception) {
                    // Timeout occurred
                }
                
                // Final fallback using failover
                if (result == null) {
                    try {
                        kotlinx.coroutines.withTimeout(5000) {
                            val res = providerManager.executeWithFailover("lyrics", cacheKey) { provider ->
                                if (provider.name == "ListenFree" || provider.name == "LRCLIB") return@executeWithFailover null
                                provider.getLyrics(title, artist, songId)
                            }
                            result = res.first
                            providerName = res.second
                        }
                    } catch (e: Exception) {}
                }
                
                if (result != null) {
                    val lyricsResult = result as Lyrics
                    val syncedStr = lyricsResult.synced?.let { json.encodeToString(it) }
                    cacheRepository.setLyrics(cacheKey, syncedStr, lyricsResult.plain, providerName)
                    call.response.header("X-Provider", providerName)
                    call.response.header("Cache-Control", "max-age=3600")
                    call.respond(lyricsResult)
                } else {
                    call.respond(HttpStatusCode.ServiceUnavailable, "All providers failed")
                }
            }

            get("/artist/{name...}") {
                val name = call.parameters.getAll("name")?.joinToString("/") ?: return@get call.respond(HttpStatusCode.BadRequest)
                val cacheKey = "artist:$name"
                
                val cached = cacheRepository.getSongData(cacheKey)
                if (cached != null) {
                    call.response.header("X-Provider", "Cache")
                    call.response.header("Cache-Control", "max-age=3600")
                    return@get call.respondText(cached, ContentType.Application.Json)
                }
                
                val (result, providerName) = providerManager.executeWithFailover("artist", cacheKey) { provider ->
                    provider.getArtist(name)
                }
                
                if (result != null) {
                    val responseJson = json.encodeToString(result as Artist)
                    cacheRepository.setSongData(cacheKey, name, name, providerName, responseJson)
                    call.response.header("X-Provider", providerName)
                    call.response.header("Cache-Control", "max-age=3600")
                    call.respondText(responseJson, ContentType.Application.Json)
                } else {
                    call.respond(HttpStatusCode.ServiceUnavailable, "All providers failed")
                }
            }
            
            get("/art/{mbid}") {
                val mbid = call.parameters["mbid"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                val cacheKey = "art:$mbid"
                
                val cached = cacheRepository.getSongData(cacheKey)
                if (cached != null) {
                    call.response.header("X-Provider", "Cache")
                    call.response.header("Cache-Control", "max-age=3600")
                    return@get call.respondText(cached, ContentType.Application.Json)
                }
                
                val (result, providerName) = providerManager.executeWithFailover("art", cacheKey) { provider ->
                    provider.getAlbum(mbid)
                }
                
                if (result != null) {
                    val album = result as Album
                    val artResp = ArtResponse(url = album.coverArt, thumbnail = album.coverArt, source = providerName)
                    val responseJson = json.encodeToString(artResp)
                    cacheRepository.setSongData(cacheKey, album.title, album.artist, providerName, responseJson, 7 * 24) // 7 days TTL for art
                    call.response.header("X-Provider", providerName)
                    call.response.header("Cache-Control", "max-age=3600")
                    call.respondText(responseJson, ContentType.Application.Json)
                } else {
                    call.respond(HttpStatusCode.ServiceUnavailable, "All providers failed")
                }
            }
            
            get("/album/{id...}") {
                val id = call.parameters.getAll("id")?.joinToString("/") ?: return@get call.respond(HttpStatusCode.BadRequest)
                val cacheKey = "album_data:$id"
                
                val cached = cacheRepository.getSongData(cacheKey)
                if (cached != null) {
                    call.response.header("X-Provider", "Cache")
                    call.response.header("Cache-Control", "max-age=3600")
                    return@get call.respondText(cached, ContentType.Application.Json)
                }
                
                val (result, providerName) = providerManager.executeWithFailover("album_data", cacheKey) { provider ->
                    provider.getAlbum(id)
                }
                
                if (result != null) {
                    val album = result as Album
                    val responseJson = json.encodeToString(album)
                    cacheRepository.setSongData(cacheKey, album.title, album.artist, providerName, responseJson)
                    call.response.header("X-Provider", providerName)
                    call.response.header("Cache-Control", "max-age=3600")
                    call.respondText(responseJson, ContentType.Application.Json)
                } else {
                    call.respond(HttpStatusCode.ServiceUnavailable, "All providers failed")
                }
            }
            
            get("/recommendations") {
                val artist = call.request.queryParameters["artist"] ?: ""
                val track = call.request.queryParameters["track"] ?: ""
                val cacheKey = "rec:$artist:$track"
                
                val cached = cacheRepository.getSongData(cacheKey)
                if (cached != null) {
                    call.response.header("X-Provider", "Cache")
                    call.response.header("Cache-Control", "max-age=3600")
                    return@get call.respondText(cached, ContentType.Application.Json)
                }
                
                var result: Any? = null
                var providerName = ""
                try {
                    kotlinx.coroutines.withTimeout(5000) {
                        val res = providerManager.executeWithFailover("recommendations", cacheKey) { provider ->
                            val list = provider.getRecommendations("$artist:$track")
                            if (list.isEmpty()) null else list
                        }
                        result = res.first
                        providerName = res.second
                    }
                } catch (e: Exception) {
                    // Fallback to search by artist if recommendations hang
                    val res = providerManager.executeWithFailover("search_fallback", cacheKey) { provider ->
                        provider.searchAll(artist) ?: SearchResponse(songs = provider.search(artist))
                    }
                    result = (res.first as? SearchResponse)?.songs
                    providerName = res.second + " (Fallback)"
                }
                
                if (result != null && (result as List<*>).isNotEmpty()) {
                    val responseJson = json.encodeToString(result as List<Song>)
                    cacheRepository.setSongData(cacheKey, track, artist, providerName, responseJson)
                    call.response.header("X-Provider", providerName)
                    call.response.header("Cache-Control", "max-age=3600")
                    call.respondText(responseJson, ContentType.Application.Json)
                } else {
                    call.respond(HttpStatusCode.ServiceUnavailable, "All providers failed")
                }
            }
            get("/home") {
                try {
                    val response = defaultHttpClient.get("https://www.jiosaavn.com/api.php?__call=webapi.getLaunchData&api_version=4&_format=json&_marker=0&ctx=web6dot0")
                    if (response.status.value == 200) {
                        val json = Json.parseToJsonElement(response.bodyAsText()).jsonObject
                        val modulesJson = json["modules"]?.jsonObject ?: return@get call.respond(HttpStatusCode.ServiceUnavailable)
                        
                        val parsedModules = mutableListOf<HomeModule>()
                        
                        println("Modules keys: ${modulesJson.keys}")
                        modulesJson.keys.forEach { key ->
                            val moduleData = modulesJson[key]?.jsonObject
                            val title = moduleData?.get("title")?.jsonPrimitive?.content
                            if (title == null) {
                                println("Skipped $key because title is null")
                                return@forEach
                            }
                            
                            val itemsArray = json[key]?.jsonArray
                            if (itemsArray == null) {
                                println("Skipped $key because itemsArray is null. type=${json[key]?.javaClass?.name}")
                                return@forEach
                            }
                            if (itemsArray.isEmpty()) {
                                println("Skipped $key because itemsArray is empty")
                                return@forEach
                            }
                            
                            val items = itemsArray.mapNotNull {
                                val obj = it.jsonObject
                                Playlist(
                                    id = obj["id"]?.jsonPrimitive?.content ?: "",
                                    title = obj["title"]?.jsonPrimitive?.content ?: "",
                                    image = obj["image"]?.jsonPrimitive?.content?.replace("150x150", "500x500") ?: "",
                                    songCount = obj["list_count"]?.jsonPrimitive?.intOrNull,
                                    source = "ListenFree"
                                )
                            }
                            
                            if (items.isNotEmpty()) {
                                parsedModules.add(HomeModule(title, items))
                            } else {
                                println("Skipped $key because mapped items is empty")
                            }
                        }
                        println("Parsed ${parsedModules.size} modules")
                        call.respond(HomeResponse(modules = parsedModules))
                    } else {
                        call.respond(HttpStatusCode.ServiceUnavailable, "Failed to load home")
                    }
                } catch(e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
                }
            }
            
            get("/trending") {
                val country = call.request.queryParameters["country"] ?: "IN"
                val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 20
                val cacheKey = "trending:$country:$limit"
                
                val cached = cacheRepository.getSongData(cacheKey)
                if (cached != null) {
                    call.response.header("X-Provider", "Cache")
                    call.response.header("Cache-Control", "max-age=3600")
                    return@get call.respondText(cached, ContentType.Application.Json)
                }
                
                try {
                    val tracks = lastFm.getTopTracks(country, limit)
                    if (tracks.isNotEmpty()) {
                        val responseJson = json.encodeToString(tracks)
                        cacheRepository.setSongData(cacheKey, "Trending", country, "Last.fm", responseJson)
                        call.response.header("X-Provider", "Last.fm")
                        call.response.header("Cache-Control", "max-age=3600")
                        call.respondText(responseJson, ContentType.Application.Json)
                    } else {
                        throw Exception("LastFM empty")
                    }
                } catch(e: Exception) {
                    // Fallback to Saavn/Genius search
                    val (result, providerName) = providerManager.executeWithFailover("search", cacheKey) { provider ->
                        provider.searchAll("trending top hits") ?: SearchResponse(songs = provider.search("trending top hits"))
                    }
                    if (result != null) {
                        val responseJson = json.encodeToString((result as SearchResponse).songs)
                        cacheRepository.setSongData(cacheKey, "Trending", country, providerName, responseJson)
                        call.response.header("X-Provider", providerName)
                        call.response.header("Cache-Control", "max-age=3600")
                        call.respondText(responseJson, ContentType.Application.Json)
                    } else {
                        call.respond(HttpStatusCode.ServiceUnavailable, "All providers failed")
                    }
                }
            }

            } // end of authenticate
        }
    }
}
