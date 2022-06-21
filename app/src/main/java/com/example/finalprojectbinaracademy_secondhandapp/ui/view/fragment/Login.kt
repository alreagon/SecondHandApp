package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.api.ApiClient
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.LoginRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.LoginResponse
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentLoginBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.util.*

class Login : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
     private val binding get() = _binding!!

    // get shared preferences
    private val sharedPrefFile = "logininfo"

    // get shared preferences
    lateinit var dataStoreManager : DataStoreManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_login, container, false)
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get datastore
        dataStoreManager = DataStoreManager(requireContext())

        binding.btnShowPwd.setOnClickListener{
            showPassword(binding.etPassword, binding.btnShowPwd)
        }

        binding.btnLogin.setOnClickListener{
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
//            val loginkey : String
            if (email != "" && password != ""){
                loginUser(email,password)
            }else if (email == ""){
                Toast.makeText(requireContext(), "Please imput your email", Toast.LENGTH_LONG).show()
            }else if (password == ""){
                Toast.makeText(requireContext(), "Please imput your passsword", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(requireContext(), "Please imput your passsword and email", Toast.LENGTH_LONG).show()
            }

        }

        binding.btnGotoRegister.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_login_to_register)
        }
    }


    private fun loginUser(email : String, password : String){
        ApiClient.instance.postUser(email,password)
            .enqueue(object : retrofit2.Callback<LoginResponse>{

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful){
                        when(response.code()){
                            201 -> {
                                GlobalScope.launch {
                                    dataStoreManager.loginUserData(
                                        response.body()!!.email,
                                        password,
                                        response.body()!!.accessToken
                                    )
                                }
                                Navigation.findNavController(view!!).navigate(R.id.action_login_to_home2)
                            }

                            401 -> {
                                Toast.makeText(requireContext(), response.message(), Toast.LENGTH_LONG).show()
                            }

                            500 -> {
                                Toast.makeText(requireContext(), response.message(), Toast.LENGTH_LONG).show()
                            }

                            else -> {
                                Toast.makeText(requireContext(), "Uknown Error", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }else{
                        Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_LONG).show()
                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "No response", Toast.LENGTH_LONG).show()
                }

            })


    }

    fun showPassword(editText: EditText, imageView: ImageView) {
        val hidden = PasswordTransformationMethod.getInstance()
        val show = HideReturnsTransformationMethod.getInstance()

        if (editText.transformationMethod == hidden) {
            editText.transformationMethod = show
            imageView.setImageResource(R.drawable.ic_eye_off)
        } else {
            editText.transformationMethod = hidden
            imageView.setImageResource(R.drawable.ic_eye)
        }
    }

}