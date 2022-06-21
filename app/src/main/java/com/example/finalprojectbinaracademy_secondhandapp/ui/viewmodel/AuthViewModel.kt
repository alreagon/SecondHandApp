package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.LoginRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.LoginResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import com.example.finalprojectbinaracademy_secondhandapp.utils.NetworkHelper
import kotlinx.coroutines.launch

class AuthViewModel(
    private val remoteRepository: RemoteRepository,
    private val dataStore: DataStoreManager
): ViewModel() {

    private val _userRegis = MutableLiveData<RegisterResponse>()
    val userRegis : LiveData<RegisterResponse>
        get() = _userRegis

    private val _userlogin = MutableLiveData<LoginResponse>()
    val userLogin : LiveData<LoginResponse>
        get() = _userlogin

    fun userRegister(request: RegisterRequest) {
        viewModelScope.launch {
            val register = remoteRepository.registerUser(request)
            if (register.code() == 201) {
                _userRegis.postValue(register.body())
            } else {
                Log.d("response error", "user register error")
            }
        }
    }

    fun userLogin(request: LoginRequest) {
        viewModelScope.launch {
            val login = remoteRepository.loginUser(request)
            val body = login.body()
            if (login.code() == 201) {
                body?.let {
                    _userlogin.postValue(login.body())
                    setLogin()
                    setAccessToken(body.accessToken)
                }
            } else {
                _userlogin.postValue(LoginResponse("null","null",0,"null"))
                Log.d("response error", "failed user login")
            }
        }
    }

    fun setLogin() {
        viewModelScope.launch {
            dataStore.setLoginStatus()
        }
    }

    fun setAccessToken(accessToken: String) {
        viewModelScope.launch {
            dataStore.setAccessToken(accessToken)
        }
    }

    fun getAccessToken(): LiveData<String> {
        return dataStore.getAccessToken().asLiveData()
    }

    fun setLogout() {
        viewModelScope.launch {
            dataStore.setLogoutStatus()
        }
    }


}