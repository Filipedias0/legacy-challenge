package com.picpay.desafio.android.ui

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import com.picpay.desafio.android.MainCoroutineRule
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.repository.UserRepository
import com.picpay.desafio.android.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var repository: UserRepository
    private lateinit var user: User
    private lateinit var resourceSuccess: Resource.Succes<List<User>>
    private lateinit var resourceError: Resource.Succes<List<User>>

    @Before
    fun setUp() {
        repository = mock()
        viewModel = MainViewModel(mock())
        user = mock()
        resourceSuccess = mock()
        resourceError = mock()
    }

    @Test
    fun getUsersShowLoading() {
        runBlocking {
            viewModel.loadingStateFlow.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(View.VISIBLE)
                cancelAndConsumeRemainingEvents()
            }
        }

        viewModel.getUsers()
    }

    @Test
    fun getUsersHideLoading() {
        viewModel.getUsers()

        runBlocking {
            viewModel.loadingStateFlow.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(View.GONE)
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}