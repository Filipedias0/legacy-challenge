package com.picpay.desafio.android.domain.interactors

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.util.Resource

class GetUsersFromRemote(private val userRepository: UserRepository) {

    suspend operator fun invoke(): Resource<List<User>> {
        return userRepository.getUsersFromRemote()
    }
}