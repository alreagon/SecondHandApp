package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentBottomSheetAcceptBidBinding
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentUpdateStatusBottomSheetBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.SaleListViewModel
import com.example.finalprojectbinaracademy_secondhandapp.utils.Status
import com.example.finalprojectbinaracademy_secondhandapp.utils.errorToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateStatusBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentUpdateStatusBottomSheetBinding? = null
    private val binding get() = _binding!!
    private var status: String = ""
    private val saleListViewModel: SaleListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateStatusBottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkUpdateStatus()
        radioButtonCheck()

        binding.btnUpdateStatus.setOnClickListener {
            updateStatusProduct()
        }

    }

    private fun radioButtonCheck() {
        binding.rgUpdateStatus.setOnCheckedChangeListener { radioGroup, i ->
            when(i) {
                R.id.rbAcc -> {
                    status = "sold"
                }
                R.id.rbDeclined -> {
                    status = "available"
                }
            }
        }
    }

    private fun updateStatusProduct() {
        val productId=  arguments?.getInt(ID_PRODUCT)
        val idOrder = arguments?.getInt(ID_ORDER)

        if (productId != null && idOrder != null) {
            saleListViewModel.patchStatusProduct(productId,status)
            if (status == "available") {
                saleListViewModel.patchOrder(idOrder,"declined")
            }
        }
    }

    private fun checkUpdateStatus() {
        saleListViewModel.patchStatusProduct.observe(viewLifecycleOwner) {
            when(it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(),"update success",Toast.LENGTH_SHORT).show()
                    dismiss()
                    findNavController().navigateUp()
                }
                Status.ERROR -> {
                    Toast(requireContext()).errorToast(it.message.toString(),requireContext())
                }
            }
        }
    }

    companion object {
        const val TAG = "ModalBottomSheetChangeStatus"
        const val ID_PRODUCT = "idProduct"
        const val ID_ORDER = "idOrder"
    }
}