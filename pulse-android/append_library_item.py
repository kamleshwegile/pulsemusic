import codecs

filepath = 'app/src/main/java/com/pulse/music/ui/library/LibraryScreen.kt'
with codecs.open(filepath, 'r', 'utf-8') as f:
    content = f.read()

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
    print("LibraryItem appended")
else:
    print("LibraryItem already exists")
