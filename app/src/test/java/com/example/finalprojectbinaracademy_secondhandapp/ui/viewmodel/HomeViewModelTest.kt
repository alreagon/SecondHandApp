package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.local.db.LocalDao
import com.example.finalprojectbinaracademy_secondhandapp.data.local.db.LocalDaoHelperImpl
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.Banner
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.Product
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
class HomeViewModelTest {

    private lateinit var apiHelperImpl: ApiHelperImpl
    private lateinit var localDaoHelperImpl: LocalDaoHelperImpl
    private lateinit var remoteRepository: RemoteRepository
    private lateinit var networkHelper: NetworkHelper
    private lateinit var dataStoreManager: DataStoreManager

    private lateinit var homeViewModel: HomeViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: ApiService
    @Mock
    private lateinit var localDao: LocalDao
    @Mock
    private lateinit var bannerObserver: Observer<Resource<List<Banner>>>
    @Mock
    private lateinit var productObserver: Observer<Resource<List<Product>>>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val context = ApplicationProvider.getApplicationContext<Context>()

        apiHelperImpl = ApiHelperImpl(apiService)
        localDaoHelperImpl = LocalDaoHelperImpl(localDao)
        remoteRepository = RemoteRepository(apiHelperImpl, localDaoHelperImpl)
        networkHelper = NetworkHelper(context)
        dataStoreManager = DataStoreManager(context)

        homeViewModel = HomeViewModel(remoteRepository,dataStoreManager,networkHelper)
    }

    @Test
    fun getBannerSuccess() {
        runBlocking {
            Mockito.`when`(apiService.getBanner()).thenReturn(Response.success(emptyList()))

            homeViewModel.bannerHome()
            homeViewModel.getBannerHome.observeForever(bannerObserver)
            Mockito.verify(apiService,Mockito.times(2)).getBanner()
            Mockito.verify(bannerObserver).onChanged(Resource.success(emptyList()))
            homeViewModel.getBannerHome.removeObserver(bannerObserver)
        }
    }

    @Test
    fun getBannerFailed() {
        runBlocking {
            Mockito.`when`(apiService.getBanner())
                .thenReturn(Response.error(400,"error".toResponseBody("text".toMediaTypeOrNull())))

            homeViewModel.bannerHome()
            homeViewModel.getBannerHome.observeForever(bannerObserver)
            Mockito.verify(apiService,Mockito.times(2)).getBanner()
            Mockito.verify(bannerObserver).onChanged(Resource.error("failed to get data banner",null))
            homeViewModel.getBannerHome.removeObserver(bannerObserver)
        }
    }

    @Test
    fun getProductSuccess() {
        val params = HashMap<String,String>()
        params["page"] = "1"
        params["per_page"] = ""
        runBlocking {
            Mockito.`when`(apiService.getProductBoundResource(params))
                .thenReturn(emptyList())

            homeViewModel.getProductOfflineAll()
            homeViewModel.gettProductOffline.observeForever(productObserver)
            Mockito.verify(apiService).getProductBoundResource(params)
            Mockito.verify(productObserver).onChanged(Resource.success(emptyList()))
            homeViewModel.gettProductOffline.removeObserver(productObserver)
        }
    }

}