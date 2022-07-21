package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.daftarJualvp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.SellerOrder
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetSellerOrderResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetSellerOrderResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentDiminatiBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.SaleListDiminatiAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.DaftarJualDirections
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.SaleListViewModel
import com.example.finalprojectbinaracademy_secondhandapp.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class Diminati : Fragment() {
    private var _binding: FragmentDiminatiBinding? = null
    private val binding get() = _binding!!
    private val saleListViewModel: SaleListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDiminatiBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getSellerOrder()
    }

    private fun getSellerOrder() {
        saleListViewModel.getSellerOrder()
        saleListViewModel.listDiminati.observe(viewLifecycleOwner) {
            when(it.status) {
                Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) {
                        binding.displayDefault.visibility = View.GONE
                        setupView(it.data)
                    }
                }
            }
        }
    }

    private fun setupView(data : List<SellerOrder>?) {
        data?.let {
            val recycler = binding.recyclerDiminati
            val adapter = SaleListDiminatiAdapter(object : SaleListDiminatiAdapter.OnCLickItem{
                override fun onClickItemListener(data: SellerOrder) {
                    val action = DaftarJualDirections.actionDaftarJualToInfoPenawar(data.id)
                    findNavController().navigate(action)
                }
            })
            adapter.submitData(data)
            recycler.layoutManager = LinearLayoutManager(requireContext())
            recycler.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}