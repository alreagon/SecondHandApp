package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.SellerOrder
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.SellerProduct
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.*
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import com.example.finalprojectbinaracademy_secondhandapp.utils.NetworkHelper
import com.example.finalprojectbinaracademy_secondhandapp.utils.Resource
import com.example.finalprojectbinaracademy_secondhandapp.utils.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import retrofit2.http.PATCH

class SaleListViewModel(
    private val remoteRepository: RemoteRepository,
    private val dataStore: DataStoreManager,
    private val networkHelper: NetworkHelper
): ViewModel() {

    private val _seller = MutableLiveData<Resource<RegisterResponse>>()
    val seller: LiveData<Resource<RegisterResponse>> get() = _seller

    private val _listProduct = MutableLiveData<Resource<List<SellerProduct>>>()
    val listProduct: LiveData<Resource<List<SellerProduct>>> get() = _listProduct

    private val _listDiminati = MutableLiveData<Resource<List<SellerOrder>>>()
    val listDiminati: LiveData<Resource<List<SellerOrder>>> get() = _listDiminati

    private val _listSold = MutableLiveData<Resource<List<SellerOrder>>>()
    val listSold: LiveData<Resource<List<SellerOrder>>> get() = _listSold

    private val _diminatiById = MutableLiveData<Resource<GetSellerOrderResponseItem>>()
    val diminatiById: LiveData<Resource<GetSellerOrderResponseItem>> get() = _diminatiById

    private val _patchOrder = MutableLiveData<Resource<PatchOrderResponse>>()
    val patchOrder: LiveData<Resource<PatchOrderResponse>> get() = _patchOrder

    private val _patchStatusProduct = MutableLiveData<Resource<PostProductResponse>>()
    val patchStatusProduct: LiveData<Resource<PostProductResponse>> get() = _patchStatusProduct

    init {
//        getSellerProduct()
//        getSeller()
//        getSellerOrder()
//        getSellerSold()
    }

    fun getSeller() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                dataStore.getAccessToken().collect { accessToken ->
                    val getSeller = remoteRepository.getUser(accessToken)

                    if (getSeller.isSuccessful) {
                        _seller.postValue(Resource.success(getSeller.body()))
                    } else {
                        _seller.postValue(Resource.error("error get seller",null))
                    }
                }
            }
        }
    }

    fun getSellerProduct() {
        viewModelScope.launch {
            _listProduct.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    dataStore.getAccessToken().collectLatest {
                        val response = remoteRepository.getSellerProduct(it)
                        _listProduct.postValue(Resource.success(response.body()))
                    }
                } catch (e: Exception) {
                    _listProduct.postValue(Resource.error(e.message.toString(),null))
                }
            } else {
                val response = remoteRepository.getSellerProductOffline()
                _listProduct.postValue(Resource.success(response))
            }
        }
    }

    fun getSellerSold(status: String) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                try {
                    dataStore.getAccessToken().collectLatest {
                        val getSellerSold = remoteRepository.getSellerOrder(it,status)

                        if (getSellerSold.isSuccessful) {
                            _listSold.postValue(Resource.success(getSellerSold.body()))
                        } else {
                            _listSold.postValue(Resource.error("failed get data", null))
                        }
                    }
                } catch (e:Exception) {
                    _listSold.postValue(Resource.error(e.message.toString(), null))
                }
            } else {
                _listSold.postValue(Resource.error("please check your internet connection...", null))
            }
        }
    }

    fun getSellerOrder() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                try {
                    dataStore.getAccessToken().collectLatest {
                        val response = remoteRepository.getSellerOrder(it,"pending")
                        _listDiminati.postValue(Resource.success(response.body()))
                    }
                } catch (e: Exception) {
                    _listDiminati.postValue(Resource.error(e.message.toString(),null))
                }
            } else {
                val response = remoteRepository.getSellerOrderOffline()
                _listDiminati.postValue(Resource.success(response))
            }
        }
    }

    fun getSellerOrderById(idOrder: Int) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                try {
                    dataStore.getAccessToken().collect {
                        val getSellerOrderId = remoteRepository.getSellerOrderId(it,idOrder)

                        if (getSellerOrderId.isSuccessful) {
                            _diminatiById.postValue(Resource.success(getSellerOrderId.body()))
                        } else {
                            _diminatiById.postValue(Resource.error("failed to get data...",null))
                        }
                    }
                } catch (e:Exception) {
                    _diminatiById.postValue(Resource.error(e.message.toString(),null))
                }
            } else {
                _diminatiById.postValue(Resource.error("please check your internet connection...",null))
            }
        }
    }

    fun patchOrder(idOrder: Int,status: String) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                try {
                    val statusUpdate = status.toRequestBody("text/plain".toMediaTypeOrNull())
                    dataStore.getAccessToken().collect {
                        val patchOrder = remoteRepository.patchOrder(it,idOrder,statusUpdate)

                        if (patchOrder.isSuccessful) {
                            _patchOrder.postValue(Resource.success(patchOrder.body()))
                        } else {
                            _patchOrder.postValue(Resource.error("error to update order",null))
                        }
                    }
                } catch (e:Exception) {
                    _patchOrder.postValue(Resource.error(e.message.toString(),null))
                }
            } else {
                _patchOrder.postValue(Resource.error("please check your internet connection...",null))
            }
        }
    }

    fun patchStatusProduct(idOrder: Int,status: String) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                try {
                    val statusUpdate = status.toRequestBody("text/plain".toMediaTypeOrNull())
                    dataStore.getAccessToken().collectLatest {
                        val patchStatusProd =remoteRepository.patchStatusProduct(it,idOrder,statusUpdate)

                        if (patchStatusProd.isSuccessful) {
                            _patchStatusProduct.postValue(Resource.success(patchStatusProd.body()))
                        } else {
                            _patchStatusProduct.postValue(Resource.error("error to update status",null))
                        }
                    }
                } catch (e:Exception) {
                    _patchStatusProduct.postValue(Resource.error(e.message.toString(),null))
                }
            } else {
                _patchStatusProduct.postValue(Resource.error("please check your internet connection...",null))
            }
        }
    }

}