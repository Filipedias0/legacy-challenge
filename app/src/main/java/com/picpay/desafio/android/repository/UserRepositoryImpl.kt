package com.picpay.desafio.android.repository

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.remote.UserService
import com.picpay.desafio.android.db.UserDAO
import com.picpay.desafio.android.util.Resource

class UserRepositoryImpl(
    private val api: UserService,
    private val db: UserDAO
): UserRepository {
    override suspend fun getUsersFromRemote(): Resource<List<User>> {
        val response = try {
            api.getUsers()
        }catch (e: Exception){
            return Resource.Error("An error has ocurred.$e")
        }
        return Resource.Succes(response)
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