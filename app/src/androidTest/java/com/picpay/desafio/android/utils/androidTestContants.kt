package com.picpay.desafio.android.utils

import com.picpay.desafio.android.data.model.User
import okhttp3.mockwebserver.MockResponse

object androidTestContants {

    const val serverPort = 8080
    const val body =
        "[{\"id\":1,\"name\":\"name\",\"img\":\"img\",\"username\":\"userName\"}]"
    val userReponseData = User("img", "name", "userName", 1)

    val successResponse by lazy {
        MockResponse()
            .setResponseCode(200)
            .setBody(body)
    }

    val errorResponse by lazy { MockResponse().setResponseCode(404) }

}