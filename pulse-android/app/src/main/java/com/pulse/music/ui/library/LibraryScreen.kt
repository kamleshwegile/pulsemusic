package com.pulse.music.ui.library

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

val PulseRed = Color(0xFFF92839)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    viewModel: LibraryViewModel = hiltViewModel(),
    onNavigateToNowPlaying: () -> Unit = {},
    onNavigateToPlaylist: (Int) -> Unit = {},
    onNavigateToArtist: (String) -> Unit = {}
) {
    val filters = listOf("Liked Songs", "Playlists", "Artists")
    var selectedFilter by remember { mutableStateOf("Liked Songs") }

    val localSongs by viewModel.localSongs.collectAsState()
    val likedSongs by viewModel.likedSongs.collectAsState()
    val playlists by viewModel.playlists.collectAsState()
    val followedArtists by viewModel.followedArtists.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val context = LocalContext.current
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_AUDIO
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    var hasPermission by remember {
        mutableStateOf(ContextCompat.checkSelfPermission(context, permission) == android.content.pm.PackageManager.PERMISSION_GRANTED)
    }
    var hasAskedPermission by remember { mutableStateOf(false) }
    
    var showSpotifyDialog by remember { mutableStateOf(false) }
    var spotifyLink by remember { mutableStateOf("") }
    val isImporting by viewModel.isImporting.collectAsState()
    val contextCompat = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.importResult.collect { msg ->
            android.widget.Toast.makeText(contextCompat, msg, android.widget.Toast.LENGTH_LONG).show()
            showSpotifyDialog = false
            spotifyLink = ""
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
        hasAskedPermission = true
        if (isGranted) viewModel.loadLocalSongs()
    }

    LaunchedEffect(Unit) {
        if (hasPermission) viewModel.loadLocalSongs()
    }

    LaunchedEffect(selectedFilter) {
        if ((selectedFilter == "Local Audio" || selectedFilter == "All") && !hasPermission && !hasAskedPermission) {
            hasAskedPermission = true
            permissionLauncher.launch(permission)
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top App Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.GraphicEq, 
                        contentDescription = "Logo", 
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "Library", 
                        color = MaterialTheme.colorScheme.onBackground, 
                        fontSize = 22.sp, 
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.5).sp
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Search, 
                        contentDescription = "Search", 
                        tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                        modifier = Modifier.size(28.dp).clickable { }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .border(1.dp, MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), CircleShape)
                            .background(Color.DarkGray)
                    ) {
                        AsyncImage(
                            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuAWvVSASo7gh7ddlxBOJXSuaKoJI7_Yyc57LqB2KJI6iQxUP_VKdIDdQJXUIPnDfu8RbqoHa6HxYr0uYiGUcRQbacQ-0JYfM_BiuBr_C_zkwnd243h4lg1V8_XbYxgOas07_A2BnjczCTeCONvMW1xamltiGAtn9ZuVOOSk-yCcDiyuHhl-E7LdN0TT5q0ZHDHwIL0QntvsXY8K_9uvNT6U3gAFLVZDZjQ6CvRP7marVSLD2yMUTf62VWXlkIKEb1_vcet7sj0cTAUI",
                            contentDescription = "Profile",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            // Filter Pill Row
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(filters) { filter ->
                    val isSelected = filter == selectedFilter
                    Box(
                        modifier = Modifier
                            .height(32.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) PulseRed else Color.Transparent)
                            .border(
                                width = if (isSelected) 0.dp else 0.5.dp,
                                color = if (isSelected) Color.Transparent else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                                shape = CircleShape
                            )
                            .clickable { selectedFilter = filter }
                            .padding(horizontal = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = filter,
                            color = if (isSelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                }
            }

            // Content Area
            Box(modifier = Modifier.fillMaxSize()) {
                if (selectedFilter == "Liked Songs") {
                    val displaySongs = if (selectedFilter == "Liked Songs") likedSongs else localSongs
                    val emptyMessage = if (selectedFilter == "Liked Songs") "No liked songs yet" else "No local music found"

                    if (selectedFilter != "Liked Songs" && !hasPermission) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally, 
                            modifier = Modifier.align(Alignment.Center).padding(32.dp)
                        ) {
                            Icon(Icons.Default.Folder, contentDescription = "Folder", tint = PulseRed, modifier = Modifier.size(64.dp))
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Access Local Music", color = MaterialTheme.colorScheme.onBackground, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Allow Pulse to scan your device for local audio files.", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f), textAlign = TextAlign.Center)
                            Spacer(modifier = Modifier.height(24.dp))
                            Button(
                                onClick = { permissionLauncher.launch(permission) },
                                colors = ButtonDefaults.buttonColors(containerColor = PulseRed, contentColor = MaterialTheme.colorScheme.background),
                                shape = CircleShape
                            ) {
                                Text("Grant Permission", fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                            }
                        }
                    } else if (isLoading && selectedFilter != "Liked Songs") {
                        CircularProgressIndicator(color = PulseRed, modifier = Modifier.align(Alignment.Center))
                    } else if (displaySongs.isEmpty()) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.align(Alignment.Center)) {
                            Icon(if (selectedFilter == "Liked Songs") Icons.Default.FavoriteBorder else Icons.Default.MusicOff, contentDescription = "Empty", tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f), modifier = Modifier.size(64.dp))
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(emptyMessage, color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp)
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(), 
                            contentPadding = PaddingValues(bottom = 140.dp)
                        ) {
                            items(displaySongs) { song ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { 
                                            viewModel.playSong(song, displaySongs)
                                            onNavigateToNowPlaying() 
                                        }
                                        .padding(horizontal = 20.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier.size(56.dp).clip(RoundedCornerShape(8.dp)).background(Color.DarkGray),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (!song.albumArt.isNullOrEmpty()) {
                                            AsyncImage(
                                                model = song.albumArt,
                                                contentDescription = song.title,
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = ContentScale.Crop
                                            )
                                        } else {
                                            Icon(Icons.Default.MusicNote, contentDescription = "Audio", tint = MaterialTheme.colorScheme.onBackground, modifier = Modifier.size(24.dp))
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(song.title, color = MaterialTheme.colorScheme.onBackground, fontSize = 16.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Medium)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(song.artist, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f), fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    }
                                }
                            }
                        }
                    }
                } else if (selectedFilter == "Playlists") {
                    if (playlists.isEmpty()) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.align(Alignment.Center)) {
                            Icon(Icons.Default.QueueMusic, contentDescription = "Playlists", tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f), modifier = Modifier.size(64.dp))
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("No playlists yet", color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp)
                            Text("Create one from the player!", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f), fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(24.dp))
                            Button(
                                onClick = { showSpotifyDialog = true },
                                colors = ButtonDefaults.buttonColors(containerColor = PulseRed, contentColor = MaterialTheme.colorScheme.background)
                            ) {
                                Text("Import from Spotify", fontWeight = FontWeight.Bold)
                            }
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(), 
                            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 140.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            item(span = { GridItemSpan(2) }) {
                                OutlinedButton(
                                    onClick = { showSpotifyDialog = true },
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = PulseRed),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, PulseRed),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(Icons.Default.CloudDownload, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                                    Text("Import Spotify Playlist", fontWeight = FontWeight.Bold)
                                }
                            }
                            items(playlists) { playlist ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onNavigateToPlaylist(playlist.id) },
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier.aspectRatio(1f).fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Color.DarkGray),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Default.QueueMusic, contentDescription = "Playlist", tint = MaterialTheme.colorScheme.onBackground, modifier = Modifier.size(48.dp))
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(playlist.name, color = MaterialTheme.colorScheme.onBackground, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                                }
                            }
                        }
                    }
                } else if (selectedFilter == "Artists") {
                    if (followedArtists.isEmpty()) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.align(Alignment.Center)) {
                            Icon(Icons.Default.PersonSearch, contentDescription = "Artists", tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f), modifier = Modifier.size(64.dp))
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("No followed artists", color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp)
                            Text("Follow artists to see them here!", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f), fontSize = 14.sp)
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            modifier = Modifier.fillMaxSize(), 
                            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 140.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(followedArtists) { artist ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onNavigateToArtist(artist.name) },
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier.aspectRatio(1f).fillMaxWidth().clip(CircleShape).background(Color.DarkGray),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (!artist.image.isNullOrEmpty()) {
                                            AsyncImage(
                                                model = artist.image,
                                                contentDescription = artist.name,
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = ContentScale.Crop
                                            )
                                        } else {
                                            Icon(Icons.Default.Person, contentDescription = "Artist", tint = MaterialTheme.colorScheme.onBackground, modifier = Modifier.size(48.dp))
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(artist.name, color = MaterialTheme.colorScheme.onBackground, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                }
                            }
                        }
                    }
                }
            }
        }
        
        if (showSpotifyDialog) {
            AlertDialog(
                onDismissRequest = { if (!isImporting) showSpotifyDialog = false },
                containerColor = Color(0xFF242424),
                titleContentColor = MaterialTheme.colorScheme.onBackground,
                textContentColor = MaterialTheme.colorScheme.onBackground,
                title = { Text("Import Spotify Playlist") },
                text = {
                    Column {
                        Text("Paste your public Spotify playlist link below to automatically match and add all tracks.", color = Color.Gray, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = spotifyLink,
                            onValueChange = { spotifyLink = it },
                            placeholder = { Text("https://open.spotify.com/playlist/...", color = Color.DarkGray) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                                focusedBorderColor = PulseRed,
                                unfocusedBorderColor = Color.Gray
                            ),
                            singleLine = true,
                            enabled = !isImporting
                        )
                        if (isImporting) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CircularProgressIndicator(color = PulseRed, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Importing and matching tracks...", color = PulseRed, fontSize = 14.sp)
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = { viewModel.importSpotifyPlaylist(spotifyLink) },
                        colors = ButtonDefaults.buttonColors(containerColor = PulseRed, contentColor = MaterialTheme.colorScheme.background),
                        enabled = spotifyLink.isNotBlank() && !isImporting
                    ) {
                        Text("Import", fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showSpotifyDialog = false }, enabled = !isImporting) {
                        Text("Cancel", color = Color.Gray)
                    }
                }
            )
        }
    }
}
