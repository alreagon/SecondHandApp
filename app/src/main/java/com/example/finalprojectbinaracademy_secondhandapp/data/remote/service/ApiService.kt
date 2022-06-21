package com.example.finalprojectbinaracademy_secondhandapp.data.remote.service

import android.media.Image
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.*
import retrofit2.Response
import retrofit2.http.*
import java.io.File

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
        @Body request: UpdateProfileRequest
    ) : Response<RegisterResponse>

}

