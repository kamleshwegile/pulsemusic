package com.pulse.music.ui.album

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pulse.music.domain.Song

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumScreen(
    albumId: String,
    viewModel: AlbumViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onSongClick: (Song, List<Song>) -> Unit
) {
    LaunchedEffect(albumId) {
        viewModel.loadAlbum(albumId)
    }

    val albumInfo by viewModel.albumInfo.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val currentSong by viewModel.currentSong.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()
    val context = androidx.compose.ui.platform.LocalContext.current

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Text(
                    text = "Pulse Music",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
                }
            }
        },
        containerColor = Color.Black // Pure black background like the mockup
    ) { padding ->
        if (isLoading && albumInfo == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.White)
            }
        } else if (albumInfo != null) {
            val album = albumInfo!!
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Album Header Section
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = album.coverArt,
                            contentDescription = "Album Cover",
                            modifier = Modifier
                                .size(200.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.DarkGray),
                            contentScale = ContentScale.Crop,
                            error = rememberVectorPainter(Icons.Default.Album)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = album.title,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-0.5).sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = album.artist,
                            color = Color.White, // primary color in mockup is white
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            text = "${album.year ?: "2024"} · ${album.tracks.size} songs",
                            color = Color.White.copy(alpha = 0.4f),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                // Action Row
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                            Icon(
                                if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (isFavorite) Color(0xFFF92839) else Color.White.copy(alpha = 0.7f),
                                modifier = Modifier.size(22.dp).clickable { viewModel.toggleFavorite() }
                            )
                            Icon(Icons.Default.Download, contentDescription = "Download", tint = Color.White.copy(alpha = 0.7f), modifier = Modifier.size(22.dp))
                            Icon(
                                Icons.Default.Share, 
                                contentDescription = "Share", 
                                tint = Color.White.copy(alpha = 0.7f), 
                                modifier = Modifier.size(22.dp).clickable {
                                    val shareIntent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                                        type = "text/plain"
                                        putExtra(android.content.Intent.EXTRA_SUBJECT, "Share Album")
                                        putExtra(android.content.Intent.EXTRA_TEXT, "Check out this album: ${album.title} on Pulse Music!")
                                    }
                                    context.startActivity(android.content.Intent.createChooser(shareIntent, "Share via"))
                                }
                            )
                            Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color.White.copy(alpha = 0.7f), modifier = Modifier.size(22.dp))
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(22.dp))
                                    .clip(RoundedCornerShape(22.dp))
                                    .clickable { viewModel.shufflePlay(album.tracks) },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Shuffle, contentDescription = "Shuffle", tint = Color.White, modifier = Modifier.size(24.dp))
                            }
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(Color.White, RoundedCornerShape(22.dp))
                                    .clickable { if(album.tracks.isNotEmpty()) onSongClick(album.tracks.first(), album.tracks) },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.Black, modifier = Modifier.size(28.dp))
                            }
                        }
                    }
                }

                // Track List
                itemsIndexed(album.tracks) { index, song ->
                    val isActive = currentSong?.id == song.id
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .clickable { onSongClick(song, album.tracks) }
                            .padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(40.dp), contentAlignment = Alignment.Center) {
                            AsyncImage(
                                model = song.albumArt,
                                contentDescription = "Song Art",
                                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(4.dp)).background(Color.DarkGray),
                                contentScale = ContentScale.Crop,
                                error = rememberVectorPainter(Icons.Default.MusicNote)
                            )
                            if (isActive) {
                                Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)))
                                Icon(Icons.Default.GraphicEq, contentDescription = "Playing", tint = Color.White, modifier = Modifier.size(16.dp))
                            }
                        }
                        
                        Column(modifier = Modifier.weight(1f).padding(end = 16.dp)) {
                            Text(
                                text = song.title,
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (isActive || song.artist != album.artist) {
                                Text(
                                    text = song.artist,
                                    color = Color.White.copy(alpha = 0.4f),
                                    fontSize = 11.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            if (song.durationMs != null) {
                                val totalSecs = song.durationMs / 1000
                                val m = totalSecs / 60
                                val s = totalSecs % 60
                                Text(String.format("%d:%02d", m, s), color = Color.White.copy(alpha = 0.4f), fontSize = 12.sp)
                            }
                            Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color.White.copy(alpha = 0.4f), modifier = Modifier.size(20.dp))
                        }
                    }
                }
                
                // Footer Info
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 48.dp, bottom = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("${album.tracks.size} songs", color = Color.White.copy(alpha = 0.4f), fontSize = 12.sp)
                        Text("Released ${album.year ?: "2024"}", color = Color.White.copy(alpha = 0.4f), fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
                        Text("© ${album.year ?: "2024"} Pulse Music Records", color = Color.White.copy(alpha = 0.3f), fontSize = 10.sp, modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Album not found", color = Color.White.copy(alpha = 0.6f))
            }
        }
    }
}
