package com.example.finalprojectbinaracademy_secondhandapp.di

import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import org.koin.dsl.module

val repoModule = module {
    single { RemoteRepository(get(),get()) }
}