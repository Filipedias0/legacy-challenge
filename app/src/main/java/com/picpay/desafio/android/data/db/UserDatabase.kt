package com.picpay.desafio.android.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.desafio.android.data.entity.UserDTO

@Database(
    entities = [UserDTO::class],
    version = 1
)
abstract class UserDatabase: RoomDatabase(){
    abstract fun getUserDao(): UserDAO
}