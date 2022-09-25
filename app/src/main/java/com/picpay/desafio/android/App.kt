package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.di.AppInject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class App: Application() {
    override fun onCreate() {
        super.onCreate()
        initiateKoin()
    }

    private fun initiateKoin(){
        startKoin{
            androidContext(this@App)
            modules(provideDependency())
        }
    }
    open fun provideDependency() = AppInject.modules()
}