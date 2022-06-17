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
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.api.ApiClient
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.LoginRequest
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.LoginResponse
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentLoginBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.LoginViewModel
import retrofit2.Call
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Login.newInstance] factory method to
 * create an instance of this fragment.
 */
class Login : Fragment() {
    private var _binding: FragmentLoginBinding? = null
     private val binding get() = _binding!!

    // get shared preferences
    private val sharedPrefFile = "logininfo"

    // get shared preferences
    lateinit var dataStoreManager : DataStoreManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnShowPwd.setOnClickListener{
            showPassword(binding.etPassword, binding.btnShowPwd)
        }

        binding.btnLogin.setOnClickListener{
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val loginkey : String
            if (email != "" && password != ""){
                TODO()
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


    private fun loginUser(){
        TODO()
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