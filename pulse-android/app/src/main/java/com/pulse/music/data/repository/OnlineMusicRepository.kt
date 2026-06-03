package com.pulse.music.data.repository

import com.pulse.music.data.network.PulseApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnlineMusicRepository @Inject constructor(
    private val apiService: PulseApiService
) {
    suspend fun searchOnline(query: String): Result<Any> {
        return try {
            val response = apiService.search(query)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getLyrics(title: String, artist: String): Result<Any> {
        return try {
            Result.success(apiService.getLyrics(title, artist))
        } catch(e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getTrending(): Result<List<Any>> {
        return try {
            Result.success(apiService.getTrending())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getRecommendations(artist: String, track: String): Result<List<Any>> {
        return try {
            Result.success(apiService.getRecommendations(artist, track))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getArtistInfo(name: String): Result<Any> {
        return try {
            Result.success(apiService.getArtist(name))
        } catch(e: Exception) {
            Result.failure(e)
        }
    }
}
