@file:OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
package com.pulse.music

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pulse.music.player.MusicPlayerManager
import com.pulse.music.ui.home.HomeScreen
import com.pulse.music.ui.library.LibraryScreen
import com.pulse.music.ui.nowplaying.NowPlayingScreen
import com.pulse.music.ui.nowplaying.PulseGreen
import com.pulse.music.ui.search.SearchScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalView
import kotlinx.coroutines.launch
import android.view.HapticFeedbackConstants
import androidx.compose.runtime.rememberCoroutineScope

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var musicPlayerManager: MusicPlayerManager

    @Inject
    lateinit var authRepository: com.pulse.music.data.repository.AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (androidx.core.content.ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }
        
        val initialToken = runBlocking { authRepository.authToken.first() }
        val startDest = if (initialToken.isNullOrEmpty()) "auth_login" else "home"

        setContent {
            com.pulse.music.ui.theme.PulseMusicTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val showBottomNav = currentRoute != "now_playing" && currentRoute != "auth_login" && currentRoute != "auth_register" && currentRoute != "auth_forgot_password"
                val showMiniPlayer = currentRoute != "now_playing" && currentRoute != "auth_login" && currentRoute != "auth_register" && currentRoute != "auth_forgot_password"
                val currentSong by musicPlayerManager.currentSong.collectAsState()
                val isPlaying by musicPlayerManager.isPlaying.collectAsState()

                Scaffold { paddingValues ->
                    Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                        Surface(color = Color.Black, modifier = Modifier.fillMaxSize()) {
                        NavHost(
                            navController = navController,
                            startDestination = startDest
                        ) {
                            composable("auth_login") {
                                com.pulse.music.ui.auth.LoginScreen(
                                    onNavigateToHome = {
                                        navController.navigate("home") {
                                            popUpTo("auth_login") { inclusive = true }
                                        }
                                    },
                                    onNavigateToRegister = {
                                        navController.navigate("auth_register")
                                    },
                                    onNavigateToForgotPassword = {
                                        navController.navigate("auth_forgot_password")
                                    }
                                )
                            }
                            composable("auth_register") {
                                com.pulse.music.ui.auth.RegisterScreen(
                                    onNavigateToHome = {
                                        navController.navigate("home") {
                                            popUpTo("auth_register") { inclusive = true }
                                            popUpTo("auth_login") { inclusive = true }
                                        }
                                    },
                                    onNavigateToLogin = {
                                        navController.navigate("auth_login") {
                                            popUpTo("auth_register") { inclusive = true }
                                        }
                                    }
                                )
                            }
                            composable("auth_forgot_password") {
                                com.pulse.music.ui.auth.ForgotPasswordScreen(
                                    onNavigateToLogin = {
                                        navController.navigate("auth_login") {
                                            popUpTo("auth_forgot_password") { inclusive = true }
                                        }
                                    }
                                )
                            }
                            composable("home") {
                                HomeScreen(
                                    onNavigateToNowPlaying = {
                                        navController.navigate("now_playing")
                                    },
                                    onNavigateToAlbum = { albumId ->
                                        navController.navigate("album/$albumId")
                                    },
                                    onNavigateToArtist = { artistName ->
                                        navController.navigate("artist/$artistName")
                                    }
                                )
                            }
                            composable("search") {
                                SearchScreen(
                                    onNavigateToArtist = { artistName ->
                                        navController.navigate("artist/$artistName")
                                    },
                                    onNavigateToAlbum = { albumId ->
                                        navController.navigate("album/$albumId")
                                    }
                                )
                            }
                            composable("artist/{artistName}") { backStackEntry ->
                                val artistName = backStackEntry.arguments?.getString("artistName") ?: return@composable
                                com.pulse.music.ui.artist.ArtistScreen(
                                    artistName = artistName,
                                    onBack = { navController.popBackStack() },
                                    onSongClick = { song, playlist -> 
                                        musicPlayerManager.playSongFromList(song, playlist) 
                                    },
                                    onAlbumClick = { albumId ->
                                        navController.navigate("album/$albumId")
                                    },
                                    onArtistClick = { simArtistName ->
                                        navController.navigate("artist/$simArtistName")
                                    },
                                    onShuffleClick = { playlist ->
                                        val shuffled = playlist.shuffled()
                                        if (shuffled.isNotEmpty()) {
                                            musicPlayerManager.setShuffleEnabled(true)
                                            musicPlayerManager.playSongFromList(shuffled.first(), shuffled)
                                        }
                                    }
                                )
                            }
                            composable("album/{albumId}") { backStackEntry ->
                                val albumId = backStackEntry.arguments?.getString("albumId") ?: return@composable
                                com.pulse.music.ui.album.AlbumScreen(
                                    albumId = albumId,
                                    onBack = { navController.popBackStack() },
                                    onSongClick = { song, playlist ->
                                        musicPlayerManager.playSongFromList(song, playlist)
                                    }
                                )
                            }
                            composable("library") {
                                LibraryScreen(
                                    onNavigateToNowPlaying = {
                                        navController.navigate("now_playing")
                                    },
                                    onNavigateToPlaylist = { playlistId ->
                                        navController.navigate("playlist/$playlistId")
                                    },
                                    onNavigateToArtist = { artistName ->
                                        navController.navigate("artist/$artistName")
                                    }
                                )
                            }
                            composable("profile") {
                                com.pulse.music.ui.profile.ProfileScreen(
                                    onNavigateToAuth = {
                                        navController.navigate("auth_login") {
                                            popUpTo("home") { inclusive = true }
                                        }
                                    }
                                )
                            }
                            composable(
                                "playlist/{playlistId}",
                                arguments = listOf(androidx.navigation.navArgument("playlistId") { type = androidx.navigation.NavType.IntType })
                            ) { backStackEntry ->
                                val playlistId = backStackEntry.arguments?.getInt("playlistId") ?: return@composable
                                com.pulse.music.ui.playlist.PlaylistScreen(
                                    playlistId = playlistId,
                                    onBack = { navController.popBackStack() }
                                )
                            }
                            composable("now_playing") {
                                NowPlayingScreen(
                                    onBack = {
                                        navController.popBackStack()
                                    },
                                    onNavigateToArtist = { artistName ->
                                        navController.navigate("artist/$artistName")
                                    },
                                    onNavigateToAlbum = { albumId ->
                                        navController.navigate("album/$albumId")
                                    }
                                )
                            }
                        }
                    }

                        // Floating UI Overlay (MiniPlayer + BottomNav)
                        Column(
                            modifier = Modifier.align(Alignment.BottomCenter)
                        ) {
                            if (showMiniPlayer && currentSong != null) {
                                val queue by musicPlayerManager.queue.collectAsState()
                                val currentIndex by musicPlayerManager.currentIndex.collectAsState()
                                MiniPlayer(
                                    queue = queue,
                                    currentIndex = currentIndex,
                                    isPlaying = isPlaying,
                                    onTogglePlay = { musicPlayerManager.togglePlayPause() },
                                    onSkipNext = { musicPlayerManager.skipNext() },
                                    onSkipPrev = { musicPlayerManager.skipPrevious() },
                                    onClick = { navController.navigate("now_playing") }
                                )
                            }

                            if (showBottomNav) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            androidx.compose.ui.graphics.Brush.verticalGradient(
                                                colors = listOf(Color.Transparent, Color(0xE6000000), Color.Black)
                                            )
                                        )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 16.dp, bottom = 12.dp),
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        val items = listOf(
                                            Triple("Home", Icons.Default.Home, "home"),
                                            Triple("Search", Icons.Default.Search, "search"),
                                            Triple("Library", Icons.Default.LibraryMusic, "library"),
                                            Triple("Profile", Icons.Default.Person, "profile")
                                        )
                                        items.forEach { (label, icon, route) ->
                                            val selected = currentRoute == route
                                            val color = if (selected) Color.White else Color.Gray
                                            
                                            Column(
                                                modifier = Modifier
                                                    .clickable(
                                                        interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                                                        indication = null
                                                    ) {
                                                        navController.navigate(route) {
                                                            popUpTo(navController.graph.startDestinationId) { saveState = false }
                                                            launchSingleTop = true
                                                            restoreState = false
                                                        }
                                                    },
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Icon(icon, contentDescription = label, tint = color, modifier = Modifier.size(24.dp))
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(label, color = color, fontSize = 10.sp, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
            }
        }
    }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            musicPlayerManager.release()
        }
    }
}

@Composable
fun MiniPlayer(
    queue: List<com.pulse.music.domain.Song>,
    currentIndex: Int,
    isPlaying: Boolean,
    onTogglePlay: () -> Unit,
    onSkipNext: () -> Unit,
    onSkipPrev: () -> Unit,
    onClick: () -> Unit
) {
    val currentSong = queue.getOrNull(currentIndex) ?: return
    val nextSong = queue.getOrNull(currentIndex + 1)
    val prevSong = queue.getOrNull(currentIndex - 1)

    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val view = LocalView.current
    var hasHapticFired by remember { mutableStateOf(false) }

    BoxWithConstraints(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
        val widthPx = constraints.maxWidth.toFloat()
        val threshold = widthPx * 0.35f

        // Background tracks (Preview)
        if (offsetX.value < 0f && nextSong != null) {
            val alpha = (kotlin.math.abs(offsetX.value) / widthPx).coerceIn(0f, 1f)
            MiniPlayerCard(
                song = nextSong,
                isPlaying = false,
                modifier = Modifier
                    .graphicsLayer {
                        this.alpha = alpha * 0.8f
                        scaleX = 0.9f + (alpha * 0.1f)
                        scaleY = 0.9f + (alpha * 0.1f)
                    }
            )
        } else if (offsetX.value > 0f && prevSong != null) {
            val alpha = (kotlin.math.abs(offsetX.value) / widthPx).coerceIn(0f, 1f)
            MiniPlayerCard(
                song = prevSong,
                isPlaying = false,
                modifier = Modifier
                    .graphicsLayer {
                        this.alpha = alpha * 0.8f
                        scaleX = 0.9f + (alpha * 0.1f)
                        scaleY = 0.9f + (alpha * 0.1f)
                    }
            )
        }

        // Foreground track
        MiniPlayerCard(
            song = currentSong,
            isPlaying = isPlaying,
            onTogglePlay = onTogglePlay,
            modifier = Modifier
                .graphicsLayer {
                    translationX = offsetX.value
                    if (kotlin.math.abs(offsetX.value) > 0) {
                        val scale = 1f - (kotlin.math.abs(offsetX.value) / widthPx) * 0.05f
                        scaleX = scale
                        scaleY = scale
                    }
                }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            scope.launch {
                                val dx = offsetX.value
                                if (dx < -threshold && nextSong != null) {
                                    view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                                    offsetX.animateTo(-widthPx, tween(250, easing = androidx.compose.animation.core.FastOutSlowInEasing))
                                    onSkipNext()
                                    offsetX.snapTo(0f) // Instantly snap to 0, seamless with the preview!
                                } else if (dx > threshold && prevSong != null) {
                                    view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                                    offsetX.animateTo(widthPx, tween(250, easing = androidx.compose.animation.core.FastOutSlowInEasing))
                                    onSkipPrev()
                                    offsetX.snapTo(0f) // Instantly snap to 0, seamless with the preview!
                                } else {
                                    offsetX.animateTo(0f, spring(stiffness = Spring.StiffnessLow))
                                }
                                hasHapticFired = false
                            }
                        },
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            scope.launch {
                                val newVal = offsetX.value + dragAmount
                                if (newVal < 0 && nextSong == null) {
                                    offsetX.snapTo((offsetX.value + dragAmount * 0.2f).coerceAtMost(0f))
                                } else if (newVal > 0 && prevSong == null) {
                                    offsetX.snapTo((offsetX.value + dragAmount * 0.2f).coerceAtLeast(0f))
                                } else {
                                    offsetX.snapTo(newVal)
                                }

                                if (kotlin.math.abs(offsetX.value) > threshold && !hasHapticFired) {
                                    hasHapticFired = true
                                    view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                                } else if (kotlin.math.abs(offsetX.value) <= threshold && hasHapticFired) {
                                    hasHapticFired = false
                                }
                            }
                        }
                    )
                }
                .clickable { onClick() }
        )
    }
}

@Composable
fun MiniPlayerCard(
    song: com.pulse.music.domain.Song,
    isPlaying: Boolean,
    onTogglePlay: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(8.dp)),
        color = Color(0xFF242424),
        shadowElevation = 8.dp,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF3E3E3E)),
                contentAlignment = Alignment.Center
            ) {
                if (!song.albumArt.isNullOrEmpty()) {
                    coil.compose.AsyncImage(
                        model = song.albumArt,
                        contentDescription = "Album Art",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(Icons.Default.MusicNote, contentDescription = "Music", tint = Color.Gray, modifier = Modifier.size(24.dp))
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = song.title,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    modifier = Modifier.basicMarquee()
                )
                Text(
                    text = song.artist,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (onTogglePlay != null) {
                IconButton(onClick = onTogglePlay, modifier = Modifier.size(48.dp)) {
                    Icon(
                        if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play/Pause",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            } else {
                Spacer(modifier = Modifier.width(48.dp))
            }
        }
    }
}