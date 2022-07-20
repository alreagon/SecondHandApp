package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import android.util.Log
import androidx.datastore.dataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetResponseProductId
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.PostBuyerOrderRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.PostBuyerOrderResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import com.example.finalprojectbinaracademy_secondhandapp.utils.Resource
import kotlinx.coroutines.launch

class BuyerDetailViewModel(
    private val remoteRepository: RemoteRepository,
    private val dataStore: DataStoreManager
) : ViewModel() {

    private val _getProductId = MutableLiveData<GetResponseProductId>()
    val getproductId: LiveData<GetResponseProductId>
        get() = _getProductId

    private val _postBuyerOrder = MutableLiveData<Resource<PostBuyerOrderResponse>>()
    val postBuyerOrder: LiveData<Resource<PostBuyerOrderResponse>>
        get() = _postBuyerOrder

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
    fun PostBuyerOrder(request: PostBuyerOrderRequest){
        viewModelScope.launch {
            dataStore.getAccessToken().collect{
                val postBuyerOrder = remoteRepository.postBuyerOrder(it,request)

            if (postBuyerOrder.isSuccessful){
                _postBuyerOrder.postValue(Resource.success(postBuyerOrder.body()))
            }else   {
                _postBuyerOrder.postValue(Resource.error("error PostBuyerOrder", null))
            }
            }
        }
    }



}