package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentBuyerDetailProdukBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.BuyerDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BuyerDetailProduk : Fragment(R.layout.fragment_buyer_detail_produk) {

    private var _binding: FragmentBuyerDetailProdukBinding? = null
    private val binding get() = _binding!!
    private val args: BuyerDetailProdukArgs by navArgs()
    private val buyerDetailViewModel: BuyerDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuyerDetailProdukBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showDetailProdukBuyer()

    }

    private fun showDetailProdukBuyer() {
        buyerDetailViewModel.BuyerDetailProdukId(args.idProdukDetail)
        buyerDetailViewModel.getproductId.observe(viewLifecycleOwner) {
            binding.apply {
                Glide.with(requireView())
                    .load(it.imageUrl)
                    .into(imageDetailBuyer)
                JudulDetailBuyer.text = it.name
                if (!it.categories.isEmpty()){
                    KategoriDetailBuyer.text = it.categories[0].name
                } else{
                    KategoriDetailBuyer.text = "Kategori kosong"
                }

                hargaDetailBuyer.text = it.basePrice.toString()
                namaPenjualDetailBuyer.text = it.user.fullName
                namaKotaDetailBuyer.text = it.user.city
                deskripsiDetailBuyer.text = it.description
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}