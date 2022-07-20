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
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.LoginRequest
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentLoginBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.AuthViewModel
import com.example.finalprojectbinaracademy_secondhandapp.utils.PasswordUtils
import com.example.finalprojectbinaracademy_secondhandapp.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkLogin()

        binding.tvGoToRegis.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegister()
            findNavController().navigate(action)
        }

        binding.btnLogin.setOnClickListener{
            loginUser()
        }

        binding.ivBackButton.setOnClickListener { findNavController().navigateUp() }
    }

    private fun checkLogin() {
        authViewModel.userLogin.observe(requireActivity()) { userLogin ->
            when(userLogin.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Toast.makeText(requireContext(),"loading to login",Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    val action = LoginFragmentDirections.actionLoginFragmentToHome2()
                    findNavController().navigate(action)

                    Toast.makeText(requireContext(),"Hello ${userLogin.data?.name}...", Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), userLogin.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loginUser() {
        val email = binding.etEmail.text.toString()
        val pass = binding.etPassword.text.toString()

        if (inputCheck(email,pass)) {
            val request = LoginRequest(email,pass)
            authViewModel.userLogin(request)
        } else {
            validateErrorInput(email,pass)
        }
        binding.progressBar.visibility = View.GONE
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
}