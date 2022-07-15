package com.example.finalprojectbinaracademy_secondhandapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class PostBuyerOrderRequest(
    @SerializedName("product_id")
    val product_id: String,
    val bid_price : String
)