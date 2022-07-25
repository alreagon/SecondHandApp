package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.Banner
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.Product
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentHomeBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.HomeAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.ImageSliderAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.HomeViewModel
import com.example.finalprojectbinaracademy_secondhandapp.utils.Status
import com.example.finalprojectbinaracademy_secondhandapp.utils.errorToast
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class Home : Fragment(R.layout.fragment_home) {

    private lateinit var adapterSlider: ImageSliderAdapter
    private val listImageSliderAdapter = ArrayList<Banner>()
    private var list = ArrayList<Product>()
    private var homeAdapter = HomeAdapter()
    private lateinit var recyclerView: RecyclerView
    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.bannerHome()
        observeBanner()
        setupRecycler()
        observeProduct()
        binding.goToALlProduct.setOnClickListener {
            val action = HomeDirections.actionHome2ToAllProductFragment()
            findNavController().navigate(action)
        }

        binding.textViewSearch.setOnClickListener {
            findNavController().navigate(HomeDirections.actionHome2ToSearchFragment())
        }
    }

    private fun setupRecycler() {
        homeAdapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Product) {
                findNavController().navigate(HomeDirections.actionHome2ToBuyerDetailProduk(data.id))
            }
        })
        recyclerView = binding.rvProductHome
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = homeAdapter
        binding.progressBar.visibility = View.GONE
    }

    private fun observeProduct() {
        buttonCategoryAll()

        binding.apply {

            time.selectButton(btn1)
            btn1.setOnClickListener {
                btn1.isSelected = true
                buttonCategoryAll()
            }

            btn2.setOnClickListener {
                // pakaian pria
                val id = 4
                buttonCategory(id)
            }

            btn3.setOnClickListener {
                // sepatu pria
                val id = 5
                buttonCategory(id)
            }

            btn4.setOnClickListener {
                // kesehatan
                val id = 8
                buttonCategory(id)
            }

            btn5.setOnClickListener {
                // Aksesoris Fahion
                val id = 7
                buttonCategory(id)
            }

            btn6.setOnClickListener {
                // komputer dan aksesoris
                val id = 2
                buttonCategory(id)
            }

            btn7.setOnClickListener {
                // makanan dan minuman
                val id = 10
                buttonCategory(id)
            }

            btn8.setOnClickListener {
                // perlengkapan rumah
                val id = 12
                buttonCategory(id)
            }

        }
    }

    private fun buttonCategory(id: Int) {

        homeViewModel.getProductOfflineCategory(id)
        homeViewModel.gettProductOffline.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { listProduct ->
                        list.clear()
                        homeAdapter.submitData(listProduct.take(40))
                    }
                    binding.progressBar.visibility = View.GONE
                    binding.goToALlProduct.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.goToALlProduct.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.goToALlProduct.visibility = View.VISIBLE
                    Toast(requireContext()).errorToast(
                        it.message.toString(),
                        requireContext()
                    )
                }
            }
        }
    }

    private fun buttonCategoryAll() {

        homeViewModel.getProductOfflineAll()
        homeViewModel.gettProductOffline.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { listProduct ->
                        list.clear()
                        homeAdapter.submitData(listProduct.take(40))
                    }
                    binding.progressBar.visibility = View.GONE
                    binding.goToALlProduct.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.goToALlProduct.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.goToALlProduct.visibility = View.VISIBLE
                    Toast(requireContext()).errorToast(
                        it.message.toString(),
                        requireContext()
                    )
                }
            }
        }
    }


    private fun observeBanner() {
        homeViewModel.getBannerHome.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { listBanner ->
                        listImageSliderAdapter.addAll(listBanner)
                        imageSlider()
                        binding.progressBarBanner.visibility = View.GONE
                    }
                }
                Status.ERROR -> {
                    binding.progressBarBanner.visibility = View.GONE
                    Toast(requireContext())
                        .errorToast(
                            it.message.toString(),
                            requireContext()
                        )
                }
                Status.LOADING -> {
                    binding.progressBarBanner.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun imageSlider() {
        adapterSlider = ImageSliderAdapter(listImageSliderAdapter)
        binding.piewpager.adapter = adapterSlider
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

