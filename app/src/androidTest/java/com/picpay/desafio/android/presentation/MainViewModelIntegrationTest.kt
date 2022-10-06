package com.picpay.desafio.android.presentation

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.data.remote.UserService
import com.picpay.desafio.android.data.db.UserDAO
import com.picpay.desafio.android.data.db.UserDatabase
import com.picpay.desafio.android.data.entity.UserDTO
import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.domain.interactors.GetUsers
import com.picpay.desafio.android.domain.model.UserModel
import com.picpay.desafio.android.presentation.viewModels.MainViewModel
import com.picpay.desafio.android.utils.dataMock.UserMock.listOfMockedUserDTO
import com.picpay.desafio.android.utils.dataMock.UserMock.listOfMockedUserModel
import kotlinx.coroutines.runBlocking
import okhttp3.internal.wait
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class MainViewModelIntegrationTest {
    private lateinit var userDatabase: UserDatabase
    private lateinit var userDao: UserDAO
    private lateinit var viewModel: MainViewModel
    private lateinit var repository: UserRepositoryImpl
    private val api: UserService = mock()

    @Before
    fun setup() {
        userDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            UserDatabase::class.java
        ).build()

        userDao = userDatabase.getUserDao()

        repository = UserRepositoryImpl(api, userDao)

        viewModel = MainViewModel(
            GetUsers(repository),
        )
    }

    @After
    fun closeDb() {
        userDatabase.close()
    }

    @Test
    fun getUsersSuccess(): Unit = runBlocking {
        whenever(api.getUsers()).thenReturn(listOfMockedUserModel)

        viewModel.getUserList()
        viewModel.userListStateFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(listOfMockedUserModel)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun getUsersSaveItemsIntoDbOnSuccess(): Unit = runBlocking {
        whenever(api.getUsers()).thenReturn(listOfMockedUserModel)

        viewModel.getUserList()
        assertThat(userDao.getContacts()).isNotEmpty()
    }

    @Test
    fun getUserFromDbWhenResourceIsNotSuccess() = runBlocking {
        userDao.insertContactList(listOfMockedUserDTO)

        whenever(repository.getUsersFromRemote()).thenReturn(Result.failure(Throwable("")))
        viewModel.getUserList()

        viewModel.userListStateFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(listOfMockedUserModel)
            cancelAndConsumeRemainingEvents()
        }
    }
}