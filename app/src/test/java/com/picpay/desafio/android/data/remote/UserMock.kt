package com.picpay.desafio.android.data.remote

import com.picpay.desafio.android.data.entity.UserDTO
import com.picpay.desafio.android.data.entity.mapper.toModelList

object UserMock {
    const val mockedBody =
        "[{\"id\":1,\"name\":\"name\",\"img\":\"img\",\"username\":\"userName\"}]"
    val listOfMockedUserDTO = listOf(UserDTO("img", "name", "userName", 1))
    val listOfMockedUserModel = listOfMockedUserDTO.toModelList()
}