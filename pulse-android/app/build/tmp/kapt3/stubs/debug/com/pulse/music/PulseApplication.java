package com.pulse.music;

@dagger.hilt.android.HiltAndroidApp()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u0006\u001a\u00020\u0007H\u0016\u00a8\u0006\b"}, d2 = {"Lcom/pulse/music/PulseApplication;", "Landroid/app/Application;", "Lcoil/ImageLoaderFactory;", "()V", "newImageLoader", "Lcoil/ImageLoader;", "onCreate", "", "app_debug"})
public final class PulseApplication extends android.app.Application implements coil.ImageLoaderFactory {
    
    public PulseApplication() {
        super();
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public coil.ImageLoader newImageLoader() {
        return null;
    }
}