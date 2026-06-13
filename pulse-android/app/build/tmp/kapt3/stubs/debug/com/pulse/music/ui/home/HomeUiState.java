package com.pulse.music.ui.home;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2 = {"Lcom/pulse/music/ui/home/HomeUiState;", "", "()V", "Error", "Loading", "Success", "Lcom/pulse/music/ui/home/HomeUiState$Error;", "Lcom/pulse/music/ui/home/HomeUiState$Loading;", "Lcom/pulse/music/ui/home/HomeUiState$Success;", "app_debug"})
public abstract class HomeUiState {
    
    private HomeUiState() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/pulse/music/ui/home/HomeUiState$Error;", "Lcom/pulse/music/ui/home/HomeUiState;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Error extends com.pulse.music.ui.home.HomeUiState {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String message = null;
        
        public Error(@org.jetbrains.annotations.NotNull()
        java.lang.String message) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.pulse.music.ui.home.HomeUiState.Error copy(@org.jetbrains.annotations.NotNull()
        java.lang.String message) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/pulse/music/ui/home/HomeUiState$Loading;", "Lcom/pulse/music/ui/home/HomeUiState;", "()V", "app_debug"})
    public static final class Loading extends com.pulse.music.ui.home.HomeUiState {
        @org.jetbrains.annotations.NotNull()
        public static final com.pulse.music.ui.home.HomeUiState.Loading INSTANCE = null;
        
        private Loading() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u00a2\u0006\u0002\u0010\tJ\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H\u00c6\u0003JI\u0010\u0013\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u00d6\u0003J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000b\u00a8\u0006\u001c"}, d2 = {"Lcom/pulse/music/ui/home/HomeUiState$Success;", "Lcom/pulse/music/ui/home/HomeUiState;", "recentPlays", "", "Lcom/pulse/music/domain/Song;", "allRecentPlays", "suggested", "modules", "Lcom/pulse/music/data/network/HomeModule;", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getAllRecentPlays", "()Ljava/util/List;", "getModules", "getRecentPlays", "getSuggested", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
    public static final class Success extends com.pulse.music.ui.home.HomeUiState {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.pulse.music.domain.Song> recentPlays = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.pulse.music.domain.Song> allRecentPlays = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.pulse.music.domain.Song> suggested = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.pulse.music.data.network.HomeModule> modules = null;
        
        public Success(@org.jetbrains.annotations.NotNull()
        java.util.List<com.pulse.music.domain.Song> recentPlays, @org.jetbrains.annotations.NotNull()
        java.util.List<com.pulse.music.domain.Song> allRecentPlays, @org.jetbrains.annotations.NotNull()
        java.util.List<com.pulse.music.domain.Song> suggested, @org.jetbrains.annotations.NotNull()
        java.util.List<com.pulse.music.data.network.HomeModule> modules) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.pulse.music.domain.Song> getRecentPlays() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.pulse.music.domain.Song> getAllRecentPlays() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.pulse.music.domain.Song> getSuggested() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.pulse.music.data.network.HomeModule> getModules() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.pulse.music.domain.Song> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.pulse.music.domain.Song> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.pulse.music.domain.Song> component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.pulse.music.data.network.HomeModule> component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.pulse.music.ui.home.HomeUiState.Success copy(@org.jetbrains.annotations.NotNull()
        java.util.List<com.pulse.music.domain.Song> recentPlays, @org.jetbrains.annotations.NotNull()
        java.util.List<com.pulse.music.domain.Song> allRecentPlays, @org.jetbrains.annotations.NotNull()
        java.util.List<com.pulse.music.domain.Song> suggested, @org.jetbrains.annotations.NotNull()
        java.util.List<com.pulse.music.data.network.HomeModule> modules) {
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