package com.pulse.music.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Note: Replace 'Any' with actual Domain/Data Models (SearchResponse, LyricsResponse, etc.)
interface PulseApiService {
    @GET("api/v1/search")
    suspend fun search(
        @Query("q") query: String,
        @Query("type") type: String = "song"
    ): Any

    @GET("api/v1/lyrics")
    suspend fun getLyrics(
        @Query("title") title: String,
        @Query("artist") artist: String
    ): Any

    @GET("api/v1/artist/{name}")
    suspend fun getArtist(
        @Path("name") name: String
    ): Any

    @GET("api/v1/recommendations")
    suspend fun getRecommendations(
        @Query("artist") artist: String,
        @Query("track") track: String
    ): List<Any>

    @GET("api/v1/trending")
    suspend fun getTrending(
        @Query("country") country: String = "IN"
    ): List<Any>

    @GET("api/v1/health")
    suspend fun getHealth(): Map<String, Any>
}
