package com.picpay.desafio.android.db

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.picpay.desafio.android.data.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
class UserDAOTest {


    private lateinit var userDatabase: UserDatabase
    private lateinit var userDAO: UserDAO

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    //TODO IMPLEMENT USER FACTORY
    @Before
    fun initDb(){
        userDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            UserDatabase::class.java
        ).build()

        userDAO = userDatabase.getUserDao()
    }

    @After
    fun closeDb(){
        userDatabase.close()
    }

    @Test
    fun insertContactListIntoDb() = runBlocking{
        val userList = listOf(User("img", "Name", "UserName", 1))
        userDAO.insertContactList(userList)

        assertThat(userDAO.getContacts()).isEqualTo(userList)
    }

    @Test
    fun insertUserIntoDb() = runBlocking{
        val userList = User("img", "Name", "UserName", 1)
        userDAO.insertUser(userList)

        assertThat(userDAO.getContacts()).isEqualTo(listOf(userList))
    }

    @Test
    fun deleteUserFromDb () = runBlocking{
        val user = User("img", "Name", "UserName", 1)
        val userList = listOf(user)
        userDAO.insertContactList(userList)

        userDAO.deleteUser(user)

        assertThat(userDAO.getContacts()).isEqualTo(listOf<User>())
    }



}