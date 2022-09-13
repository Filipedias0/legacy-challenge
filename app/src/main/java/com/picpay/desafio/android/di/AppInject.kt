package com.picpay.desafio.android.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.data.remote.UserService
import com.picpay.desafio.android.util.constants.retrofitConstants.URL
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object AppInject {

    private val appModules = module {
        //Look onto single <example>
        single {
            Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserService::class.java)
        }
    }

    fun modules(): List<Module> =
        ArrayList<Module>().apply {
            add(appModules)
        }
}

