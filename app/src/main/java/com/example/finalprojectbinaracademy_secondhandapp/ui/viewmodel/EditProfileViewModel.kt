package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.ChangePasswordResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import com.example.finalprojectbinaracademy_secondhandapp.utils.Resource
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class EditProfileViewModel(
    private val remoteRepository: RemoteRepository,
    private val dataStore: DataStoreManager
): ViewModel() {
    private val _detailUser = MutableLiveData<RegisterResponse>()
    val detailUser : LiveData<RegisterResponse> get() = _detailUser

    private val _updateProfile = MutableLiveData<RegisterResponse>()
    val updateProfile: LiveData<RegisterResponse> get() = _updateProfile

    private val _changePass = MutableLiveData<Resource<ChangePasswordResponse>>()
    val changePass: LiveData<Resource<ChangePasswordResponse>> get() = _changePass

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

    fun updateProfile(accessToken: String,image:File,name: String,phone: String,address: String,city: String) {
        viewModelScope.launch {
            val requestFile = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageUpload = MultipartBody.Part.createFormData("image",image.name,requestFile)
            val name = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val address = address.toRequestBody("text/plain".toMediaTypeOrNull())
            val phone = phone.toRequestBody("text/plain".toMediaTypeOrNull())
            val city = city.toRequestBody("text/plain".toMediaTypeOrNull())

            val updateProfile = remoteRepository.updateProfile(accessToken,name,phone,address,city,imageUpload)
            val code = updateProfile.code()
            val body = updateProfile.body()

            if (code == 200) {
                body?.let {
                    _updateProfile.postValue(it)
                }
            } else {
                _updateProfile.postValue(RegisterResponse("null","null","null",
                    "null","null",0,"null","null","null","null"))
            }
        }
    }

    fun changePassword(currentPass: String, newPass: String, confirmPass: String) {
        viewModelScope.launch {
            val current = currentPass.toRequestBody("text/plain".toMediaTypeOrNull())
            val new = newPass.toRequestBody("text/plain".toMediaTypeOrNull())
            val confirm = confirmPass.toRequestBody("text/plain".toMediaTypeOrNull())

            dataStore.getAccessToken().collect { accessToken ->
                _changePass.postValue(Resource.loading(null))

                val changePass = remoteRepository.changePassword(accessToken,current,new,confirm)

                if (changePass.isSuccessful) {
                    _changePass.postValue(Resource.success(changePass.body()))
                } else {
                    if (changePass.code() == 400) {
                        _changePass.postValue(Resource.error("password salah euy..",null))
                    } else {
                        _changePass.postValue(Resource.error("sorry service unavailable..", null))
                    }
                }
            }
        }
    }

}