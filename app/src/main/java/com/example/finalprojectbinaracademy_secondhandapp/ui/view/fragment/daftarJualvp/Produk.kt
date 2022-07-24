package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.daftarJualvp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.SellerProduct
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentProdukBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.SellerProductAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.SellerProductOnClick
import com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.DaftarJualDirections
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
    ): View {
        _binding = FragmentProdukBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getSellerProduct()
    }

    private fun getSellerProduct() {
        saleListViewModel.getSellerProduct()
        saleListViewModel.listProduct.observe(viewLifecycleOwner) {
            when (it.status) {
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

    private fun setupListProduct(data: List<SellerProduct>?) {
        val recycler = binding.recylcerProduct
        data?.let {
            val adapter = SellerProductAdapter(object : SellerProductOnClick {
                override fun onItemClick(data: SellerProduct) {
                    val action = DaftarJualDirections.actionDaftarJualToBuyerDetailProduk(data.id)
                    findNavController().navigate(action)
                }

                override fun onHeaderClick() {
                    val action = DaftarJualDirections.actionDaftarJualToSellerPostProduct()
                    findNavController().navigate(action)
                }
            })
            adapter.SubmitListWithHeader(data)
            recycler.layoutManager = GridLayoutManager(requireContext(), 2)
            recycler.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}