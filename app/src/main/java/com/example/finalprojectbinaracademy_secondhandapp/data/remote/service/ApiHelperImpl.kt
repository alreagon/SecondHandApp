package com.example.finalprojectbinaracademy_secondhandapp.data.remote.service

import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) {

    suspend fun registerUser(request: RegisterRequest): Response<RegisterResponse> {
        return apiService.registerUser(request)
    }

    suspend fun loginUser(request: LoginRequest): Response<LoginResponse> {
        return apiService.loginUser(request)
    }

    suspend fun getUser(accessToken: String): Response<RegisterResponse> {
        return apiService.getUser(accessToken)
    }

    suspend fun updateProfile(
        accessToken: String,
        name: RequestBody,
        phone: RequestBody,
        address: RequestBody,
        city: RequestBody,
        image: MultipartBody.Part
    ): Response<RegisterResponse> {
        return apiService.updateUserProfile(accessToken, image, name, phone, address, city)
    }

    suspend fun getNotification(accessToken: String): Response<NotificationResponse> {
        return apiService.getNotification(accessToken)
    }

    suspend fun getBuyerProduct(): Response<GetProductResponse> {
        return apiService.getBuyerProduct()
    }

    suspend fun getBanner(): Response<BannerResponse> {
        return apiService.getBanner()
    }

    suspend fun getBuyerProductId(buyerId: Int): Response<GetResponseProductId> {
        return apiService.getBuyerProductId(buyerId)
    }
}