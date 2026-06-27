package com.pulse.music;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.pulse.music.data.local.AppDatabase;
import com.pulse.music.data.local.FollowedArtistDao;
import com.pulse.music.data.local.LikedSongDao;
import com.pulse.music.data.local.PlaylistDao;
import com.pulse.music.data.local.SongDao;
import com.pulse.music.data.network.PulseApiService;
import com.pulse.music.data.repository.AuthRepository;
import com.pulse.music.data.repository.FollowedArtistRepository;
import com.pulse.music.data.repository.JamRepository;
import com.pulse.music.data.repository.LikedSongsRepository;
import com.pulse.music.data.repository.LocalMusicRepository;
import com.pulse.music.data.repository.MusicRepository;
import com.pulse.music.data.repository.OnlineMusicRepository;
import com.pulse.music.data.repository.PlaylistRepository;
import com.pulse.music.di.DatabaseModule_ProvideAppDatabaseFactory;
import com.pulse.music.di.DatabaseModule_ProvideFollowedArtistDaoFactory;
import com.pulse.music.di.DatabaseModule_ProvideLikedSongDaoFactory;
import com.pulse.music.di.DatabaseModule_ProvidePlaylistDaoFactory;
import com.pulse.music.di.DatabaseModule_ProvideSongDaoFactory;
import com.pulse.music.di.NetworkModule_ProvideOkHttpClientFactory;
import com.pulse.music.di.NetworkModule_ProvidePulseApiServiceFactory;
import com.pulse.music.di.NetworkModule_ProvideRetrofitFactory;
import com.pulse.music.player.MusicPlayerManager;
import com.pulse.music.player.PlaybackService;
import com.pulse.music.player.PlaybackService_MembersInjector;
import com.pulse.music.ui.album.AlbumViewModel;
import com.pulse.music.ui.album.AlbumViewModel_HiltModules_KeyModule_ProvideFactory;
import com.pulse.music.ui.artist.ArtistViewModel;
import com.pulse.music.ui.artist.ArtistViewModel_HiltModules_KeyModule_ProvideFactory;
import com.pulse.music.ui.auth.AuthViewModel;
import com.pulse.music.ui.auth.AuthViewModel_HiltModules_KeyModule_ProvideFactory;
import com.pulse.music.ui.home.HomeViewModel;
import com.pulse.music.ui.home.HomeViewModel_HiltModules_KeyModule_ProvideFactory;
import com.pulse.music.ui.jam.JamViewModel;
import com.pulse.music.ui.jam.JamViewModel_HiltModules_KeyModule_ProvideFactory;
import com.pulse.music.ui.library.LibraryViewModel;
import com.pulse.music.ui.library.LibraryViewModel_HiltModules_KeyModule_ProvideFactory;
import com.pulse.music.ui.lockscreen.LockScreenActivity;
import com.pulse.music.ui.lockscreen.LockScreenActivity_MembersInjector;
import com.pulse.music.ui.nowplaying.NowPlayingViewModel;
import com.pulse.music.ui.nowplaying.NowPlayingViewModel_HiltModules_KeyModule_ProvideFactory;
import com.pulse.music.ui.playlist.PlaylistViewModel;
import com.pulse.music.ui.playlist.PlaylistViewModel_HiltModules_KeyModule_ProvideFactory;
import com.pulse.music.ui.search.SearchViewModel;
import com.pulse.music.ui.search.SearchViewModel_HiltModules_KeyModule_ProvideFactory;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class DaggerPulseApplication_HiltComponents_SingletonC {
  private DaggerPulseApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public PulseApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements PulseApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public PulseApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements PulseApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public PulseApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements PulseApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public PulseApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements PulseApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public PulseApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements PulseApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public PulseApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements PulseApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public PulseApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements PulseApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public PulseApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends PulseApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends PulseApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends PulseApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends PulseApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
      injectMainActivity2(mainActivity);
    }

    @Override
    public void injectLockScreenActivity(LockScreenActivity lockScreenActivity) {
      injectLockScreenActivity2(lockScreenActivity);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return ImmutableSet.<String>of(AlbumViewModel_HiltModules_KeyModule_ProvideFactory.provide(), ArtistViewModel_HiltModules_KeyModule_ProvideFactory.provide(), AuthViewModel_HiltModules_KeyModule_ProvideFactory.provide(), HomeViewModel_HiltModules_KeyModule_ProvideFactory.provide(), JamViewModel_HiltModules_KeyModule_ProvideFactory.provide(), LibraryViewModel_HiltModules_KeyModule_ProvideFactory.provide(), NowPlayingViewModel_HiltModules_KeyModule_ProvideFactory.provide(), PlaylistViewModel_HiltModules_KeyModule_ProvideFactory.provide(), SearchViewModel_HiltModules_KeyModule_ProvideFactory.provide());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    private MainActivity injectMainActivity2(MainActivity instance) {
      MainActivity_MembersInjector.injectMusicPlayerManager(instance, singletonCImpl.musicPlayerManagerProvider.get());
      MainActivity_MembersInjector.injectAuthRepository(instance, singletonCImpl.authRepositoryProvider.get());
      return instance;
    }

    private LockScreenActivity injectLockScreenActivity2(LockScreenActivity instance) {
      LockScreenActivity_MembersInjector.injectMusicPlayerManager(instance, singletonCImpl.musicPlayerManagerProvider.get());
      return instance;
    }
  }

  private static final class ViewModelCImpl extends PulseApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AlbumViewModel> albumViewModelProvider;

    private Provider<ArtistViewModel> artistViewModelProvider;

    private Provider<AuthViewModel> authViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<JamViewModel> jamViewModelProvider;

    private Provider<LibraryViewModel> libraryViewModelProvider;

    private Provider<NowPlayingViewModel> nowPlayingViewModelProvider;

    private Provider<PlaylistViewModel> playlistViewModelProvider;

    private Provider<SearchViewModel> searchViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private FollowedArtistRepository followedArtistRepository() {
      return new FollowedArtistRepository(singletonCImpl.followedArtistDao(), singletonCImpl.providePulseApiServiceProvider.get());
    }

    private LocalMusicRepository localMusicRepository() {
      return new LocalMusicRepository(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));
    }

    private LikedSongsRepository likedSongsRepository() {
      return new LikedSongsRepository(singletonCImpl.likedSongDao(), singletonCImpl.providePulseApiServiceProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.albumViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.artistViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.authViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.jamViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.libraryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.nowPlayingViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.playlistViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.searchViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
    }

    @Override
    public Map<String, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return ImmutableMap.<String, javax.inject.Provider<ViewModel>>builderWithExpectedSize(9).put("com.pulse.music.ui.album.AlbumViewModel", ((Provider) albumViewModelProvider)).put("com.pulse.music.ui.artist.ArtistViewModel", ((Provider) artistViewModelProvider)).put("com.pulse.music.ui.auth.AuthViewModel", ((Provider) authViewModelProvider)).put("com.pulse.music.ui.home.HomeViewModel", ((Provider) homeViewModelProvider)).put("com.pulse.music.ui.jam.JamViewModel", ((Provider) jamViewModelProvider)).put("com.pulse.music.ui.library.LibraryViewModel", ((Provider) libraryViewModelProvider)).put("com.pulse.music.ui.nowplaying.NowPlayingViewModel", ((Provider) nowPlayingViewModelProvider)).put("com.pulse.music.ui.playlist.PlaylistViewModel", ((Provider) playlistViewModelProvider)).put("com.pulse.music.ui.search.SearchViewModel", ((Provider) searchViewModelProvider)).build();
    }

    @Override
    public Map<String, Object> getHiltViewModelAssistedMap() {
      return ImmutableMap.<String, Object>of();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.pulse.music.ui.album.AlbumViewModel 
          return (T) new AlbumViewModel(singletonCImpl.musicRepositoryProvider.get(), singletonCImpl.musicPlayerManagerProvider.get());

          case 1: // com.pulse.music.ui.artist.ArtistViewModel 
          return (T) new ArtistViewModel(singletonCImpl.musicRepositoryProvider.get(), viewModelCImpl.followedArtistRepository(), singletonCImpl.musicPlayerManagerProvider.get());

          case 2: // com.pulse.music.ui.auth.AuthViewModel 
          return (T) new AuthViewModel(singletonCImpl.authRepositoryProvider.get(), singletonCImpl.onlineMusicRepositoryProvider.get(), singletonCImpl.musicPlayerManagerProvider.get());

          case 3: // com.pulse.music.ui.home.HomeViewModel 
          return (T) new HomeViewModel(singletonCImpl.onlineMusicRepositoryProvider.get(), singletonCImpl.songDao(), singletonCImpl.musicPlayerManagerProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.authRepositoryProvider.get());

          case 4: // com.pulse.music.ui.jam.JamViewModel 
          return (T) new JamViewModel(singletonCImpl.musicPlayerManagerProvider.get(), singletonCImpl.authRepositoryProvider.get(), singletonCImpl.jamRepositoryProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 5: // com.pulse.music.ui.library.LibraryViewModel 
          return (T) new LibraryViewModel(viewModelCImpl.localMusicRepository(), singletonCImpl.musicPlayerManagerProvider.get(), viewModelCImpl.likedSongsRepository(), singletonCImpl.playlistRepositoryProvider.get(), viewModelCImpl.followedArtistRepository());

          case 6: // com.pulse.music.ui.nowplaying.NowPlayingViewModel 
          return (T) new NowPlayingViewModel(singletonCImpl.musicRepositoryProvider.get(), singletonCImpl.musicPlayerManagerProvider.get(), viewModelCImpl.likedSongsRepository(), singletonCImpl.playlistRepositoryProvider.get());

          case 7: // com.pulse.music.ui.playlist.PlaylistViewModel 
          return (T) new PlaylistViewModel(singletonCImpl.playlistRepositoryProvider.get(), viewModelCImpl.likedSongsRepository(), singletonCImpl.musicPlayerManagerProvider.get());

          case 8: // com.pulse.music.ui.search.SearchViewModel 
          return (T) new SearchViewModel(singletonCImpl.onlineMusicRepositoryProvider.get(), singletonCImpl.songDao(), singletonCImpl.playlistDao(), singletonCImpl.musicPlayerManagerProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends PulseApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends PulseApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }

    @Override
    public void injectPlaybackService(PlaybackService playbackService) {
      injectPlaybackService2(playbackService);
    }

    private PlaybackService injectPlaybackService2(PlaybackService instance) {
      PlaybackService_MembersInjector.injectMusicPlayerManager(instance, singletonCImpl.musicPlayerManagerProvider.get());
      return instance;
    }
  }

  private static final class SingletonCImpl extends PulseApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<OkHttpClient> provideOkHttpClientProvider;

    private Provider<Retrofit> provideRetrofitProvider;

    private Provider<PulseApiService> providePulseApiServiceProvider;

    private Provider<OnlineMusicRepository> onlineMusicRepositoryProvider;

    private Provider<MusicPlayerManager> musicPlayerManagerProvider;

    private Provider<AppDatabase> provideAppDatabaseProvider;

    private Provider<AuthRepository> authRepositoryProvider;

    private Provider<MusicRepository> musicRepositoryProvider;

    private Provider<JamRepository> jamRepositoryProvider;

    private Provider<PlaylistRepository> playlistRepositoryProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private SongDao songDao() {
      return DatabaseModule_ProvideSongDaoFactory.provideSongDao(provideAppDatabaseProvider.get());
    }

    private FollowedArtistDao followedArtistDao() {
      return DatabaseModule_ProvideFollowedArtistDaoFactory.provideFollowedArtistDao(provideAppDatabaseProvider.get());
    }

    private LikedSongDao likedSongDao() {
      return DatabaseModule_ProvideLikedSongDaoFactory.provideLikedSongDao(provideAppDatabaseProvider.get());
    }

    private PlaylistDao playlistDao() {
      return DatabaseModule_ProvidePlaylistDaoFactory.providePlaylistDao(provideAppDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 4));
      this.provideRetrofitProvider = DoubleCheck.provider(new SwitchingProvider<Retrofit>(singletonCImpl, 3));
      this.providePulseApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<PulseApiService>(singletonCImpl, 2));
      this.onlineMusicRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<OnlineMusicRepository>(singletonCImpl, 1));
      this.musicPlayerManagerProvider = DoubleCheck.provider(new SwitchingProvider<MusicPlayerManager>(singletonCImpl, 0));
      this.provideAppDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<AppDatabase>(singletonCImpl, 6));
      this.authRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AuthRepository>(singletonCImpl, 5));
      this.musicRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<MusicRepository>(singletonCImpl, 7));
      this.jamRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<JamRepository>(singletonCImpl, 8));
      this.playlistRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<PlaylistRepository>(singletonCImpl, 9));
    }

    @Override
    public void injectPulseApplication(PulseApplication pulseApplication) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.pulse.music.player.MusicPlayerManager 
          return (T) new MusicPlayerManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.onlineMusicRepositoryProvider.get());

          case 1: // com.pulse.music.data.repository.OnlineMusicRepository 
          return (T) new OnlineMusicRepository(singletonCImpl.providePulseApiServiceProvider.get());

          case 2: // com.pulse.music.data.network.PulseApiService 
          return (T) NetworkModule_ProvidePulseApiServiceFactory.providePulseApiService(singletonCImpl.provideRetrofitProvider.get());

          case 3: // retrofit2.Retrofit 
          return (T) NetworkModule_ProvideRetrofitFactory.provideRetrofit(singletonCImpl.provideOkHttpClientProvider.get());

          case 4: // okhttp3.OkHttpClient 
          return (T) NetworkModule_ProvideOkHttpClientFactory.provideOkHttpClient(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 5: // com.pulse.music.data.repository.AuthRepository 
          return (T) new AuthRepository(singletonCImpl.providePulseApiServiceProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideAppDatabaseProvider.get());

          case 6: // com.pulse.music.data.local.AppDatabase 
          return (T) DatabaseModule_ProvideAppDatabaseFactory.provideAppDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 7: // com.pulse.music.data.repository.MusicRepository 
          return (T) new MusicRepository(singletonCImpl.onlineMusicRepositoryProvider.get(), singletonCImpl.songDao(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 8: // com.pulse.music.data.repository.JamRepository 
          return (T) new JamRepository(singletonCImpl.providePulseApiServiceProvider.get());

          case 9: // com.pulse.music.data.repository.PlaylistRepository 
          return (T) new PlaylistRepository(singletonCImpl.playlistDao(), singletonCImpl.providePulseApiServiceProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
