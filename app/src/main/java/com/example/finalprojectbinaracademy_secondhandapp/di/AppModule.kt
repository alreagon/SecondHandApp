package com.example.finalprojectbinaracademy_secondhandapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.finalprojectbinaracademy_secondhandapp.BuildConfig
import com.example.finalprojectbinaracademy_secondhandapp.data.local.ProductRoomDatabase
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiHelperImpl
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiService
import com.example.finalprojectbinaracademy_secondhandapp.utils.NetworkHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single { provideNetworkHelper(androidContext()) }
    single { provideOkHttpClient() }
    single { provideRetrofit(get(),BuildConfig.BASE_URL) }
    single { provideApiService(get()) }
    single { ApiHelperImpl(get()) }
    single { DataStoreManager(androidContext()) }
    single { provideProductDatabase(get()) }
}

private fun provideProductDatabase(app :Application) : ProductRoomDatabase =
    Room.databaseBuilder(app, ProductRoomDatabase::class.java, "product_database")
        .build()

private fun provideNetworkHelper(context: Context) = NetworkHelper(context)

private fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient.Builder()
        .connectTimeout(1,TimeUnit.MINUTES)
        .writeTimeout(1,TimeUnit.MINUTES)
        .readTimeout(1,TimeUnit.MINUTES)
        .addInterceptor(loggingInterceptor)
        .build()
} else OkHttpClient
    .Builder()
    .connectTimeout(1,TimeUnit.MINUTES)
    .writeTimeout(1,TimeUnit.MINUTES)
    .readTimeout(1,TimeUnit.MINUTES)
    .build()

private fun provideRetrofit (
    okHttpClient: OkHttpClient,
    BASE_URl: String
) : Retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URl)
        .client(okHttpClient)
        .build()

private fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


