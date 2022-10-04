package com.picpay.desafio.android.di

import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.data.remote.UserService
import com.picpay.desafio.android.data.db.UserDAO
import com.picpay.desafio.android.data.db.UserDatabase
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.domain.interactors.GetUsers
import com.picpay.desafio.android.presentation.viewModels.MainViewModel
import com.picpay.desafio.android.util.constants.Constants.USER_DATABASE_NAME
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppInject {
    private const val BASE_URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

    private val viewModelsModule = module {
        viewModel { MainViewModel(get()) }
    }

    private val networkModule = module{
        single {
            okHttpProvider()
        }

        single {
            gsonProvider()
        }

        single {
            createService<UserService>(get(), get())
        }
    }

    private val dataModule = module {

        single<UserRepository> { UserRepositoryImpl(get(), get()) }

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
    }

    private val useCasesModule = module {
        factory<GetUsers> { GetUsers(get()) }
    }

    fun modules(): List<Module> =
        ArrayList<Module>().apply {
            add(dataModule)
            add(useCasesModule)
            add(viewModelsModule)
            add(networkModule)
        }

    fun okHttpProvider() =
        OkHttpClient.Builder()
            .build()

    fun gsonProvider() = GsonBuilder().create()

    private inline fun <reified T> createService(
        okHttp: OkHttpClient,
        gson: Gson
    ): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(T::class.java)
    }
}

