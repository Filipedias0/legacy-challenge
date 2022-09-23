package com.picpay.desafio.android.domain.interactors

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.domain.repository.UserRepository

class InsertContactListIntoDb(private val userRepository: UserRepository, ) {

    suspend operator fun invoke(userList: List<User>) {
        userRepository.insertContactListIntoDb(userList)
    }
}