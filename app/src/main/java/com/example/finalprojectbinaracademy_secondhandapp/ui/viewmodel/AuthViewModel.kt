package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import androidx.lifecycle.*
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.LoginRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.LoginResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import com.example.finalprojectbinaracademy_secondhandapp.utils.NetworkHelper
import com.example.finalprojectbinaracademy_secondhandapp.utils.Resource
import kotlinx.coroutines.launch

class AuthViewModel(
    private val remoteRepository: RemoteRepository,
    private val dataStore: DataStoreManager,
    private val networkHelper: NetworkHelper
): ViewModel() {

    private val _userRegis = MutableLiveData<Resource<RegisterResponse>>()
    val userRegis : LiveData<Resource<RegisterResponse>>
        get() = _userRegis

    private val _userlogin = MutableLiveData<Resource<LoginResponse>>()
    val userLogin : LiveData<Resource<LoginResponse>>
        get() = _userlogin

    fun userRegister(request: RegisterRequest) {
        viewModelScope.launch {
            _userRegis.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    val register = remoteRepository.registerUser(request)
                    if (register.code() == 201) {
                        _userRegis.postValue(Resource.success(register.body()))
                    } else {
                        _userRegis.postValue(Resource.error("Failed to registration",null))
                    }
                } catch (e: Exception) {
                    _userRegis.postValue(Resource.error(e.message.toString(),null))
                }
            } else {
                _userRegis.postValue(Resource.error("please check your internet connection...",null))
            }
        }
    }

    fun userLogin(request: LoginRequest) {
        viewModelScope.launch {
            _userlogin.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    val response = remoteRepository.loginUser(request)
                    if (response.code() == 201) {
                        _userlogin.postValue(Resource.success(response.body()))
                        setLogin()
                        response.body()?.let {
                            setAccessToken(it.accessToken)
                        }
                    } else {
                        _userlogin.postValue(Resource.error("Please check your credentials..",null))
                    }
                } catch (e: Exception) {
                    _userlogin.postValue(Resource.error(e.message.toString(),null))
                }
            } else {
                _userlogin.postValue(Resource.error("please check your internet connection...",null))
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