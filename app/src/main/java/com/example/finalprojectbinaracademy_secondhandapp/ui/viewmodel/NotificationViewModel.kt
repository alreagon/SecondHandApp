package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.NotificationResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.ReadNotificationResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import com.example.finalprojectbinaracademy_secondhandapp.utils.Resource
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val remoteRepository: RemoteRepository,
    private val dataStore: DataStoreManager
): ViewModel() {

    private val _listNotif = MutableLiveData<NotificationResponse>()
    val listNotif : LiveData<NotificationResponse> get() = _listNotif

    private val _readNotification = MutableLiveData<Resource<ReadNotificationResponse>>()
    val readNotification: LiveData<Resource<ReadNotificationResponse>> get() = _readNotification

    fun getAccessToken() : LiveData<String> {
        return dataStore.getAccessToken().asLiveData()
    }

    fun getStatusLogin() : LiveData<Boolean> {
        return dataStore.getStatusLogin().asLiveData()
    }

    fun getNotif(accessToken: String) {
        viewModelScope.launch {
            val response = remoteRepository.getNotification(accessToken)
            val codeResponse = response.code()

            if (codeResponse == 200 ) {
                if (response.body() != null) {
                    _listNotif.postValue(response.body())
                }
            } else {
                Log.d("code != 200", "failed get notification")
            }
        }
    }

    fun readNotification(id: Int) {
        viewModelScope.launch {
            dataStore.getAccessToken().collect { accessTkn ->
                val readNotif = remoteRepository.readNotification(accessTkn, id)

                if (readNotif.isSuccessful) {
                    _readNotification.postValue(Resource.success(readNotif.body()))
                } else {
                    _readNotification.postValue(Resource.error("failed to read notification",null))
                }
            }
        }
    }
}