package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.LoginRequest
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentLoginBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.AuthViewModel
import com.example.finalprojectbinaracademy_secondhandapp.utils.PasswordUtils
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer
import java.util.*

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModel()

    // get shared preferences
    private val sharedPrefFile = "logininfo"

    // get shared preferences
    lateinit var dataStoreManager : DataStoreManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvGoToRegis.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegister()
            findNavController().navigate(action)
        }

        binding.btnLogin.setOnClickListener{
//            val email = binding.etEmail.text.toString()
//            val password = binding.etPassword.text.toString()
////            val loginkey : String
//            if (email != "" && password != ""){
//                loginUser(email,password)
//            }else if (email == ""){
//                Toast.makeText(requireContext(), "Please imput your email", Toast.LENGTH_LONG).show()
//            }else if (password == ""){
//                Toast.makeText(requireContext(), "Please imput your passsword", Toast.LENGTH_LONG).show()
//            }else{
//                Toast.makeText(requireContext(), "Please imput your passsword and email", Toast.LENGTH_LONG).show()
//            }
            binding.progressBar.visibility = View.VISIBLE
            loginUser()
        }

//        binding.btnGotoRegister.setOnClickListener {
//            Navigation.findNavController(view).navigate(R.id.action_login_to_register)
//        }
    }

    private fun loginUser() {
        val email = binding.etEmail.text.toString()
        val pass = binding.etPassword.text.toString()

        if (inputCheck(email,pass)) {
            val request = LoginRequest(email,pass)

            authViewModel.userLogin(request)

            authViewModel.userLogin.observe(requireActivity(), Observer { userLogin ->
                if (userLogin.id == 0) {
                    Toast.makeText(requireContext(),"Login Failed..", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                } else {
//                    val action = LoginFragmentDirections.actionLoginFragmentToEditProfile()
                    val action = LoginFragmentDirections.actionLoginFragmentToBuyerNotification()
                    findNavController().navigate(action)
                    Toast.makeText(requireContext(),"Login Successfully", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            validateErrorInput(email,pass)

        }

    }

    private fun validateErrorInput(email:String,password:String) {

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.wrapEmail.error = "Format email salah"
        } else {
            binding.wrapEmail.error = null
        }

        if (TextUtils.isEmpty(password)) {
            binding.wrapPassword.error = "Masukkan password"
        } else {
            binding.wrapPassword.error = null
        }
    }

    private fun inputCheck(email:String,pass: String): Boolean {
        return !(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || !PasswordUtils().validate(pass)
                || !Patterns.EMAIL_ADDRESS.matcher(email).matches() )
    }

//    private fun loginUser(email : String, password : String){
//        ApiClient.instance.postUser(email,password)
//            .enqueue(object : retrofit2.Callback<LoginResponse>{
//
//                override fun onResponse(
//                    call: Call<LoginResponse>,
//                    response: Response<LoginResponse>
//                ) {
//                    if (response.isSuccessful){
//                        when(response.code()){
//                            201 -> {
//                                GlobalScope.launch {
//                                    dataStoreManager.loginUserData(
//                                        response.body()!!.email,
//                                        password,
//                                        response.body()!!.accessToken
//                                    )
//                                }
//                                Navigation.findNavController(view!!).navigate(R.id.action_login_to_home2)
//                            }
//
//                            401 -> {
//                                Toast.makeText(requireContext(), response.message(), Toast.LENGTH_LONG).show()
//                            }
//
//                            500 -> {
//                                Toast.makeText(requireContext(), response.message(), Toast.LENGTH_LONG).show()
//                            }
//
//                            else -> {
//                                Toast.makeText(requireContext(), "Uknown Error", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//
//                    }else{
//                        Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_LONG).show()
//                    }
//
//                }
//
//                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                    Toast.makeText(requireContext(), "No response", Toast.LENGTH_LONG).show()
//                }
//
//            })
//
//
//    }
//
//    fun showPassword(editText: EditText, imageView: ImageView) {
//        val hidden = PasswordTransformationMethod.getInstance()
//        val show = HideReturnsTransformationMethod.getInstance()
//
//        if (editText.transformationMethod == hidden) {
//            editText.transformationMethod = show
//            imageView.setImageResource(R.drawable.ic_eye_off)
//        } else {
//            editText.transformationMethod = hidden
//            imageView.setImageResource(R.drawable.ic_eye)
//        }
//    }

}