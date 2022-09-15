package com.picpay.desafio.android.ui

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.repository.UserRepository
import com.picpay.desafio.android.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    //Create BaseViewModel with delegates
    private val _userListStateFlow = MutableStateFlow(listOf<User>())
    val userListStateFlow = _userListStateFlow.asStateFlow()

    //val breakingNews: MutableLiveData<List<User>> = MutableLiveData()

    private val _loadingStateFlow = MutableStateFlow(View.VISIBLE)
    val loadingStateFlow = _loadingStateFlow.asStateFlow()

    private val _loadingStatusStateFlow = MutableStateFlow("")
    val loadingStatusStateFlow = _loadingStatusStateFlow.asStateFlow()

    fun getUsers(){
        _loadingStateFlow.value = View.VISIBLE

        when(
            val response = userRepository.getUsers()
        ){
            is Resource.Succes -> {
                response.data?.let {
                    _userListStateFlow.value = it
                }
            }

            is Resource.Error -> {
                _loadingStateFlow.value = View.VISIBLE
                response.message?.let{
                    _loadingStatusStateFlow.value = it
                }
            }
            else -> {
                _loadingStateFlow.value = View.GONE
            }
        }
    }


}