package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.local.db.LocalDao
import com.example.finalprojectbinaracademy_secondhandapp.data.local.db.LocalDaoHelperImpl
import com.example.finalprojectbinaracademy_secondhandapp.data.local.db.MyDatabase
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.LoginRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.LoginResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiHelperImpl
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiService
import com.example.finalprojectbinaracademy_secondhandapp.utils.NetworkHelper
import com.example.finalprojectbinaracademy_secondhandapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.robolectric.RobolectricTestRunner
import retrofit2.Response
import java.util.regex.Matcher

@RunWith(AndroidJUnit4::class)
class AuthViewModelTest {

    private lateinit var apiHelperImpl: ApiHelperImpl
    private lateinit var localDaoHelperImpl: LocalDaoHelperImpl
    private lateinit var remoteRepository: RemoteRepository
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var networkHelper: NetworkHelper
    private lateinit var request: RegisterRequest
    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var authViewModel: AuthViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: ApiService
    @Mock
    private lateinit var localDao: LocalDao
    @Mock
    private lateinit var regisObserver: Observer<Resource<RegisterResponse>>
    @Mock
    private lateinit var loginObserver: Observer<Resource<LoginResponse>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        apiHelperImpl = ApiHelperImpl(apiService)
        val context = ApplicationProvider.getApplicationContext<Context>()
        localDaoHelperImpl = LocalDaoHelperImpl(localDao)
        remoteRepository = RemoteRepository(apiHelperImpl,localDaoHelperImpl)
        dataStoreManager = DataStoreManager(context)
        networkHelper = NetworkHelper(context)

        authViewModel = AuthViewModel(remoteRepository,dataStoreManager,networkHelper)
    }

    @Test
    fun registerSuccessTest() {
        request = RegisterRequest(
            "test_address",
            "test_city",
            "user23121@gmail.com",
            "test_fullname",
            null,
            "test password",
            "test phone number"
        )

        runBlocking {
            Mockito.`when`(apiService.registerUser(request)).thenReturn(Response.success(
                RegisterResponse("","","","","",0,null,"","","")
            ))

            authViewModel.userRegister(request)
            authViewModel.userRegis.observeForever(regisObserver)
            Mockito.verify(apiService).registerUser(request)
            Mockito.verify(regisObserver).onChanged(Resource.success(
                RegisterResponse("","","","","",0,null,"","","")
            ))
            authViewModel.userRegis.removeObserver(regisObserver)
        }
    }

    @Test
    fun registerFailedTest() {
        request = RegisterRequest(
            "test_address",
            "test_city",
            "user23121@gmail.com",
            "test_fullname",
            null,
            "test password",
            "test phone number"
        )

        runBlocking {
            Mockito.`when`(apiService.registerUser(request))
                .thenReturn(Response.error(401,"error response".toResponseBody("text/plain".toMediaTypeOrNull())))

            authViewModel.userRegister(request)
            authViewModel.userRegis.observeForever(regisObserver)
            Mockito.verify(apiService).registerUser(request)
            Mockito.verify(regisObserver).onChanged(Resource.error("Failed to registration",null))
            authViewModel.userRegis.removeObserver(regisObserver)

        }
    }

    @Test
    fun loginSuccessTest() {
        val loginRequest = LoginRequest(
            "pedaganghandal@gmail.com",
            "Masukaja800"
        )

        runBlocking {
            Mockito.`when`(apiService.loginUser(loginRequest)).thenReturn(Response.success(LoginResponse("","",0,"")))

            authViewModel.userLogin(loginRequest)
            authViewModel.userLogin.observeForever(loginObserver)
            Mockito.verify(apiService).loginUser(loginRequest)
            Mockito.verify(loginObserver).onChanged(Resource.success(LoginResponse("","",0,"")))
            authViewModel.userLogin.removeObserver(loginObserver)
        }
    }

    @Test
    fun loginFailedTest() {
        val loginRequest = LoginRequest(
            "pedaganghandal@gmail.com",
            "Masukaja800"
        )
        runBlocking {
            Mockito.`when`(apiService.loginUser(loginRequest))
                .thenReturn(Response.error(400,"error response".toResponseBody("text/plain".toMediaTypeOrNull())))

            authViewModel.userLogin(loginRequest)
            authViewModel.userLogin.observeForever(loginObserver)
            Mockito.verify(apiService).loginUser(loginRequest)
            Mockito.verify(loginObserver).onChanged(Resource.error("Please check your credentials..",null))
            authViewModel.userLogin.removeObserver(loginObserver)
        }
    }

}