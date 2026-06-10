package com.pulse.music.ui.library

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.graphics.Brush
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
    val filters = listOf("Playlists", "Podcasts", "Albums", "Artists", "Downloaded")
    var selectedFilter by remember { mutableStateOf("") }

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

    LaunchedEffect(Unit) {
        viewModel.importResult.collect { msg ->
            android.widget.Toast.makeText(context, msg, android.widget.Toast.LENGTH_LONG).show()
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
        if ((selectedFilter == "Downloaded") && !hasPermission && !hasAskedPermission) {
            hasAskedPermission = true
            permissionLauncher.launch(permission)
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.DarkGray)
                    ) {
                        AsyncImage(
                            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuAWvVSASo7gh7ddlxBOJXSuaKoJI7_Yyc57LqB2KJI6iQxUP_VKdIDdQJXUIPnDfu8RbqoHa6HxYr0uYiGUcRQbacQ-0JYfM_BiuBr_C_zkwnd243h4lg1V8_XbYxgOas07_A2BnjczCTeCONvMW1xamltiGAtn9ZuVOOSk-yCcDiyuHhl-E7LdN0TT5q0ZHDHwIL0QntvsXY8K_9uvNT6U3gAFLVZDZjQ6CvRP7marVSLD2yMUTf62VWXlkIKEb1_vcet7sj0cTAUI",
                            contentDescription = "Profile",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        "Your Library", 
                        color = MaterialTheme.colorScheme.onBackground, 
                        fontSize = 34.sp, 
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Search, 
                        contentDescription = "Search", 
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(28.dp).clickable { }
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                    Icon(
                        Icons.Default.Add, 
                        contentDescription = "Add", 
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(28.dp).clickable { showSpotifyDialog = true }
                    )
                }
            }

            // Filter Chips
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(filters) { filter ->
                    val isSelected = filter == selectedFilter
                    val bgColor by animateColorAsState(if (isSelected) Color.White else Color(0xFF2A2A2A))
                    val txtColor by animateColorAsState(if (isSelected) Color.Black else Color.White)

                    Box(
                        modifier = Modifier
                            .height(38.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(bgColor)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = androidx.compose.material.ripple.rememberRipple(color = Color.Gray)
                            ) { 
                                selectedFilter = if (isSelected) "" else filter 
                            }
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = filter,
                            color = txtColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Sort Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.SwapVert, contentDescription = "Sort", tint = MaterialTheme.colorScheme.onBackground, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Recents", color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
                Icon(Icons.Default.GridView, contentDescription = "Grid", tint = MaterialTheme.colorScheme.onBackground, modifier = Modifier.size(20.dp))
            }

            // Library Items
            LazyColumn(
                modifier = Modifier.fillMaxSize(), 
                contentPadding = PaddingValues(bottom = 140.dp)
            ) {
                // Liked Songs
                if (selectedFilter.isEmpty() || selectedFilter == "Playlists") {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(84.dp)
                                .clickable { }
                                .padding(horizontal = 24.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Brush.linearGradient(listOf(Color(0xFF450af5), Color(0xFFc4efd9)))),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Favorite, contentDescription = "Liked", tint = Color.White, modifier = Modifier.size(28.dp))
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text("Liked Songs", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Medium)
                                Spacer(modifier = Modifier.height(2.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.PushPin, contentDescription = "Pinned", tint = Color(0xFF1DB954), modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Playlist • ${likedSongs.size} songs", color = Color(0xFFA7A7A7), fontSize = 16.sp)
                                }
                            }
                        }
                    }
                }

                // Playlists
                if (selectedFilter.isEmpty() || selectedFilter == "Playlists") {
                    items(playlists) { playlist ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(84.dp)
                                .clickable { onNavigateToPlaylist(playlist.id) }
                                .padding(horizontal = 24.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.DarkGray),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.QueueMusic, contentDescription = "Playlist", tint = Color.White, modifier = Modifier.size(32.dp))
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(playlist.name, color = Color.White, fontSize = 24.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Medium)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text("Playlist • Rahul", color = Color(0xFFA7A7A7), fontSize = 16.sp)
                            }
                        }
                    }
                }

                // Artists
                if (selectedFilter.isEmpty() || selectedFilter == "Artists") {
                    items(followedArtists) { artist ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(84.dp)
                                .clickable { onNavigateToArtist(artist.name) }
                                .padding(horizontal = 24.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(CircleShape)
                                    .background(Color.DarkGray),
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
                                    Icon(Icons.Default.Person, contentDescription = "Artist", tint = Color.White, modifier = Modifier.size(32.dp))
                                }
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(artist.name, color = Color.White, fontSize = 24.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Medium)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text("Artist", color = Color(0xFFA7A7A7), fontSize = 16.sp)
                            }
                        }
                    }
                }
                
                // Downloaded / Local
                if (selectedFilter == "Downloaded") {
                    items(localSongs) { song ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(84.dp)
                                .clickable { 
                                    viewModel.playSong(song, localSongs)
                                    onNavigateToNowPlaying() 
                                }
                                .padding(horizontal = 24.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.DarkGray),
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
                                    Icon(Icons.Default.MusicNote, contentDescription = "Audio", tint = Color.White, modifier = Modifier.size(24.dp))
                                }
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(song.title, color = Color.White, fontSize = 24.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Medium)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text("Song • ${song.artist}", color = Color(0xFFA7A7A7), fontSize = 16.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                        }
                    }
                }
            }
        }
        
        // Spotify Import Dialog
        if (showSpotifyDialog) {
            AlertDialog(
                onDismissRequest = { if (!isImporting) showSpotifyDialog = false },
                containerColor = Color(0xFF2A2A2A),
                titleContentColor = MaterialTheme.colorScheme.onBackground,
                textContentColor = MaterialTheme.colorScheme.onBackground,
                title = { Text("Import Spotify Playlist") },
                text = {
                    Column {
                        Text("Paste your public Spotify playlist link below.", color = Color.Gray, fontSize = 14.sp)
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
