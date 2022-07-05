package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterRequest
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentRegisterBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.AuthViewModel
import com.example.finalprojectbinaracademy_secondhandapp.utils.PasswordUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_register, container, false)
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            binding.pbRegister.visibility = View.VISIBLE
            registerUser()
        }

        binding.tvGoToLogin.setOnClickListener {
//            val action = RegisterFragmentDirections.actionRegisterToLoginFragment()
//            findNavController().navigate(action)
            findNavController().navigateUp()
        }

        binding.btnBackToLogin.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun registerUser() {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val pass = binding.etPassword.text.toString()
        val confirmpass = binding.etConfirmPass.text.toString()

        if (inputCheck(name,email,pass,confirmpass)) {
            val request = RegisterRequest("DEFAULT_ADDRESS","DEFAULT_CITY",email,name,null,pass,"000")

            registerViewModel.userRegister(request)

            registerViewModel.userRegis.observe(viewLifecycleOwner) { user ->

                if (user != null) {
                    val action = RegisterFragmentDirections.actionRegisterToLoginFragment()
                    findNavController().navigate(action,
                        NavOptions.Builder().setPopUpTo(R.id.register, true).build())
                    Toast.makeText(requireContext(),"register successfully",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(),"register failed", Toast.LENGTH_SHORT).show()
                    binding.pbRegister.visibility = View.GONE
                }
            }

        } else {
            validateErrorInput(name,email,pass,confirmpass)
            binding.pbRegister.visibility = View.GONE
        }
    }

    private fun validateErrorInput(name:String,email:String,password:String,confirmpass: String) {
        if (TextUtils.isEmpty(name)) {
            binding.wrapName.error = "Masukkan nama anda"
        } else {
            binding.wrapName.error = null
        }

        if (TextUtils.isEmpty(confirmpass)) {
            binding.wrapConfirmPass.error = "Konfirmasi password tidak boleh kosong"
        } else {
            binding.wrapConfirmPass.error = null
        }

        if (confirmpass != password) {
            Toast.makeText(requireContext(),"Konfirmasi password salah",Toast.LENGTH_SHORT).show()
        }

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

        if (!PasswordUtils().validate(password)) {
            binding.wrapPassword.error = "Harus mengandung upercase, lowercase, angka, dan min. 8 karakter"
        } else {
            binding.wrapPassword.error = null
        }


    }

    private fun inputCheck(name: String, email:String, pass: String, confirmpass: String): Boolean {
        return !(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || !PasswordUtils().validate(pass) ||
                TextUtils.isEmpty(confirmpass) || !Patterns.EMAIL_ADDRESS.matcher(email).matches() || pass != confirmpass)
    }

}