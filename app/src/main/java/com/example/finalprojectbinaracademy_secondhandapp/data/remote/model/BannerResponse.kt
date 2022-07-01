package com.example.finalprojectbinaracademy_secondhandapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class BannerResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image : String?
)
