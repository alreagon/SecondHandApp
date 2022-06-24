package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository

class SellViewModel(
    private val remoteRepository: RemoteRepository,
    private val dataStoreManager: DataStoreManager
): ViewModel() {

    fun getStatusLogin() : LiveData<Boolean> {
        return  dataStoreManager.getStatusLogin().asLiveData()
    }

}