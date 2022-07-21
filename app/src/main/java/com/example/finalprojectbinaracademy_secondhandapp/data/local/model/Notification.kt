package com.example.finalprojectbinaracademy_secondhandapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.ProductNotification
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.User
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.UserX
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

@Entity(tableName = "notifications")
data class Notification(
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("bid_price")
    val bidPrice: Int?,
    @SerializedName("buyer_name")
    val buyerName: String?,
    @SerializedName("createdAt")
    val createdAt: String,
    @PrimaryKey
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
    val transactionDate: String?,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("User")
    val user: User
)

class NotificationTypeConverter {
    @TypeConverter
    fun productToString(product: ProductNotification?) = Gson().toJson(product)

    @TypeConverter
    fun stringToProduct(string: String): ProductNotification = Gson().fromJson(string,ProductNotification::class.java)

    @TypeConverter
    fun userToString(user: User) = Gson().toJson(user)

    @TypeConverter
    fun stringToUser(string: String): User = Gson().fromJson(string,User::class.java)

    @TypeConverter
    fun userxToString(user: UserX) = Gson().toJson(user)

    @TypeConverter
    fun stringToUserX(string: String): UserX = Gson().fromJson(string,UserX::class.java)
}
