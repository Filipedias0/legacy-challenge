package com.picpay.desafio.android.domain.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("img") val img: String,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("id") val id: Int? = null
)
