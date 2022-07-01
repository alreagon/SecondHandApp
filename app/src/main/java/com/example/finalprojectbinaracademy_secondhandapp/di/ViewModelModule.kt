package com.example.finalprojectbinaracademy_secondhandapp.di

import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel(get(),get()) }
    viewModel { EditProfileViewModel(get(),get()) }
    viewModel { NotificationViewModel(get(),get()) }
    viewModel { SellViewModel(get(),get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel {BuyerDetailViewModel(get())}
}