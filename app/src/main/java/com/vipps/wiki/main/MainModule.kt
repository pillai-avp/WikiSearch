package com.vipps.wiki.main

import com.vipps.wiki.repo.ContentSearch
import com.vipps.wiki.repo.WikiRepository
import com.vipps.wiki.repo.WikiRepositoryImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.koinApplication
import org.koin.dsl.module

// declared ViewModel
val mainModule : Module = module {

    factory<WikiRepository> { WikiRepositoryImpl(wikiServices = get())}
    factory { ContentSearch() }

    viewModel {
        MainViewModel(
            application = get(),
            wikiRepository = get()
        )
    }

}