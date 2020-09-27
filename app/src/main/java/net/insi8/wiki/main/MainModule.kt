package net.insi8.wiki.main

import net.insi8.wiki.repo.WikiRepository
import net.insi8.wiki.repo.WikiRepositoryImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

// declared ViewModel
val mainModule : Module = module {

    factory<WikiRepository> { WikiRepositoryImpl(wikiServices = get()) }

    viewModel {
        MainViewModel(
            application = get(),
            wikiRepository = get()
        )
    }

}