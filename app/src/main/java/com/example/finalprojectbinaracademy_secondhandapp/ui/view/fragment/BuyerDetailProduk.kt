package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.PostBuyerOrderRequest
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentBuyerDetailProdukBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.BuyerDetailViewModel
import com.example.finalprojectbinaracademy_secondhandapp.utils.rupiah
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottomsheet.*
import kotlinx.android.synthetic.main.toast_notification.*
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("InflateParams")
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

        binding.btnBackDetail.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showDetailProdukBuyer() {
        buyerDetailViewModel.BuyerDetailProdukId(args.idProdukDetail)
        buyerDetailViewModel.getproductId.observe(viewLifecycleOwner) {
            binding.apply {
                Glide.with(requireView())
                    .load(it.imageUrl)
                    .centerCrop()
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            imageDetailBuyer.setImageResource(R.drawable.default_photo)
                            return true
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }
                    })
                    .into(imageDetailBuyer)
                it.user.imageUrl?.let {
                    Glide.with(requireContext())
                        .load(it)
                        .centerCrop()
                        .into(imageProfileDetailBuyer)
                }
                JudulDetailBuyer.text = it.name
                if (!it.categories.isEmpty()) {
                    KategoriDetailBuyer.text = it.categories[0].name
                } else {
                    KategoriDetailBuyer.text = "Kategori kosong"
                }

                hargaDetailBuyer.text = rupiah(it.basePrice.toDouble())
                namaPenjualDetailBuyer.text = it.user.fullName
                namaKotaDetailBuyer.text = it.user.city
                deskripsiDetailBuyer.text = it.description

                butonNegoDetailProdukBuyer.setOnClickListener {
                    dialogAction()
                }

            }
        }

    }


    private fun dialogAction() {
        val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        val view1 = layoutInflater.inflate(R.layout.bottomsheet, null)
        dialog.setCancelable(true)
        dialog.setContentView(view1)
        dialog.show()
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation

        dialog.btnKirim.setOnClickListener {
        val inputanBid = dialog.etBidBuyerr.text.toString()

        if (inputanBid.isEmpty()){
            Toast.makeText(requireContext(), "Tawaranmu masih kosong", Toast.LENGTH_SHORT).show()
        }else{

            val etBuyerBidBuyerr = dialog.etBidBuyerr.text.toString()
            val request = PostBuyerOrderRequest(args.idProdukDetail.toString(), etBuyerBidBuyerr)
            buyerDetailViewModel.PostBuyerOrder(request)
            buyerDetailViewModel.postBuyerOrder.observe(viewLifecycleOwner) { input ->

                if (input != null) {
                    dialog.dismiss()
                    dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation

                    Toast(requireContext()).showCustomToast(
                        "Harga tawarmu berhasil dikirim\nke penjual")

                    binding.butonNegoDetailProdukBuyer.visibility = View.GONE
                    binding.butonMenungguResponPenjual.visibility = View.VISIBLE

                } else {
                    Toast.makeText(
                        requireContext(),
                        "error something in BuyerDetailProduk",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

        }
    }

    private fun Toast.showCustomToast(message: String) {

        val layout: View = layoutInflater.inflate(R.layout.toast_notification, Toasttt)
        val textView = layout.findViewById<TextView>(R.id.textNotifToast)
        val btnX = layout.findViewById<ImageButton>(R.id.xxxx)

        textView.text = message
        this.apply {
            setGravity(Gravity.TOP, 0, 200   )
            duration = Toast.LENGTH_LONG
            view = layout
            btnX.setOnClickListener{
                if (true){
                    cancel()
                    onStop()
                }
            }
            show()


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}