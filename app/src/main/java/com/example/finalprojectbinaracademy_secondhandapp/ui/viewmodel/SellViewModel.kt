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
import com.example.finalprojectbinaracademy_secondhandapp.utils.NetworkHelper
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
    private val dataStoreManager: DataStoreManager,
    private val networkHelper: NetworkHelper
): ViewModel() {

    private val _listCategory = MutableLiveData<Resource<CategoryResponse>>()
    val listCategory: LiveData<Resource<CategoryResponse>> get() = _listCategory

    private val _category = MutableLiveData<Resource<CategoryResponseItem>>()
    val category : LiveData<Resource<CategoryResponseItem>> get() = _category

    private val _user = MutableLiveData<Resource<RegisterResponse>>()
    val user: LiveData<Resource<RegisterResponse>> get() = _user

    private val _postProduct = MutableLiveData<Resource<PostProductResponse>>()
    val postProduct: LiveData<Resource<PostProductResponse>> get() = _postProduct

    fun getStatusLogin() : LiveData<Boolean> {
        return  dataStoreManager.getStatusLogin().asLiveData()
    }

    fun getCategory() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                try {
                    val response = remoteRepository.getCategory()
                    val codeResponse = response.code()

                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            _listCategory.postValue(Resource.success(response.body()))
                        }
                    } else {
                        _listCategory.postValue(Resource.error("failed to get category",null))
                    }
                } catch (e:Exception) {
                    _listCategory.postValue(Resource.error(e.message.toString(),null))
                }
            } else {
                _listCategory.postValue(Resource.error("please check your internet connection...",null))
            }
        }
    }

    fun getCategoryById(id: Int) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                try {
                    val response = remoteRepository.getCategoryById(id)
                    val body = response.body()

                    if (response.isSuccessful) {
                        body?.let { data ->
                            _category.postValue(Resource.success(data))
                        }
                    } else {
                        _category.postValue(Resource.error("failed to get category",null))
                    }
                } catch (e:Exception) {
                    _category.postValue(Resource.error(e.message.toString(),null))
                }
            } else {
                _category.postValue(Resource.error("please check your internet connection...",null))
            }
        }
    }

    fun getUserByAccessToken() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                try {
                    dataStoreManager.getAccessToken().collect { accessToken ->
                        val getUser = remoteRepository.getUser(accessToken)
                        val body = getUser.body()

                        if (getUser.isSuccessful) {
                            body?.let {
                                _user.postValue(Resource.success(it))
                            }
                        } else {
                            _user.postValue(Resource.error("failed to get user",null))
                        }
                    }
                } catch (e:Exception) {
                    _user.postValue(Resource.error(e.message.toString(),null))
                }
            } else {
                _user.postValue(Resource.error("please check your internet connection...",null))
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
            if (networkHelper.isNetworkConnected()) {
                try {
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
                        } else if (postProduct.code() == 400) {
                            _postProduct.postValue(Resource.error("Kamu sudah mencapai maksimal post product...",null))
                        } else {
                            _postProduct.postValue(Resource.error("failed to post product",null))
                        }
                    }
                } catch (e:Exception) {
                    _postProduct.postValue(Resource.error(e.message.toString(),null))
                }
            } else {
                _postProduct.postValue(Resource.error("please check your internet connection...",null))
            }
        }
    }
}