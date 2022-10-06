package com.picpay.desafio.android.utils.dataMock

import com.picpay.desafio.android.data.entity.UserDTO
import com.picpay.desafio.android.data.entity.mapper.toModelList

object UserMock {

    val listOfMockedUserDTO = listOf(UserDTO("img", "name", "userName", 1), (UserDTO("img2", "name2", "userName2", 2)))
    val listOfMockedUserModel = listOfMockedUserDTO.toModelList()
    val mockedUser = UserDTO("img", "name", "userName", 1)

}