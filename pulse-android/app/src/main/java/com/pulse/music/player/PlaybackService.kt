package com.pulse.music.player

import android.content.Intent
import androidx.media3.common.Player
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.DefaultMediaNotificationProvider
import androidx.media3.session.MediaNotification
import androidx.core.app.NotificationCompat
import android.widget.RemoteViews
import com.pulse.music.R
import android.app.PendingIntent
import androidx.media3.common.util.UnstableApi
import androidx.annotation.OptIn
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.cancel

@AndroidEntryPoint
class PlaybackService : MediaSessionService() {
    
    @Inject
    lateinit var musicPlayerManager: MusicPlayerManager

    // Observe shared JamSessionManager for network playback sync
    private val jamSessionManager = com.pulse.music.ui.jam.JamSessionManager

    private var mediaSession: MediaSession? = null
    private val scope = kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.SupervisorJob() + kotlinx.coroutines.Dispatchers.Main)
    
    

    override fun onCreate() {
        super.onCreate()
        val player = musicPlayerManager.player
        // Observe Jam incoming sync events and apply to local player
        var ignoreNextSeek = false
        var ignoreNextPlayChange = false

        scope.launch {
            jamSessionManager.incomingSync.collect { sync ->
                sync?.let { (isPlaying, positionMs, _) ->
                    if (player.playWhenReady != isPlaying) {
                        ignoreNextPlayChange = true
                        player.playWhenReady = isPlaying
                    }
                    val diff = player.currentPosition - positionMs
                    if (Math.abs(diff) > 2000) {
                        // Hard seek if diff > 2 seconds
                        ignoreNextSeek = true
                        player.seekTo(positionMs)
                        player.playbackParameters = androidx.media3.common.PlaybackParameters(1.0f)
                    } else if (Math.abs(diff) > 100) {
                        // Drift correction: Speed up or slow down slightly
                        val speed = if (diff < 0) 1.02f else 0.98f
                        player.playbackParameters = androidx.media3.common.PlaybackParameters(speed)
                    } else {
                        // In sync, restore normal speed
                        player.playbackParameters = androidx.media3.common.PlaybackParameters(1.0f)
                    }
                }
            }
        }
        scope.launch {
            jamSessionManager.incomingSongWithSync.collect { data ->
                data?.let { (song, syncInfo) ->
                    val (isPlaying, positionMs) = syncInfo
                    if (musicPlayerManager.currentSong.value?.id != song.id) {
                        if (player.playWhenReady != isPlaying) {
                            ignoreNextPlayChange = true
                        }
                        if (Math.abs(player.currentPosition - positionMs) > 1000) {
                            ignoreNextSeek = true
                        }
                        musicPlayerManager.playSong(song, positionMs, isPlaying)
                    }
                }
            }
        }
        scope.launch {
            jamSessionManager.incomingSong.collect { song ->
                song?.let { 
                    if (musicPlayerManager.currentSong.value?.id != it.id) {
                        musicPlayerManager.playSong(it)
                    }
                }
            }
        }
        scope.launch {
            jamSessionManager.queue.collect { queueJson ->
                if (jamSessionManager.isConnected.value && queueJson.isNotEmpty()) {
                    val songs = queueJson.map { item ->
                        com.pulse.music.domain.Song(
                            id = item.optString("song_id", ""),
                            title = item.optString("title", "Unknown Title"),
                            artist = item.optString("artist", "Unknown Artist"),
                            albumArt = item.optString("albumArt", ""),
                            durationMs = item.optLong("durationMs", 0L),
                            source = item.optString("source", "")
                        )
                    }
                    musicPlayerManager.setQueueSync(songs)
                }
            }
        }
        // Listen to local playback changes and broadcast to Jam participants
        player.addListener(object : Player.Listener {
            override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                if (ignoreNextPlayChange) {
                    ignoreNextPlayChange = false
                    return
                }
                val position = player.currentPosition
                jamSessionManager.broadcastPlayPause(playWhenReady, position)
            }
            
            override fun onPositionDiscontinuity(oldPosition: Player.PositionInfo, newPosition: Player.PositionInfo, reason: Int) {
                if (reason == Player.DISCONTINUITY_REASON_SEEK) {
                    if (ignoreNextSeek) {
                        ignoreNextSeek = false
                        return
                    }
                    val position = newPosition.positionMs
                    jamSessionManager.broadcastPlayPause(player.playWhenReady, position)
                }
            }

            override fun onMediaItemTransition(mediaItem: androidx.media3.common.MediaItem?, reason: Int) {
                // If it transitioned locally (by user explicitly), broadcast it.
                // If it was an AUTO transition (track finished naturally):
                // - Only the HOST should broadcast the next song to keep everyone in sync.
                // - Guests should ignore auto-transitions and wait for the host's broadcast.
                if (reason == Player.MEDIA_ITEM_TRANSITION_REASON_AUTO) {
                    if (jamSessionManager.isConnected.value && !jamSessionManager.isHost) {
                        player.pause()
                        return
                    }
                    if (!jamSessionManager.isHost) return
                }
                
                // Fetch the actual song that the player just transitioned to
                val idx = player.currentMediaItemIndex
                val queue = musicPlayerManager.queue.value
                val newSong = if (idx >= 0 && idx < queue.size) queue[idx] else musicPlayerManager.currentSong.value
                
                if (newSong != null && jamSessionManager.isConnected.value) {
                    jamSessionManager.broadcastPlaySong(newSong)
                }
            }
        })
        val intent = Intent(this, com.pulse.music.ui.lockscreen.LockScreenActivity::class.java)
        val options = android.app.ActivityOptions.makeBasic()
        if (android.os.Build.VERSION.SDK_INT >= 34) { // UPSIDE_DOWN_CAKE
            options.setPendingIntentBackgroundActivityStartMode(android.app.ActivityOptions.MODE_BACKGROUND_ACTIVITY_START_ALLOWED)
        }
        val pendingIntent = android.app.PendingIntent.getActivity(this, 0, intent, android.app.PendingIntent.FLAG_IMMUTABLE, options.toBundle())

        mediaSession = MediaSession.Builder(this, player)
            .setSessionActivity(pendingIntent)
            .setBitmapLoader(object : androidx.media3.common.util.BitmapLoader {
                override fun supportsMimeType(mimeType: String): Boolean = true
                override fun decodeBitmap(data: ByteArray): com.google.common.util.concurrent.ListenableFuture<android.graphics.Bitmap> {
                    return com.google.common.util.concurrent.Futures.immediateFuture(android.graphics.BitmapFactory.decodeByteArray(data, 0, data.size))
                }
                override fun loadBitmapFromMetadata(metadata: androidx.media3.common.MediaMetadata): com.google.common.util.concurrent.ListenableFuture<android.graphics.Bitmap>? {
                    val data = metadata.artworkData
                    if (data != null) {
                        return decodeBitmap(data)
                    }
                    return null
                }
                override fun loadBitmap(uri: android.net.Uri): com.google.common.util.concurrent.ListenableFuture<android.graphics.Bitmap> {
                    val future = com.google.common.util.concurrent.SettableFuture.create<android.graphics.Bitmap>()
                    java.util.concurrent.Executors.newSingleThreadExecutor().execute {
                        try {
                            android.util.Log.d("PlaybackService", "Starting bitmap download for: $uri")
                            val urlStr = uri.toString().replace("http://", "https://")
                            val url = java.net.URL(urlStr)
                            val connection = url.openConnection() as java.net.HttpURLConnection
                            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36")
                            connection.doInput = true
                            connection.connect()
                            val responseCode = connection.responseCode
                            android.util.Log.d("PlaybackService", "HTTP Response Code: $responseCode")
                            val input = connection.inputStream
                            val bitmap = android.graphics.BitmapFactory.decodeStream(input)
                            input.close()
                            connection.disconnect()
                            if (bitmap != null) {
                                android.util.Log.d("PlaybackService", "Bitmap decoded successfully: ${bitmap.width}x${bitmap.height}")
                                future.set(bitmap)
                            } else {
                                android.util.Log.e("PlaybackService", "BitmapFactory returned null for: $urlStr")
                                future.setException(Exception("BitmapFactory returned null"))
                            }
                        } catch (e: Exception) {
                            android.util.Log.e("PlaybackService", "Exception loading bitmap: ${e.message}", e)
                            future.setException(e)
                        }
                    }
                    return future
                }
            })
            .setCallback(object : MediaSession.Callback {
                override fun onCustomCommand(
                    session: MediaSession,
                    controller: MediaSession.ControllerInfo,
                    customCommand: androidx.media3.session.SessionCommand,
                    args: android.os.Bundle
                ): com.google.common.util.concurrent.ListenableFuture<androidx.media3.session.SessionResult> {
                    return com.google.common.util.concurrent.Futures.immediateFuture(
                        androidx.media3.session.SessionResult(androidx.media3.session.SessionResult.RESULT_SUCCESS)
                    )
                }
            })
            .build()
            
        scope.launch {
            musicPlayerManager.activePlayerFlow.collect { newPlayer ->
                mediaSession?.player = newPlayer
            }
        }
        
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        mediaSession?.release()
        mediaSession = null
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        // Hard reset the player manager so the UI state matches the destroyed player
        musicPlayerManager.reset()
        
        try {
            stopForeground(STOP_FOREGROUND_REMOVE)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        stopSelf()
        
        super.onTaskRemoved(rootIntent)
        
        // Completely kill the process so the next launch is 100% fresh
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(0)
    }
}
