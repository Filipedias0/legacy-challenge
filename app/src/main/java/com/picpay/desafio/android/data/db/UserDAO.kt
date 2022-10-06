package com.picpay.desafio.android.data.db

import androidx.room.*
import com.picpay.desafio.android.data.entity.UserDTO

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userDTO: UserDTO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContactList(userDTO: List<UserDTO>)

    @Query("SELECT * FROM user_table")
    suspend fun getContacts(): List<UserDTO>

    @Delete()
    suspend fun deleteUser(userDTO: UserDTO)


}