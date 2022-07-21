package com.example.finalprojectbinaracademy_secondhandapp

import android.app.Application
import com.example.finalprojectbinaracademy_secondhandapp.di.appModule
import com.example.finalprojectbinaracademy_secondhandapp.di.localDbModule
import com.example.finalprojectbinaracademy_secondhandapp.di.repoModule
import com.example.finalprojectbinaracademy_secondhandapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule, localDbModule, repoModule, viewModelModule)
        }
    }
}