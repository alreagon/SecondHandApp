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
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.Banner
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.Product
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentHomeBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.HomeAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.ImageSliderAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.HomeViewModel
import com.example.finalprojectbinaracademy_secondhandapp.utils.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class Home : Fragment(R.layout.fragment_home) {

    private lateinit var adapterSlider: ImageSliderAdapter
    private val listImageSliderAdapter = ArrayList<Banner>()
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
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

        observeProduct()
        observeBanner()
        setupRecycler()

        binding.goToALlProduct.setOnClickListener {
            val action = HomeDirections.actionHome2ToAllProductFragment()
            findNavController().navigate(action)
        }

        binding.textViewSearch.setOnClickListener {
            findNavController().navigate(HomeDirections.actionHome2ToSearchFragment())
        }
    }

    private fun setupRecycler() {
        homeAdapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Product) {
                findNavController().navigate(HomeDirections.actionHome2ToBuyerDetailProduk(data.id))
            }
        })
        recyclerView = binding.rvProductHome
        recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = homeAdapter
        binding.progressBar.visibility = View.GONE
    }

    private fun observeBanner() {
        homeViewModel.getBannerHome.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS -> {
                    it.data?.let { listBanner ->
                        listImageSliderAdapter.addAll(listBanner)
                        imageSlider()
                    }
                }
                Status.ERROR -> {
                    Toast(requireContext())
                        .errorToast(
                            it.message.toString(),
                            requireContext()
                        )
                }
                Status.LOADING -> {

                }
            }
        }
    }

    private fun observeProduct() {
        homeViewModel.getProductOffline()
        homeViewModel.gettProductOffline.observe(viewLifecycleOwner) {
            when(it.status) {
                Status.SUCCESS -> {
                    it.data?.let { listProduct ->
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
                    Toast(requireContext()).errorToast(it.message.toString(),requireContext())
                }
            }
        }
    }
    private fun imageSlider() {

        runnable = object : Runnable {
            var index = 0
            override fun run() {
                if (index == listImageSliderAdapter.size)
                    index = 0
                Log.e("Ini Runnable,", "$index")
                binding.viewPager.currentItem = index
                index++
                handler.postDelayed(this, 5000)
            }

        }

        adapterSlider = ImageSliderAdapter(listImageSliderAdapter)
        binding.viewPager.adapter = adapterSlider

//        binding.viewPager.registerOnPageChangeCallback(
//            object :
//                ViewPager2.OnPageChangeCallback() {
//                override fun onPageSelected(position: Int) {
//                    super.onPageSelected(position)
//                }
//            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handler = Handler(Looper.getMainLooper())
    }

    override fun onResume() {
        super.onResume()
//        handler.post(runnable)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runnable)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}