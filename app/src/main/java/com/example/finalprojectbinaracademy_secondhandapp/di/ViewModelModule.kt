package com.example.finalprojectbinaracademy_secondhandapp.di

import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.AuthViewModel
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.EditProfileViewModel
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.HomeViewModel
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.NotificationViewModel
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.SellViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel(get(),get()) }
    viewModel { EditProfileViewModel(get(),get()) }
    viewModel { NotificationViewModel(get(),get()) }
    viewModel { SellViewModel(get(),get()) }
    viewModel { HomeViewModel(get(), get()) }
}