package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.CategoryResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.CategoryResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.PostProductResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import com.example.finalprojectbinaracademy_secondhandapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class SellViewModel(
    private val remoteRepository: RemoteRepository,
    private val dataStoreManager: DataStoreManager
): ViewModel() {

    private val _listCategory = MutableLiveData<CategoryResponse>()
    val listCategory: LiveData<CategoryResponse> get() = _listCategory

    private val _category = MutableLiveData<CategoryResponseItem>()
    val category : LiveData<CategoryResponseItem> get() = _category

    private val _user = MutableLiveData<RegisterResponse>()
    val user: LiveData<RegisterResponse> get() = _user

    private val _postProduct = MutableLiveData<Resource<PostProductResponse>>()
    val postProduct: LiveData<Resource<PostProductResponse>> get() = _postProduct

    fun getStatusLogin() : LiveData<Boolean> {
        return  dataStoreManager.getStatusLogin().asLiveData()
    }

    fun getCategory() {
        viewModelScope.launch {
            val response = remoteRepository.getCategory()
            val codeResponse = response.code()

            if (codeResponse == 200 ) {
                if (response.body() != null) {
                    _listCategory.postValue(response.body())
                }
            } else {
                Log.d("code != 200", "failed get notification")
            }
        }
    }

    fun getCategoryById(id: Int) {
        viewModelScope.launch {
            val response = remoteRepository.getCategoryById(id)
            val codeResponse = response.code()
            val body = response.body()

            if (codeResponse == 200) {
                body?.let { data ->
                    _category.postValue(data)
                }
            } else {
                Log.d("code != 200", "failed get notification")
            }
        }
    }

    fun getUserByAccessToken() {
        viewModelScope.launch {
            dataStoreManager.getAccessToken().collect { accessToken ->
                val getUser = remoteRepository.getUser(accessToken)
                val code = getUser.code()
                val body = getUser.body()

                if (code == 200) {
                    body?.let {
                        _user.postValue(it)
                    }
                } else {
                    Log.d("response error", "user register error")
                }
            }
        }
    }

    fun postProduct(
        name: String,
        description: String,
        basePrice : Int,
        categoryIds : List<Int>,
        location: String,
        productImage: File
    ) {
        viewModelScope.launch {
            val requestFile = productImage.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val requestImage = MultipartBody.Part.createFormData("image",productImage.name,requestFile)
            val name = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val desc = description.toRequestBody("text/plain".toMediaTypeOrNull())
            val basePrice = basePrice.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val location = location.toRequestBody("text/plain".toMediaTypeOrNull())
            val category = categoryIds[0].toString().toRequestBody("text/plain".toMediaTypeOrNull())

            dataStoreManager.getAccessToken().collect { acstkn ->
                val postProduct = remoteRepository.postProduct(acstkn,name,desc,basePrice,category,location,requestImage)

                if (postProduct.isSuccessful) {
                    _postProduct.postValue(Resource.success(postProduct.body()))
                } else {
                    _postProduct.postValue(Resource.error("Failed to post product",null))
                }
            }

        }
    }
}