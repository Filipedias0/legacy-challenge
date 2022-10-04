package com.picpay.desafio.android.data.remote

import com.picpay.desafio.android.data.model.User

object UserMock {
    const val mockedBody =
        "[{\"id\":1,\"name\":\"name\",\"img\":\"img\",\"username\":\"userName\"}]"
    val listOfMockedUser = listOf(User("img", "name", "userName", 1))
}