package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.local.db.LocalDao
import com.example.finalprojectbinaracademy_secondhandapp.data.local.db.LocalDaoHelperImpl
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.SellerOrder
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.SellerProduct
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.PostProductResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiHelperImpl
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiService
import com.example.finalprojectbinaracademy_secondhandapp.utils.NetworkHelper
import com.example.finalprojectbinaracademy_secondhandapp.utils.Resource
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(AndroidJUnit4::class)
class SaleListViewModelTest{

    private lateinit var apiHelperImpl: ApiHelperImpl
    private lateinit var localDaoHelperImpl: LocalDaoHelperImpl
    private lateinit var remoteRepository: RemoteRepository
    private lateinit var networkHelper: NetworkHelper
    private lateinit var dataStoreManager: DataStoreManager

    private lateinit var saleListViewModel: SaleListViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: ApiService
    @Mock
    private lateinit var localDao: LocalDao
    @Mock
    private lateinit var sellerProductObserver: Observer<Resource<List<SellerProduct>>>
    @Mock
    private lateinit var sellerOrderObserver: Observer<Resource<List<SellerOrder>>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        val context = ApplicationProvider.getApplicationContext<Context>()

        apiHelperImpl = ApiHelperImpl(apiService)
        localDaoHelperImpl = LocalDaoHelperImpl(localDao)
        remoteRepository = RemoteRepository(apiHelperImpl, localDaoHelperImpl)
        networkHelper = NetworkHelper(context)
        dataStoreManager = DataStoreManager(context)

        saleListViewModel = SaleListViewModel(remoteRepository,dataStoreManager,networkHelper)
    }

    @Test
    fun getSellerProductSuccess() {
        runBlocking {
            dataStoreManager.setAccessToken("accessToken")
            Mockito.`when`(apiService.getSellerProduct("accessToken"))
                .thenReturn(Response.success(emptyList()))

            saleListViewModel.getSellerProduct()
            saleListViewModel.listProduct.observeForever(sellerProductObserver)
            Mockito.verify(apiService).getSellerProduct("accessToken")
            Mockito.verify(sellerProductObserver).onChanged(Resource.success(emptyList()))
            saleListViewModel.listProduct.removeObserver(sellerProductObserver)
        }
    }

    @Test
    fun getSellerProductFailed() {
        runBlocking {
            dataStoreManager.setAccessToken("accessToken")
            Mockito.`when`(apiService.getSellerProduct("accessToken"))
                .thenReturn(Response.error(400,"error".toResponseBody("text/plain".toMediaTypeOrNull())))

            saleListViewModel.getSellerProduct()
            saleListViewModel.listProduct.observeForever(sellerProductObserver)
            Mockito.verify(apiService).getSellerProduct("accessToken")
            Mockito.verify(sellerProductObserver).onChanged(Resource.error("failed to get product",null))
            saleListViewModel.listProduct.removeObserver(sellerProductObserver)
        }
    }

    @Test
    fun getSellerSoldSuccess() {
        runBlocking {
            dataStoreManager.setAccessToken("accessToken")
            Mockito.`when`(apiService.getSellerOrder("accessToken",""))
                .thenReturn(Response.success(emptyList()))

            saleListViewModel.getSellerSold("")
            saleListViewModel.listSold.observeForever(sellerOrderObserver)
            Mockito.verify(apiService).getSellerOrder("accessToken","")
            Mockito.verify(sellerOrderObserver).onChanged(Resource.success(emptyList()))
            saleListViewModel.listSold.removeObserver(sellerOrderObserver)
        }
    }

    @Test
    fun getSellerSoldFailed() {
        runBlocking {
            dataStoreManager.setAccessToken("accessToken")
            Mockito.`when`(apiService.getSellerOrder("accessToken",""))
                .thenReturn(Response.error(400,"error".toResponseBody("text/plain".toMediaTypeOrNull())))

            saleListViewModel.getSellerSold("")
            saleListViewModel.listSold.observeForever(sellerOrderObserver)
            Mockito.verify(apiService).getSellerOrder("accessToken","")
            Mockito.verify(sellerOrderObserver).onChanged(Resource.error("failed get data", null))
            saleListViewModel.listSold.removeObserver(sellerOrderObserver)
        }
    }
}