package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.remote.UserService
import com.picpay.desafio.android.data.db.UserDAO
import com.picpay.desafio.android.domain.repository.UserRepository

class UserRepositoryImpl(
    private val api: UserService,
    private val db: UserDAO
): UserRepository {
    override suspend fun getUsersFromRemote(): Result<List<User>> {
        return try {
            val response = api.getUsers()
            Result.success(response)
        }catch (e: Exception){
            Result.failure(e)
        }

    }

    override suspend fun insertContactListIntoDb(userList: List<User>){
        db.insertContactList(userList)
    }

    override suspend fun insertUserIntoDb(user: User){
        db.insertUser(user)
    }

    override suspend fun getContactListFromDb(): List<User> {
        return db.getContacts()
    }

    override suspend fun deleteUserFromDb(user: User){
        db.deleteUser(user)
    }


}