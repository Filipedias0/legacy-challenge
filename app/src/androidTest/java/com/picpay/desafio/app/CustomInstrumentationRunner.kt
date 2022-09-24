package com.picpay.desafio.app

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.picpay.desafio.android.App
import com.picpay.desafio.android.ui.MainActivity

class CustomInstrumentationRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader,
                                className: String,
                                context: Context
    ): Application {
        return super.newApplication(cl,
            App::class.java.name,
            context)
    }
}