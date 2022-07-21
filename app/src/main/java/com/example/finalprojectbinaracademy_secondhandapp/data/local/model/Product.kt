package com.example.finalprojectbinaracademy_secondhandapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.Category
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.ProductNotification
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.User
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

@Entity(tableName = "products")
data class Product (
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

class CategoryTypeConverter {
    @TypeConverter
    fun listToJson(value: List<Category>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value,Array<Category>::class.java).toList()
}
