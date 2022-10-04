package com.picpay.desafio.android.domain.repository

import com.picpay.desafio.android.data.model.User

interface UserRepository {
    suspend fun getUsersFromRemote(): Result<List<User>>
    suspend fun insertContactListIntoDb(userList: List<User>)
    suspend fun insertUserIntoDb(user: User)
    suspend fun getContactListFromDb(): List<User>
    suspend fun deleteUserFromDb(user: User)
}

