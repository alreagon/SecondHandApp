package com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository

import android.util.Log
import androidx.paging.*
import com.example.finalprojectbinaracademy_secondhandapp.data.local.db.LocalDaoHelperImpl
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.*
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.Product
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.*
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiHelperImpl
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class RemoteRepository(
    private val apiHelperImpl: ApiHelperImpl,
    private val localDaoHelperImpl: LocalDaoHelperImpl
) {

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

    suspend fun getNotification(accessToken: String): List<Notification> {
        val response = apiHelperImpl.getNotification(accessToken)
        Log.d("1244",response.toString())
        localDaoHelperImpl.deleteInsertNotif(response)
        return response
    }

    fun getNotifOffline(): List<Notification> {
        return localDaoHelperImpl.getAllNotification()
    }

    suspend fun readNotification(accessToken: String, id: Int): Response<ReadNotificationResponse> {
        return apiHelperImpl.readNotification(accessToken, id)
    }

    suspend fun getBuyerProduct(page: Int): Response<List<GetProductResponseItem>> {
        val parameters = HashMap<String,String>()
        parameters["page"] = page.toString()
        parameters["per_page"] = "20"
        return apiHelperImpl.getBuyerProduct(parameters)
    }

    suspend fun getBuyerProductSearch(parameters: HashMap<String,String>): Response<List<GetProductResponseItem>> {
        return apiHelperImpl.getBuyerProduct(parameters)
    }

    suspend fun getBanner(): Response<List<Banner>> {
        val response = apiHelperImpl.getBanner()
        response.body()?.let {
            localDaoHelperImpl.deleteInsertBanner(it)
        }
        return apiHelperImpl.getBanner()
    }

    fun getBannerOffline(): List<Banner> {
        return localDaoHelperImpl.getALlBanner()
    }

    fun getProductPaging() = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {ProductPageSource(apiHelperImpl)}
    ).liveData

    suspend fun getProductBoundResource(params: HashMap<String,String>): List<Product> {
        val response = apiHelperImpl.getProducBoundResource(params)
        localDaoHelperImpl.deleteAndInsertData(response.take(40))
        return response
    }

//    fun getProductBoundResource() = networkBoundResource(
//        query = { productDao.getAllProducts() },
//        fetch = {
//            val params = HashMap<String,String>()
//            params["page"] = "1"
//            params["per_page"] = "11"
//            delay(2000)
//            Log.d("GETTTTT","MASOOOKKK")
//            apiHelperImpl.getProducBoundResource(params)
//        },
//        saveFetchResult = {
//            db.withTransaction {
//                productDao.deleteAllProduct()
//                productDao.insertProduct(it.data)
//                Log.d("SAVEEEE",it.data.size.toString())
//            }
//        }
//    )

    fun getProductOffline(): List<Product> {
        return localDaoHelperImpl.getAllProduct()
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

    suspend fun patchStatusProduct(
        accessToken: String,
        idOrder: Int,
        status: RequestBody
    ): Response<PostProductResponse> {
        return apiHelperImpl.patchStatusProduct(accessToken, idOrder, status)
    }

    suspend fun getSellerProduct(accessToken: String): Response<List<SellerProduct>> {
        val response = apiHelperImpl.sellerGetProduct(accessToken)
        response.body()?.let {
            localDaoHelperImpl.deleteAndInsertDataSeller(it)
        }
        return response
    }

    fun getSellerProductOffline(): List<SellerProduct> {
        return localDaoHelperImpl.getAllProductSeller()
    }

    suspend fun getSellerOrder(
        accessToken: String,
        status: String
    ): Response<List<SellerOrder>> {
        val response = apiHelperImpl.getSellerOrder(accessToken,status)
        if (response.isSuccessful) {
            response.body()?.let {
                localDaoHelperImpl.deleteAndInsertOrderSeller(it)
            }
        }
        return response
    }

    fun getSellerOrderOffline(): List<SellerOrder> {
        return localDaoHelperImpl.getAllOrderSeller()
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