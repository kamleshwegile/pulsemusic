import codecs
import re

filepath = 'app/src/main/java/com/pulse/music/ui/library/LibraryScreen.kt'
with codecs.open(filepath, 'r', 'utf-8') as f:
    content = f.read()

# 1. Imports
imports = '''import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.material.icons.automirrored.filled.QueueMusic
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults'''
if "LazyVerticalGrid" not in content:
    content = content.replace('import androidx.compose.foundation.lazy.items', 'import androidx.compose.foundation.lazy.items\n' + imports)

# 2. State variables and filters
content = content.replace('val filters = listOf("Playlists", "Podcasts", "Albums", "Artists", "Downloaded")', 'val filters = listOf("Playlists", "Albums", "Artists", "Local")')

state_vars_regex = r'val isLoading by viewModel\.isLoading\.collectAsState\(\)\s*val context = LocalContext\.current'

state_vars_new = '''val isLoading by viewModel.isLoading.collectAsState()

    var isGridView by remember { mutableStateOf(false) }
    var sortOption by remember { mutableStateOf("Recents") }
    var showSortMenu by remember { mutableStateOf(false) }
    var showAddMenu by remember { mutableStateOf(false) }
    var isSearching by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    
    var showCreatePlaylistDialog by remember { mutableStateOf(false) }
    var newPlaylistName by remember { mutableStateOf("") }

    val basePlaylists = if (searchQuery.isBlank()) playlists else playlists.filter { it.name.contains(searchQuery, ignoreCase = true) }
    val baseArtists = if (searchQuery.isBlank()) followedArtists else followedArtists.filter { it.name.contains(searchQuery, ignoreCase = true) }
    val baseLocalSongs = if (searchQuery.isBlank()) localSongs else localSongs.filter { it.title.contains(searchQuery, ignoreCase = true) || (it.artist?.contains(searchQuery, ignoreCase = true) == true) }

    val filteredPlaylists = if (sortOption == "Alphabetical") basePlaylists.sortedBy { it.name.lowercase() } else basePlaylists
    val filteredArtists = if (sortOption == "Alphabetical") baseArtists.sortedBy { it.name.lowercase() } else baseArtists
    val filteredLocalSongs = if (sortOption == "Alphabetical") baseLocalSongs.sortedBy { it.title.lowercase() } else baseLocalSongs

    val context = LocalContext.current'''

content = re.sub(state_vars_regex, state_vars_new, content, count=1)

# Remove Downloaded references and rename to Local
content = content.replace('selectedFilter == "Downloaded"', 'selectedFilter == "Local"')

# 3. Fix Top Bar (Search + Add Menu)
# Regex to replace the Top Bar
top_bar_regex = r'// Header\s*Row\(\s*modifier = Modifier\s*\.fillMaxWidth\(\)\s*\.padding\(horizontal = 24\.dp,\s*vertical = 16\.dp\),\s*horizontalArrangement = Arrangement\.SpaceBetween,\s*verticalAlignment = Alignment\.CenterVertically\s*\)\s*\{[\s\S]*?Icon\(\s*Icons\.Default\.Add,[\s\S]*?\}\s*\}'

top_bar_new = '''// Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isSearching) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.weight(1f).padding(end = 16.dp),
                        placeholder = { Text("Search in Library", color = Color.Gray) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = Color(0xFF2A2A2A),
                            unfocusedContainerColor = Color(0xFF2A2A2A),
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        trailingIcon = {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.Gray,
                                modifier = Modifier.clickable { 
                                    isSearching = false
                                    searchQuery = ""
                                }
                            )
                        }
                    )
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "Your Library", 
                            color = MaterialTheme.colorScheme.onBackground, 
                            fontSize = 34.sp, 
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                if (!isSearching) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Search, 
                            contentDescription = "Search", 
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(28.dp).clickable { isSearching = true }
                        )
                        Spacer(modifier = Modifier.width(24.dp))
                        Box {
                            Icon(
                                Icons.Default.Add, 
                                contentDescription = "Add", 
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.size(28.dp).clickable { showAddMenu = true }
                            )
                            DropdownMenu(
                                expanded = showAddMenu,
                                onDismissRequest = { showAddMenu = false },
                                modifier = Modifier.background(Color(0xFF2A2A2A))
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Create Playlist", color = Color.White) },
                                    onClick = {
                                        showCreatePlaylistDialog = true
                                        showAddMenu = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Import from Spotify", color = Color.White) },
                                    onClick = {
                                        showSpotifyDialog = true
                                        showAddMenu = false
                                    }
                                )
                            }
                        }
                    }
                }
            }'''
content = re.sub(top_bar_regex, top_bar_new, content, count=1)

# 4. Sort Bar
sort_bar_regex = r'// Sort Bar\s*Row\([\s\S]*?Text\("Recents"[\s\S]*?Icon\(Icons\.Default\.GridView[\s\S]*?\}'

sort_bar_new = '''// Sort Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { showSortMenu = true }.padding(end = 8.dp)
                    ) {
                        Icon(Icons.Default.SwapVert, contentDescription = "Sort", tint = MaterialTheme.colorScheme.onBackground, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(sortOption, color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    }
                    DropdownMenu(
                        expanded = showSortMenu,
                        onDismissRequest = { showSortMenu = false },
                        modifier = Modifier.background(Color(0xFF2A2A2A))
                    ) {
                        listOf("Recents", "Alphabetical").forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option, color = Color.White) },
                                onClick = {
                                    sortOption = option
                                    showSortMenu = false
                                }
                            )
                        }
                    }
                }
                Icon(
                    if (isGridView) Icons.AutoMirrored.Filled.ViewList else Icons.Default.GridView,
                    contentDescription = "Toggle View",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(20.dp).clickable { isGridView = !isGridView }
                )
            }'''
content = re.sub(sort_bar_regex, sort_bar_new, content, count=1)


# 5. LazyColumn -> LazyVerticalGrid
lazy_col_regex = r'// Library Items\s*LazyColumn\(\s*modifier = Modifier\.fillMaxSize\(\),\s*contentPadding = PaddingValues\(bottom = 140\.dp\)\s*\)\s*\{'

lazy_col_new = '''// Library Items
            LazyVerticalGrid(
                columns = GridCells.Fixed(if (isGridView) 2 else 1),
                modifier = Modifier.fillMaxSize().padding(horizontal = if (isGridView) 16.dp else 0.dp), 
                contentPadding = PaddingValues(bottom = 140.dp, top = 8.dp),
                horizontalArrangement = if (isGridView) Arrangement.spacedBy(16.dp) else Arrangement.Start,
                verticalArrangement = if (isGridView) Arrangement.spacedBy(16.dp) else Arrangement.Top
            ) {'''
content = re.sub(lazy_col_regex, lazy_col_new, content, count=1)

# Fix Liked Songs item
item_regex = r'if \(selectedFilter\.isEmpty\(\) \|\| selectedFilter == "Playlists"\) \{\s*item \{'
content = re.sub(item_regex, r'if (selectedFilter.isEmpty() || selectedFilter == "Playlists") {\n                    item(span = { GridItemSpan(maxLineSpan) }) {', content, count=1)


bullet = chr(8226)


# Playlists replacement
old_playlists_regex = r'// Playlists\s*if \(selectedFilter\.isEmpty\(\) \|\| selectedFilter == "Playlists"\) \{\s*items\(playlists\) \{ playlist ->[\s\S]*?Text\("Playlist • Rahul"[^\n]*\n\s*\}\s*\}\s*\}\s*\}'
new_playlists = '''// Playlists
                if (selectedFilter.isEmpty() || selectedFilter == "Playlists") {
                    items(filteredPlaylists) { playlist ->
                        val songs by remember(playlist.id) {
                            viewModel.getSongsForPlaylist(playlist.id)
                        }.collectAsState(initial = emptyList())
                        val totalMs = songs.sumOf { it.durationMs ?: 0L }
                        val totalSecs = totalMs / 1000
                        val hours = totalSecs / 3600
                        val mins = (totalSecs % 3600) / 60
                        val durationStr = when {
                            totalMs == 0L -> ""
                            hours > 0 -> " BULLET ${hours}hr ${mins}min"
                            else -> " BULLET ${mins} min"
                        }
                        val validCovers = songs.mapNotNull { it.albumArt }.filter { it.isNotEmpty() }
                        
                        LibraryItem(
                            isGridView = isGridView,
                            title = playlist.name,
                            subtitle = "Playlist BULLET ${songs.size} songs$durationStr",
                            onClick = { onNavigateToPlaylist(playlist.id) },
                            imageContent = {
                                if (validCovers.size >= 4) {
                                    Column(modifier = Modifier.fillMaxSize()) {
                                        Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                                            AsyncImage(model = validCovers[0], contentDescription = null, modifier = Modifier.weight(1f).fillMaxHeight(), contentScale = ContentScale.Crop)
                                            AsyncImage(model = validCovers[1], contentDescription = null, modifier = Modifier.weight(1f).fillMaxHeight(), contentScale = ContentScale.Crop)
                                        }
                                        Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                                            AsyncImage(model = validCovers[2], contentDescription = null, modifier = Modifier.weight(1f).fillMaxHeight(), contentScale = ContentScale.Crop)
                                            AsyncImage(model = validCovers[3], contentDescription = null, modifier = Modifier.weight(1f).fillMaxHeight(), contentScale = ContentScale.Crop)
                                        }
                                    }
                                } else if (validCovers.isNotEmpty()) {
                                    AsyncImage(model = validCovers[0], contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                                } else {
                                    Icon(Icons.AutoMirrored.Filled.QueueMusic, contentDescription = "Playlist", tint = Color.White, modifier = Modifier.size(32.dp))
                                }
                            }
                        )
                    }
                }'''.replace("BULLET", bullet)
content = re.sub(old_playlists_regex, new_playlists, content, count=1)

# Artists replacement
old_artists_regex = r'// Artists\s*if \(selectedFilter\.isEmpty\(\) \|\| selectedFilter == "Artists"\) \{\s*items\(followedArtists\) \{ artist ->[\s\S]*?Text\("Artist"[^\n]*\n\s*\}\s*\}\s*\}\s*\}'
new_artists = '''// Artists
                if (selectedFilter.isEmpty() || selectedFilter == "Artists") {
                    items(filteredArtists) { artist ->
                        LibraryItem(
                            isGridView = isGridView,
                            title = artist.name,
                            subtitle = "Artist",
                            isCircle = true,
                            onClick = { onNavigateToArtist(artist.name) },
                            imageContent = {
                                if (!artist.image.isNullOrEmpty()) {
                                    AsyncImage(model = artist.image, contentDescription = artist.name, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                                } else {
                                    Icon(Icons.Default.Person, contentDescription = "Artist", tint = Color.White, modifier = Modifier.size(32.dp))
                                }
                            }
                        )
                    }
                }'''
content = re.sub(old_artists_regex, new_artists, content, count=1)


# Local Songs replacement
old_local_regex = r'// Downloaded / Local\s*if \(selectedFilter == "Local"\) \{\s*items\(localSongs\) \{ song ->[\s\S]*?Text\("Song [^\n]*\n\s*\}\s*\}\s*\}\s*\}'
new_local = '''// Downloaded / Local
                if (selectedFilter == "Local") {
                    items(filteredLocalSongs) { song ->
                        LibraryItem(
                            isGridView = isGridView,
                            title = song.title,
                            subtitle = "Song BULLET ${song.artist}",
                            onClick = { 
                                viewModel.playSong(song, filteredLocalSongs)
                                onNavigateToNowPlaying() 
                            },
                            imageContent = {
                                if (!song.albumArt.isNullOrEmpty()) {
                                    AsyncImage(model = song.albumArt, contentDescription = song.title, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                                } else {
                                    Icon(Icons.Default.MusicNote, contentDescription = "Audio", tint = Color.White, modifier = Modifier.size(24.dp))
                                }
                            }
                        )
                    }
                }'''.replace("BULLET", bullet)
content = re.sub(old_local_regex, new_local, content, count=1)

# Add the Dialog for Playlist creation
dialog_ui = '''
    if (showCreatePlaylistDialog) {
        AlertDialog(
            onDismissRequest = {
                showCreatePlaylistDialog = false
                newPlaylistName = ""
            },
            title = { Text("Create Playlist", color = Color.White) },
            text = {
                OutlinedTextField(
                    value = newPlaylistName,
                    onValueChange = { newPlaylistName = it },
                    label = { Text("Playlist Name", color = Color.Gray) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF1DB954),
                        unfocusedBorderColor = Color.Gray,
                        focusedContainerColor = Color(0xFF2A2A2A),
                        unfocusedContainerColor = Color(0xFF2A2A2A)
                    )
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (newPlaylistName.isNotBlank()) {
                            viewModel.createPlaylist(newPlaylistName)
                            showCreatePlaylistDialog = false
                            newPlaylistName = ""
                        }
                    }
                ) {
                    Text("Create", color = Color(0xFF1DB954))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showCreatePlaylistDialog = false
                        newPlaylistName = ""
                    }
                ) {
                    Text("Cancel", color = Color.Gray)
                }
            },
            containerColor = Color(0xFF1E1E1E)
        )
    }
'''

# We will inject this before the `if (showSpotifyDialog) {` check, which comes after the `Box` ends.
# Oh wait, `showSpotifyDialog` is injected at the bottom of the LibraryScreen.
# Let's find `if (showSpotifyDialog)` and replace it with both dialogs.

content = content.replace('if (showSpotifyDialog) {', dialog_ui + '\n    if (showSpotifyDialog) {')


library_item_composable = '''

@Composable
fun LibraryItem(
    isGridView: Boolean,
    title: String,
    subtitle: String,
    isCircle: Boolean = false,
    onClick: () -> Unit,
    imageContent: @Composable () -> Unit
) {
    if (isGridView) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(if (isCircle) CircleShape else RoundedCornerShape(8.dp))
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                imageContent()
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(title, color = Color.White, fontSize = 16.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(subtitle, color = Color(0xFFA7A7A7), fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp)
                .clickable { onClick() }
                .padding(horizontal = 24.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(if (isCircle) CircleShape else RoundedCornerShape(8.dp))
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                imageContent()
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = Color.White, fontSize = 24.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(2.dp))
                Text(subtitle, color = Color(0xFFA7A7A7), fontSize = 16.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}
'''
if "fun LibraryItem(" not in content:
    content += library_item_composable

with codecs.open(filepath, 'w', 'utf-8') as f:
    f.write(content)

print("Rewrite finished.")
