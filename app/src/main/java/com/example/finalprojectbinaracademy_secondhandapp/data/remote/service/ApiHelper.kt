package com.example.koinapp.data.api

import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.LoginRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.LoginResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterResponse
import retrofit2.Response

interface ApiHelper {

    suspend fun registerUser(request: RegisterRequest): Response<RegisterResponse>

    suspend fun loginUser(request: LoginRequest): Response<LoginResponse>
}