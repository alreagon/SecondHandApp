package com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository

import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.*
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiHelperImpl
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class RemoteRepository(private val apiHelperImpl: ApiHelperImpl) {

    suspend fun registerUser(request: RegisterRequest): Response<RegisterResponse> {
        return apiHelperImpl.registerUser(request)
    }

    suspend fun loginUser(request: LoginRequest): Response<LoginResponse> {
        return apiHelperImpl.loginUser(request)
    }

    suspend fun getUser(accessToken: String): Response<RegisterResponse> {
        return apiHelperImpl.getUser(accessToken)
    }

    suspend fun updateProfile(
        accessToken: String,
        name: RequestBody,
        phone: RequestBody,
        address: RequestBody,
        city: RequestBody,
        image: MultipartBody.Part
    ): Response<RegisterResponse> {
        return apiHelperImpl.updateProfile(accessToken, name, phone, address, city, image)
    }

    suspend fun getNotification(accessToken: String): Response<NotificationResponse> {
        return apiHelperImpl.getNotification(accessToken)
    }

    suspend fun getBuyerProduct(): Response<GetProductResponse> {
        return apiHelperImpl.getBuyerProduct()
    }

    suspend fun getBanner(accessToken: String): Response<BannerResponse> {
        return apiHelperImpl.getBanner(accessToken)
    }

    suspend fun getBuyerProductId(buyerId: Int): Response<GetResponseProductId> {
        return apiHelperImpl.getBuyerProductId(buyerId)
    }

}