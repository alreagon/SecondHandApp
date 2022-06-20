package com.example.finalprojectbinaracademy_secondhandapp.api

import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.LoginRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface UserApi {
    //login service
    @POST("auth/login")
    @FormUrlEncoded
    fun postUser(
        @Field("email") email : String,
        @Field("password") password : String
    ) : Call<LoginResponse>
}