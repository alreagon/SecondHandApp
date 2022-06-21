package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.UpdateProfileRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val remoteRepository: RemoteRepository,
    private val dataStore: DataStoreManager
): ViewModel() {
    private val _detailUser = MutableLiveData<RegisterResponse>()
    val detailUser : LiveData<RegisterResponse> get() = _detailUser

    private val _updateProfile = MutableLiveData<RegisterResponse>()
    val updateProfile: LiveData<RegisterResponse> get() = _updateProfile

    fun getAccessToken() : LiveData<String> {
        return dataStore.getAccessToken().asLiveData()
    }

    fun getUserByAccessToken(accessToken: String) {
        viewModelScope.launch {
            val getUser = remoteRepository.getUser(accessToken)
            val code = getUser.code()
            val body = getUser.body()

            if (code == 200) {
                body?.let {
                    _detailUser.postValue(it)
                }
            } else {
                Log.d("response error", "user register error")
            }
        }
    }

    fun updateProfile(accessToken: String,request: UpdateProfileRequest) {
        viewModelScope.launch {
            val updateProfile = remoteRepository.updateProfile(accessToken,request)
            val code = updateProfile.code()
            val body = updateProfile.body()

            if (code == 200) {
                body?.let {
                    _updateProfile.postValue(it)
                }
            } else {
                _updateProfile.postValue(RegisterResponse("null","null","null","null","null",0,"null","null","null","null"))
            }
        }
    }

}