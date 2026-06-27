package com.pulse.music.update;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u0013B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\u0004H\u0086@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0004J\u0018\u0010\u0010\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/pulse/music/update/UpdateManager;", "", "()V", "GITHUB_API_URL", "", "GITHUB_OWNER", "GITHUB_REPO", "checkForUpdate", "Lcom/pulse/music/update/UpdateManager$UpdateInfo;", "currentVersion", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "downloadAndInstallUpdate", "", "context", "Landroid/content/Context;", "downloadUrl", "installApk", "uri", "Landroid/net/Uri;", "UpdateInfo", "app_debug"})
public final class UpdateManager {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String GITHUB_OWNER = "RAHUL-0568";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String GITHUB_REPO = "Pulse-Music-backend";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String GITHUB_API_URL = "https://api.github.com/repos/RAHUL-0568/Pulse-Music-backend/releases/latest";
    @org.jetbrains.annotations.NotNull()
    public static final com.pulse.music.update.UpdateManager INSTANCE = null;
    
    private UpdateManager() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object checkForUpdate(@org.jetbrains.annotations.NotNull()
    java.lang.String currentVersion, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pulse.music.update.UpdateManager.UpdateInfo> $completion) {
        return null;
    }
    
    public final void downloadAndInstallUpdate(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String downloadUrl) {
    }
    
    private final void installApk(android.content.Context context, android.net.Uri uri) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u000f\u001a\u00020\u0005H\u00c6\u0003J\'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0011\u001a\u00020\u00032\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t\u00a8\u0006\u0016"}, d2 = {"Lcom/pulse/music/update/UpdateManager$UpdateInfo;", "", "hasUpdate", "", "newVersion", "", "downloadUrl", "(ZLjava/lang/String;Ljava/lang/String;)V", "getDownloadUrl", "()Ljava/lang/String;", "getHasUpdate", "()Z", "getNewVersion", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class UpdateInfo {
        private final boolean hasUpdate = false;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String newVersion = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String downloadUrl = null;
        
        public UpdateInfo(boolean hasUpdate, @org.jetbrains.annotations.NotNull()
        java.lang.String newVersion, @org.jetbrains.annotations.NotNull()
        java.lang.String downloadUrl) {
            super();
        }
        
        public final boolean getHasUpdate() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getNewVersion() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getDownloadUrl() {
            return null;
        }
        
        public final boolean component1() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.pulse.music.update.UpdateManager.UpdateInfo copy(boolean hasUpdate, @org.jetbrains.annotations.NotNull()
        java.lang.String newVersion, @org.jetbrains.annotations.NotNull()
        java.lang.String downloadUrl) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}