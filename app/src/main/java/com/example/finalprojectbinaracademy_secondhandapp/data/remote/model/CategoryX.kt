package com.example.finalprojectbinaracademy_secondhandapp.data.remote.model


import com.google.gson.annotations.SerializedName

data class CategoryX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)