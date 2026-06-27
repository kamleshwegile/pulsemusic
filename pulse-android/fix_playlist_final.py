with open('app/src/main/java/com/pulse/music/ui/playlist/PlaylistScreen.kt', 'r', encoding='utf-8') as f:
    lines = f.readlines()

new_lines = []
skip = False
for line in lines:
    if '@Composable' in line and 'fun AnimatedEqualizer()' in lines[lines.index(line)+1]:
        skip = True
    if skip and '}' in line and line.startswith('}'):
        skip = False
        continue
    if not skip:
        new_lines.append(line)

with open('app/src/main/java/com/pulse/music/ui/playlist/PlaylistScreen.kt', 'w', encoding='utf-8') as f:
    f.writelines(new_lines)
