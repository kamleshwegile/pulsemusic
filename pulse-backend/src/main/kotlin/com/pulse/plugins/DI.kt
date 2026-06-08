package com.pulse.plugins

import com.pulse.providers.*
import com.pulse.repository.*
import org.koin.dsl.module

val appModule = module {
    single { CacheRepository() }
    
    // Providers
    single { ListenFreeProvider() }
    single { MusicBrainzProvider() }
    single { LRCLIBProvider() }
    single { LastFmProvider() }
    single { GeniusProvider() }
    single { CoverArtArchiveProvider() }
    single { TheAudioDBProvider() }
    single { LyricsOvhProvider() }
    
    single { 
        ProviderManager(
            listOf(
                get<ListenFreeProvider>(),
                get<MusicBrainzProvider>(),
                get<LRCLIBProvider>(),
                get<LastFmProvider>(),
                get<GeniusProvider>(),
                get<CoverArtArchiveProvider>(),
                get<TheAudioDBProvider>(),
                get<LyricsOvhProvider>()
            ),
            get()
        ) 
    }
}
