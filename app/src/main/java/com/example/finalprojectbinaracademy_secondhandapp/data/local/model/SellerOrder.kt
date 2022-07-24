package com.example.finalprojectbinaracademy_secondhandapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.ProductNotification
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.UserX
import com.google.gson.annotations.SerializedName

@Entity(tableName = "seller_orders")
data class SellerOrder(
    @SerializedName("base_price")
    val basePrice: String,
    @SerializedName("buyer_id")
    val buyerId: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_product")
    val imageProduct: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("Product")
    val product: ProductNotification?,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("transaction_date")
    val transactionDate: String?,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("User")
    val user: UserX
)
