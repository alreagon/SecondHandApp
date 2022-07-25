package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.runtime.saveable.autoSaver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.local.db.LocalDao
import com.example.finalprojectbinaracademy_secondhandapp.data.local.db.LocalDaoHelperImpl
import com.example.finalprojectbinaracademy_secondhandapp.data.local.db.MyDatabase
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.Notification
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.ReadNotificationResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiHelperImpl
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiService
import com.example.finalprojectbinaracademy_secondhandapp.utils.NetworkHelper
import com.example.finalprojectbinaracademy_secondhandapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
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
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@RunWith(AndroidJUnit4::class)
class NotificationViewModelTest {

    private lateinit var apiHelperImpl: ApiHelperImpl
    private lateinit var localDaoHelperImpl: LocalDaoHelperImpl
    private lateinit var remoteRepository: RemoteRepository
    private lateinit var db: MyDatabase
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var networkHelper: NetworkHelper
    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var notificationViewModel: NotificationViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: ApiService
    @Mock
    private lateinit var localDao: LocalDao
    @Mock
    private lateinit var observer: Observer<Resource<List<Notification>>>
    @Mock
    private lateinit var observerReadNotif: Observer<Resource<ReadNotificationResponse>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        apiHelperImpl = ApiHelperImpl(apiService)
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context,MyDatabase::class.java).allowMainThreadQueries().build()
        localDaoHelperImpl = LocalDaoHelperImpl(localDao)
        remoteRepository = RemoteRepository(apiHelperImpl,localDaoHelperImpl)
        dataStoreManager = DataStoreManager(context)
        networkHelper = NetworkHelper(context)

        notificationViewModel = NotificationViewModel(remoteRepository,dataStoreManager,networkHelper)

    }

    @Test
    fun getNotifSuccess() {
        runBlocking {
            dataStoreManager.setAccessToken("accessToken")
            Mockito.`when`(apiService.getNotification("accessToken")).thenReturn(emptyList())

            notificationViewModel.getNotif()
            notificationViewModel.listNotif.observeForever(observer)
            Mockito.verify(apiService).getNotification("accessToken")
            Mockito.verify(observer).onChanged(Resource.success(emptyList()))
            notificationViewModel.listNotif.removeObserver(observer)
        }
    }

    @Test
    fun readNotifSuccess() {
        runBlocking {
            dataStoreManager.setAccessToken("accessToken")
            Mockito.`when`(apiService.readNotification("accessToken",0))
                .thenReturn(Response.success(ReadNotificationResponse(
                    "","","","",0,"",0,"",
                    true,0,"","","",""))
                )

            notificationViewModel.readNotification(0)
            notificationViewModel.readNotification.observeForever(observerReadNotif)
            Mockito.verify(apiService).readNotification("accessToken",0)
            Mockito.verify(observerReadNotif).onChanged(Resource.success(ReadNotificationResponse(
                "","","","",0,"",0,"",
                true,0,"","","",""))
            )
            notificationViewModel.readNotification.removeObserver(observerReadNotif)
        }
    }

    @Test
    fun readNotifFailed() {
        runBlocking {
            dataStoreManager.setAccessToken("accessToken")
            Mockito.`when`(apiService.readNotification("accessToken",0))
                .thenReturn(Response.error(400,"error".toResponseBody("text/plain".toMediaTypeOrNull())))

            notificationViewModel.readNotification(0)
            notificationViewModel.readNotification.observeForever(observerReadNotif)
            Mockito.verify(apiService).readNotification("accessToken",0)
            Mockito.verify(observerReadNotif).onChanged(Resource.error("faid to read notification",null))
            notificationViewModel.readNotification.removeObserver(observerReadNotif)
        }
    }

}