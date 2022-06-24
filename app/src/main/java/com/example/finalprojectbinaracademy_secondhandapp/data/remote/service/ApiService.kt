package com.example.finalprojectbinaracademy_secondhandapp.data.remote.service

import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    //register
    @POST("auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    //login
    @POST("auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>

    //get detail user
    @GET("auth/user")
    suspend fun getUser(
        @Header("access_token") accessToken: String,
    ): Response<RegisterResponse>

    //update profile
    @Multipart
    @PUT("auth/user")
    suspend fun updateUserProfile(
        @Header("access_token") accessToken: String,
        @Part imageProfile: MultipartBody.Part,
        @Part("full_name") name: RequestBody,
        @Part("phone_number") phone: RequestBody,
        @Part("address") address: RequestBody,
        @Part("city") city: RequestBody
    ): Response<RegisterResponse>

    //notification
    @GET("notification")
    suspend fun getNotification(
        @Header("access_token") accessToken: String,
    ): Response<NotificationResponse>

    //Get produck buyer
    @GET("buyer/product/{id}")
    suspend fun getBuyerProduct(@Path("id") id : Int): Response<GetProductResponseItem>

}

