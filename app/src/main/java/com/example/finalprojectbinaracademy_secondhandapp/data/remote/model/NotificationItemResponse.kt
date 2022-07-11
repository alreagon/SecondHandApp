package com.example.finalprojectbinaracademy_secondhandapp.data.remote.model


import com.google.gson.annotations.SerializedName

data class NotificationItemResponse(
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("bid_price")
    val bidPrice: Int,
    @SerializedName("buyer_name")
    val buyerName: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("Product")
    val product: ProductNotification?,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("read")
    val read: Boolean,
    @SerializedName("receiver_id")
    val receiverId: Int,
    @SerializedName("seller_name")
    val sellerName: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("transaction_date")
    val transactionDate: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("User")
    val user: User
)