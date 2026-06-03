package com.pulse.music.ui.artist

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ArtistScreen(viewModel: ArtistViewModel = hiltViewModel()) {
    val artistInfo = viewModel.artistInfo.collectAsState().value
    
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Artist Name", style = MaterialTheme.typography.headlineLarge)
        
        Text("Popular Tracks")
        
        Text("Albums")
        
        Text("Similar Artists")
    }
}
