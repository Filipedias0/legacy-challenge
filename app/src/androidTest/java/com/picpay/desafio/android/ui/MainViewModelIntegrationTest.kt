package com.picpay.desafio.android.ui

//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.data.remote.UserService
import com.picpay.desafio.android.data.db.UserDAO
import com.picpay.desafio.android.data.db.UserDatabase
import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.domain.interactors.GetUsersFromRemote
import com.picpay.desafio.android.domain.interactors.InsertContactListIntoDb
import com.picpay.desafio.android.ui.viewModels.MainViewModel
import com.picpay.desafio.android.util.Resource
import com.picpay.desafio.android.utils.dataMock.UserMock.listOfMockedUser
import kotlinx.coroutines.runBlocking
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
    val api: UserService = mock()

    @Before
    fun setup() {
        userDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            UserDatabase::class.java
        ).allowMainThreadQueries().build()

        userDao = userDatabase.getUserDao()

        repository = UserRepositoryImpl(api, userDao)

        viewModel = MainViewModel(
            repository,
            GetUsersFromRemote(repository),
            InsertContactListIntoDb(repository)
        )
    }

    @After
    fun closeDb() {
        userDatabase.close()
    }

    @Test
    fun getUsersSaveItemsIntoDbOnSuccess(): Unit = runBlocking {
        whenever(api.getUsers()).thenReturn(listOfMockedUser)

        viewModel.getUsers().let {
            assertThat(userDao.getContacts()).isNotEmpty()
        }
    }

    @Test
    fun repositoryCallsApi() = runBlocking {
        whenever(api.getUsers()).thenReturn(listOfMockedUser)
        repository.getUsersFromRemote().let {
            assertThat(it.data).isEqualTo(listOfMockedUser)
        }
    }

    @Test
    fun getUserCallsRemote() = runBlocking {
        whenever(api.getUsers()).thenReturn(listOfMockedUser)
        viewModel.getUsers().apply {
            verify(api).getUsers()
        }
    }

    @Test
    fun getUserInsertReponseDataIntoDb() = runBlocking {
        whenever(api.getUsers()).thenReturn(listOfMockedUser)
        viewModel.getUsers().apply {
            val userIsInserted = userDao.getContacts()
            assertThat(userIsInserted).isEqualTo(listOfMockedUser)
        }
    }

    @Test
    fun getUserFromDbWhenResourceIsNotSuccess() = runBlocking {
        whenever(api.getUsers()).thenReturn(listOfMockedUser)
        viewModel.getUsers()

        whenever(repository.getUsersFromRemote()).thenReturn(Resource.Error("", null))
        viewModel.getUsers().apply {
            viewModel.userListStateFlow.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(listOfMockedUser)
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}