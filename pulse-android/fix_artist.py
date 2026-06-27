import re

with open('app/src/main/java/com/pulse/music/ui/artist/ArtistScreen.kt', 'r', encoding='utf-8') as f:
    code = f.read()

target = '''                        Text(
                            text = "",
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.width(32.dp)
                        )'''

replacement = '''                        val isActive = currentSong?.id == song.id
                        if (isActive && isPlaying) {
                            Box(modifier = Modifier.width(32.dp), contentAlignment = Alignment.CenterStart) {
                                com.pulse.music.ui.components.PlayingAnimation()
                            }
                        } else {
                            Text(
                                text = "",
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = if (isActive) 1f else 0.7f),
                                fontSize = 15.sp,
                                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
                                modifier = Modifier.width(32.dp)
                            )
                        }'''

# Handle escaping of dollar signs in python raw string writing for Kotlin
target = target.replace("", "")
replacement = replacement.replace("", "")

code = code.replace(target, replacement)

with open('app/src/main/java/com/pulse/music/ui/artist/ArtistScreen.kt', 'w', encoding='utf-8') as f:
    f.write(code)
