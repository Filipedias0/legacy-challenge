package com.picpay.desafio.android.utils

import com.picpay.desafio.android.data.model.User
import okhttp3.mockwebserver.MockResponse

object UserMock {

    const val serverPort = 8080
    const val body =
        "[{\"id\":1,\"name\":\"name\",\"img\":\"img\",\"username\":\"userName\"}]"

    val listOfMockedUser = listOf(User("img", "name", "userName", 1), (User("img2", "name2", "userName2", 2)))
    val mockedUser = User("img", "name", "userName", 1)

    val successResponse by lazy {
        MockResponse()
            .setResponseCode(200)
            .setBody(body)
    }

    val errorResponse by lazy { MockResponse().setResponseCode(404) }

}