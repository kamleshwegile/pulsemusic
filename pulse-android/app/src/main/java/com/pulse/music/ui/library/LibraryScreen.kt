package com.pulse.music.ui.library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(viewModel: LibraryViewModel = hiltViewModel()) {
    val filter = viewModel.filter.collectAsState().value
    val filters = listOf("All", "Playlists", "Albums", "Artists", "Downloaded")

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Text("+")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            LazyRow {
                items(filters.size) { i ->
                    FilterChip(
                        selected = filter == filters[i],
                        onClick = { viewModel.setFilter(filters[i]) },
                        label = { Text(filters[i]) }
                    )
                }
            }
            Text("Library Items corresponding to Filter: $filter")
        }
    }
}
