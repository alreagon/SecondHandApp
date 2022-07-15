package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetResponseProductId
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.PostBuyerOrderRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.PostBuyerOrderResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import kotlinx.coroutines.launch

class BuyerDetailViewModel(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val _getProductId = MutableLiveData<GetResponseProductId>()
    val getproductId: LiveData<GetResponseProductId>
        get() = _getProductId

    private val _getBuyerOrder = MutableLiveData<PostBuyerOrderResponse>()
    val getBuyerOrder: LiveData<PostBuyerOrderResponse>
        get() = _getBuyerOrder

    fun BuyerDetailProdukId(buyerId: Int) {
        viewModelScope.launch {
            val productId = remoteRepository.getBuyerProductId(buyerId)
            if (productId.code() == 200) {
                _getProductId.postValue(productId.body())
            } else {
                Log.d("response error", "get product error")
            }
        }

    }



}