package net.insi8.wiki.api

import com.google.gson.Gson
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


var serviceModule : Module = module {

    single {
        Gson()
    }

    single<Converter.Factory> {
        GsonConverterFactory.create(get())
    }

    single<WikiServices> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/w/api.php/")
            .addConverterFactory(get())
            .build()
        retrofit.create(WikiServices::class.java)
    }
}