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

    suspend fun changePassword(
        accessToken: String,
        currentPass: RequestBody,
        newPass: RequestBody,
        confirmPass: RequestBody
    ): Response<ChangePasswordResponse> {
        return apiHelperImpl.changePassword(accessToken, currentPass, newPass, confirmPass)
    }

    suspend fun getNotification(accessToken: String): Response<NotificationResponse> {
        return apiHelperImpl.getNotification(accessToken)
    }

    suspend fun readNotification(accessToken: String, id: Int): Response<ReadNotificationResponse> {
        return apiHelperImpl.readNotification(accessToken, id)
    }

    suspend fun getBuyerProduct(parameters: HashMap<String,String>): Response<GetProductResponse> {
        return apiHelperImpl.getBuyerProduct(parameters)
    }

    suspend fun getBanner(): Response <List<BannerResponse>> {
        return apiHelperImpl.getBanner()
    }

    suspend fun getBuyerProductId(buyerId: Int): Response<GetResponseProductId> {
        return apiHelperImpl.getBuyerProductId(buyerId)
    }

    suspend fun getCategory(): Response<CategoryResponse> {
        return apiHelperImpl.getCategory()
    }

    suspend fun getCategoryById(id: Int): Response<CategoryResponseItem> {
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
    ): Response<PostProductResponse> {
        return apiHelperImpl.postProduct(
            accessToken,
            name,
            description,
            basePrice,
            categoryId,
            location,
            productImage
        )
    }

    suspend fun getSellerProduct(accessToken: String): Response<List<GetProductResponseItem>> {
        return apiHelperImpl.sellerGetProduct(accessToken)
    }

    suspend fun getSellerOrder(
        accessToken: String,
        status: String?
    ): Response<GetSellerOrderResponse> {
        return apiHelperImpl.getSellerOrder(accessToken, status)
    }

    suspend fun getSellerOrderId(
        accessToken: String,
        idOrder: Int
    ): Response<GetSellerOrderResponseItem> {
        return apiHelperImpl.getSellerOrderId(accessToken, idOrder)
    }

    suspend fun patchOrder(
        accessToken: String,
        idOrder: Int,
        status: RequestBody
    ): Response<PatchOrderResponse> {
        return apiHelperImpl.patchOrder(accessToken, idOrder, status)
    }
    suspend fun postBuyerOrder(
        accessToken: String,
        request: PostBuyerOrderRequest
    ) : Response<PostBuyerOrderResponse>{
        return apiHelperImpl.postBuyerOrder(accessToken, request)
    }

}