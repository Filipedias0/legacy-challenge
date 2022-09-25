package com.picpay.desafio.android.di

import com.google.gson.GsonBuilder
import com.picpay.desafio.android.data.remote.UserService
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit Koin DI component for Instrumentation Testing
 */
fun configureNetworkForInstrumentationTest(baseTestApi: String) = module (override = true) {

    single {
        Retrofit.Builder()
            .baseUrl(baseTestApi)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    factory { get<Retrofit>().create(UserService::class.java) }
}

