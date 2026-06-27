import re

with open('app/src/main/java/com/pulse/music/ui/profile/ProfileScreen.kt', 'r', encoding='utf-8') as f:
    code = f.read()

new_app_updates = '''@Composable
private fun AppUpdates(context: android.content.Context) {
    val coroutineScope = rememberCoroutineScope()
    var isChecking by remember { mutableStateOf(false) }
    var updateAvailable by remember { mutableStateOf<com.pulse.music.update.UpdateManager.UpdateInfo?>(null) }
    var downloadProgress by remember { mutableStateOf(-1f) }

    LaunchedEffect(Unit) {
        val info = com.pulse.music.update.UpdateManager.checkForUpdate(com.pulse.music.BuildConfig.VERSION_NAME)
        if (info != null && info.hasUpdate) {
            updateAvailable = info
        }
    }

    Column(modifier = Modifier.padding(bottom = 24.dp)) {
        Text("App", color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
        SettingsCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (!isChecking && downloadProgress == -1f) {
                            isChecking = true
                            coroutineScope.launch {
                                try {
                                    val updateInfo = updateAvailable ?: com.pulse.music.update.UpdateManager.checkForUpdate(com.pulse.music.BuildConfig.VERSION_NAME)
                                    if (updateInfo != null && updateInfo.hasUpdate) {
                                        updateAvailable = updateInfo
                                        downloadProgress = 0f
                                        com.pulse.music.update.UpdateManager.downloadAndInstallUpdate(context, updateInfo.downloadUrl, coroutineScope) { progress ->
                                            downloadProgress = progress
                                            if (progress >= 1f) {
                                                downloadProgress = -1f
                                            }
                                        }
                                    } else {
                                        android.widget.Toast.makeText(context, "You are on the latest version!", android.widget.Toast.LENGTH_SHORT).show()
                                    }
                                } catch (e: Exception) {
                                    android.widget.Toast.makeText(context, "Failed to check for updates", android.widget.Toast.LENGTH_SHORT).show()
                                } finally {
                                    isChecking = false
                                }
                            }
                        }
                    }
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.SystemUpdate, contentDescription = null, tint = Color.LightGray)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        val titleText = when {
                            downloadProgress >= 0f -> "Downloading Update..."
                            isChecking -> "Checking for updates..."
                            updateAvailable != null -> "New update available (" + updateAvailable!!.newVersion + ")"
                            else -> "Check for Updates"
                        }
                        Text(titleText, color = if (updateAvailable != null) Color(0xFF10B981) else MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                        Text("Current version: " + com.pulse.music.BuildConfig.VERSION_NAME, color = Color.Gray, fontSize = 12.sp)
                    }
                    if (downloadProgress == -1f) {
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
                    }
                }
                
                if (downloadProgress >= 0f) {
                    Spacer(modifier = Modifier.height(12.dp))
                    androidx.compose.material3.LinearProgressIndicator(
                        progress = { downloadProgress },
                        modifier = Modifier.fillMaxWidth().height(4.dp),
                        color = Color(0xFFF92839),
                        trackColor = Color.DarkGray
                    )
                    Text(
                        text = "'''+'''% downloaded",
                        color = Color.Gray,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(top = 4.dp).align(Alignment.End)
                    )
                }
            }
        }
    }
}'''

# Replace the block
pattern = r'@Composable\s*private fun AppUpdates\(context: android\.content\.Context\)\s*\{.*?(?=@Composable\s*private fun AccountActions)'
code = re.sub(pattern, new_app_updates + '\n\n', code, flags=re.DOTALL)

with open('app/src/main/java/com/pulse/music/ui/profile/ProfileScreen.kt', 'w', encoding='utf-8') as f:
    f.write(code)
