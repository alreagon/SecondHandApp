package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RegisterResponse
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentDaftarJualBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.MyPagerAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.SaleListViewModel
import com.example.finalprojectbinaracademy_secondhandapp.utils.Status
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_daftar_jual.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DaftarJual : Fragment(R.layout.fragment_daftar_jual) {
    private var _binding: FragmentDaftarJualBinding? = null
    private val binding get() = _binding!!
    private val saleListViewModel: SaleListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDaftarJualBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSeller()

//        vp_1.adapter = MyPagerAdapter(childFragmentManager)
//        tabs_main.setupWithViewPager(vp_1)
        setupViewPager()

        binding.btnEdit.setOnClickListener {
            val action = DaftarJualDirections.actionDaftarJualToEditProfile()
            findNavController().navigate(action)
        }
    }

    private fun setupViewPager() {
        val viewpager = binding.vpDaftarJual
        val tab = binding.tabsMain
        viewpager.adapter = MyPagerAdapter(childFragmentManager,lifecycle)

        TabLayoutMediator(tab,viewpager){ tab,position ->
            tab.text = MyPagerAdapter.tabTitle[position]
        }.attach()
    }

    private fun getSeller() {
        saleListViewModel.getSeller()
        saleListViewModel.seller.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    setupView(it.data)
                }
            }
        }
    }

    private fun setupView(data: RegisterResponse?) {
        binding.tvUserName.text = data?.fullName
        binding.tvCity.text = data?.city
        if (data?.imageUrl != null) {
            Glide.with(this)
                .load(data?.imageUrl)
                .centerCrop()
                .into(binding.ivProfil)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}