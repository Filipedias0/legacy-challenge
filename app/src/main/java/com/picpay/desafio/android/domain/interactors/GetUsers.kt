package com.picpay.desafio.android.domain.interactors

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.domain.repository.UserRepository

class GetUsers(private val userRepository: UserRepository) {

    suspend operator fun invoke(): Result<List<User>> {

        val response = userRepository.getUsersFromRemote()

        response.onSuccess {
            userRepository.insertContactListIntoDb(it)
        }

        if (response.isSuccess) {
            return response
        } else {
            return response.onFailure {
                val userListFromDb = userRepository.getContactListFromDb()

                return if (userListFromDb.isNotEmpty()) {
                    Result.success(userListFromDb)
                } else {
                    Result.failure(it)
                }
            }
        }
    }
}