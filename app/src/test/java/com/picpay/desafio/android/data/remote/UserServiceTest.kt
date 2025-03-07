package com.picpay.desafio.android.data.remote

import com.google.gson.GsonBuilder
import com.picpay.desafio.android.data.remote.UserMock.listOfMockedUserDTO
import com.picpay.desafio.android.data.remote.UserMock.listOfMockedUserModel
import com.picpay.desafio.android.data.remote.UserMock.mockedBody
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserServiceTest {
    private lateinit var service: UserService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        val gson = GsonBuilder().create()

        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(UserService::class.java)
    }

    @After
    fun after() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should correct endpoint when receiving parameter`() {
        runBlocking {
            mockWebServer.enqueue(MockResponse().setBody(mockedBody))
            val firstResult = service.getUsers()

            assertEquals(firstResult, listOfMockedUserModel)
        }
    }
}