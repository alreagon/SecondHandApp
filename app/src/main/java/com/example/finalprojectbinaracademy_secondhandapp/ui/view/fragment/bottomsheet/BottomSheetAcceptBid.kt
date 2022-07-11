package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.bottomsheet

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetSellerOrderResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentBottomSheetAcceptBidBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.SaleListViewModel
import com.example.finalprojectbinaracademy_secondhandapp.utils.Status
import com.example.finalprojectbinaracademy_secondhandapp.utils.rupiah
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetAcceptBid : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetAcceptBidBinding? = null
    private val binding get() = _binding!!
    private val saleListViewModel: SaleListViewModel by viewModel()
    private var phoneNumber: Long = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBottomSheetAcceptBidBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getOrderId()

        binding.btnToWasap.setOnClickListener {
            intentToWasap(phoneNumber.toString(),"")
        }
    }

    private fun getOrderId() {
        val idOrder =  arguments?.getInt(ID_ORDER)
        idOrder?.let {
            saleListViewModel.getSellerOrderById(idOrder)
        }
        saleListViewModel.diminatiById.observe(viewLifecycleOwner) {
            when(it.status) {
                Status.SUCCESS -> {
                    setupView(it.data)
                    it.data?.let {
                        phoneNumber = it.user.phoneNumber.toLong()
                    }
                }
                Status.ERROR -> {

                }
            }
        }
    }

    private fun setupView(data: GetSellerOrderResponseItem?) {
        data?.let {
            binding.tvBuyerNameBS.text = data.user.fullName
            binding.tvCityBS.text = data.user.city
            binding.tvNameProductBS.text = data.productName
            binding.tvBasePriceBS.text = rupiah(data.basePrice.toDouble())
            binding.tvBidPricveBS.text = "Ditawar ${rupiah(data.price.toDouble())}"

            Glide.with(this)
                .load(data.product.imageUrl)
                .centerCrop()
                .into(binding.ivProductBS)
        }
    }

    private fun intentToWasap(phone: String, message: String) {
//        if (appInstalledOrNot("com.whatsapp")) {
        val sendIntent = Intent("android.intent.action.MAIN")
        sendIntent.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("62$phone") + "@s.whatsapp.net" ) //phone number without "+" prefix
        startActivity(sendIntent)
//        } else {
//            Toast.makeText(requireContext(), "Whats app not installed on your device", Toast.LENGTH_SHORT).show();
//        }
    }

    private fun appInstalledOrNot(url: String): Boolean {
        val packageManager = activity?.packageManager
        val app_installed: Boolean
        app_installed = try {
            packageManager?.getPackageInfo(url, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        return app_installed
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val TAG = "ModalBottomSheet"
        const val ID_ORDER = "IdOrder"
    }
}