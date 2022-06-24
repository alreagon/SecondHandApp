package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

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
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentRegisterBinding
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentSellerDetailProdukBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.SellViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SellerDetailProduk : Fragment() {
    private var _binding: FragmentSellerDetailProdukBinding? = null
    private val binding get() = _binding!!
    private val sellViewModel: SellViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSellerDetailProdukBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkUserLogin()
    }

    private fun checkUserLogin() {
        sellViewModel.getStatusLogin().observe(viewLifecycleOwner, Observer {
            if (!it) {
                val action = SellerDetailProdukDirections.actionSellerDetailProdukToLoginFragment()
                findNavController().navigate(action, NavOptions.Builder().setPopUpTo(R.id.seller_DetailProduk, true).build())
                Toast.makeText(requireContext(),"Anda belum login, silahkan login...", Toast.LENGTH_SHORT).show()
            }
        })
    }

}