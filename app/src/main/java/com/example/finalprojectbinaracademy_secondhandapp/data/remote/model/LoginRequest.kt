package com.example.finalprojectbinaracademy_secondhandapp.data.remote.model


import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)