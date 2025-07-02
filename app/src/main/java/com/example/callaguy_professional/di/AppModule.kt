package com.example.callaguy_professional.di

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.callaguy_professional.core.data.HttpClientFactory
import com.example.callaguy_professional.core.auth.SharedPrefTokenProvider
import com.example.callaguy_professional.core.auth.TokenProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    //Shared Preferences
    single<SharedPreferences> {
        androidContext().getSharedPreferences("prefs", MODE_PRIVATE)
    }

    //Token Provider
    single<TokenProvider> {
        SharedPrefTokenProvider(get())
    }

    //HttpClient
    single { HttpClientFactory.create(get()) }
}