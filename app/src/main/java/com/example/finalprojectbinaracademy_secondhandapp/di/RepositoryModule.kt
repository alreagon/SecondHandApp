package com.example.finalprojectbinaracademy_secondhandapp.di

import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.AuthRepository
import org.koin.dsl.module

val repoModule = module {
    single { AuthRepository(get()) }
}