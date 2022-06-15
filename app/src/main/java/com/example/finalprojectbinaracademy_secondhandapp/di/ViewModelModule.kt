package com.example.finalprojectbinaracademy_secondhandapp.di

import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { RegisterViewModel(get(),get()) }
}