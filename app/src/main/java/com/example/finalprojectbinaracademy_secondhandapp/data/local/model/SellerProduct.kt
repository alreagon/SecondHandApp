package com.example.finalprojectbinaracademy_secondhandapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.Category
import com.google.gson.annotations.SerializedName

@Entity(tableName = "seller_products")
data class SellerProduct(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("Categories")
    val categories: List<Category>
)