package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentHomeBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.HomeAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.ImageSliderAdapter2
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.HomeViewModel
import com.example.finalprojectbinaracademy_secondhandapp.utils.imageData
import org.koin.androidx.viewmodel.ext.android.viewModel

class Home : Fragment(R.layout.fragment_home) {

    private lateinit var adapter: ImageSliderAdapter2
    private val list = ArrayList<imageData>()
    private lateinit var dots: ArrayList<TextView>
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

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


//        binding.apply {
//            haha.setOnClickListener {
//
//                val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
//                val view1 = layoutInflater.inflate(R.layout.bottomsheet, null)
//                dialog.setCancelable(true)
//                dialog.setContentView(view1)
//                dialog.show()
//                dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
//            }
//        }

        imageSlider()

        homeViewModel.productHome()
        homeViewModel.getproduct.observe(viewLifecycleOwner) {
            setBuyerProduct(it)
        }
//
        homeViewModel.isLoading.observe(viewLifecycleOwner) { showLoading(it) }

    }

    @SuppressLint("SetTextI18n")
    private fun setBuyerProduct(product: List<GetProductResponseItem>) {
        binding.apply {
            val homeAdapter = HomeAdapter(product)
            rvProductHome.setHasFixedSize(true)
//            rvProductHome.layoutManager = LinearLayoutManager(requireContext())
//            rvProductHome.layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,true)
            rvProductHome.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rvProductHome.adapter = homeAdapter
            homeAdapter.setOnItemClickCallback(object :
                HomeAdapter.OnItemClickCallback {
                override fun onItemClicked(data: GetProductResponseItem) {
                    findNavController().navigate(HomeDirections.actionHome2ToBuyerDetailProduk(data.id))
                }
            })

        }
    }

    private fun imageSlider() {

        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            var index = 0
            override fun run() {
                if (index == list.size)
                    index = 0
                Log.e("Ini Runnable,", "$index")
                binding.viewPager.setCurrentItem(index)
                index++
                handler.postDelayed(this, 5000)
            }

        }


        list.add(
            imageData(
                R.drawable.aamiin
//                R.layout.fragment_info_penawar
            )
        )
        list.add(
            imageData(
                R.drawable.aamiin
            )
        )
        list.add(
            imageData(
                R.drawable.aamiin
            )
        )
        list.add(
            imageData(
                R.drawable.aamiin
            )
        )
        adapter = ImageSliderAdapter2(list)
        binding.viewPager.adapter = adapter

        binding.viewPager.registerOnPageChangeCallback(
            object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
    }

    override fun onStart() {
        super.onStart()
        handler.post(runnable)

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





















