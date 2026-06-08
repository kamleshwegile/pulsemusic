package com.pulse.music.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pulse.music.ui.auth.AuthViewModel
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.clickable
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateToAuth: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val token by viewModel.token.collectAsState()
    val username by viewModel.username.collectAsState()
    val profilePicUri by viewModel.profilePicUri.collectAsState()
    
    val highQuality by viewModel.highQuality.collectAsState()
    val spatialAudio by viewModel.spatialAudio.collectAsState()
    val gapless by viewModel.gapless.collectAsState()
    val crossfade by viewModel.crossfade.collectAsState()
    
    // Image picker launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { viewModel.updateProfilePic(it.toString()) }
    }
    
    // If user somehow logs out, go to auth
    LaunchedEffect(token) {
        if (token.isNullOrEmpty()) {
            onNavigateToAuth()
        }
    }

    Scaffold(
        containerColor = Color(0xFF050505)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 24.dp, bottom = 140.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { 
                ProfileHero(
                    username = username ?: "User", 
                    profilePicUri = profilePicUri,
                    onEditProfilePic = { launcher.launch("image/*") }
                ) 
            }
            item { DiscoveryInsights() }
            item { 
                PlaybackSettings(
                    highQuality = highQuality,
                    spatialAudio = spatialAudio,
                    gapless = gapless,
                    crossfade = crossfade,
                    onUpdate = { hq, sa, gp, cf -> 
                        viewModel.updatePlaybackSettings(hq, sa, gp, cf)
                    }
                ) 
            }
            item {
                val context = androidx.compose.ui.platform.LocalContext.current
                StorageSettings(onClearCache = {
                    context.cacheDir.listFiles()?.forEach { file -> 
                        file.deleteRecursively() 
                    }
                    android.widget.Toast.makeText(context, "Cache cleared successfully", android.widget.Toast.LENGTH_SHORT).show()
                })
            }
            item {
                AccountActions(onLogout = { viewModel.logout() })
            }
        }
    }
}

@Composable
private fun ProfileHero(username: String, profilePicUri: String?, onEditProfilePic: () -> Unit) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFF1E3A8A).copy(alpha = 0.4f), Color(0xFF065F46).copy(alpha = 0.2f))
    )
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(gradientBrush)
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(24.dp))
            .padding(24.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray)
                    .border(3.dp, Color.White.copy(alpha = 0.1f), CircleShape)
                    .clickable { onEditProfilePic() },
                contentAlignment = Alignment.Center
            ) {
                if (profilePicUri.isNullOrEmpty()) {
                    Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White, modifier = Modifier.size(50.dp))
                } else {
                    AsyncImage(
                        model = profilePicUri,
                        contentDescription = "Profile Picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                
                // Edit icon overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Profile Picture", tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(24.dp))
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Text(username, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Brush.horizontalGradient(listOf(Color(0xFF10B981), Color(0xFF06B6D4))))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text("PREMIUM", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
            
            Text("@${username.lowercase()} • 120 Days Streak", color = Color(0xFF34D399), fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp))
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("128", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text("HOURS/MO", color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("4.2k", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text("FOLLOWERS", color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun DiscoveryInsights() {
    Column {
        Text("Listening Insights", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
        SettingsCard {
            SettingRow(icon = Icons.Default.Explore, title = "Discovery Score", description = "You explore 40% more new music than average.")
            Divider(color = Color.White.copy(alpha = 0.05f))
            SettingRow(icon = Icons.Default.LibraryMusic, title = "Top Genres", description = "Punjabi Pop, Lo-Fi Chill, Bollywood")
        }
    }
}

@Composable
private fun PlaybackSettings(
    highQuality: Boolean,
    spatialAudio: Boolean,
    gapless: Boolean,
    crossfade: Int,
    onUpdate: (Boolean, Boolean, Boolean, Int) -> Unit
) {
    Column {
        Text("Playback", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
        SettingsCard {
            SettingRow(
                icon = Icons.Default.HighQuality, 
                title = "High Quality Streaming", 
                description = "Always stream at 320kbps",
                trailing = { Switch(checked = highQuality, onCheckedChange = { onUpdate(it, spatialAudio, gapless, crossfade) }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF10B981), checkedTrackColor = Color(0xFF10B981).copy(alpha = 0.5f))) }
            )
            HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
            SettingRow(
                icon = Icons.Default.Headphones, 
                title = "Spatial Audio", 
                description = "Enable immersive soundstage",
                trailing = { Switch(checked = spatialAudio, onCheckedChange = { onUpdate(highQuality, it, gapless, crossfade) }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF10B981), checkedTrackColor = Color(0xFF10B981).copy(alpha = 0.5f))) }
            )
            HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
            SettingRow(
                icon = Icons.Default.SkipNext, 
                title = "Gapless Playback", 
                description = "Eliminate pauses between tracks",
                trailing = { Switch(checked = gapless, onCheckedChange = { onUpdate(highQuality, spatialAudio, it, crossfade) }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF10B981), checkedTrackColor = Color(0xFF10B981).copy(alpha = 0.5f))) }
            )
            HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Crossfade", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Medium)
                    Text("${crossfade}s", color = Color(0xFF10B981), fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
                Slider(
                    value = crossfade.toFloat(),
                    onValueChange = { onUpdate(highQuality, spatialAudio, gapless, it.toInt()) },
                    valueRange = 0f..12f,
                    steps = 11,
                    colors = SliderDefaults.colors(thumbColor = Color(0xFF10B981), activeTrackColor = Color(0xFF10B981))
                )
            }
        }
    }
}

@Composable
private fun PremiumCard() {
    val premiumBrush = Brush.linearGradient(
        colors = listOf(Color(0xFFF59E0B).copy(alpha = 0.2f), Color(0xFFEF4444).copy(alpha = 0.2f))
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(premiumBrush)
            .border(1.dp, Color(0xFFF59E0B).copy(alpha = 0.3f), RoundedCornerShape(24.dp))
            .padding(24.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFBBF24), modifier = Modifier.size(28.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("ListenFree Premium", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text("Enjoy ad-free music listening, offline playback, and highest audio quality.", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B), contentColor = Color.Black),
                shape = RoundedCornerShape(50)
            ) {
                Text("Manage Subscription", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(20.dp))
    ) {
        Column(modifier = Modifier.padding(8.dp), content = content)
    }
}

@Composable
private fun SettingRow(
    icon: ImageVector,
    title: String,
    description: String? = null,
    trailing: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.05f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Medium)
            if (description != null) {
                Text(description, color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(top = 2.dp))
            }
        }
        if (trailing != null) {
            Spacer(modifier = Modifier.width(16.dp))
            trailing()
        } else {
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
        }
    }
}

@Composable
private fun AccountActions(onLogout: () -> Unit) {
    Column {
        Text("Account", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
        SettingsCard {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onLogout() }
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Logout, contentDescription = "Logout", tint = Color(0xFFEF4444), modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Log Out", color = Color(0xFFEF4444), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun StorageSettings(onClearCache: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    var cacheSizeMb by remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope()

    fun calculateCacheSize() {
        var sizeBytes = 0L
        try {
            context.cacheDir.walkTopDown().filter { it.isFile }.forEach { sizeBytes += it.length() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        cacheSizeMb = sizeBytes / (1024f * 1024f)
    }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
            calculateCacheSize()
        }
    }

    Column {
        Text("Storage", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
        SettingsCard {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { 
                        onClearCache()
                        scope.launch(kotlinx.coroutines.Dispatchers.IO) {
                            kotlinx.coroutines.delay(500)
                            calculateCacheSize()
                        }
                    }
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.DeleteSweep, contentDescription = "Clear Cache", tint = Color.Gray, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        val formattedSize = String.format("%.1f MB", cacheSizeMb)
                        Text("Clear Cache ($formattedSize)", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        Text("Free up space by removing cached audio and images", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
