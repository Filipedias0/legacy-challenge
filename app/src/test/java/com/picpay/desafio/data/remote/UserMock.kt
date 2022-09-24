package com.picpay.desafio.data.remote

import com.picpay.desafio.android.data.model.User
import okhttp3.mockwebserver.MockResponse

object UserMock {
    const val mockedBody =
        "[{\"id\":1,\"name\":\"name\",\"img\":\"img\",\"username\":\"userName\"}]"
    val listOfMockedUser = listOf(User("img", "name", "userName", 1))
}