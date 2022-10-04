package com.picpay.desafio.android.domain.interactors

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.util.Resource

class GetUsers(private val userRepository: UserRepository) {

    suspend operator fun invoke(): Resource<List<User>> {

        val response = userRepository.getUsersFromRemote()
        when (
            response
        ) {
            is Resource.Succes -> {
                response.data?.let {
                    userRepository.insertContactListIntoDb(it)
                }
            }

            else -> {
                val userListFromDb = userRepository.getContactListFromDb()
                response.data = userListFromDb
            }
        }
        return response
    }
}