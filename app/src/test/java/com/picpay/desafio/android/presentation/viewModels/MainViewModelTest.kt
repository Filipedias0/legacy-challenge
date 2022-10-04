package com.picpay.desafio.android.presentation.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.utils.MainCoroutineRule
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

    @Before
    fun setUp() {
        repository = mock()
        viewModel = MainViewModel(mock())
    }

    @Test
    fun getUserListHideLoading() {
        viewModel.getUserList()

        runBlocking {
            viewModel.loadingStateFlow.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(false)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

}