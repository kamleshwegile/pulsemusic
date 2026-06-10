package com.pulse.music.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pulse.music.domain.Song

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToNowPlaying: () -> Unit = {},
    onNavigateToAlbum: (String) -> Unit = {},
    onNavigateToArtist: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    var showHistory by remember { mutableStateOf(false) }
    
    if (showHistory && uiState is HomeUiState.Success) {
        val state = uiState as HomeUiState.Success
        RecentlyPlayedScreen(
            songs = state.allRecentPlays ?: emptyList(),
            onBack = { showHistory = false },
            onSongClick = { song ->
                viewModel.playSong(song, state.allRecentPlays ?: emptyList())
                onNavigateToNowPlaying()
            },
            onRemoveSong = { song ->
                viewModel.removeRecentSong(song)
            }
        )
        return
    }
    
    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = { Text("Pulse", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { /* Settings */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Text(
                    text = "Good Morning!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 16.dp)
                )
            }
            
            when (val state = uiState) {
                is HomeUiState.Loading -> {
                    item { 
                        Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = Color(0xFF1ED760))
                        }
                    }
                }
                is HomeUiState.Error -> {
                    item { 
                        Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp)) 
                    }
                }
                is HomeUiState.Success -> {
                    if (state.recentPlays.isNotEmpty()) {
                        item {
                            SectionTitle(
                                title = "Recently Played", 
                                onSeeAll = if ((state.allRecentPlays?.size ?: 0) > 5) { { showHistory = true } } else null
                            )
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp), contentPadding = PaddingValues(horizontal = 16.dp)) {
                                items(state.recentPlays) { song ->
                                    SongCard(song, onRemove = {
                                        viewModel.removeRecentSong(song)
                                    }, onClick = {
                                        viewModel.playSong(song, state.recentPlays)
                                        onNavigateToNowPlaying()
                                    })
                                }
                            }
                        }
                    }

                    if (state.suggested.isNotEmpty()) {
                        item {
                            SectionTitle("Suggested For You")
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp), contentPadding = PaddingValues(horizontal = 16.dp)) {
                                items(state.suggested) { song ->
                                    SongCard(song, onClick = {
                                        viewModel.playSong(song, state.suggested)
                                        onNavigateToNowPlaying()
                                    })
                                }
                            }
                        }
                    }
                    
                    state.modules.forEach { module ->
                        if (module.items.isNotEmpty()) {
                            item {
                                SectionTitle(module.title)
                                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp), contentPadding = PaddingValues(horizontal = 16.dp)) {
                                    items(module.items) { playlist ->
                                        PlaylistCard(
                                            playlist = com.pulse.music.domain.Playlist(
                                                id = playlist.id,
                                                title = playlist.title,
                                                image = playlist.image,
                                                songCount = playlist.songCount,
                                                source = playlist.source
                                            ),
                                            onClick = { onNavigateToAlbum(playlist.id) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            // Proper bottom safe area padding to scroll behind the floating nav bar
            item { Spacer(modifier = Modifier.height(140.dp)) }
        }
    }
}

@Composable
fun SectionTitle(title: String, onSeeAll: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        if (onSeeAll != null) {
            Text(
                text = "See All",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                modifier = Modifier
                    .clickable(onClick = onSeeAll)
                    .padding(4.dp)
            )
        }
    }
}

@Composable
fun SongCard(song: Song, onRemove: (() -> Unit)? = null, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .clickable(onClick = onClick)
    ) {
        Box {
            if (!song.albumArt.isNullOrEmpty()) {
                coil.compose.AsyncImage(
                    model = song.albumArt,
                    contentDescription = song.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF242424)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.MusicNote, contentDescription = "Music", tint = Color.Gray, modifier = Modifier.size(48.dp))
                }
            }
            if (onRemove != null) {
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(24.dp)
                        .background(Color.Black.copy(alpha = 0.5f), androidx.compose.foundation.shape.CircleShape)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Remove", tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = song.title,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = song.artist,
            fontSize = 13.sp,
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun PlaylistCard(playlist: com.pulse.music.domain.Playlist, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .clickable(onClick = onClick)
    ) {
        if (!playlist.image.isNullOrEmpty()) {
            coil.compose.AsyncImage(
                model = playlist.image,
                contentDescription = playlist.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF242424)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.PlaylistPlay, contentDescription = "Playlist", tint = Color.Gray, modifier = Modifier.size(48.dp))
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = playlist.title,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        if (playlist.songCount != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${playlist.songCount} Songs",
                fontSize = 13.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ArtistCard(artist: com.pulse.music.domain.Artist, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!artist.image.isNullOrEmpty()) {
            coil.compose.AsyncImage(
                model = artist.image,
                contentDescription = artist.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(androidx.compose.foundation.shape.CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(Color(0xFF242424)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = "Artist", tint = Color.Gray, modifier = Modifier.size(48.dp))
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = artist.name,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecentlyPlayedScreen(
    songs: List<Song>,
    onBack: () -> Unit,
    onSongClick: (Song) -> Unit,
    onRemoveSong: (Song) -> Unit
) {
    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = { Text("Recently Played", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                )
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(songs) { song ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSongClick(song) }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!song.albumArt.isNullOrEmpty()) {
                        coil.compose.AsyncImage(
                            model = song.albumArt,
                            contentDescription = song.title,
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF242424)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.MusicNote, contentDescription = null, tint = Color.Gray)
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = song.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = song.artist,
                            fontSize = 14.sp,
                            color = Color.Gray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    IconButton(onClick = { onRemoveSong(song) }) {
                        Icon(Icons.Default.Close, contentDescription = "Remove", tint = Color.Gray, modifier = Modifier.size(20.dp))
                    }
                }
            }
        }
    }
}
