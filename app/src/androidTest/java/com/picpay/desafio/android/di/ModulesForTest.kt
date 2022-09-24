package com.picpay.desafio.android.di

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.picpay.desafio.android.data.remote.UserService
import okhttp3.OkHttpClient
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ModulesForTest {
    private const val BASE_URL = "http://127.0.0.1:8080"

    private fun dataModule() = module{
        single {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .build()
                )
                .build()
                .create(T::class.java)
        }
    }

    fun load(){
        loadKoinModules(
            dataModule()
        )
    }
}