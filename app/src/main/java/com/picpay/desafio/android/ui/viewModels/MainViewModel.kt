package com.picpay.desafio.android.ui.viewModels

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.domain.interactors.GetUsersFromRemote
import com.picpay.desafio.android.domain.interactors.InsertContactListIntoDb
import com.picpay.desafio.android.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val userRepository: UserRepository,
    private val getUsersFromRemote: GetUsersFromRemote,
    private val insertContactListIntoDb: InsertContactListIntoDb
) : ViewModel() {
    //Create BaseViewModel with delegates
    private val _userListStateFlow = MutableStateFlow(listOf<User>())
    val userListStateFlow = _userListStateFlow.asStateFlow()

    private val _loadingStateFlow = MutableStateFlow(View.VISIBLE)
    val loadingStateFlow = _loadingStateFlow.asStateFlow()

    private val _loadingStatusStateFlow = MutableStateFlow("")
    val loadingStatusStateFlow = _loadingStatusStateFlow.asStateFlow()

    fun getUsers() {
        _loadingStateFlow.value = View.VISIBLE

        viewModelScope.launch {
             val response = getUsersFromRemote()
            _loadingStateFlow.value = View.GONE

            when (
                response
            ) {
                is Resource.Succes -> {
                    response.data?.let {
                        _userListStateFlow.value = it
                        insertContactListIntoDb(it)
                    }
                }

                is Resource.Error -> {
                    _userListStateFlow.value = getContactListFromDb()
                    response.message?.let {
                        _loadingStatusStateFlow.value = it
                    }
                }
                else -> {
                    _userListStateFlow.value = getContactListFromDb()
                }
            }
        }
    }

    private suspend fun getContactListFromDb(): List<User> {
            return userRepository.getContactListFromDb()
    }
}