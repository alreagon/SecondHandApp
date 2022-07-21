package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.daftarJualvp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.SellerOrder
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetSellerOrderResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetSellerOrderResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentProdukBinding
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentTerjualBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.SaleListDiminatiAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.DaftarJualDirections
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.SaleListViewModel
import com.example.finalprojectbinaracademy_secondhandapp.utils.Status
import com.example.finalprojectbinaracademy_secondhandapp.utils.errorToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class Terjual : Fragment() {
    private var _binding: FragmentTerjualBinding? = null
    private val binding get() = _binding!!
    private val saleListViewModel: SaleListViewModel by viewModel()
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTerjualBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeSoldOrder()
        setupSpinner()
        getOnItemSelect()
    }

    private fun observeSoldOrder() {
        saleListViewModel.listSold.observe(viewLifecycleOwner) {
            when(it.status) {
                Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) {
                        setupView(it.data)
                        binding.displayDefault.visibility = View.GONE
                    }
                }
                Status.ERROR -> {
                    Toast(requireContext()).errorToast(it.message.toString(),requireContext())
                }
            }
        }
    }

    private fun setupView(data: List<SellerOrder>?) {
        data?.let {
            val recycler = binding.recycler
            val adapter = SaleListDiminatiAdapter(object : SaleListDiminatiAdapter.OnCLickItem{
                override fun onClickItemListener(data: SellerOrder) {
                    val action = DaftarJualDirections.actionDaftarJualToInfoPenawar(data.id)
                    findNavController().navigate(action)
                }
            })
            recycler.layoutManager = LinearLayoutManager(requireContext())
            adapter.submitData(data)
            recycler.adapter = adapter
        }
    }

    private fun setupSpinner() {
        spinner = binding.filterTerjual
        val adapter = ArrayAdapter(requireContext(),R.layout.spinner_list_category_item,resources.getStringArray(R.array.filter_terjual))
        spinner.adapter = adapter
    }

    private fun getOnItemSelect() {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val status = p0?.getItemAtPosition(p2).toString()
                saleListViewModel.getSellerSold(status)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}