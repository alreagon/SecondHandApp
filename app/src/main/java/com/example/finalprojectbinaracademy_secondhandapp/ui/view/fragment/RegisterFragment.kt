package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterRequest
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentRegisterBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.AuthViewModel
import com.example.finalprojectbinaracademy_secondhandapp.utils.PasswordUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_register, container, false)
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            registerUser()
        }

        binding.tvGoToLogin.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterToLoginFragment()
            findNavController().navigate(action)
        }
    }

    private fun registerUser() {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val phoneNumber = binding.etPhone.text.toString()
        val address = binding.etAddress.text.toString()
        val pass = binding.etPassword.text.toString()

        if (inputCheck(name,email,phoneNumber,address,pass)) {
            val request = RegisterRequest(address,"jakarta",email,name,null,pass,phoneNumber.toLong())

            registerViewModel.userRegister(request)

            registerViewModel.userRegis.observe(requireActivity(), Observer { user ->

                if (user != null) {
                    val action = RegisterFragmentDirections.actionRegisterToLoginFragment()
                    findNavController().navigate(action)
                    Toast.makeText(requireContext(),"register successfully",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(),"register failed", Toast.LENGTH_SHORT).show()
                }
            })

        } else {
            validateErrorInput(name,email,phoneNumber,address,pass)
        }
    }

    private fun validateErrorInput(name:String,email:String,phone:String,address:String,password:String) {
        if (TextUtils.isEmpty(name)) {
            binding.wrapName.error = "Masukkan nama anda"
        } else {
            binding.wrapName.error = null
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.wrapEmail.error = "Format email salah"
        } else {
            binding.wrapEmail.error = null
        }

        if (TextUtils.isEmpty(address)) {
            binding.wrapAddress.error = "Masukkan alamat anda"
        } else {
            binding.wrapAddress.error = null
        }

        if (TextUtils.isEmpty(password)) {
            binding.wrapPassword.error = "Masukkan password"
        } else {
            binding.wrapPassword.error = null
        }

        if (!PasswordUtils().validate(password)) {
            binding.wrapPassword.error = "Harus mengandung upercase, lowercase, angka, dan min. 8 karakter"
        } else {
            binding.wrapPassword.error = null
        }

        if (TextUtils.isEmpty(phone)) {
            binding.wrapPhone.error = "Masukkan nomor telepon anda"
        } else {
            binding.wrapPhone.error = null
        }
    }

    private fun inputCheck(name: String, email:String, phone: String, address: String, pass: String): Boolean {
        return !(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || !PasswordUtils().validate(pass) ||
                TextUtils.isEmpty(phone) || TextUtils.isEmpty(address) || !Patterns.EMAIL_ADDRESS.matcher(email).matches() )
    }

}