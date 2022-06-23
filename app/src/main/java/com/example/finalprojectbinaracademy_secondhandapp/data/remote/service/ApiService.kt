package com.example.finalprojectbinaracademy_secondhandapp.data.remote.service

import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("auth/register")
    suspend fun registerUser(@Body request: RegisterRequest) : Response<RegisterResponse>

    @POST("auth/login")
    suspend fun loginUser(@Body request: LoginRequest) : Response<LoginResponse>

    @GET("auth/user")
    suspend fun getUser(
        @Header("access_token") accessToken: String,
    ) : Response<RegisterResponse>

    @Multipart
    @PUT("auth/user")
    suspend fun updateUserProfile(
        @Header("access_token") accessToken: String,
        @Part imageProfile : MultipartBody.Part,
        @Part("full_name") name: RequestBody,
        @Part("phone_number") phone: RequestBody,
        @Part("address") address: RequestBody,
        @Part("city") city: RequestBody
    ) : Response<RegisterResponse>

}

