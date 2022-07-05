package com.example.finalprojectbinaracademy_secondhandapp.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.File

@Parcelize
data class RequestPostProduct(
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("category_ids")
    val categoryIds: ArrayList<Int>,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: File,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String
): Parcelable