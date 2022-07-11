package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.*
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import kotlinx.coroutines.launch

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

    init {
        val params = HashMap<String,String>()
        params["page"] = "2"
        params["per_page"] = "20"
        getProductHome(params)
    }

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

    fun getProductHome(parameters: HashMap<String,String>) {
        _isLoading.value = true
        viewModelScope.launch {
            val product = remoteRepository.getBuyerProduct(parameters)
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