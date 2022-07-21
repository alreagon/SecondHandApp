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
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.Product
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentSearchBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.HomeAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.HomeViewModel
import com.example.finalprojectbinaracademy_secondhandapp.utils.Status
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var rvProduct: RecyclerView
    private val searchViewModel: HomeViewModel by viewModel()
    private lateinit var searchProductResultAdapter: HomeAdapter

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

        observeSearchProduct()
        setupRecycler()

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search_bar.clearFocus()
                searchViewModel.getSearchProduct(query!!)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun observeSearchProduct() {
        searchViewModel.getproduct.observe(viewLifecycleOwner) {
            when(it.status) {
                Status.LOADING -> {
                    binding.PBSearch.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    it.data?.let {
                        searchProductResultAdapter.submitData(it)
                        binding.PBSearch.visibility = View.GONE
                    }
                    if (it.data.isNullOrEmpty()) {
                        binding.displayDefault.visibility = View.VISIBLE
                    } else {
                        binding.displayDefault.visibility = View.GONE
                    }
                    binding.PBSearch.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.PBSearch.visibility = View.GONE
                    binding.displayDefault.visibility = View.GONE
                }
            }
        }
    }

    private fun setupRecycler() {

        searchProductResultAdapter = HomeAdapter()
        rvProduct = binding.rvSearchResult
        rvProduct.setHasFixedSize(true)
        rvProduct.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        searchProductResultAdapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Product) {
                findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToBuyerDetailProduk(data.id))
            }
        })
        rvProduct.adapter = searchProductResultAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
