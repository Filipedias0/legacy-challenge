package com.picpay.desafio.android.domain.repository

import com.picpay.desafio.android.data.entity.UserDTO
import com.picpay.desafio.android.domain.model.UserModel

interface UserRepository {
    suspend fun getUsersFromRemote(): Result<List<UserModel>>
    suspend fun insertContactListIntoDb(userDTOList: List<UserDTO>)
    suspend fun insertUserIntoDb(userDTO: UserDTO)
    suspend fun getContactListFromDb(): List<UserDTO>
    suspend fun deleteUserFromDb(userDTO: UserDTO)
}

