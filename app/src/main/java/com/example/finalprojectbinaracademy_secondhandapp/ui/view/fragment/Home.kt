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
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.BannerResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentHomeBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.HomeAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.ImageSliderAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.ImageSliderAdapter2
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.HomeViewModel
import com.example.finalprojectbinaracademy_secondhandapp.utils.imageData
import org.koin.androidx.viewmodel.ext.android.viewModel

class Home : Fragment(R.layout.fragment_home) {

    private lateinit var adapterSlider: ImageSliderAdapter
    private val listImageSliderAdapter = ArrayList<BannerResponse>()
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var rvProduct: RecyclerView
    private var page = 1
    private var totalPage = 2

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


        setupRecycler()

        handler = Handler(Looper.getMainLooper())
        homeViewModel.BannerHome()
//        adapterSlider = ImageSliderAdapter(listImageSliderAdapter)
        homeViewModel.getBannerHome.observe(viewLifecycleOwner){
            listImageSliderAdapter.addAll(it)

            imageSlider()
        }
//        homeViewModel.getProductHome()
//        homeViewModel.getproduct.observe(viewLifecycleOwner) {
//            setBuyerProduct(it.take(10))
//        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { showLoading(it) }

        scrollListener()
    }

//    @SuppressLint("SetTextI18n")
//    private fun setBuyerProduct(product: List<GetProductResponseItem>) {
//        binding.apply {
//            val homeAdapter = HomeAdapter()
////            val layoutManager = LinearLayoutManager(requireContext())
//            val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//            homeAdapter.submitData(product)
//            homeAdapter.setOnItemClickCallback(object :
//                HomeAdapter.OnItemClickCallback {
//                    override fun onItemClicked(data: GetProductResponseItem) {
//                        findNavController().navigate(HomeDirections.actionHome2ToBuyerDetailProduk(data.id))
//                }
//            })
//            rvProductHome.setHasFixedSize(true)
////            rvProductHome.layoutManager = LinearLayoutManager(requireContext())
////            rvProductHome.layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,true)
//            rvProductHome.layoutManager = layoutManager
//            rvProductHome.adapter = homeAdapter
//            rvProductHome.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    val visibleItemCount = layoutManager.childCount
//                    val total = adapter.itemCount
//                    val arrayInt: IntArray? = null
//                    val firstVisibleItems= layoutManager.findFirstVisibleItemPositions(arrayInt)
//                    var pastVisibleItems = 0
//                    if(firstVisibleItems != null && firstVisibleItems.size > 0) {
//                        pastVisibleItems = firstVisibleItems[0];
//                    }
//
//                    if (page < totalPage) {
//                        if (visibleItemCount + pastVisibleItems >= total) {
//                            loadMoreProduct()
//                            homeViewModel.getproduct.observe(viewLifecycleOwner) { newPage ->
//                                homeAdapter.submitData(newPage.data)
//                            }
//                        }
//                    }
//
//                    super.onScrolled(recyclerView, dx, dy)
//                }
//            })
//
//        }
//    }

    private fun scrollListener() {
        binding.nestedScroll.setOnScrollChangeListener(object :NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(
                v: NestedScrollView,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    loadMoreProduct()
                    binding.progressBar2.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun loadMoreProduct() {
        page ++
        val params = HashMap<String,String>()
        params["page"] = page.toString()
        params["per_page"] = "20"

        homeViewModel.getProductHome(params)
        homeViewModel.getproduct.observe(viewLifecycleOwner) {
            homeAdapter.submitData(it)
            binding.progressBar2.visibility = View.GONE
        }
    }

    private fun setupRecycler() {

        homeAdapter = HomeAdapter()
        rvProduct = binding.rvProductHome
        rvProduct.setHasFixedSize(true)
        rvProduct.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        homeAdapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GetProductResponseItem) {
                findNavController().navigate(HomeDirections.actionHome2ToBuyerDetailProduk(data.id))
            }
        })
        homeViewModel.getproduct.observe(viewLifecycleOwner) {
            homeAdapter.submitData(it)
            binding.progressBar2.visibility = View.GONE
        }
        rvProduct.adapter = homeAdapter
    }
//    @SuppressLint("SetTextI18n")
//    private fun setBuyerProduct(product: List<GetProductResponseItem>) {
//        binding.apply {
//            val homeAdapter = HomeAdapter(product)
//            rvProductHome.setHasFixedSize(true)
////            rvProductHome.layoutManager = LinearLayoutManager(requireContext())
////            rvProductHome.layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,true)
//            rvProductHome.layoutManager =
//                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//            rvProductHome.adapter = homeAdapter
//            homeAdapter.setOnItemClickCallback(object :
//                HomeAdapter.OnItemClickCallback {
//                override fun onItemClicked(data: GetProductResponseItem) {
//                    findNavController().navigate(HomeDirections.actionHome2ToBuyerDetailProduk(data.id))
//                }
//            })
//
//        }
//        rvProduct.adapter = homeAdapter
//    }

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
//        list.add(
//            imageData(
//                "https://firebasestorage.googleapis.com/v0/b/market-final-project.appspot.com/o/banner%2FBAN-1656828994178-lega_calcio.png?alt=media"
//            )
//        )
//        list.add(
//            imageData(
//                "https://firebasestorage.googleapis.com/v0/b/market-final-project.appspot.com/o/banner%2FBAN-1656829026499-la_liga.jpg?alt=media"
//            )
//        )
//        list.add(
//            imageData(
//                "https://firebasestorage.googleapis.com/v0/b/market-final-project.appspot.com/o/banner%2FBAN-1656829041324-Premier_league.png?alt=media"
//            )
//        )
//        list.add(
//            imageData(
//                "https://firebasestorage.googleapis.com/v0/b/market-final-project.appspot.com/o/banner%2FBAN-1656829065552-champions_league.jpg?alt=media"
//            )
//        )
//        list.add(
//            imageData(
//                "https://firebasestorage.googleapis.com/v0/b/market-final-project.appspot.com/o/banner%2FBAN-1656829081250-europa_league.jpg?alt=media"
//            )
//        )

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