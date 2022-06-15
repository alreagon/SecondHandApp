package com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository

import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterResponse
import com.example.koinapp.data.api.ApiHelper
import retrofit2.Response

class AuthRepository(private val apiHelper: ApiHelper) {

    suspend fun registerUser(request: RegisterRequest): Response<RegisterResponse>{
        return apiHelper.registerUser(request)
    }

}