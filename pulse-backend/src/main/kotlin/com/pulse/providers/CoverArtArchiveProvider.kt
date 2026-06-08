package com.pulse.providers

import com.pulse.models.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.*

class CoverArtArchiveProvider : MusicProvider {
    override val name = "CoverArtArchive"
    override val priority = 1

    override suspend fun search(query: String): List<Song> = emptyList()
    override suspend fun getArtist(id: String): Artist? = null
    
    override suspend fun getAlbum(id: String): Album? {
        // id is MBID
        val response = defaultHttpClient.get("https://coverartarchive.org/release/$id")
        if (response.status.value != 200) return null
        
        val json = Json.parseToJsonElement(response.bodyAsText()).jsonObject
        val images = json["images"]?.jsonArray
        val frontImage = images?.firstOrNull { it.jsonObject["front"]?.jsonPrimitive?.boolean == true }?.jsonObject
        val imageUrl = frontImage?.get("image")?.jsonPrimitive?.content
        
        return Album(id = id, title = "", artist = "", coverArt = imageUrl)
    }
    
    override suspend fun getLyrics(title: String, artist: String, songId: String?): Lyrics? = null
    override suspend fun getRecommendations(songId: String): List<Song> = emptyList()
    override fun isHealthy(): Boolean = true
}
