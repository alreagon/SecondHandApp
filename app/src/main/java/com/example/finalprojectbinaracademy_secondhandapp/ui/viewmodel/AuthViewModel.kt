package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.LoginRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.LoginResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import com.example.finalprojectbinaracademy_secondhandapp.utils.NetworkHelper
import com.example.finalprojectbinaracademy_secondhandapp.utils.Resource
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel(
    private val remoteRepository: RemoteRepository,
    private val dataStore: DataStoreManager
): ViewModel() {

    private val _userRegis = MutableLiveData<RegisterResponse>()
    val userRegis : LiveData<RegisterResponse>
        get() = _userRegis

    private val _userlogin = MutableLiveData<Resource<LoginResponse>>()
    val userLogin : LiveData<Resource<LoginResponse>>
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
            _userlogin.postValue(Resource.loading(null))
            remoteRepository.loginUser(request).let {
                if (it.isSuccessful) {
                    _userlogin.postValue(Resource.success(it.body()))
                    setLogin()
                    it.body()?.let { body ->
                        setAccessToken(body.accessToken)
                    }
                } else {
                    _userlogin.postValue(Resource.error("Please check your credentials..",null))
                }
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

    fun checkUserLogin() : LiveData<Boolean> {
        return dataStore.getStatusLogin().asLiveData()
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