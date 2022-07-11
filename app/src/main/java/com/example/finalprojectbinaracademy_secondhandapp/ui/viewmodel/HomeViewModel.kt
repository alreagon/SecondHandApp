package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.example.finalprojectbinaracademy_secondhandapp.api.ApiClient
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.*
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiService
import com.example.finalprojectbinaracademy_secondhandapp.utils.NetworkHelper
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val remoteRepository: RemoteRepository,
    private val dataStore: DataStoreManager

) : ViewModel() {

    private val _getProduct = MutableLiveData<GetProductResponse>()
    val getproduct: LiveData<GetProductResponse>
        get() = _getProduct

    private val _getBannerHome = MutableLiveData<BannerResponse>()
    val getBannerHome: LiveData<BannerResponse>
        get() = _getBannerHome

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getAccessToken() : LiveData<String> {
        return dataStore.getAccessToken().asLiveData()
    }

    fun getStatusLogin() : LiveData<Boolean> {
        return dataStore.getStatusLogin().asLiveData()
    }

    fun BannerHome() {
        viewModelScope.launch {
            val response = remoteRepository.getBanner()
            val codeResponse = response.code()

            if (codeResponse == 200 ) {
                if (response.body() != null) {
                    _getBannerHome.postValue(response.body())
                }
            } else {
                Log.d("code != 200", "failed get Banner response")
            }
        }
    }

    fun setAccessToken(accessToken: String) {
        viewModelScope.launch {
            dataStore.setAccessToken(accessToken)
        }
    }

    fun setLogin() {
        viewModelScope.launch {
            dataStore.setLoginStatus()
        }
    }

    fun productHome() {
        _isLoading.value = true
        viewModelScope.launch {
            val product = remoteRepository.getBuyerProduct()
            if (product.code() == 200) {
                _isLoading.value = false
                _getProduct.postValue(product.body())
            } else {
                _isLoading.value = false
                Log.d("response error", "get product error")
            }
        }

    }


}