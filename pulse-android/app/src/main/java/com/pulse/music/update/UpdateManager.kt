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
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object UpdateManager {

    // PLACEHOLDERS for the user to replace later!
    private const val GITHUB_OWNER = "YOUR_GITHUB_USERNAME"
    private const val GITHUB_REPO = "YOUR_REPO_NAME"
    private const val GITHUB_API_URL = "https://api.github.com/repos/$GITHUB_OWNER/$GITHUB_REPO/releases/latest"

    data class UpdateInfo(val hasUpdate: Boolean, val newVersion: String, val downloadUrl: String)

    suspend fun checkForUpdate(currentVersion: String): UpdateInfo? = withContext(Dispatchers.IO) {
        if (GITHUB_OWNER == "YOUR_GITHUB_USERNAME") {
            // Return dummy update if placeholders are still present so the user can test the UI flow
            kotlinx.coroutines.delay(1000)
            return@withContext UpdateInfo(true, "v2.0.0", "dummy_url")
        }

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
                
                // Very basic version string comparison (assuming format like "v1.0.0")
                val isNewer = tagName.replace("v", "").replace(".", "").toIntOrNull() ?: 0 >
                              currentVersion.replace("v", "").replace(".", "").toIntOrNull() ?: 0

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

    fun downloadAndInstallUpdate(context: Context, downloadUrl: String) {
        if (downloadUrl == "dummy_url") {
            Toast.makeText(context, "This is a test! Add your real GitHub repo to UpdateManager.kt to actually download.", Toast.LENGTH_LONG).show()
            return
        }

        Toast.makeText(context, "Downloading update...", Toast.LENGTH_SHORT).show()

        val request = DownloadManager.Request(Uri.parse(downloadUrl))
            .setTitle("Pulse Music Update")
            .setDescription("Downloading the latest version...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Pulse-update.apk")
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = downloadManager.enqueue(request)

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
