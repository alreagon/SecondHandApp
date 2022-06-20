package com.example.finalprojectbinaracademy_secondhandapp.data.remote.service

import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.LoginRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.LoginResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterResponse
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService){

    suspend fun registerUser(request: RegisterRequest): Response<RegisterResponse> {
        return apiService.registerUser(request)
    }

    suspend fun loginUser(request: LoginRequest): Response<LoginResponse> {
        return apiService.loginUser(request)
    }

}