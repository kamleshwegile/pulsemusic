import re

# Fix PlaylistScreen
with open('app/src/main/java/com/pulse/music/ui/playlist/PlaylistScreen.kt', 'r', encoding='utf-8') as f:
    ps_code = f.read()

# Make sure PlayingAnimation is imported in PlaylistScreen (already did this earlier)

# Fix AlbumScreen
with open('app/src/main/java/com/pulse/music/ui/album/AlbumScreen.kt', 'r', encoding='utf-8') as f:
    as_code = f.read()

if 'import com.pulse.music.ui.components.PlayingAnimation' not in as_code:
    as_code = as_code.replace('import androidx.compose.ui.unit.sp', 'import androidx.compose.ui.unit.sp\nimport com.pulse.music.ui.components.PlayingAnimation')

as_code = as_code.replace(
    'Icon(Icons.Default.GraphicEq, contentDescription = "Playing", tint = MaterialTheme.colorScheme.onBackground, modifier = Modifier.size(16.dp))',
    'PlayingAnimation()'
)

with open('app/src/main/java/com/pulse/music/ui/album/AlbumScreen.kt', 'w', encoding='utf-8') as f:
    f.write(as_code)

# Fix ArtistScreen
with open('app/src/main/java/com/pulse/music/ui/artist/ArtistScreen.kt', 'r', encoding='utf-8') as f:
    ar_code = f.read()

if 'import com.pulse.music.ui.components.PlayingAnimation' not in ar_code:
    ar_code = ar_code.replace('import androidx.compose.ui.unit.sp', 'import androidx.compose.ui.unit.sp\nimport com.pulse.music.ui.components.PlayingAnimation')

# Need to find the ArtistTopTrackItem or wherever tracks are drawn in ArtistScreen
# We'll just replace the song.albumArt part
# Let's see what ArtistScreen looks like first...
