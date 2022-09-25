package com.picpay.desafio.app

import com.picpay.desafio.android.App
import org.koin.core.module.Module

/**
 * Helps to configure required dependencies for Instru Tests.
 * Method provideDependency can be overrided and new dependencies can be supplied.
 */
class TestApp : App() {
    override fun provideDependency() = listOf<Module>()
}