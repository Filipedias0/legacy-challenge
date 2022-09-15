package com.picpay.desafio.android.di

import androidx.room.Room
import com.picpay.desafio.android.data.remote.UserService
import com.picpay.desafio.android.db.UserDAO
import com.picpay.desafio.android.db.UserDatabase
import com.picpay.desafio.android.repository.UserRepository
import com.picpay.desafio.android.repository.UserRepositoryImpl
import com.picpay.desafio.android.ui.MainViewModel
import com.picpay.desafio.android.util.constants.Constants.URL
import com.picpay.desafio.android.util.constants.Constants.USER_DATABASE_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppInject {

    private val appModules = module {
        single {
            Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserService::class.java)
        }
        single<UserRepository> { UserRepositoryImpl(get()) }
        single {
            Room.databaseBuilder(
                androidApplication(),
                UserDatabase::class.java,
                USER_DATABASE_NAME
            ).build()
        }
        single<UserDAO> {
            val dataBase = get<UserDatabase>()
            dataBase.getUserDao()
        }
        viewModel { MainViewModel(get()) }
    }

    fun modules(): List<Module> =
        ArrayList<Module>().apply {
            add(appModules)
        }
}

