package com.example.finalprojectbinaracademy_secondhandapp.data.remote.model


import com.google.gson.annotations.SerializedName

data class GetProductResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("data")
    val data: ArrayList<GetProductResponseItem>
)