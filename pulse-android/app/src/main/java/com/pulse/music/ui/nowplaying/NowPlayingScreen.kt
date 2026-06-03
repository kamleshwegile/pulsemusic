package com.pulse.music.ui.nowplaying

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NowPlayingScreen(viewModel: NowPlayingViewModel = hiltViewModel()) {
    val currentSong = viewModel.currentSong.collectAsState().value
    val progress = viewModel.progress.collectAsState().value
    val lyricsState = viewModel.lyricsState.collectAsState().value

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Now Playing", style = MaterialTheme.typography.headlineSmall)
        Text("Song Title")
        Text("Artist")

        Slider(value = progress.toFloat(), onValueChange = {})

        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            IconButton(onClick = {}) { Text("Prev") }
            IconButton(onClick = {}) { Text("Play") }
            IconButton(onClick = {}) { Text("Next") }
        }

        AnimatedVisibility(visible = true) {
            Column {
                Text("Lyrics Panel", color = MaterialTheme.colorScheme.onSurface)
                when (lyricsState) {
                    is LyricsState.Loading -> CircularProgressIndicator()
                    is LyricsState.Plain -> Text((lyricsState as LyricsState.Plain).text)
                    is LyricsState.Synced -> { /* Synced scrolling logic */ }
                    is LyricsState.Unavailable -> Text("No lyrics found")
                }
            }
        }
    }
}
