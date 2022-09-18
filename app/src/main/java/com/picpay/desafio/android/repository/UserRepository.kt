package com.picpay.desafio.android.repository

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.util.Resource

interface UserRepository {
    suspend fun getUsersFromRemote(): Resource<List<User>>
    suspend fun insertContactListIntoDb(userList: List<User>)
    suspend fun insertUserIntoDb(user: User)
    suspend fun getContactListFromDb(): List<User>
    suspend fun deleteUserFromDb(user: User)

}

