package com.picpay.desafio.android.data.db

import androidx.room.Room
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.picpay.desafio.android.data.db.UserDAO
import com.picpay.desafio.android.data.db.UserDatabase
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.utils.dataMock.UserMock.listOfMockedUser
import com.picpay.desafio.android.utils.dataMock.UserMock.mockedUser
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

@MediumTest
class UserDAOTest {

    private lateinit var userDatabase: UserDatabase
    private lateinit var userDAO: UserDAO

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
        userDAO.insertContactList(listOfMockedUser)

        assertThat(userDAO.getContacts()).isEqualTo(listOfMockedUser)
    }

    @Test
    fun insertUserIntoDb() = runBlocking{
        userDAO.insertUser(mockedUser)

        assertThat(userDAO.getContacts()).isEqualTo(listOf(mockedUser))
    }

    @Test
    fun deleteUserFromDb () = runBlocking{
        userDAO.insertUser(mockedUser)

        userDAO.deleteUser(mockedUser)

        assertThat(userDAO.getContacts()).isEqualTo(listOf<User>())
    }



}