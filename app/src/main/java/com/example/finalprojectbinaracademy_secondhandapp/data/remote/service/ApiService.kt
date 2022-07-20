package com.example.finalprojectbinaracademy_secondhandapp.data.remote.service

import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    //register
    @POST("auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    //login
    @POST("auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>

    //get detail user
    @GET("auth/user")
    suspend fun getUser(
        @Header("access_token") accessToken: String,
    ): Response<RegisterResponse>

    //update profile
    @Multipart
    @PUT("auth/user")
    suspend fun updateUserProfile(
        @Header("access_token") accessToken: String,
        @Part imageProfile: MultipartBody.Part,
        @Part("full_name") name: RequestBody,
        @Part("phone_number") phone: RequestBody,
        @Part("address") address: RequestBody,
        @Part("city") city: RequestBody
    ): Response<RegisterResponse>

    //change password
    @Multipart
    @PUT("auth/change-password")
    suspend fun changePassword(
        @Header("access_token") accessToken: String,
        @Part("current_password") currentPass: RequestBody,
        @Part("new_password") newPass: RequestBody,
        @Part("confirm_password") confirmPass: RequestBody
    ): Response<ChangePasswordResponse>

    //notification
    @GET("notification")
    suspend fun getNotification(
        @Header("access_token") accessToken: String,
    ): Response<NotificationResponse>

    //read notification
    @PATCH("notification/{id}")
    suspend fun readNotification(
        @Header("access_token") accessToken: String,
        @Path("id") idNotif: Int
    ): Response<ReadNotificationResponse>

    //Get product buyer
    @GET("buyer/product")
    suspend fun getBuyerProduct(
        @QueryMap parameters: HashMap<String, String>
    ): Response<List<GetProductResponseItem>>

    //Get product buyer SEARCH
    @GET("buyer/product")
    suspend fun getBuyerProductSearch(
        @QueryMap parameters: HashMap<String, String>,
        @Query("search") productName : String
    ): Response<List<GetProductResponseItem>>

    //Get product buyer {id}
    @GET("buyer/product/{id}")
    suspend fun getBuyerProductId(
        @Path("id")
        buyerId: Int
    ): Response<GetResponseProductId>

    //Get banner
    @GET("seller/banner")
    suspend fun getBanner(): Response<List<BannerResponse>>

    // get category
    @GET("seller/category")
    suspend fun getCategory(): Response<CategoryResponse>

    //get category by id
    @GET("seller/category/{id}")
    suspend fun getCategoryById(
        @Path("id") id: Int
    ): Response<CategoryResponseItem>

    //post product
    @Multipart
    @POST("seller/product")
    suspend fun sellerPostProduct(
        @Header("access_token") accessToken: String,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("base_price") basePrice: RequestBody,
        @Part("category_ids") categoryId: RequestBody,
        @Part("location") location: RequestBody,
        @Part productImage: MultipartBody.Part
    ): Response<PostProductResponse>

    //get seller product
    @GET("seller/product")
    suspend fun getSellerProduct(
        @Header("access_token") accessToken: String
    ): Response<List<GetProductResponseItem>>

    //get seller order from user
    @GET("seller/order")
    suspend fun getSellerOrder(
        @Header("access_token") accessToken: String,
        @Query("status") status: String?
    ): Response<GetSellerOrderResponse>

    //get seller order by id
    @GET("seller/order/{id}")
    suspend fun getSellerOrderId(
        @Header("access_token") accessToken: String,
        @Path("id") idOrder: Int
    ): Response<GetSellerOrderResponseItem>

    //seller accept or delete
    @Multipart
    @PATCH("seller/order/{id}")
    suspend fun patchOrder(
        @Header("access_token") accessToken: String,
        @Path("id") idOrder: Int,
        @Part("status") status: RequestBody
    ): Response<PatchOrderResponse>

    //Post buyer order
    @POST("buyer/order")
    suspend fun postBuyerOrder(
        @Header("access_token") accessToken: String,
        @Body request: PostBuyerOrderRequest
    ): Response<PostBuyerOrderResponse>

}

