package com.example.finalprojectbinaracademy_secondhandapp.data.remote.model

import android.provider.MediaStore
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import java.io.File

data class UpdateProfileRequest(
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("image")
    val image: File?,
    @SerializedName("phone_number")
    val phoneNumber: Long
)
