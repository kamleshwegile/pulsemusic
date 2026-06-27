package com.pulse.music.ui.auth;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010!\u001a\u00020\"J\"\u0010#\u001a\u00020\"2\u0006\u0010$\u001a\u00020\u00192\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\"0&J8\u0010\'\u001a\u00020\"2\u0006\u0010$\u001a\u00020\u00192\u0006\u0010(\u001a\u00020\u00192\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\"0*2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\"0&J\u0006\u0010,\u001a\u00020\"JH\u0010-\u001a\u00020\"2\u0006\u0010\u001f\u001a\u00020\u00192\u0006\u0010$\u001a\u00020\u00192\u0006\u0010(\u001a\u00020\u00192\u0006\u0010.\u001a\u00020\u00192\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\"0*2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\"0&J\"\u0010/\u001a\u00020\"2\u0006\u0010$\u001a\u00020\u00192\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\"0&J8\u00100\u001a\u00020\"2\u0006\u0010$\u001a\u00020\u00192\u0006\u0010.\u001a\u00020\u00192\u0006\u00101\u001a\u00020\u00192\u0018\u0010%\u001a\u0014\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\"02J8\u00103\u001a\u00020\"2\u0006\u00104\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u00192\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\"0*2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\"0&J&\u00105\u001a\u00020\"2\u0006\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u00106\u001a\u00020\"2\u0006\u00107\u001a\u00020\u0019J\u0016\u00108\u001a\u00020\"2\u0006\u00109\u001a\u00020:2\u0006\u00107\u001a\u00020\u0019J0\u0010;\u001a\u00020\"2\u0006\u0010$\u001a\u00020\u00192\u0006\u0010.\u001a\u00020\u00192\u0018\u0010%\u001a\u0014\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\"02R\u0016\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000fR\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00140\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00190\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00140\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u000fR\u0019\u0010\u001d\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00190\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u000fR\u0019\u0010\u001f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00190\r\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u000f\u00a8\u0006<"}, d2 = {"Lcom/pulse/music/ui/auth/AuthViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/pulse/music/data/repository/AuthRepository;", "musicRepository", "Lcom/pulse/music/data/repository/OnlineMusicRepository;", "musicPlayerManager", "Lcom/pulse/music/player/MusicPlayerManager;", "(Lcom/pulse/music/data/repository/AuthRepository;Lcom/pulse/music/data/repository/OnlineMusicRepository;Lcom/pulse/music/player/MusicPlayerManager;)V", "_capsuleState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/pulse/music/data/network/MusicCapsuleResponse;", "capsuleState", "Lkotlinx/coroutines/flow/StateFlow;", "getCapsuleState", "()Lkotlinx/coroutines/flow/StateFlow;", "crossfade", "", "getCrossfade", "gapless", "", "getGapless", "highQuality", "getHighQuality", "profilePicUri", "", "getProfilePicUri", "spatialAudio", "getSpatialAudio", "token", "getToken", "username", "getUsername", "fetchMusicCapsule", "", "forgotPassword", "email", "onResult", "Lkotlin/Function1;", "login", "password", "onSuccess", "Lkotlin/Function0;", "onError", "logout", "register", "code", "requestRegisterOtp", "resetPassword", "newPassword", "Lkotlin/Function2;", "socialLogin", "provider", "updatePlaybackSettings", "updateProfilePic", "uri", "uploadProfilePic", "context", "Landroid/content/Context;", "verifyCode", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AuthViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.repository.AuthRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.data.repository.OnlineMusicRepository musicRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pulse.music.player.MusicPlayerManager musicPlayerManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.pulse.music.data.network.MusicCapsuleResponse> _capsuleState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pulse.music.data.network.MusicCapsuleResponse> capsuleState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> token = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> username = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> profilePicUri = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> highQuality = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> spatialAudio = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> gapless = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> crossfade = null;
    
    @javax.inject.Inject()
    public AuthViewModel(@org.jetbrains.annotations.NotNull()
    com.pulse.music.data.repository.AuthRepository repository, @org.jetbrains.annotations.NotNull()
    com.pulse.music.data.repository.OnlineMusicRepository musicRepository, @org.jetbrains.annotations.NotNull()
    com.pulse.music.player.MusicPlayerManager musicPlayerManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pulse.music.data.network.MusicCapsuleResponse> getCapsuleState() {
        return null;
    }
    
    public final void fetchMusicCapsule() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getToken() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getUsername() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getProfilePicUri() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getHighQuality() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getSpatialAudio() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getGapless() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getCrossfade() {
        return null;
    }
    
    public final void updateProfilePic(@org.jetbrains.annotations.NotNull()
    java.lang.String uri) {
    }
    
    public final void uploadProfilePic(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String uri) {
    }
    
    public final void updatePlaybackSettings(boolean highQuality, boolean spatialAudio, boolean gapless, int crossfade) {
    }
    
    public final void login(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onError) {
    }
    
    public final void requestRegisterOtp(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onResult) {
    }
    
    public final void register(@org.jetbrains.annotations.NotNull()
    java.lang.String username, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onError) {
    }
    
    public final void socialLogin(@org.jetbrains.annotations.NotNull()
    java.lang.String provider, @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onError) {
    }
    
    public final void logout() {
    }
    
    public final void forgotPassword(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onResult) {
    }
    
    public final void verifyCode(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> onResult) {
    }
    
    public final void resetPassword(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    java.lang.String newPassword, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> onResult) {
    }
}