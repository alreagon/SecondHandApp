package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.local.db.LocalDao
import com.example.finalprojectbinaracademy_secondhandapp.data.local.db.LocalDaoHelperImpl
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.CategoryResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.CategoryResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiHelperImpl
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiService
import com.example.finalprojectbinaracademy_secondhandapp.utils.NetworkHelper
import com.example.finalprojectbinaracademy_secondhandapp.utils.Resource
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.io.File

@RunWith(AndroidJUnit4::class)
class SellViewModelTest {

    private lateinit var apiHelperImpl: ApiHelperImpl
    private lateinit var localDaoHelperImpl: LocalDaoHelperImpl
    private lateinit var remoteRepository: RemoteRepository
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var networkHelper: NetworkHelper

    private lateinit var sellViewModel: SellViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: ApiService
    @Mock
    private lateinit var localDao: LocalDao
    @Mock
    private lateinit var listCategoryObserver: Observer<Resource<CategoryResponse>>
    @Mock
    private lateinit var userObserver: Observer<Resource<RegisterResponse>>
    @Mock
    private lateinit var categoryObserver: Observer<Resource<CategoryResponseItem>>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val context = ApplicationProvider.getApplicationContext<Context>()

        apiHelperImpl = ApiHelperImpl(apiService)
        localDaoHelperImpl = LocalDaoHelperImpl(localDao)
        remoteRepository = RemoteRepository(apiHelperImpl, localDaoHelperImpl)
        dataStoreManager = DataStoreManager(context)
        networkHelper = NetworkHelper(context)

        sellViewModel = SellViewModel(remoteRepository, dataStoreManager, networkHelper)
    }

    @Test
    fun getCategorySuccess() {
        runBlocking {
            Mockito.`when`(apiService.getCategory()).thenReturn(Response.success(CategoryResponse()))

            sellViewModel.getCategory()
            sellViewModel.listCategory.observeForever(listCategoryObserver)
            Mockito.verify(apiService).getCategory()
            Mockito.verify(listCategoryObserver).onChanged(Resource.success(CategoryResponse()))
            sellViewModel.listCategory.removeObserver(listCategoryObserver)

        }
    }

    @Test
    fun getCategoryFailed() {
        runBlocking {
            Mockito.`when`(apiService.getCategory())
                .thenReturn(Response.error(400,"error response".toResponseBody("text/plain".toMediaTypeOrNull())))

            sellViewModel.getCategory()
            sellViewModel.listCategory.observeForever(listCategoryObserver)
            Mockito.verify(apiService).getCategory()
            Mockito.verify(listCategoryObserver).onChanged(Resource.error("failed to get category",null))
            sellViewModel.listCategory.removeObserver(listCategoryObserver)
        }
    }

    @Test
    fun getCategoryByIdSuccess() {
        runBlocking {
            Mockito.`when`(apiService.getCategoryById(0))
                .thenReturn(Response.success(CategoryResponseItem("",0,"","")))

            sellViewModel.getCategoryById(0)
            sellViewModel.category.observeForever(categoryObserver)
            Mockito.verify(apiService).getCategoryById(0)
            Mockito.verify(categoryObserver).onChanged(Resource.success(CategoryResponseItem("",0,"","")))
            sellViewModel.category.removeObserver(categoryObserver)
        }
    }

    @Test
    fun getCategoryByIdFailed() {
        runBlocking {
            Mockito.`when`(apiService.getCategoryById(0))
                .thenReturn(Response.error(401,"error".toResponseBody("text/plain".toMediaTypeOrNull())))

            sellViewModel.getCategoryById(0)
            sellViewModel.category.observeForever(categoryObserver)
            Mockito.verify(apiService).getCategoryById(0)
            Mockito.verify(categoryObserver).onChanged(Resource.error("failed to get category",null))
            sellViewModel.category.removeObserver(categoryObserver)
        }
    }

    @Test
    fun getUserSuccess() {
        runBlocking {
            dataStoreManager.setAccessToken("accsstkn")

            Mockito.`when`(apiService.getUser("accsstkn")).thenReturn(Response.success(
                RegisterResponse("","","","","",0,null,"","","")
            ))

            sellViewModel.getUserByAccessToken()
            sellViewModel.user.observeForever(userObserver)
            Mockito.verify(apiService).getUser("accsstkn")
            Mockito.verify(userObserver).onChanged(Resource.success(
                RegisterResponse("","","","","",0,null,"","","")
            ))
            sellViewModel.user.removeObserver(userObserver)
        }
    }

    @Test
    fun getUSerFailed() {
        runBlocking {
            dataStoreManager.setAccessToken("accsstkn")

            Mockito.`when`(apiService.getUser("accsstkn"))
                .thenReturn(Response.error(401,"error".toResponseBody("text/plain".toMediaTypeOrNull())))

            sellViewModel.getUserByAccessToken()
            sellViewModel.user.observeForever(userObserver)
            Mockito.verify(apiService).getUser("accsstkn")
            Mockito.verify(userObserver).onChanged(Resource.error(
                "failed to get user",null
            ))
            sellViewModel.user.removeObserver(userObserver)
        }
    }

    @Test
    fun postProduct() {
        runBlocking {
            dataStoreManager.setAccessToken("accsstkn")
//            Mockito.`when`(apiService.sellerPostProduct(
//                "accsstkn",
//                "".toRequestBody("text/plain".toMediaTypeOrNull()),
//                "".toRequestBody("text/plain".toMediaTypeOrNull()),
//                "".toRequestBody("text/plain".toMediaTypeOrNull()),
//                "".toRequestBody("text/plain".toMediaTypeOrNull()),
//                "".toRequestBody("text/plain".toMediaTypeOrNull()),
////                MultipartBody.Part.createFormData("image","",File("image.jpg").createNewFile()))
//            )
        }
    }
}