package com.example.finalprojectbinaracademy_secondhandapp.data.remote.service

import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.*
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService){

    suspend fun registerUser(request: RegisterRequest): Response<RegisterResponse> {
        return apiService.registerUser(request)
    }

    suspend fun loginUser(request: LoginRequest): Response<LoginResponse> {
        return apiService.loginUser(request)
    }

    suspend fun getUser(accessToken: String) : Response<RegisterResponse> {
        return apiService.getUser(accessToken)
    }

    suspend fun updateProfile(accessToken: String, request: UpdateProfileRequest) : Response<RegisterResponse> {
        return apiService.updateUserProfile(accessToken,request)
    }

}