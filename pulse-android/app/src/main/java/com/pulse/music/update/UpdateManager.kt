package com.pulse.music.update

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
    private const val GITHUB_API_URL = "https://api.github.com/repos/$GITHUB_OWNER/$GITHUB_REPO/releases/latest"

    val isDownloading = kotlinx.coroutines.flow.MutableStateFlow(false)
    val downloadProgress = kotlinx.coroutines.flow.MutableStateFlow(0f)
    val downloadedBytes = kotlinx.coroutines.flow.MutableStateFlow(0L)
    val downloadStatusText = kotlinx.coroutines.flow.MutableStateFlow("Starting...")

    data class UpdateInfo(val hasUpdate: Boolean, val newVersion: String, val downloadUrl: String)

    fun cleanupOldUpdates(context: Context) {
        try {
            val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            val files = dir?.listFiles { _, name -> name.startsWith("Pulse-update-") && name.endsWith(".apk") }
            files?.forEach { it.delete() }
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
                
                val cleanTag = tagName.replace(Regex("[^0-9]"), "").toIntOrNull() ?: 0
                val cleanCurrent = currentVersion.replace(Regex("[^0-9]"), "").toIntOrNull() ?: 0
                val isNewer = cleanTag > cleanCurrent

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

    fun downloadAndInstallUpdate(context: Context, downloadUrl: String, scope: CoroutineScope) {
        if (isDownloading.value) return
        isDownloading.value = true
        downloadProgress.value = 0f
        downloadedBytes.value = 0L
        downloadStatusText.value = "Starting..."
        
        cleanupOldUpdates(context)

        val request = DownloadManager.Request(Uri.parse(downloadUrl))
            .setTitle("Pulse Music Update")
            .setDescription("Downloading the latest version...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, ("Pulse-update-" + System.currentTimeMillis() + ".apk"))
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = downloadManager.enqueue(request)

        scope.launch(Dispatchers.IO) {
            while (isDownloading.value) {
                val query = DownloadManager.Query().setFilterById(downloadId)
                val cursor = downloadManager.query(query)
                if (cursor != null && cursor.moveToFirst()) {
                    val bytesDownloadedIndex = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
                    val bytesTotalIndex = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
                    val statusIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                    
                    if (bytesDownloadedIndex != -1 && bytesTotalIndex != -1 && statusIndex != -1) {
                        val bytesDownloaded = cursor.getLong(bytesDownloadedIndex)
                        val bytesTotal = cursor.getLong(bytesTotalIndex)
                        val status = cursor.getInt(statusIndex)

                        when (status) {
                            DownloadManager.STATUS_PENDING -> downloadStatusText.value = "Pending..."
                            DownloadManager.STATUS_RUNNING -> downloadStatusText.value = "Downloading..."
                            DownloadManager.STATUS_PAUSED -> downloadStatusText.value = "Paused..."
                            DownloadManager.STATUS_SUCCESSFUL -> downloadStatusText.value = "Complete!"
                            DownloadManager.STATUS_FAILED -> downloadStatusText.value = "Failed."
                        }

                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            downloadProgress.value = 1f
                            isDownloading.value = false
                        } else if (status == DownloadManager.STATUS_FAILED) {
                            isDownloading.value = false
                        } else {
                            downloadedBytes.value = bytesDownloaded
                            if (bytesTotal > 0) {
                                downloadProgress.value = bytesDownloaded.toFloat() / bytesTotal.toFloat()
                            } else {
                                downloadProgress.value = -1f // Indeterminate
                            }
                        }
                    }
                }
                cursor?.close()
                if (isDownloading.value) delay(500)
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






