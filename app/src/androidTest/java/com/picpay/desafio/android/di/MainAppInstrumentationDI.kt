package com.picpay.desafio.android.di

import androidx.room.Room
import com.picpay.desafio.android.data.db.UserDAO
import com.picpay.desafio.android.data.db.UserDatabase
import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.domain.interactors.GetUsersFromRemote
import com.picpay.desafio.android.domain.interactors.InsertContactListIntoDb
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.presentation.viewModels.MainViewModel
import com.picpay.desafio.android.util.constants.Constants
import okhttp3.mockwebserver.MockWebServer
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Main Koin DI component for Instrumentation Testing
 */

val MockWebServerInstrumentationTest = module {

    factory {
        MockWebServer()
    }

}

private val useCasesModule = module {
    factory<GetUsersFromRemote> { GetUsersFromRemote(get()) }
    factory<InsertContactListIntoDb> { InsertContactListIntoDb(get()) }
}

private val dataModule = module {

    single<UserRepository> { UserRepositoryImpl(get(), get()) }

    single {
        Room.databaseBuilder(
            androidApplication(),
            UserDatabase::class.java,
            Constants.USER_DATABASE_NAME
        ).build()
    }

    single<UserDAO> {
        val dataBase = get<UserDatabase>()
        dataBase.getUserDao()
    }
}

private val viewModelsModule = module {
    viewModel { MainViewModel(get(), get(), get()) }
}

fun generateTestAppComponent(baseApi: String)
        = listOf(
    configureNetworkForInstrumentationTest(baseApi),
    MockWebServerInstrumentationTest,
    useCasesModule,
    viewModelsModule,
    dataModule
)

