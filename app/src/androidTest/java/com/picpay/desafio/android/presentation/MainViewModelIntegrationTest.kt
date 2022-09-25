package com.picpay.desafio.android.presentation

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
import com.picpay.desafio.android.presentation.viewModels.MainViewModel
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
    private val api: UserService = mock()

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

        viewModel.getUsers()
        assertThat(userDao.getContacts()).isNotEmpty()
    }

    @Test
    fun repositoryCallsApi() = runBlocking {
        whenever(api.getUsers()).thenReturn(listOfMockedUser)
        val getUsersReturn = repository.getUsersFromRemote()
        assertThat(getUsersReturn.data).isEqualTo(listOfMockedUser)

    }

    @Test
    fun getUserCallsRemote(): Unit = runBlocking {
        whenever(api.getUsers()).thenReturn(listOfMockedUser)
        viewModel.getUsers()
        verify(api).getUsers()
    }

    @Test
    fun getUserInsertReponseDataIntoDb() = runBlocking {
        whenever(api.getUsers()).thenReturn(listOfMockedUser)
        viewModel.getUsers()
        val userIsInserted = userDao.getContacts()
        assertThat(userIsInserted).isEqualTo(listOfMockedUser)
    }

    @Test
    fun getUserFromDbWhenResourceIsNotSuccess() = runBlocking {
        userDao.insertContactList(listOfMockedUser)

        whenever(repository.getUsersFromRemote()).thenReturn(Resource.Error("", null))
        viewModel.getUsers()
        viewModel.userListStateFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(listOfMockedUser)
            cancelAndConsumeRemainingEvents()
        }
    }
}