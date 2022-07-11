package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.*
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import com.example.finalprojectbinaracademy_secondhandapp.utils.Resource
import com.example.finalprojectbinaracademy_secondhandapp.utils.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import retrofit2.http.PATCH

class SaleListViewModel(
    private val remoteRepository: RemoteRepository,
    private val dataStore: DataStoreManager
): ViewModel() {

    private val _seller = MutableLiveData<Resource<RegisterResponse>>()
    val seller: LiveData<Resource<RegisterResponse>> get() = _seller

    private val _listProduct = MutableLiveData<Resource<List<GetProductResponseItem>>>()
    val listProduct: LiveData<Resource<List<GetProductResponseItem>>> get() = _listProduct

    private val _listDiminati = MutableLiveData<Resource<GetSellerOrderResponse>>()
    val listDiminati: LiveData<Resource<GetSellerOrderResponse>> get() = _listDiminati

    private val _diminatiById = MutableLiveData<Resource<GetSellerOrderResponseItem>>()
    val diminatiById: LiveData<Resource<GetSellerOrderResponseItem>> get() = _diminatiById

    private val _patchOrder = MutableLiveData<Resource<PatchOrderResponse>>()
    val patchOrder: LiveData<Resource<PatchOrderResponse>> get() = _patchOrder

    init {
        getSellerProduct()
        getSeller()
        getSellerOrder()
    }

    private fun getSeller() {
        viewModelScope.launch {
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

    private fun getSellerProduct() {
        viewModelScope.launch {
            dataStore.getAccessToken().collect {
                val getProduct = remoteRepository.getSellerProduct(it)

                if (getProduct.isSuccessful) {
                    _listProduct.postValue(Resource.success(getProduct.body()))
                } else {
                    _listProduct.postValue(Resource.error("failed to get product",null))
                }
            }
        }
    }

    fun getSellerOrder() {
        viewModelScope.launch {
            dataStore.getAccessToken().collect {
                val getSellerOrder = remoteRepository.getSellerOrder(it,null)

                if (getSellerOrder.isSuccessful) {
                    _listDiminati.postValue(Resource.success(getSellerOrder.body()))
                } else {
                    _listDiminati.postValue(Resource.error("failed to get list",null))
                }
            }
        }
    }

    fun getSellerOrderById(idOrder: Int) {
        viewModelScope.launch {
            dataStore.getAccessToken().collect {
                val getSellerOrderId = remoteRepository.getSellerOrderId(it,idOrder)

                if (getSellerOrderId.isSuccessful) {
                    _diminatiById.postValue(Resource.success(getSellerOrderId.body()))
                } else {
                    _diminatiById.postValue(Resource.error("failed to get data...",null))
                }
            }
        }
    }

    fun patchOrder(idOrder: Int,status: String) {
        viewModelScope.launch {
            val statusUpdate = status.toRequestBody("text/plain".toMediaTypeOrNull())
            dataStore.getAccessToken().collect {
                val patchOrder = remoteRepository.patchOrder(it,idOrder,statusUpdate)

                if (patchOrder.isSuccessful) {
                    _patchOrder.postValue(Resource.success(patchOrder.body()))
                } else {
                    _patchOrder.postValue(Resource.error("error to update order",null))
                }
            }
        }
    }

}