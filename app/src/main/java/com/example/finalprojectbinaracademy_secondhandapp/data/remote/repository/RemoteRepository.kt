package com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository

import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiHelperImpl
import retrofit2.Response

class RemoteRepository(private val apiHelperImpl: ApiHelperImpl) {

    suspend fun registerUser(request: RegisterRequest): Response<RegisterResponse>{
        return apiHelperImpl.registerUser(request)
    }

}