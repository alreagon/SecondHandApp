package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentAllProductBinding
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentDaftarJualBinding
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentHomeBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.ProductPagingAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
        // Inflate the layout for this fragment
        _binding = FragmentAllProductBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
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
//        lifecycleScope.launchWhenCreated {
//            homeViewModel.getProductPaging().collectLatest {
//                adapter.submitData(it)
//            }
//        }
//        binding.progressBar.visibility = View.GONE
        recyclerView.adapter = adapter
    }

}