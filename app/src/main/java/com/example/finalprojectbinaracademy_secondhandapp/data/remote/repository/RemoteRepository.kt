package com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository

import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.*
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiHelperImpl
import retrofit2.Response

class RemoteRepository(private val apiHelperImpl: ApiHelperImpl) {

    suspend fun registerUser(request: RegisterRequest): Response<RegisterResponse>{
        return apiHelperImpl.registerUser(request)
    }

    suspend fun loginUser(request: LoginRequest) : Response<LoginResponse> {
        return apiHelperImpl.loginUser(request)
    }

    suspend fun getUser(accessToken: String) : Response<RegisterResponse> {
        return apiHelperImpl.getUser(accessToken)
    }

    suspend fun updateProfile(accessToken: String,request: UpdateProfileRequest) : Response<RegisterResponse> {
        return apiHelperImpl.updateProfile(accessToken,request)
    }

}