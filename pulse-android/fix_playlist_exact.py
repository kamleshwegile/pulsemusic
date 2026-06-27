with open('app/src/main/java/com/pulse/music/ui/playlist/PlaylistScreen.kt', 'r', encoding='utf-8') as f:
    text = f.read()

target = '''@Composable
fun AnimatedEqualizer() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.height(16.dp)
    ) {
        val transition = rememberInfiniteTransition(label = "eq")
        EqBar(transition, 0)
        EqBar(transition, 1)
        EqBar(transition, 2)
        EqBar(transition, 3)
    }
}'''

text = text.replace(target, '')

with open('app/src/main/java/com/pulse/music/ui/playlist/PlaylistScreen.kt', 'w', encoding='utf-8') as f:
    f.write(text)
