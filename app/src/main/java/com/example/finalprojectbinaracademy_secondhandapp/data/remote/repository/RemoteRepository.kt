package com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository

import androidx.room.withTransaction
import com.example.finalprojectbinaracademy_secondhandapp.data.local.ProductDao
import com.example.finalprojectbinaracademy_secondhandapp.data.local.ProductRoomDatabase
import com.example.finalprojectbinaracademy_secondhandapp.data.local.Resource
import com.example.finalprojectbinaracademy_secondhandapp.data.local.networkBoundResource
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.*
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiHelperImpl
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
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

    suspend fun getBanner(): Response<BannerResponse> {
        return apiHelperImpl.getBanner()
    }

    suspend fun getBuyerProductId(buyerId: Int): Response<GetResponseProductId> {
        return apiHelperImpl.getBuyerProductId(buyerId)
    }

}
