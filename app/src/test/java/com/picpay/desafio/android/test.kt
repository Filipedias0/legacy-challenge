package com.picpay.desafio.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.remote.UserService
import com.picpay.desafio.android.db.UserDAO
import com.picpay.desafio.android.db.UserDatabase
import com.picpay.desafio.android.repository.UserRepositoryImpl
import com.picpay.desafio.android.ui.MainViewModel
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class test {
    private val apiJson = """ [{ "img": "img", "name": "name", "username": "username", "id": 1 }] """
    private val apiData = listOf(User("img", "name", "username", 1))

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockWebServer = MockWebServer()


    private val userDao: UserDAO =
        Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            UserDatabase::class.java).allowMainThreadQueries().build().getUserDao()

    private val api by lazy {
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val userService by lazy{
        api.create(UserService::class.java)
    }

    private val viewModel = MainViewModel(UserRepositoryImpl( userService ,userDao))

    @Test
    fun getUsersSaveItemsIntoDbOnSuccess()  {

        mockWebServer.enqueue(
            MockResponse()
                .setBody(apiJson)
                .setResponseCode(200)
        )

        viewModel.getUsers()



        runBlocking {
            Truth.assertThat(userDao.getContacts()).isEqualTo(apiData)
        }
    }
}