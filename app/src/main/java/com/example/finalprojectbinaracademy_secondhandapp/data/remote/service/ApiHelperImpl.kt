package com.example.finalprojectbinaracademy_secondhandapp.data.remote.service

import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.*
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.Product
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.*
import com.example.finalprojectbinaracademy_secondhandapp.utils.Resource
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
        phone:RequestBody,
        address:RequestBody,
        city:RequestBody,
        image: MultipartBody.Part
    ): Response<RegisterResponse> {
        return apiService.updateUserProfile(accessToken, image, name, phone, address, city)
    }

    suspend fun changePassword(
        accessToken: String,
        currentPass : RequestBody,
        newPass : RequestBody,
        confirmPass: RequestBody
    ) : Response<ChangePasswordResponse> {
        return apiService.changePassword(accessToken, currentPass, newPass, confirmPass)
    }

    suspend fun getNotification(accessToken: String): List<Notification> {
        return apiService.getNotification(accessToken)
    }

    suspend fun readNotification(accessToken: String, id: Int): Response<ReadNotificationResponse> {
        return apiService.readNotification(accessToken, id)
    }

    suspend fun getBuyerProduct(parameters: HashMap<String,String>): Response<List<GetProductResponseItem>> {
        return apiService.getBuyerProduct(parameters)
    }

    suspend fun getProducBoundResource(parameters: HashMap<String, String>) : List<Product> {
        return apiService.getProductBoundResource(parameters)
    }

    suspend fun getBanner(): Response<List<Banner>> {
        return apiService.getBanner()
    }

    suspend fun getBuyerProductId(buyerId: Int): Response<GetResponseProductId> {
        return apiService.getBuyerProductId(buyerId)
    }

    suspend fun getCategory() : Response<CategoryResponse> {
        return apiService.getCategory()
    }

    suspend fun getCategoryById(id :Int) : Response<CategoryResponseItem> {
        return apiService.getCategoryById(id)
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
        return apiService.sellerPostProduct(accessToken,name, description, basePrice, categoryId, location, productImage)
    }

    suspend fun patchStatusProduct(
        accessToken: String,
        idOrder: Int,
        status: RequestBody
    ): Response<PostProductResponse> {
        return apiService.patchStatusProduct(accessToken, idOrder, status)
    }

    suspend fun sellerGetProduct(accessToken: String): Response<List<SellerProduct>> {
        return apiService.getSellerProduct(accessToken)
    }

    suspend fun getSellerOrder(accessToken: String, status: String): Response<List<SellerOrder>> {
        return apiService.getSellerOrder(accessToken,status)
    }

    suspend fun getSellerOrderId(accessToken: String,idOrder: Int): Response<GetSellerOrderResponseItem> {
        return apiService.getSellerOrderId(accessToken,idOrder)
    }

    suspend fun patchOrder(accessToken: String,idOrder: Int,status: RequestBody): Response<PatchOrderResponse> {
        return apiService.patchOrder(accessToken, idOrder, status)
    }

    suspend fun postBuyerOrder(accessToken: String, request: PostBuyerOrderRequest ) : Response<PostBuyerOrderResponse>{
        return apiService.postBuyerOrder(accessToken, request )
    }
}