package com.example.callaguy_professional.di

import org.koin.android.ext.koin.androidContext
import android.app.Application
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(appModule)
        }
    }
}