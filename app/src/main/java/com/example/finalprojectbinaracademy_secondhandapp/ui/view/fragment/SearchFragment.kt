package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentSearchBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.HomeAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var rvProduct: RecyclerView
    private var page = 1
    private val searchViewModel: HomeViewModel by viewModel()
    private lateinit var searchProductResultAdapter: HomeAdapter

    private lateinit var searchArrayList: ArrayList<GetProductResponseItem>

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val params = HashMap<String, String>()
        params["page"] = page.toString()
        params["per_page"] = "20"

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search_bar.clearFocus()
                searchViewModel.getSearchProduct(params, query!!)

                searchViewModel.getproduct.observe(viewLifecycleOwner) {
                    searchProductResultAdapter.submitData(it)
                    binding.PBSearch.visibility = View.GONE
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }


        })

        setupRecycler()

        searchViewModel.isLoading.observe(viewLifecycleOwner) { showLoading(it) }

        scrollListener()
    }

    private fun scrollListener() {
        binding.nestedScroll.setOnScrollChangeListener(object :
            NestedScrollView.OnScrollChangeListener {
            override fun onScrollChange(
                v: NestedScrollView,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
//                    loadMoreProduct()
                    binding.PBSearch.visibility = View.VISIBLE
                }
            }
        })
    }

//    private fun loadMoreProduct() {
//        page++
//        val params = HashMap<String, String>()
//        params["page"] = page.toString()
//        params["per_page"] = "20"
//
//    }


    private fun setupRecycler() {

        searchProductResultAdapter = HomeAdapter()
        rvProduct = binding.rvSearchResult
        rvProduct.setHasFixedSize(true)
        rvProduct.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        searchProductResultAdapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GetProductResponseItem) {
                findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToBuyerDetailProduk(data.id))
            }
        })
        rvProduct.adapter = searchProductResultAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.PBSearch.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


//    class SearchFragment : Fragment() {
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_search, container, false)
//    }
//
//
//}