package com.picpay.desafio.android.repository

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.remote.UserService
import com.picpay.desafio.android.util.Resource

class UserRepositoryImpl(
    private val api: UserService
): UserRepository {
    override fun getUsers(): Resource<List<User>> {
        val response = try {
            api.getUsers()
        }catch (e: Exception){
            return Resource.Error("An error has ocurred.$e")
        }
        return Resource.Succes(response)
    }
}