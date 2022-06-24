package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.NotificationResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val remoteRepository: RemoteRepository,
    private val dataStore: DataStoreManager
): ViewModel() {

    private val _listNotif = MutableLiveData<NotificationResponse>()
    val listNotif : LiveData<NotificationResponse> get() = _listNotif

    fun getAccessToken() : LiveData<String> {
        return dataStore.getAccessToken().asLiveData()
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
}