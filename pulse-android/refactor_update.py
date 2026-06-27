import re

with open('app/src/main/java/com/pulse/music/update/UpdateManager.kt', 'r', encoding='utf-8') as f:
    code = f.read()

# We need to completely rewrite UpdateManager to handle progress and cleanup!
new_code = '''package com.pulse.music.update

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object UpdateManager {

    private const val GITHUB_OWNER = "RAHUL-0568"
    private const val GITHUB_REPO = "Pulse-Music-Releases"
    private const val GITHUB_API_URL = "https://api.github.com/repos/\/\/releases/latest"

    data class UpdateInfo(val hasUpdate: Boolean, val newVersion: String, val downloadUrl: String)

    fun cleanupOldUpdates(context: Context) {
        try {
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Pulse-update.apk")
            if (file.exists()) {
                file.delete()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun checkForUpdate(currentVersion: String): UpdateInfo? = withContext(Dispatchers.IO) {
        try {
            val url = URL(GITHUB_API_URL)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json")

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = reader.readText()
                reader.close()

                val json = JSONObject(response)
                val tagName = json.getString("tag_name")
                
                val isNewer = (tagName.replace("v", "").replace(".", "").toIntOrNull() ?: 0) >
                              (currentVersion.replace("v", "").replace(".", "").toIntOrNull() ?: 0)

                var apkUrl = ""
                val assets = json.getJSONArray("assets")
                for (i in 0 until assets.length()) {
                    val asset = assets.getJSONObject(i)
                    if (asset.getString("name").endsWith(".apk")) {
                        apkUrl = asset.getString("browser_download_url")
                        break
                    }
                }

                if (isNewer && apkUrl.isNotEmpty()) {
                    return@withContext UpdateInfo(true, tagName, apkUrl)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext UpdateInfo(false, currentVersion, "")
    }

    fun downloadAndInstallUpdate(context: Context, downloadUrl: String, scope: CoroutineScope, onProgress: (Float) -> Unit) {
        cleanupOldUpdates(context) // Delete any old apk before downloading new one

        val request = DownloadManager.Request(Uri.parse(downloadUrl))
            .setTitle("Pulse Music Update")
            .setDescription("Downloading the latest version...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Pulse-update.apk")
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = downloadManager.enqueue(request)

        // Poll progress
        scope.launch(Dispatchers.IO) {
            var isDownloading = true
            while (isDownloading) {
                val query = DownloadManager.Query().setFilterById(downloadId)
                val cursor = downloadManager.query(query)
                if (cursor != null && cursor.moveToFirst()) {
                    val bytesDownloadedIndex = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
                    val bytesTotalIndex = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
                    val statusIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                    
                    if (bytesDownloadedIndex != -1 && bytesTotalIndex != -1 && statusIndex != -1) {
                        val bytesDownloaded = cursor.getInt(bytesDownloadedIndex)
                        val bytesTotal = cursor.getInt(bytesTotalIndex)
                        val status = cursor.getInt(statusIndex)

                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            onProgress(1f)
                            isDownloading = false
                        } else if (status == DownloadManager.STATUS_FAILED) {
                            isDownloading = false
                        } else if (bytesTotal > 0) {
                            onProgress(bytesDownloaded.toFloat() / bytesTotal.toFloat())
                        }
                    }
                }
                cursor?.close()
                if (isDownloading) delay(500)
            }
        }

        val onComplete = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == downloadId) {
                    val uri = downloadManager.getUriForDownloadedFile(downloadId)
                    if (uri != null) {
                        installApk(context, uri)
                    }
                    context.unregisterReceiver(this)
                }
            }
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE), Context.RECEIVER_EXPORTED)
        } else {
            context.registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        }
    }

    private fun installApk(context: Context, uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/vnd.android.package-archive")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to start installer.", Toast.LENGTH_SHORT).show()
        }
    }
}
'''

with open('app/src/main/java/com/pulse/music/update/UpdateManager.kt', 'w', encoding='utf-8') as f:
    f.write(new_code)
