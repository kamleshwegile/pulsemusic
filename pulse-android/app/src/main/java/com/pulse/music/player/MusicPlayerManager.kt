package com.pulse.music.player

import android.content.Context
import android.media.AudioManager
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.pulse.music.domain.Song
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.cancelChildren
import javax.inject.Inject
import javax.inject.Singleton
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import com.pulse.music.data.repository.dataStore
import kotlinx.coroutines.flow.first

enum class RepeatMode { OFF, ONE, ALL }

@Singleton
class MusicPlayerManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val onlineRepo: com.pulse.music.data.repository.OnlineMusicRepository
) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private val sharedDataSourceFactory by lazy {
        val cacheSize: Long = 1024L * 1024L * 1024L // 1 GB
        val cacheDir = java.io.File(context.cacheDir, "audio_cache")
        val cache = androidx.media3.datasource.cache.SimpleCache(
            cacheDir,
            androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor(cacheSize),
            androidx.media3.database.StandaloneDatabaseProvider(context)
        )
        val cacheDataSourceFactory = androidx.media3.datasource.cache.CacheDataSource.Factory()
            .setCache(cache)
            .setUpstreamDataSourceFactory(androidx.media3.datasource.DefaultHttpDataSource.Factory())
            .setFlags(androidx.media3.datasource.cache.CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
            
        androidx.media3.datasource.ResolvingDataSource.Factory(
            cacheDataSourceFactory,
            object : androidx.media3.datasource.ResolvingDataSource.Resolver {
                override fun resolveDataSpec(dataSpec: androidx.media3.datasource.DataSpec): androidx.media3.datasource.DataSpec {
                    val uri = dataSpec.uri
                    if (uri.scheme == "pulse" && uri.host == "resolve") {
                        val songId = uri.lastPathSegment ?: return dataSpec
                        val title = uri.getQueryParameter("title") ?: ""
                        val artist = uri.getQueryParameter("artist") ?: ""
                        
                        val dummySong = Song(id = songId, title = title, artist = artist, album = "", albumArt = "", durationMs = null, source = "online")
                        val resolvedUrl = kotlinx.coroutines.runBlocking { resolveSongSource(dummySong) }
                        if (resolvedUrl != "online") {
                            return dataSpec.buildUpon().setUri(android.net.Uri.parse(resolvedUrl)).build()
                        }
                    }
                    return dataSpec
                }
            }
        )
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun createExoPlayer(): ExoPlayer {
        val audioAttributes = androidx.media3.common.AudioAttributes.Builder()
            .setUsage(androidx.media3.common.C.USAGE_MEDIA)
            .setContentType(androidx.media3.common.C.AUDIO_CONTENT_TYPE_MUSIC)
            .build()
            
        return ExoPlayer.Builder(context)
            .setAudioAttributes(audioAttributes, false)
            .setMediaSourceFactory(androidx.media3.exoplayer.source.DefaultMediaSourceFactory(context).setDataSourceFactory(sharedDataSourceFactory))
            .build().also { exo ->
            exo.addListener(createPlayerListener(exo))
        }
    }

    private val player1: ExoPlayer by lazy { createExoPlayer() }
    private val player2: ExoPlayer by lazy { createExoPlayer() }
    
    private val _activePlayerFlow = MutableStateFlow<ExoPlayer>(player1)
    val activePlayerFlow: StateFlow<ExoPlayer> = _activePlayerFlow.asStateFlow()
    
    val player: ExoPlayer get() = _activePlayerFlow.value
    private var mediaControllerFuture: com.google.common.util.concurrent.ListenableFuture<androidx.media3.session.MediaController>? = null
    private var crossfadeSecs = 0
    private var fadingPlayer: ExoPlayer? = null
    private var isCrossfading = false
    private var crossfadeStartTime = 0L

    init {
        val sessionToken = androidx.media3.session.SessionToken(context, android.content.ComponentName(context, PlaybackService::class.java))
        mediaControllerFuture = androidx.media3.session.MediaController.Builder(context, sessionToken).buildAsync()
        
        scope.launch {
            val cfKey = intPreferencesKey("crossfade")
            val gaplessKey = booleanPreferencesKey("gapless")
            context.dataStore.data.collect { prefs ->
                crossfadeSecs = prefs[cfKey] ?: 0
                val gapless = prefs[gaplessKey] ?: false
                player1.skipSilenceEnabled = gapless
                player2.skipSilenceEnabled = gapless
            }
        }
    }

    // ── Queue ─────────────────────────────────────────────────────────────

    private var _queue = MutableStateFlow<List<Song>>(emptyList())
    val queue: StateFlow<List<Song>> = _queue.asStateFlow()

    private var _currentIndex = MutableStateFlow(-1)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    // ── Exposed state ────────────────────────────────────────────────────

    private val _currentSong = MutableStateFlow<Song?>(null)
    val currentSong: StateFlow<Song?> = _currentSong.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()

    private val _shuffleEnabled = MutableStateFlow(false)
    val shuffleEnabled: StateFlow<Boolean> = _shuffleEnabled.asStateFlow()

    private val _repeatMode = MutableStateFlow(RepeatMode.OFF)
    val repeatMode: StateFlow<RepeatMode> = _repeatMode.asStateFlow()

    // ── Volume ───────────────────────────────────────────────────────────

    val maxVolume: Int get() = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

    fun getVolumeFraction(): Float {
        val current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        val max = maxVolume
        return if (max > 0) current.toFloat() / max.toFloat() else 0f
    }

    fun setVolumeFraction(fraction: Float) {
        val max = maxVolume
        val newVol = (fraction * max).toInt().coerceIn(0, max)
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVol, 0)
    }

    // ── Position polling ─────────────────────────────────────────────────

    private var positionPollingJob: Job? = null

    private fun startPositionPolling() {
        positionPollingJob?.cancel()
        positionPollingJob = scope.launch {
            while (isActive) {
                _currentPosition.value = player.currentPosition
                

                // Crossfade Logic
                try {
                    if (crossfadeSecs > 0) {
                        val duration = player.duration
                        if (duration > 0 && duration != androidx.media3.common.C.TIME_UNSET) {
                            val timeLeft = duration - player.currentPosition
                            var actualCrossfadeMs = crossfadeSecs * 1000L
                            
                            // Temporarily comment out smart gapless so crossfade triggers on ALL tracks
                            /*
                            val currentIdx = player.currentMediaItemIndex
                            if (currentIdx >= 0 && currentIdx < _queue.value.size - 1) {
                                val currentSong = _queue.value[currentIdx]
                                val nextSong = _queue.value[currentIdx + 1]
                                if (!currentSong.album.isNullOrEmpty() && currentSong.album == nextSong.album) {
                                    actualCrossfadeMs = 0L // Fall back to pure gapless
                                }
                            }
                            */
                            
                            // Debug log every 5 seconds left
                            if (timeLeft in 1..20000L && timeLeft % 1000L < 50L) {
                                android.util.Log.d("Crossfade", "Tick: timeLeft=$timeLeft, crossfadeMs=$actualCrossfadeMs, queueIdx=${player.currentMediaItemIndex}")
                            }

                            if (actualCrossfadeMs > 0L && timeLeft <= actualCrossfadeMs && timeLeft > 0 && player.currentMediaItemIndex < _queue.value.size - 1 && !isCrossfading) {
                                val nextPlayer = if (player === player1) player2 else player1
                                if (nextPlayer.mediaItemCount > 0) {
                                    android.util.Log.d("Crossfade", "--- Crossfade Check ---")
                                    android.util.Log.d("Crossfade", "currentPlayer.isPlaying: ${player.isPlaying}")
                                    android.util.Log.d("Crossfade", "alternatePlayer.isPlaying: ${nextPlayer.isPlaying}")
                                    android.util.Log.d("Crossfade", "alternatePlayer.playbackState: ${nextPlayer.playbackState}")
                                    android.util.Log.d("Crossfade", "alternatePlayer.currentMediaItem: ${nextPlayer.currentMediaItem?.mediaId}")

                                    if (nextPlayer.playbackState == androidx.media3.common.Player.STATE_READY) {
                                        android.util.Log.d("Crossfade", "Secondary player is READY. Starting crossfade.")
                                        isCrossfading = true
                                        crossfadeStartTime = System.currentTimeMillis()
                                        
                                        fadingPlayer = player
                                        val fadingIdx = fadingPlayer!!.currentMediaItemIndex
                                        if (fadingIdx + 1 < fadingPlayer!!.mediaItemCount) {
                                            fadingPlayer!!.removeMediaItems(fadingIdx + 1, fadingPlayer!!.mediaItemCount)
                                        }

                                        nextPlayer.volume = 0f
                                        nextPlayer.play()
                                        android.util.Log.d("Crossfade", "Secondary player play() called.")
                                        
                                        // Spotify-style: Swap UI to the next track immediately
                                        _activePlayerFlow.value = nextPlayer
                                        val nextIdx = nextPlayer.currentMediaItemIndex
                                        if (nextIdx >= 0 && nextIdx < _queue.value.size) {
                                            _currentIndex.value = nextIdx
                                            _currentSong.value = _queue.value[nextIdx]
                                        }
                                    } else {
                                        android.util.Log.w("Crossfade", "Secondary player NOT READY (State: ${nextPlayer.playbackState}). Fallback: Continuing current track.")
                                    }
                                }
                            }
                        }
                    }
                } catch(e: Exception) {}
                
                // Headroom and ReplayGain simulation
                val headroom = 0.707f // -3dB to prevent clipping during overlap sum
                val replayGainOut = 1.0f // Placeholder: read from current track metadata
                val replayGainIn = 1.0f  // Placeholder: read from next track metadata

                if (isCrossfading && fadingPlayer != null) {
                    val elapsed = System.currentTimeMillis() - crossfadeStartTime
                    val crossfadeMs = crossfadeSecs * 1000L
                    if (elapsed < crossfadeMs) {
                        val fraction = elapsed.toFloat() / crossfadeMs.toFloat()
                        // Equal-power crossfade using sine/cosine for Spotify-like transition
                        val fadeOut = kotlin.math.cos((Math.PI / 2.0) * fraction).toFloat()
                        val fadeIn = kotlin.math.sin((Math.PI / 2.0) * fraction).toFloat()
                        
                        fadingPlayer!!.volume = fadeOut * headroom * replayGainOut
                        val nextPlayer = if (fadingPlayer === player1) player2 else player1
                        nextPlayer.volume = fadeIn * headroom * replayGainIn
                    } else {
                        isCrossfading = false
                        
                        val nextPlayerTemp = player // Since we already swapped at the start, player is now the next player
                        nextPlayerTemp.volume = 1f * headroom * replayGainIn
                        
                        fadingPlayer!!.stop()
                        fadingPlayer!!.clearMediaItems()
                        fadingPlayer = null
                        android.util.Log.d("Crossfade", "Player swap completed.")
                    }
                } else {
                    player.volume = 1f * headroom * replayGainOut
                }
                
                delay(16L) // ~60Hz update rate for smooth volume ramping
            }
        }
    }

    private fun stopPositionPolling() {
        positionPollingJob?.cancel()
        positionPollingJob = null
    }

    private fun preBufferAlternatePlayer() {
        val currentIdx = player.currentMediaItemIndex
        val nextIdx = currentIdx + 1
        val alternatePlayer = if (player === player1) player2 else player1
        if (nextIdx >= 0 && nextIdx < _queue.value.size) {
            android.util.Log.d("Crossfade", "preBufferAlternatePlayer: Preparing nextIdx $nextIdx")
            alternatePlayer.setMediaItems(_queue.value.map { buildMediaItem(it) })
            alternatePlayer.seekTo(nextIdx, 0)
            alternatePlayer.prepare()
            alternatePlayer.pause()
        } else {
            alternatePlayer.clearMediaItems()
        }
    }

    fun reset() {
        _queue.value = emptyList()
        _currentIndex.value = -1
        _currentSong.value = null
        _isPlaying.value = false
        _currentPosition.value = 0L
        _duration.value = 0L
        
        player1.pause()
        player1.stop()
        player1.clearMediaItems()
        player2.pause()
        player2.stop()
        player2.clearMediaItems()
        fadingPlayer = null
        isCrossfading = false
        stopPositionPolling()
    }

    private suspend fun resolveSongSource(song: Song): String {
        if (song.id.startsWith("local_") || song.source.contains("saavncdn.com") || song.source.endsWith(".mp3") || song.source.endsWith(".mp4") || song.source.endsWith(".m4a")) {
            return song.source
        }
        return withContext(Dispatchers.IO) {
            try {
                val url = "https://music-api.albatross0071.workers.dev/api/songs/${song.id}"
                val conn = java.net.URL(url).openConnection() as java.net.HttpURLConnection
                conn.requestMethod = "GET"
                conn.connectTimeout = 5000
                conn.readTimeout = 5000
                val text = conn.inputStream.bufferedReader().use { it.readText() }
                conn.disconnect()
                
                val json = org.json.JSONObject(text)
                if (json.getBoolean("success")) {
                    val dataArray = json.getJSONArray("data")
                    if (dataArray.length() > 0) {
                        val downloadUrls = dataArray.getJSONObject(0).getJSONArray("downloadUrl")
                        val bestUrlObj = downloadUrls.getJSONObject(downloadUrls.length() - 1)
                        val u = bestUrlObj.getString("url")
                        if (u.isNotEmpty()) return@withContext u
                    }
                }
            } catch (e: Exception) {
                // Ignore and fallback
            }
            
            // Fallback 1: Direct JioSaavn API decryption
            try {
                val url = "https://www.jiosaavn.com/api.php?__call=song.getDetails&pids=${song.id}&_format=json&_marker=0&ctx=web6dot0"
                val conn = java.net.URL(url).openConnection() as java.net.HttpURLConnection
                conn.requestMethod = "GET"
                conn.connectTimeout = 5000
                conn.readTimeout = 5000
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                val text = conn.inputStream.bufferedReader().use { it.readText() }
                conn.disconnect()
                
                val json = org.json.JSONObject(text)
                var songObj: org.json.JSONObject? = null
                if (json.has(song.id)) {
                    songObj = json.getJSONObject(song.id)
                } else if (json.has("songs")) {
                    val songsArr = json.getJSONArray("songs")
                    if (songsArr.length() > 0) {
                        songObj = songsArr.getJSONObject(0)
                    }
                }
                
                if (songObj != null) {
                    if (songObj.has("encrypted_media_url")) {
                        try {
                            val encUrl = songObj.getString("encrypted_media_url")
                            val key = "38346591".toByteArray()
                            val secretKeySpec = javax.crypto.spec.SecretKeySpec(key, "DES")
                            val cipher = javax.crypto.Cipher.getInstance("DES/ECB/PKCS5Padding")
                            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, secretKeySpec)
                            val decoded = android.util.Base64.decode(encUrl.trim(), android.util.Base64.DEFAULT)
                            val decrypted = cipher.doFinal(decoded)
                            var decryptedUrl = String(decrypted)
                            
                            val kbps320 = songObj.optBoolean("320kbps", false) || songObj.optString("320kbps") == "true"
                            if (kbps320) {
                                decryptedUrl = decryptedUrl.replace("_96.mp4", "_320.mp4")
                            } else {
                                decryptedUrl = decryptedUrl.replace("_96.mp4", "_160.mp4")
                            }
                            
                            if (decryptedUrl.isNotEmpty()) {
                                return@withContext decryptedUrl
                            }
                        } catch (e: Exception) {
                            android.util.Log.e("MusicPlayerManager", "Decryption failed", e)
                        }
                    }
                    
                    // Fallback to media_preview_url hack
                    if (songObj.has("media_preview_url")) {
                        val preview = songObj.getString("media_preview_url")
                        val kbps320 = songObj.optBoolean("320kbps", false) || songObj.optString("320kbps") == "true"
                        var full = preview.replace("preview.saavncdn.com", "aac.saavncdn.com")
                        if (kbps320) {
                            full = full.replace("_96_p.mp4", "_320.mp4")
                        } else {
                            full = full.replace("_96_p.mp4", "_160.mp4")
                        }
                        if (full.isNotEmpty()) {
                            return@withContext full
                        }
                    }
                }
            } catch (e: Exception) {
                android.util.Log.e("MusicPlayerManager", "JioSaavn decryption failed: ", e)
                e.printStackTrace()
            }
            
            // Fallback 2: saavn.dev API
            try {
                val url = "https://saavn.dev/api/songs?id=${song.id}"
                val conn = java.net.URL(url).openConnection() as java.net.HttpURLConnection
                conn.requestMethod = "GET"
                conn.connectTimeout = 5000
                conn.readTimeout = 5000
                val text = conn.inputStream.bufferedReader().use { it.readText() }
                conn.disconnect()
                
                val json = org.json.JSONObject(text)
                if (json.getBoolean("success")) {
                    val dataArray = json.getJSONArray("data")
                    if (dataArray.length() > 0) {
                        val downloadUrls = dataArray.getJSONObject(0).getJSONArray("downloadUrl")
                        val bestUrlObj = downloadUrls.getJSONObject(downloadUrls.length() - 1)
                        val u = bestUrlObj.getString("url")
                        if (u.isNotEmpty()) return@withContext u
                    }
                }
            } catch (e: Exception) {
                android.util.Log.e("MusicPlayerManager", "Saavn.dev failed: ", e)
                e.printStackTrace()
            }
            
            // Fallback 2: search
            try {
                val searchUrl = "${com.pulse.music.BuildConfig.API_BASE_URL}api/v1/search?q=${java.net.URLEncoder.encode(song.title + " " + song.artist, "UTF-8")}"
                val searchConn = java.net.URL(searchUrl).openConnection() as java.net.HttpURLConnection
                searchConn.connectTimeout = 5000
                searchConn.readTimeout = 5000
                val searchText = searchConn.inputStream.bufferedReader().use { it.readText() }
                searchConn.disconnect()
                val searchJson = org.json.JSONObject(searchText)
                val songsArray = searchJson.getJSONArray("songs")
                if (songsArray.length() > 0) {
                    val searchId = songsArray.getJSONObject(0).getString("id")
                    // Recursively resolve the real ID
                    if (searchId != song.id && searchId.isNotEmpty()) {
                        return@withContext resolveSongSource(song.copy(id = searchId))
                    }
                }
            } catch (e: Exception) {
                android.util.Log.e("MusicPlayerManager", "Search fallback failed: ", e)
                e.printStackTrace()
            }
            return@withContext song.source
        }
    }

    // ── Player.Listener ──────────────────────────────────────────────────

    private fun createPlayerListener(targetPlayer: ExoPlayer) = object : Player.Listener {

        override fun onIsPlayingChanged(playing: Boolean) {
            if (targetPlayer !== player) return
            _isPlaying.value = playing
            if (playing) startPositionPolling() else stopPositionPolling()
        }
        
        override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
            if (targetPlayer !== player) return
            super.onPlayerError(error)
            error.printStackTrace()
            android.os.Handler(android.os.Looper.getMainLooper()).post {
                android.widget.Toast.makeText(context, "Playback Error: ${error.message}", android.widget.Toast.LENGTH_LONG).show()
            }
        }
        
        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            if (targetPlayer !== player) return
            if (isCrossfading) return // Ignore transitions during crossfade, we already swapped active player

            val idx = player.currentMediaItemIndex
            if (idx >= 0 && idx < _queue.value.size) {
                _currentIndex.value = idx
                val current = _queue.value[idx]
                _currentSong.value = current
                
                // Pre-buffer alternate player
                val nextIdx = idx + 1
                if (nextIdx < _queue.value.size) {
                    val alternatePlayer = if (player === player1) player2 else player1
                    alternatePlayer.setMediaItems(_queue.value.map { buildMediaItem(it) })
                    alternatePlayer.seekTo(nextIdx, 0)
                    alternatePlayer.prepare()
                    alternatePlayer.pause()
                }

                // Dynamically fetch and append 5 recommended songs to the queue on every song play
                if (!current.id.startsWith("local_")) {
                    scope.launch(Dispatchers.IO) {
                        try {
                            val recs = onlineRepo.getRecommendations(current.artist, current.title).getOrNull()
                            if (!recs.isNullOrEmpty()) {
                                // Filter out songs already in the queue and take up to 5
                                val existingTitles = _queue.value.map { it.title.trim().lowercase() + it.artist.trim().lowercase() }.toSet()
                                val newSongs = recs.filter { it.title.trim().lowercase() + it.artist.trim().lowercase() !in existingTitles }
                                    .distinctBy { it.title.trim().lowercase() + it.artist.trim().lowercase() }
                                    .take(5)
                                
                                if (newSongs.isNotEmpty()) {
                                    withContext(Dispatchers.Main) {
                                        val q = _queue.value.toMutableList()
                                        q.addAll(newSongs)
                                        _queue.value = q
                                        
                                        // Add to ExoPlayer
                                        for (song in newSongs) {
                                            player.addMediaItem(buildMediaItem(song))
                                        }
                                        preBufferAlternatePlayer()
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            if (playbackState == Player.STATE_READY) {
                _duration.value = player.duration.coerceAtLeast(0L)
            }
        }
    }

    // ── Public API ───────────────────────────────────────────────────────

    /** Play a song and set entire list as queue. */
    fun playSongFromList(song: Song, allSongs: List<Song>) {
        abortCrossfade()
        scope.launch {
            _queue.value = allSongs
            val idx = allSongs.indexOfFirst { it.id == song.id }.coerceAtLeast(0)
            _currentIndex.value = idx
            
            val resolvedSource = resolveSongSource(song)
            val resolvedSong = song.copy(source = resolvedSource)
            
            val updatedQueue = allSongs.toMutableList()
            updatedQueue[idx] = resolvedSong
            _queue.value = updatedQueue
            _currentSong.value = resolvedSong
            
            val mediaItems = updatedQueue.map { buildMediaItem(it) }
            player.setMediaItems(mediaItems, idx, 0)
            player.prepare()
            player.play()
            preBufferAlternatePlayer()
            
            // Auto-fetch recommendations and append to queue
            launch(Dispatchers.IO) {
                try {
                    val encodedArtist = java.net.URLEncoder.encode(song.artist.split(",").first().trim(), "UTF-8")
                    val encodedTrack = java.net.URLEncoder.encode(song.title, "UTF-8")
                    val url = "${com.pulse.music.BuildConfig.API_BASE_URL}api/v1/recommendations?artist=$encodedArtist&track=$encodedTrack"
                    val conn = java.net.URL(url).openConnection() as java.net.HttpURLConnection
                    conn.requestMethod = "GET"
                    conn.connectTimeout = 5000
                    conn.readTimeout = 5000
                    val text = conn.inputStream.bufferedReader().use { it.readText() }
                    conn.disconnect()
                    
                    val arr = org.json.JSONArray(text)
                    val recs = mutableListOf<Song>()
                    for (i in 0 until arr.length()) {
                        val obj = arr.getJSONObject(i)
                        val recId = obj.getString("id")
                        val recTitle = obj.getString("title")
                        val recArtist = obj.optString("artist", "Unknown")
                        val recAlbum = obj.optString("album", "")
                        val recArt = obj.optString("albumArt", "")
                        recs.add(Song(recId, recTitle, recArtist, recAlbum, recArt, null, "online"))
                    }
                    
                    withContext(Dispatchers.Main) {
                        val q = _queue.value.toMutableList()
                        val existingIds = q.map { it.id }.toSet()
                        val uniqueRecs = recs.filter { it.id !in existingIds }
                        if (uniqueRecs.isNotEmpty()) {
                            q.addAll(uniqueRecs)
                            _queue.value = q
                            player.addMediaItems(uniqueRecs.map { buildMediaItem(it) })
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    /** Play a single song. */
    @OptIn(UnstableApi::class)
    fun playSong(song: Song) {
        abortCrossfade()
        scope.launch {
            val resolvedSource = resolveSongSource(song)
            val resolvedSong = song.copy(source = resolvedSource)
            
            val idx = _queue.value.indexOfFirst { it.id == song.id }
            if (idx >= 0) {
                val q = _queue.value.toMutableList()
                q[idx] = resolvedSong
                _queue.value = q
                _currentIndex.value = idx
                _currentSong.value = resolvedSong
                
                player.replaceMediaItem(idx, buildMediaItem(resolvedSong))
                player.seekTo(idx, 0) // Force start from beginning
                player.prepare()
                player.play()
                preBufferAlternatePlayer()
            } else {
                val q = _queue.value.toMutableList()
                q.add(resolvedSong)
                _queue.value = q
                
                val newIdx = q.size - 1
                _currentIndex.value = newIdx
                _currentSong.value = resolvedSong
                
                player.addMediaItem(buildMediaItem(resolvedSong))
                player.seekTo(newIdx, 0)
                player.prepare()
                player.play()
                preBufferAlternatePlayer()
            }
        }
    }

    fun enqueue(song: Song) {
        scope.launch {
            val resolvedSong = song.copy(source = resolveSongSource(song))
            withContext(Dispatchers.Main) {
                val q = _queue.value.toMutableList()
                q.add(resolvedSong)
                _queue.value = q
                player.addMediaItem(buildMediaItem(resolvedSong))
                preBufferAlternatePlayer()
                if (!player.isPlaying && _queue.value.size == 1) {
                    _currentIndex.value = 0
                    _currentSong.value = resolvedSong
                    player.prepare()
                    player.play()
                }
            }
        }
    }

    private fun buildMediaItem(song: Song): MediaItem {
        val metadata = androidx.media3.common.MediaMetadata.Builder()
            .setTitle(song.title)
            .setArtist(song.artist)
            .setAlbumTitle(song.album)
            .setArtworkUri(if (song.albumArt != null) android.net.Uri.parse(song.albumArt) else null)
            .build()
            
        val uriStr = if (!song.id.startsWith("local_") && !song.source.contains("saavncdn.com") && !song.source.contains("youtube") && !song.source.endsWith(".mp3") && !song.source.endsWith(".m4a")) {
            "pulse://resolve/${song.id}?title=${java.net.URLEncoder.encode(song.title, "UTF-8")}&artist=${java.net.URLEncoder.encode(song.artist, "UTF-8")}"
        } else {
            song.source
        }
            
        return MediaItem.Builder()
            .setMediaId(song.id)
            .setUri(android.net.Uri.parse(uriStr))
            .setMediaMetadata(metadata)
            .build()
    }

    private fun abortCrossfade() {
        if (isCrossfading) {
            isCrossfading = false
            fadingPlayer?.stop()
            fadingPlayer?.clearMediaItems()
            fadingPlayer = null
            player.volume = 1f
        }
    }

    /** Toggle between play and pause. */
    fun togglePlayPause() {
        if (player.isPlaying) {
            player.pause()
            if (isCrossfading) {
                val alternate = if (player === player1) player2 else player1
                alternate.pause()
            }
        } else {
            player.play()
            if (isCrossfading) {
                val alternate = if (player === player1) player2 else player1
                alternate.play()
            }
        }
    }

    /** Seek to [positionMs] within the current track. */
    fun seekTo(positionMs: Long) {
        abortCrossfade()
        player.seekTo(positionMs)
        _currentPosition.value = positionMs
    }

    /** Skip to the next track in the queue. */
    fun skipNext() {
        if (isCrossfading) {
            // Force complete the crossfade immediately
            player.volume = 1f
            fadingPlayer?.stop()
            fadingPlayer?.clearMediaItems()
            fadingPlayer = null
            isCrossfading = false
            
            preBufferAlternatePlayer()
            return
        }
        if (player.hasNextMediaItem()) {
            player.seekToNextMediaItem()
        }
    }

    /** Skip to the previous track, or restart if > 3s in. */
    fun skipPrevious() {
        abortCrossfade()
        if (player.currentPosition > 3000) {
            player.seekTo(0)
            _currentPosition.value = 0
            return
        }
        if (player.hasPreviousMediaItem()) {
            player.seekToPreviousMediaItem()
        }
    }

    /** Toggle shuffle on/off. */
    fun toggleShuffle() {
        _shuffleEnabled.value = !_shuffleEnabled.value
        player.shuffleModeEnabled = _shuffleEnabled.value
    }

    /** Cycle repeat mode: OFF → ALL → ONE → OFF. */
    fun cycleRepeatMode() {
        _repeatMode.value = when (_repeatMode.value) {
            RepeatMode.OFF -> RepeatMode.ALL
            RepeatMode.ALL -> RepeatMode.ONE
            RepeatMode.ONE -> RepeatMode.OFF
        }
        player.repeatMode = when (_repeatMode.value) {
            RepeatMode.OFF -> Player.REPEAT_MODE_OFF
            RepeatMode.ALL -> Player.REPEAT_MODE_ALL
            RepeatMode.ONE -> Player.REPEAT_MODE_ONE
        }
    }

    /** Remove a song from queue by index. */
    fun removeFromQueue(index: Int) {
        val q = _queue.value.toMutableList()
        if (index < 0 || index >= q.size) return
        q.removeAt(index)
        _queue.value = q
        player.removeMediaItem(index)
        
        if (q.isEmpty()) {
            _currentSong.value = null
            _currentIndex.value = -1
        }
    }

    fun stopAndClear() {
        scope.coroutineContext.cancelChildren()
        player1.pause()
        player1.stop()
        player1.clearMediaItems()
        player2.pause()
        player2.stop()
        player2.clearMediaItems()
        _queue.value = emptyList()
        _currentSong.value = null
        _currentIndex.value = -1
        _isPlaying.value = false
        _currentPosition.value = 0L
    }

    /** Release the underlying [ExoPlayer] and cancel coroutines. */
    fun release() {
        stopPositionPolling()
        player1.release()
        player2.release()
    }
}
