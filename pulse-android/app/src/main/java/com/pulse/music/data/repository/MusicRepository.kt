package com.pulse.music.data.repository

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Singleton
class MusicRepository @Inject constructor(
    private val onlineRepo: OnlineMusicRepository
    // private val localDao: MusicDao
) {
    fun search(query: String): Flow<List<Any>> = flow {
        // Fallback architecture
        // val local = localDao.search(query)
        // emit(local)
        
        val online = onlineRepo.searchOnline(query)
        if (online.isSuccess) {
            val data = online.getOrNull()
            // Deduplicate and combine logic
            if (data != null) emit(listOf(data))
        }
    }
    
    fun getTrending(): Flow<List<Any>> = flow {
        // val local = localDao.getTrending()
        val online = onlineRepo.getTrending()
        if (online.isSuccess) emit(online.getOrDefault(emptyList()))
        // else if (local.isNotEmpty()) emit(local)
    }
    
    fun getLyrics(title: String, artist: String): Flow<Any?> = flow {
        // val local = localDao.getLyrics(title, artist)
        val online = onlineRepo.getLyrics(title, artist)
        if (online.isSuccess) emit(online.getOrNull())
        // else emit(local)
    }
    
    fun getRecommendations(artist: String, track: String): Flow<List<Any>> = flow {
        val online = onlineRepo.getRecommendations(artist, track)
        if (online.isSuccess) emit(online.getOrDefault(emptyList())) else emit(emptyList())
    }
    
    fun getArtistInfo(name: String): Flow<Any?> = flow {
        // val local = localDao.getArtistInfo(name)
        val online = onlineRepo.getArtistInfo(name)
        if (online.isSuccess) emit(online.getOrNull())
        // else emit(local)
    }
}
