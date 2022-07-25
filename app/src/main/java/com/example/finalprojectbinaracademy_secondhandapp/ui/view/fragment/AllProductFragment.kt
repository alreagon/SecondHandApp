package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentAllProductBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.ProductPagingAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllProductFragment : Fragment() {
    private var _binding: FragmentAllProductBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var adapter: ProductPagingAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllProductBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        
        binding.btnBackALlProduct.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecycler() {
        adapter = ProductPagingAdapter()
        homeViewModel.getProductPaging().observe(viewLifecycleOwner) { data ->
            adapter.submitData(lifecycle,data)
        }

        adapter.onItemClick(object : ProductPagingAdapter.OnItemClickCallback{
            override fun onItemClicked(data: GetProductResponseItem) {
                findNavController().navigate(AllProductFragmentDirections.actionAllProductFragmentToBuyerDetailProduk(data.id))
            }
        })
        recyclerView = binding.recyclerAllProduct
        recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter
    }

}