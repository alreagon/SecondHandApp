package com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository

import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.*
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiHelperImpl
import com.example.finalprojectbinaracademy_secondhandapp.utils.Resource
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
    ) : Response<RegisterResponse> {
        return apiHelperImpl.updateProfile(accessToken,name,phone,address,city,image)
    }

    suspend fun getNotification(accessToken: String): Response<NotificationResponse> {
        return apiHelperImpl.getNotification(accessToken)
    }

    suspend fun readNotification(accessToken: String, id: Int): Response<ReadNotificationResponse> {
        return apiHelperImpl.readNotification(accessToken, id)
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

    suspend fun getCategory() : Response<CategoryResponse> {
        return apiHelperImpl.getCategory()
    }

    suspend fun getCategoryById(id: Int) : Response<CategoryResponseItem> {
        return apiHelperImpl.getCategoryById(id)
    }

    suspend fun postProduct(
        accessToken: String,
        name: RequestBody,
        description: RequestBody,
        basePrice: RequestBody,
        categoryId: RequestBody,
        location: RequestBody,
        productImage: MultipartBody.Part
    ) : Response<PostProductResponse> {
        return apiHelperImpl.postProduct(accessToken, name, description, basePrice, categoryId, location, productImage)
    }

}