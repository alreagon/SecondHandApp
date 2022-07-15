package com.example.finalprojectbinaracademy_secondhandapp.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BannerResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("image_url")
    val image_url: String?,
) : Parcelable
