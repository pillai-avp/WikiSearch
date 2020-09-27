package net.insi8.wiki

import android.app.Application
import net.insi8.wiki.api.serviceModule
import net.insi8.wiki.main.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WikiApplication : Application(){
    override fun onCreate(){
        super.onCreate()

        // start Koin dependency injection framework!

        startKoin {
            // declare used Android context
            androidContext(this@WikiApplication)

            modules(
                serviceModule,
                mainModule
            )
        }
    }
}