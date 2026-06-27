with open('app/src/main/java/com/pulse/music/ui/playlist/PlaylistScreen.kt', 'r', encoding='utf-8') as f:
    code = f.read()

import_statement = "import com.pulse.music.ui.components.PlayingAnimation"
if import_statement not in code:
    code = code.replace("import com.pulse.music.ui.theme.PulseMusicTheme", "import com.pulse.music.ui.theme.PulseMusicTheme\nimport com.pulse.music.ui.components.PlayingAnimation")

# Delete local PlayingAnimation and EqBar functions
# Using regex to remove them completely
import re
code = re.sub(r'@Composable\s*fun PlayingAnimation\(\)\s*\{[^\}]*\}[^\}]*\}\s*\}', '', code)
code = re.sub(r'@Composable\s*fun EqBar\(transition: InfiniteTransition, index: Int\)\s*\{.*?(?=@Composable|$)', '', code, flags=re.DOTALL)

with open('app/src/main/java/com/pulse/music/ui/playlist/PlaylistScreen.kt', 'w', encoding='utf-8') as f:
    f.write(code)
