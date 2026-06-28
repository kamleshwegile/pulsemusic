package com.pulse.music.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u001d\b\u0007\u0018\u00002\u00020\u0001B!\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ$\u0010)\u001a\b\u0012\u0004\u0012\u00020+0*2\u0006\u0010\u001b\u001a\u00020\rH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b,\u0010-J,\u0010.\u001a\b\u0012\u0004\u0012\u00020/0*2\u0006\u0010\u001b\u001a\u00020\r2\u0006\u00100\u001a\u00020\rH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b1\u00102J\u000e\u00103\u001a\u000204H\u0086@\u00a2\u0006\u0002\u00105J\u0018\u00106\u001a\u000607j\u0002`82\n\u00109\u001a\u000607j\u0002`8H\u0002J<\u0010:\u001a\b\u0012\u0004\u0012\u00020/0*2\u0006\u0010\'\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\r2\u0006\u00100\u001a\u00020\r2\u0006\u0010;\u001a\u00020\rH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b<\u0010=J$\u0010>\u001a\b\u0012\u0004\u0012\u00020+0*2\u0006\u0010\u001b\u001a\u00020\rH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b?\u0010-J4\u0010@\u001a\b\u0012\u0004\u0012\u00020+0*2\u0006\u0010\u001b\u001a\u00020\r2\u0006\u0010;\u001a\u00020\r2\u0006\u0010A\u001a\u00020\rH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bB\u0010CJ\u0016\u0010D\u001a\u0002042\u0006\u0010E\u001a\u00020/H\u0082@\u00a2\u0006\u0002\u0010FJ,\u0010G\u001a\b\u0012\u0004\u0012\u00020/0*2\u0006\u0010H\u001a\u00020\r2\u0006\u0010I\u001a\u00020\rH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bJ\u00102J.\u0010K\u001a\u0002042\u0006\u0010\u001f\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010LJ\u0016\u0010M\u001a\u0002042\u0006\u0010N\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010-J,\u0010O\u001a\b\u0012\u0004\u0012\u00020\r0*2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010P\u001a\u00020\rH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bQ\u0010RJ,\u0010S\u001a\b\u0012\u0004\u0012\u00020+0*2\u0006\u0010\u001b\u001a\u00020\r2\u0006\u0010;\u001a\u00020\rH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bT\u00102R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000f0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0018R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0018R\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0018R\u0019\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0018R\u0017\u0010#\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0018R\u0019\u0010%\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0018R\u0019\u0010\'\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0018\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006U"}, d2 = {"Lcom/pulse/music/data/repository/AuthRepository;", "", "api", "Lcom/pulse/music/data/network/PulseApiService;", "context", "Landroid/content/Context;", "database", "Lcom/pulse/music/data/local/AppDatabase;", "(Lcom/pulse/music/data/network/PulseApiService;Landroid/content/Context;Lcom/pulse/music/data/local/AppDatabase;)V", "CROSSFADE_KEY", "Landroidx/datastore/preferences/core/Preferences$Key;", "", "EMAIL_KEY", "", "GAPLESS_KEY", "", "HIGH_QUALITY_KEY", "PROFILE_PIC_KEY", "SPATIAL_AUDIO_KEY", "TOKEN_KEY", "USERNAME_KEY", "authToken", "Lkotlinx/coroutines/flow/Flow;", "getAuthToken", "()Lkotlinx/coroutines/flow/Flow;", "crossfade", "getCrossfade", "email", "getEmail", "gapless", "getGapless", "highQuality", "getHighQuality", "profilePicUri", "getProfilePicUri", "spatialAudio", "getSpatialAudio", "userId", "getUserId", "username", "getUsername", "forgotPassword", "Lkotlin/Result;", "Lcom/pulse/music/data/network/ForgotPasswordResponse;", "forgotPassword-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "login", "Lcom/pulse/music/data/network/AuthResponse;", "password", "login-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "parseError", "Ljava/lang/Exception;", "Lkotlin/Exception;", "e", "register", "code", "register-yxL6bBk", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "requestRegisterOtp", "requestRegisterOtp-gIAlu-s", "resetPassword", "newPassword", "resetPassword-BWLJW6A", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveAuth", "response", "(Lcom/pulse/music/data/network/AuthResponse;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "socialLogin", "provider", "token", "socialLogin-0E7RQCE", "updatePlaybackSettings", "(ZZZILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateProfilePic", "uri", "uploadProfilePic", "uriString", "uploadProfilePic-0E7RQCE", "(Landroid/content/Context;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "verifyCode", "verifyCode-0E7RQCE", "app_debug"})
public final class AuthRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.network.PulseApiService api = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.local.AppDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> TOKEN_KEY = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> USERNAME_KEY = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> EMAIL_KEY = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> PROFILE_PIC_KEY = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> HIGH_QUALITY_KEY = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> SPATIAL_AUDIO_KEY = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> GAPLESS_KEY = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.datastore.preferences.core.Preferences.Key<java.lang.Integer> CROSSFADE_KEY = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.String> authToken = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.String> email = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.String> userId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.String> username = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.String> profilePicUri = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> highQuality = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> spatialAudio = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> gapless = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Integer> crossfade = null;
    
    @javax.inject.Inject()
    public AuthRepository(@org.jetbrains.annotations.NotNull()
    com.pulse.music.data.network.PulseApiService api, @dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.pulse.music.data.local.AppDatabase database) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getAuthToken() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getEmail() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getUserId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getUsername() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getProfilePicUri() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getHighQuality() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getSpatialAudio() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getGapless() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getCrossfade() {
        return null;
    }
    
    private final java.lang.Exception parseError(java.lang.Exception e) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object logout(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateProfilePic(@org.jetbrains.annotations.NotNull()
    java.lang.String uri, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updatePlaybackSettings(boolean highQuality, boolean spatialAudio, boolean gapless, int crossfade, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object saveAuth(com.pulse.music.data.network.AuthResponse response, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}