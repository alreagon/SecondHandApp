package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.AuthRepository
import com.example.finalprojectbinaracademy_secondhandapp.utils.NetworkHelper
import com.example.finalprojectbinaracademy_secondhandapp.utils.Resource
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val networkHelper: NetworkHelper
): ViewModel() {

    private val _userRegis = MutableLiveData<RegisterResponse>()
    val userRegis : LiveData<RegisterResponse>
        get() = _userRegis

    fun userRegister(request: RegisterRequest) {
        viewModelScope.launch {
            val register = authRepository.registerUser(request)
            register.let {
                _userRegis.postValue(it.body())
            }
        }
    }


}