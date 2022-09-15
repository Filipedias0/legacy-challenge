package com.picpay.desafio.android.data.remote

import com.picpay.desafio.android.data.model.User
import retrofit2.http.GET


interface UserService {

    @GET("users")
    suspend fun getUsers(): List<User>
}