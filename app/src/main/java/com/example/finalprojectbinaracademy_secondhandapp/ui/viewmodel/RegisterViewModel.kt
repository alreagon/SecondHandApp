package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import com.example.finalprojectbinaracademy_secondhandapp.utils.NetworkHelper
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val remoteRepository: RemoteRepository,
    private val networkHelper: NetworkHelper,

    private val pref: DataStoreManager

): ViewModel() {

    private val _userRegis = MutableLiveData<RegisterResponse>()
    val userRegis : LiveData<RegisterResponse>
        get() = _userRegis

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


    fun saveUserDataStore(id: Int, status: Boolean) {
        viewModelScope.launch {
            pref.saveUser(id, status)
        }
    }

    fun getLoginStatus(): LiveData<Boolean> {
        return pref.getLoginStatus().asLiveData()
    }


}