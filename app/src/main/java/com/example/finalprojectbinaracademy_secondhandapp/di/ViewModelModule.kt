package com.example.finalprojectbinaracademy_secondhandapp.di

import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel(get(),get(),get()) }
    viewModel { EditProfileViewModel(get(),get(),get()) }
    viewModel { NotificationViewModel(get(),get(),get()) }
    viewModel { SellViewModel(get(),get(),get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { BuyerDetailViewModel(get(), get(),get()) }
    viewModel { SaleListViewModel(get(),get(),get()) }
}