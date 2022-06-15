package com.example.finalprojectbinaracademy_secondhandapp.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiClient {
    const val BASE_URL = "https://market-final-project.herokuapp.com/"

    private val logging : HttpLoggingInterceptor
    get() {
        val httpLoggingInterceptor : HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val client = OkHttpClient.Builder().addInterceptor(logging).build()

    val instance : ApiService by lazy {

        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
//            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
        retrofit.create(ApiService::class.java)
    }
}