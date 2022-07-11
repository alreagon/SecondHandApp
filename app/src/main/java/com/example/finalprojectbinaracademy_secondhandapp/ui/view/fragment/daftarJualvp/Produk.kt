package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.daftarJualvp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentProdukBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.HomeAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.SaleListAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.DaftarJualDirections
import com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.Home
import com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.HomeDirections
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.SaleListViewModel
import com.example.finalprojectbinaracademy_secondhandapp.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class Produk : Fragment() {
    private var _binding: FragmentProdukBinding? = null
    private val binding get() = _binding!!
    private val saleListViewModel: SaleListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProdukBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSellerProduct()

        binding.ivAddProductListSell.setOnClickListener {
            val action = DaftarJualDirections.actionDaftarJualToSellerPostProduct()
            findNavController().navigate(action)
        }
    }

    private fun getSellerProduct() {
        saleListViewModel.listProduct.observe(viewLifecycleOwner) {
            when(it.status) {
                Status.SUCCESS -> {
                    setupListProduct(it.data)
                }
                Status.ERROR -> {

                }
                Status.LOADING -> {

                }
            }
        }
    }

    private fun setupListProduct(data: List<GetProductResponseItem>?) {
        val recycler = binding.recylcerProduct
        data?.let {
            val adapter = SaleListAdapter(object : HomeAdapter.OnItemClickCallback{
                override fun onItemClicked(data: GetProductResponseItem) {
                    val action = DaftarJualDirections.actionDaftarJualToBuyerDetailProduk(data.id)
                    findNavController().navigate(action)
                }
            })
            adapter.submitData(data)
            recycler.layoutManager = GridLayoutManager(requireContext(),2)
            recycler.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}