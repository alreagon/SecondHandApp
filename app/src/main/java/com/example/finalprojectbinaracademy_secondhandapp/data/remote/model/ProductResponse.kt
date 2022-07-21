package com.example.finalprojectbinaracademy_secondhandapp.data.remote.model

import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.Product
import com.google.gson.annotations.SerializedName

data class ProductResponse (
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("data")
    val data: List<Product>
)