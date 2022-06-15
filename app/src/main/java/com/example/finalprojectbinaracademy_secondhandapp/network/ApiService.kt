package com.example.finalprojectbinaracademy_secondhandapp.network

import com.example.finalprojectbinaracademy_secondhandapp.model.login.GetUserItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // Login Service
    @GET("user")
    fun getUser(@Query("email") email: String): Call<List<GetUserItem>>

}