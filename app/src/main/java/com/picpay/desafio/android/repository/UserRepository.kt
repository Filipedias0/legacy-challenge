package com.picpay.desafio.android.repository

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.util.Resource

interface UserRepository {
    suspend fun getUsers(): Resource<List<User>>
}

