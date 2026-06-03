package com.pulse.providers

import com.pulse.models.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.*

class TheAudioDBProvider : MusicProvider {
    override val name = "TheAudioDB"
    override val priority = 2

    override suspend fun search(query: String): List<Song> = emptyList()
    
    override suspend fun getArtist(id: String): Artist? {
        val response = defaultHttpClient.get("https://www.theaudiodb.com/api/v1/json/2/searchartist.php") {
            parameter("s", id)
        }
        if (response.status.value != 200) return null
        val json = Json.parseToJsonElement(response.bodyAsText()).jsonObject
        val artists = json["artists"]?.jsonArray ?: return null
        val artistObj = artists.firstOrNull()?.jsonObject ?: return null
        
        return Artist(
            id = id,
            name = artistObj["strArtist"]?.jsonPrimitive?.content ?: id,
            bio = artistObj["strBiographyEN"]?.jsonPrimitive?.content,
            image = artistObj["strArtistThumb"]?.jsonPrimitive?.content,
            genres = listOfNotNull(artistObj["strGenre"]?.jsonPrimitive?.content),
            score = artistObj["intScore"]?.jsonPrimitive?.content?.toIntOrNull()
        )
    }

    override suspend fun getAlbum(id: String): Album? = null
    override suspend fun getLyrics(title: String, artist: String): Lyrics? = null
    override suspend fun getRecommendations(songId: String): List<Song> = emptyList()
    override fun isHealthy(): Boolean = true
}
