package com.picpay.desafio.android.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.domain.interactors.GetUsers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getUsers: GetUsers,
) : ViewModel() {
    //Create BaseViewModel with delegates
    private val _userListStateFlow = MutableStateFlow(listOf<User>())
    val userListStateFlow = _userListStateFlow.asStateFlow()

    private val _loadingStateFlow = MutableStateFlow(false)
    val loadingStateFlow = _loadingStateFlow.asStateFlow()

    private val _loadingStatusStateFlow = MutableStateFlow("")

    //Implement toast
    val loadingStatusStateFlow = _loadingStatusStateFlow.asStateFlow()

    fun getUserList() {
        _loadingStateFlow.value = true

        viewModelScope.launch {
            val response = getUsers()
            _loadingStateFlow.value = false

            response.onSuccess {
                _userListStateFlow.value = it
            }

            response.onFailure {
                _loadingStatusStateFlow.value =
                    if (it.message.isNullOrEmpty()) {
                        "An unknown error ocurred"
                    } else {
                        it.message!!
                    }
            }

        }
    }
}