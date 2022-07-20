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

    private val _getProduct = MutableLiveData<List<GetProductResponseItem>>()
    val getproduct: LiveData<List<GetProductResponseItem>>
        get() = _getProduct

    private val _getBannerHome = MutableLiveData <List<BannerResponse>>()
    val getBannerHome: LiveData <List<BannerResponse>>
        get() = _getBannerHome

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        val params = HashMap<String,String>()
        params["page"] = "2"
        params["per_page"] = "20"
        getProductHome(params)
    }

    fun getAccessToken(): LiveData<String> {
        return dataStore.getAccessToken().asLiveData()
    }

    fun getStatusLogin(): LiveData<Boolean> {
        return dataStore.getStatusLogin().asLiveData()
    }

    fun BannerHome() {
        viewModelScope.launch {
            val response = remoteRepository.getBanner()
            val codeResponse = response.code()

            if (codeResponse == 200) {
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
    fun getSearchProduct(parameters: HashMap<String,String>, productName : String) {
//        _isLoading.value = true
        viewModelScope.launch {
            val product = remoteRepository.getBuyerProductSearch(parameters, productName)
            if (product.code() == 200) {
                _isLoading.value = false
                _getProduct.postValue(product.body())
            } else {
                _isLoading.value = false
                Log.d("response error", "get product error")
            }
        }

    }


//    fun getBuyerProductSearchResult(productName: String) {
//        apiServices.getSearchBuyerProduct(productName)
//            .enqueue(object: Callback<List<GetBuyerProductResponseItem>>{
//                override fun onResponse(
//                    call: Call<List<GetBuyerProductResponseItem>>,
//                    response: Response<List<GetBuyerProductResponseItem>>
//                ) {
//                    if(response.isSuccessful){
//                        liveDataBuyerProductSearchResult.value = response.body()
//                    }
//                }
//
//                override fun onFailure(
//                    call: Call<List<GetBuyerProductResponseItem>>,
//                    t: Throwable
//                ) {
//                    //
//                }
//
//            })
//    }

//    private val liveDataBuyerProduct = MutableLiveData<List<GetBuyerProductResponseItem>>()
//    val buyerProduct: LiveData<List<GetBuyerProductResponseItem>> = liveDataBuyerProduct
//    private val apiServices = api
//
//    private val liveDataBuyerProductById = MutableLiveData<GetProductDetail>()
//    val buyerProductById: LiveData<GetProductDetail> = liveDataBuyerProductById
//
//    private val liveDataBuyerProductSearchResult =
//        MutableLiveData<List<GetBuyerProductResponseItem>>()
//    val searchResult: LiveData<List<GetBuyerProductResponseItem>> =
//        liveDataBuyerProductSearchResult
//
//    fun getAllBuyerProduct() {
//        apiServices.getAllBuyerProduct()
//            .enqueue(object : Callback<List<GetBuyerProductResponseItem>> {
//                override fun onResponse(
//                    call: Call<List<GetBuyerProductResponseItem>>,
//                    response: Response<List<GetBuyerProductResponseItem>>
//                ) {
//                    if (response.isSuccessful) {
//                        liveDataBuyerProduct.value = response.body()
//                    }
//                }
//
//                override fun onFailure(
//                    call: Call<List<GetBuyerProductResponseItem>>,
//                    t: Throwable
//                ) {
//                    //
//                }
//
//            })
//    }
//
//    fun getBuyerProductById(idProduct: Int) {
//        apiServices.getBuyerProductById(idProduct)
//            .enqueue(object : Callback<GetProductDetail> {
//                override fun onResponse(
//                    call: Call<GetProductDetail>,
//                    response: Response<GetProductDetail>
//                ) {
//                    if (response.isSuccessful) {
//                        liveDataBuyerProductById.value = response.body()
//                    }
//                }
//
//                override fun onFailure(call: Call<GetProductDetail>, t: Throwable) {
//                    //
//                }
//
//            })
//    }
//
//    fun getBuyerProductSearchResult(productName: String) {
//        apiServices.getSearchBuyerProduct(productName)
//            .enqueue(object: Callback<List<GetBuyerProductResponseItem>>{
//                override fun onResponse(
//                    call: Call<List<GetBuyerProductResponseItem>>,
//                    response: Response<List<GetBuyerProductResponseItem>>
//                ) {
//                    if(response.isSuccessful){
//                        liveDataBuyerProductSearchResult.value = response.body()
//                    }
//                }
//
//                override fun onFailure(
//                    call: Call<List<GetBuyerProductResponseItem>>,
//                    t: Throwable
//                ) {
//                    //
//                }
//
//            })
//    }



}