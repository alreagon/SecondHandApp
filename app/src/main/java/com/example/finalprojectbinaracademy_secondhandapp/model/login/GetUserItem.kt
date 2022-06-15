package com.example.finalprojectbinaracademy_secondhandapp.model.login

import com.google.gson.annotations.SerializedName

data class GetUserItem(
    val id: Long,

    @SerializedName("full_name")
    val fullName: String,

    val email: String,

    val password: String,

    @SerializedName("phone_number")
    val phoneNumber: Long,

    val address: String,

    @SerializedName("image_url")
    val imageURL: Any? = null,

    val createdAt: String,

    val updatedAt: String
)

