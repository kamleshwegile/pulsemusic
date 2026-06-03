package com.pulse.music.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    val query by viewModel.searchQuery.collectAsState()
    val results by viewModel.results.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.searchQuery.value = it },
            label = { Text("Search songs, artists, albums") },
            modifier = Modifier.fillMaxWidth()
        )
        if (query.isEmpty()) {
            Text("Browse Categories")
        } else {
            Text("Songs")
            Text("Artists")
            Text("Albums")
        }
    }
}
