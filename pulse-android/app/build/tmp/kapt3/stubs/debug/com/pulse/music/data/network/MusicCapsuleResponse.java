package com.pulse.music.data.network;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0003J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\u0006H\u00c6\u0003J=\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006H\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000f\u00a8\u0006\u001c"}, d2 = {"Lcom/pulse/music/data/network/MusicCapsuleResponse;", "", "totalPlays", "", "uniqueSongs", "topSongs", "", "Lcom/pulse/music/data/network/MusicCapsuleSong;", "topArtists", "Lcom/pulse/music/data/network/MusicCapsuleArtist;", "(IILjava/util/List;Ljava/util/List;)V", "getTopArtists", "()Ljava/util/List;", "getTopSongs", "getTotalPlays", "()I", "getUniqueSongs", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class MusicCapsuleResponse {
    private final int totalPlays = 0;
    private final int uniqueSongs = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.pulse.music.data.network.MusicCapsuleSong> topSongs = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.pulse.music.data.network.MusicCapsuleArtist> topArtists = null;
    
    public MusicCapsuleResponse(int totalPlays, int uniqueSongs, @org.jetbrains.annotations.NotNull()
    java.util.List<com.pulse.music.data.network.MusicCapsuleSong> topSongs, @org.jetbrains.annotations.NotNull()
    java.util.List<com.pulse.music.data.network.MusicCapsuleArtist> topArtists) {
        super();
    }
    
    public final int getTotalPlays() {
        return 0;
    }
    
    public final int getUniqueSongs() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.pulse.music.data.network.MusicCapsuleSong> getTopSongs() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.pulse.music.data.network.MusicCapsuleArtist> getTopArtists() {
        return null;
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int component2() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.pulse.music.data.network.MusicCapsuleSong> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.pulse.music.data.network.MusicCapsuleArtist> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pulse.music.data.network.MusicCapsuleResponse copy(int totalPlays, int uniqueSongs, @org.jetbrains.annotations.NotNull()
    java.util.List<com.pulse.music.data.network.MusicCapsuleSong> topSongs, @org.jetbrains.annotations.NotNull()
    java.util.List<com.pulse.music.data.network.MusicCapsuleArtist> topArtists) {
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