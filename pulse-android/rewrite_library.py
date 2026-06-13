import codecs

filepath = 'app/src/main/java/com/pulse/music/ui/library/LibraryScreen.kt'
with codecs.open(filepath, 'r', 'utf-8') as f:
    content = f.read()

# Replace Playlists
p_start = '''                // Playlists
                if (selectedFilter.isEmpty() || selectedFilter == "Playlists") {

                    items(filteredPlaylists) { playlist ->
                        Row('''
p_end = '''Text("Playlist •  songs", color = Color(0xFFA7A7A7), fontSize = 16.sp)
                            }
                        }
                    }
                }'''

if p_start in content and p_end in content:
    idx1 = content.find(p_start)
    idx2 = content.find(p_end) + len(p_end)
    
    new_playlists = '''                // Playlists
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
                            hours > 0 -> " • hr min"
                            else -> " •  min"
                        }
                        val validCovers = songs.mapNotNull { it.albumArt }.filter { it.isNotEmpty() }
                        
                        LibraryItem(
                            isGridView = isGridView,
                            title = playlist.name,
                            subtitle = "Playlist •  songs",
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
                                    Icon(Icons.Default.QueueMusic, contentDescription = "Playlist", tint = Color.White, modifier = Modifier.size(32.dp))
                                }
                            }
                        )
                    }
                }'''
    content = content[:idx1] + new_playlists + content[idx2:]
    print("Playlists replaced")

# Replace Artists
a_start = '''                // Artists
                if (selectedFilter.isEmpty() || selectedFilter == "Artists") {
                    items(filteredArtists) { artist ->
                        Row('''
a_end = '''Text("Artist", color = Color(0xFFA7A7A7), fontSize = 16.sp)
                            }
                        }
                    }
                }'''

if a_start in content and a_end in content:
    idx1 = content.find(a_start)
    idx2 = content.find(a_end) + len(a_end)
    
    new_artists = '''                // Artists
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
    content = content[:idx1] + new_artists + content[idx2:]
    print("Artists replaced")


# Replace Local Songs
l_start = '''                // Downloaded / Local
                if (selectedFilter == "Local") {
                    items(filteredLocalSongs) { song ->
                        Row('''
l_end = '''Text("Song • ", color = Color(0xFFA7A7A7), fontSize = 16.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                        }
                    }
                }'''

if l_start in content and l_end in content:
    idx1 = content.find(l_start)
    idx2 = content.find(l_end) + len(l_end)
    
    new_local = '''                // Downloaded / Local
                if (selectedFilter == "Local") {
                    items(filteredLocalSongs) { song ->
                        LibraryItem(
                            isGridView = isGridView,
                            title = song.title,
                            subtitle = "Song • ",
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
                }'''
    content = content[:idx1] + new_local + content[idx2:]
    print("Local Songs replaced")


# Add LibraryItem at the end if not exists
if "fun LibraryItem(" not in content:
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
    content += library_item_composable
    print("LibraryItem added")


with codecs.open(filepath, 'w', 'utf-8') as f:
    f.write(content)

