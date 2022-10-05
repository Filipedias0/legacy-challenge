package com.picpay.desafio.android.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.data.db.UserDAO
import com.picpay.desafio.android.data.db.UserDatabase
import com.picpay.desafio.android.data.remote.UserService
import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.domain.interactors.GetUsers
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.presentation.viewModels.MainViewModel
import com.picpay.desafio.android.util.constants.Constants.USER_DATABASE_NAME
import okhttp3.*
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AppInject {

    private const val BASE_URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

    private var CACHE_MAX_AGE_VALUE = 5
    private var CACHE_MAX_STALE_VALUE = 1
    private const val TAG = "ServiceGenerator"
    const val HEADER_CACHE_CONTROL = "Cache-Control"
    private var HEADER_PRAGMA = "Pragma"
    private const val CACHE_SIZE = (5 * 1024 * 1024).toLong()


    private val viewModelsModule = module {
        viewModel { MainViewModel(get()) }
    }

    private val networkModule = module{

        fun cacheProvider(context: Context) = Cache(context.cacheDir, CACHE_SIZE)

        fun cacheInterceptorProvider() = Interceptor { chain ->
            val request: Request = chain.request()
            val cacheControl = CacheControl.Builder()
                .maxAge(CACHE_MAX_AGE_VALUE, TimeUnit.MINUTES)
                .build()
            request.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                .build()
            chain.proceed(request)
        }

        fun offlineCacheInterceptorProvider() = Interceptor { chain ->
            try {
                chain.proceed(chain.request())
            } catch (e: Exception) {
                val cacheControl = CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(CACHE_MAX_STALE_VALUE, TimeUnit.DAYS)
                    .build()
                val offlineRequest: Request = chain.request().newBuilder()
                    .cacheControl(cacheControl)
                    .removeHeader(HEADER_PRAGMA)
                    .build()
                chain.proceed(offlineRequest)
            }
        }

        single {
            OkHttpClient.Builder()
                .addInterceptor(offlineCacheInterceptorProvider())
                .addNetworkInterceptor(cacheInterceptorProvider())
                .cache(cacheProvider(get()))
                .build()
        }

        single{
            cacheProvider(get())
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

