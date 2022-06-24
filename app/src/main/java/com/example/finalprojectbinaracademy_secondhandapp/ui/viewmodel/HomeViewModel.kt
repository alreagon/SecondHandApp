package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiService
import com.example.finalprojectbinaracademy_secondhandapp.utils.NetworkHelper
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class HomeViewModel(
    private val remoteRepository: RemoteRepository,
    private val dataStore: DataStoreManager

) : ViewModel() {

    private val _getProduct = MutableLiveData<GetProductResponseItem>()
    val getproduct : LiveData<GetProductResponseItem>
        get() = _getProduct

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading



    fun productHome() {
        viewModelScope.launch {
            val product = remoteRepository.getBuyerProduct(279)
            if (product.code() == 200) {
                _getProduct.postValue(product.body())
            } else {
                Log.d("response error", "get product error")
            }
        }

}
}