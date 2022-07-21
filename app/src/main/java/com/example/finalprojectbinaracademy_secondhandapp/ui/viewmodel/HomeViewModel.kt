package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.Banner
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.Product
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import com.example.finalprojectbinaracademy_secondhandapp.utils.NetworkHelper
import com.example.finalprojectbinaracademy_secondhandapp.utils.Resource
import kotlinx.coroutines.launch

class HomeViewModel(
    private val remoteRepository: RemoteRepository,
    private val dataStore: DataStoreManager,
    private val networkHelper: NetworkHelper

) : ViewModel() {

    private val _getProduct = MutableLiveData<Resource<List<Product>>>()
    val getproduct: LiveData<Resource<List<Product>>>
        get() = _getProduct

    private val _getProductOffline = MutableLiveData<Resource<List<Product>>>()
    val gettProductOffline: LiveData<Resource<List<Product>>> get() = _getProductOffline

    private val _getBannerHome = MutableLiveData<Resource<List<Banner>>>()
    val getBannerHome: LiveData<Resource<List<Banner>>>
        get() = _getBannerHome

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        bannerHome()
    }

    fun bannerHome() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                try {
                    val response = remoteRepository.getBanner()
                    val codeResponse = response.code()

                    if (codeResponse == 200) {
                        if (response.body() != null) {
                            _getBannerHome.postValue(Resource.success(response.body()))
                        }
                    } else {
                        _getBannerHome.postValue(Resource.error("failed to get data",null))
                    }
                } catch (e: Exception) {
                    _getBannerHome.postValue(Resource.error(e.message.toString(),null))
                }
            } else {
                val response = remoteRepository.getBannerOffline()
                _getBannerHome.postValue(Resource.success(response))
            }
        }
    }

    fun getProductPaging() = remoteRepository.getProductPaging().cachedIn(viewModelScope)

    fun getProductOffline() {
        viewModelScope.launch {
            _getProductOffline.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    val params = HashMap<String,String>()
                    params["page"] = "1"
                    params["per_page"] = ""
                    val response = remoteRepository.getProductBoundResource(params)
                    _getProductOffline.postValue(Resource.success(response))
                } catch (e: Exception) {
                    _getProductOffline.postValue(Resource.error("failed to get data from server",null))
                }
            } else {
                val response = remoteRepository.getProductOffline()
                _getProductOffline.postValue(Resource.success(response))
            }
        }
    }

    fun getSearchProduct(productName : String) {
        _isLoading.value = true
        _getProduct.postValue(Resource.loading(null))
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                val params = HashMap<String,String>()
                params["search"] = productName
                try {
                    val product = remoteRepository.getProductBoundResource(params)
                    _isLoading.value = false
                    _getProduct.postValue(Resource.success(product))
                } catch (e: Exception) {
                    _isLoading.value = false
                    _getProduct.postValue(Resource.error("failed to get data",null))
                }
            } else {
                _getProduct.postValue(Resource.error("please check your internet connection...",null))
            }
        }
    }

//    val products = remoteRepository.getProductBoundResource().asLiveData()

}