package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import androidx.lifecycle.*
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.Notification
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.ReadNotificationResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import com.example.finalprojectbinaracademy_secondhandapp.utils.NetworkHelper
import com.example.finalprojectbinaracademy_secondhandapp.utils.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val remoteRepository: RemoteRepository,
    private val dataStore: DataStoreManager,
    private val networkHelper: NetworkHelper
): ViewModel() {

    private val _listNotif = MutableLiveData<Resource<List<Notification>>>()
    val listNotif : LiveData<Resource<List<Notification>>> get() = _listNotif

    private val _readNotification = MutableLiveData<Resource<ReadNotificationResponse>>()
    val readNotification: LiveData<Resource<ReadNotificationResponse>> get() = _readNotification

    fun getStatusLogin() : LiveData<Boolean> {
        return dataStore.getStatusLogin().asLiveData()
    }

    fun getNotif() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                try {
                    dataStore.getAccessToken().collectLatest {
                        val response = remoteRepository.getNotification(it)
                        _listNotif.postValue(Resource.success(response))
                    }
                } catch (e:Exception) {
                    _listNotif.postValue(Resource.error(e.message.toString(),null))
                }
            } else {
                val response = remoteRepository.getNotifOffline()
                _listNotif.postValue(Resource.success(response))
            }
        }
    }

    fun readNotification(id: Int) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                try {
                    dataStore.getAccessToken().collectLatest {
                        val readNotif = remoteRepository.readNotification(it, id)
                        if (readNotif.isSuccessful) {
                            _readNotification.postValue(Resource.success(readNotif.body()))
                        } else {
                            _readNotification.postValue(Resource.error("faid to read notification",null))
                        }
                    }
                } catch (e:Exception) {
                    _readNotification.postValue(Resource.error(e.message.toString(),null))
                }
            } else {
                _readNotification.postValue(Resource.error("please check your internet connection...",null))
            }
        }
    }
}