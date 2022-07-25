package com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.local.db.LocalDao
import com.example.finalprojectbinaracademy_secondhandapp.data.local.db.LocalDaoHelperImpl
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.ChangePasswordResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository.RemoteRepository
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiHelperImpl
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiService
import com.example.finalprojectbinaracademy_secondhandapp.utils.NetworkHelper
import com.example.finalprojectbinaracademy_secondhandapp.utils.Resource
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
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
class EditProfileViewModelTest {

    private lateinit var apiHelperImpl: ApiHelperImpl
    private lateinit var localDaoHelperImpl: LocalDaoHelperImpl
    private lateinit var remoteRepository: RemoteRepository
    private lateinit var networkHelper: NetworkHelper
    private lateinit var dataStoreManager: DataStoreManager

    private lateinit var editProfileViewModel: EditProfileViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: ApiService
    @Mock
    private lateinit var localDao: LocalDao
    @Mock
    private lateinit var changePassObserver: Observer<Resource<ChangePasswordResponse>>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val context = ApplicationProvider.getApplicationContext<Context>()

        apiHelperImpl = ApiHelperImpl(apiService)
        localDaoHelperImpl = LocalDaoHelperImpl(localDao)
        remoteRepository = RemoteRepository(apiHelperImpl, localDaoHelperImpl)
        networkHelper = NetworkHelper(context)
        dataStoreManager = DataStoreManager(context)

        editProfileViewModel = EditProfileViewModel(remoteRepository,dataStoreManager,networkHelper)

    }

    @Test
    fun changePasswordSuccess() {
        val currentPass = "".toRequestBody("text/plain".toMediaTypeOrNull())
        val newPass = "".toRequestBody("text/plain".toMediaTypeOrNull())
        val confirmPass = "".toRequestBody("text/plain".toMediaTypeOrNull())

        runBlocking {
            dataStoreManager.setAccessToken("accssTkn")
            Mockito.`when`(apiService.changePassword("accssTkn",currentPass,newPass,confirmPass ))
                .thenReturn(Response.success(ChangePasswordResponse("","")))

            editProfileViewModel.changePassword("","","")
            editProfileViewModel.changePass.observeForever(changePassObserver)
            Mockito.verify(apiService).changePassword("accssTkn",currentPass, newPass, confirmPass)
            Mockito.verify(changePassObserver).onChanged(
                Resource.success(ChangePasswordResponse("",""))
            )
            editProfileViewModel.changePass.removeObserver(changePassObserver)
        }
    }

}