package com.pulse.music.ui.profile;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000^\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0016\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u001a\u0010\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0006H\u0003\u001a\b\u0010\u0007\u001a\u00020\u0001H\u0003\u001aN\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2$\u0010\u000f\u001a \u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\u0010H\u0003\u001a\b\u0010\u0011\u001a\u00020\u0001H\u0003\u001a(\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00142\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u001a \u0010\u0017\u001a\u00020\u00012\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0002\u0010\u0019\u001a\u00020\u001aH\u0007\u001a;\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00142\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\u00142\u0015\b\u0002\u0010 \u001a\u000f\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0003\u00a2\u0006\u0002\b!H\u0003\u001a&\u0010\"\u001a\u00020\u00012\u001c\u0010#\u001a\u0018\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020\u00010$\u00a2\u0006\u0002\b!\u00a2\u0006\u0002\b&H\u0003\u001a\u0016\u0010\'\u001a\u00020\u00012\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u00a8\u0006)"}, d2 = {"AccountActions", "", "onLogout", "Lkotlin/Function0;", "AppUpdates", "context", "Landroid/content/Context;", "DiscoveryInsights", "PlaybackSettings", "highQuality", "", "spatialAudio", "gapless", "crossfade", "", "onUpdate", "Lkotlin/Function4;", "PremiumCard", "ProfileHero", "username", "", "profilePicUri", "onEditProfilePic", "ProfileScreen", "onNavigateToAuth", "viewModel", "Lcom/pulse/music/ui/auth/AuthViewModel;", "SettingRow", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "title", "description", "trailing", "Landroidx/compose/runtime/Composable;", "SettingsCard", "content", "Lkotlin/Function1;", "Landroidx/compose/foundation/layout/ColumnScope;", "Lkotlin/ExtensionFunctionType;", "StorageSettings", "onClearCache", "app_debug"})
public final class ProfileScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ProfileScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToAuth, @org.jetbrains.annotations.NotNull()
    com.pulse.music.ui.auth.AuthViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ProfileHero(java.lang.String username, java.lang.String profilePicUri, kotlin.jvm.functions.Function0<kotlin.Unit> onEditProfilePic) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DiscoveryInsights() {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PlaybackSettings(boolean highQuality, boolean spatialAudio, boolean gapless, int crossfade, kotlin.jvm.functions.Function4<? super java.lang.Boolean, ? super java.lang.Boolean, ? super java.lang.Boolean, ? super java.lang.Integer, kotlin.Unit> onUpdate) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PremiumCard() {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SettingsCard(kotlin.jvm.functions.Function1<? super androidx.compose.foundation.layout.ColumnScope, kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SettingRow(androidx.compose.ui.graphics.vector.ImageVector icon, java.lang.String title, java.lang.String description, kotlin.jvm.functions.Function0<kotlin.Unit> trailing) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AppUpdates(android.content.Context context) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AccountActions(kotlin.jvm.functions.Function0<kotlin.Unit> onLogout) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StorageSettings(kotlin.jvm.functions.Function0<kotlin.Unit> onClearCache) {
    }
}