package com.pulse.music.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = { TopAppBar(title = { Text("Home") }) }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            item { Text("Good Morning!", style = MaterialTheme.typography.headlineMedium) }
            
            when (uiState) {
                is HomeUiState.Loading -> item { CircularProgressIndicator() }
                is HomeUiState.Error -> item { Text("Error: ${uiState.message}") }
                is HomeUiState.Success -> {
                    item { Text("Recently Played") }
                    item { Text("Trending") }
                    item { Text("Made For You") }
                }
            }
        }
    }
}
