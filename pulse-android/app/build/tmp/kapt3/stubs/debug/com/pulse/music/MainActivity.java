package com.pulse.music;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0014J\b\u0010\u0013\u001a\u00020\u0010H\u0014R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u0014"}, d2 = {"Lcom/pulse/music/MainActivity;", "Landroidx/activity/ComponentActivity;", "()V", "authRepository", "Lcom/pulse/music/data/repository/AuthRepository;", "getAuthRepository", "()Lcom/pulse/music/data/repository/AuthRepository;", "setAuthRepository", "(Lcom/pulse/music/data/repository/AuthRepository;)V", "musicPlayerManager", "Lcom/pulse/music/player/MusicPlayerManager;", "getMusicPlayerManager", "()Lcom/pulse/music/player/MusicPlayerManager;", "setMusicPlayerManager", "(Lcom/pulse/music/player/MusicPlayerManager;)V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "app_debug"})
public final class MainActivity extends androidx.activity.ComponentActivity {
    @javax.inject.Inject()
    public com.pulse.music.player.MusicPlayerManager musicPlayerManager;
    @javax.inject.Inject()
    public com.pulse.music.data.repository.AuthRepository authRepository;
    
    public MainActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pulse.music.player.MusicPlayerManager getMusicPlayerManager() {
        return null;
    }
    
    public final void setMusicPlayerManager(@org.jetbrains.annotations.NotNull()
    com.pulse.music.player.MusicPlayerManager p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pulse.music.data.repository.AuthRepository getAuthRepository() {
        return null;
    }
    
    public final void setAuthRepository(@org.jetbrains.annotations.NotNull()
    com.pulse.music.data.repository.AuthRepository p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
}