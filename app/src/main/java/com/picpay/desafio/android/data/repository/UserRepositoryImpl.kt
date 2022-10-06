package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.entity.UserDTO
import com.picpay.desafio.android.data.remote.UserService
import com.picpay.desafio.android.data.db.UserDAO
import com.picpay.desafio.android.domain.model.UserModel
import com.picpay.desafio.android.domain.repository.UserRepository

class UserRepositoryImpl(
    private val api: UserService,
    private val db: UserDAO
): UserRepository {
    override suspend fun getUsersFromRemote(): Result<List<UserModel>> {
        return try {
            val response = api.getUsers()
            Result.success(response)
        }catch (e: Exception){
            Result.failure(e)
        }

    }

    override suspend fun insertContactListIntoDb(userDTOList: List<UserDTO>){
        db.insertContactList(userDTOList)
    }

    override suspend fun insertUserIntoDb(userDTO: UserDTO){
        db.insertUser(userDTO)
    }

    override suspend fun getContactListFromDb(): List<UserDTO> {
        return db.getContacts()
    }

    override suspend fun deleteUserFromDb(userDTO: UserDTO){
        db.deleteUser(userDTO)
    }


}