package com.picpay.desafio.android.domain.interactors

import com.picpay.desafio.android.data.entity.mapper.toDTOList
import com.picpay.desafio.android.data.entity.mapper.toModelList
import com.picpay.desafio.android.domain.model.UserModel
import com.picpay.desafio.android.domain.repository.UserRepository

class GetUsers(private val userRepository: UserRepository) {

    suspend operator fun invoke(): Result<List<UserModel>> {

        val response = userRepository.getUsersFromRemote()

        response.onSuccess {
            userRepository.insertContactListIntoDb(it.toDTOList())
        }

        if (response.isSuccess) {
            return response
        } else {
            return response.onFailure {
                val userListFromDb = userRepository.getContactListFromDb()

                return if (userListFromDb.isNotEmpty()) {
                    Result.success(userListFromDb.toModelList())
                } else {
                    Result.failure(it)
                }
            }
        }
    }
}