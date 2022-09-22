package com.picpay.desafio.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_table")
data class User(
    @SerializedName("img") val img: String,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @PrimaryKey(autoGenerate = true)
@SerializedName("id") val id: Int? = null
)