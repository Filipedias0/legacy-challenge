package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.di.ModulesForTest
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppTest : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppTest)
        }
        ModulesForTest.load()
    }
}