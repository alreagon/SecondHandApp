package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentProfileBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class Profile : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutLayout.setOnClickListener {
            logout()
        }

        binding.updateProfile.setOnClickListener {
            updateProfile()
        }
    }

    private fun updateProfile() {
        authViewModel.checkUserLogin().observe(viewLifecycleOwner, Observer {
            if (it) {
                val action = ProfileDirections.actionProfileToEditProfile()
                findNavController().navigate(action)
            } else {
                val action = ProfileDirections.actionProfileUserToLoginFragment()
                findNavController().navigate(action)
                Toast.makeText(requireContext(),"Anda belum login, silahkan login atau register...",Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun logout() {
        authViewModel.checkUserLogin().observe(viewLifecycleOwner, Observer {
            if (it) {
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setTitle("Logout")
                dialog.setMessage("Apakah anda yakin ingin logout dari aplikasi?")

                dialog.setCancelable(true)
                dialog.setPositiveButton("YES"){dialogInterface, p1 ->
                    authViewModel.setLogout()

                    val action = ProfileDirections.actionProfileUserToHome2()
                    findNavController().navigate(action,
                        NavOptions.Builder().setPopUpTo(R.id.profile_user, true).build())
                    Toast.makeText(requireContext(),"Berhasil logout...", Toast.LENGTH_LONG).show()
                }

                dialog.setNegativeButton("NO"){dialogInterface, p1->
                    dialogInterface.dismiss()
                }

                dialog.show()
            } else {
                Toast.makeText(requireContext(),"Anda belum login, silahkan login atau register...",Toast.LENGTH_SHORT).show()
            }
        })
    }
}