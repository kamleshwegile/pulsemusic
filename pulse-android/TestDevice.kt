
import androidx.media3.exoplayer.ExoPlayer
import android.media.AudioDeviceInfo
fun test(exoPlayer: ExoPlayer, device: AudioDeviceInfo) {
    exoPlayer.setPreferredAudioDevice(device)
}

