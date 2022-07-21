package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentPreviewPostProductBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.SellViewModel
import com.example.finalprojectbinaracademy_secondhandapp.utils.Status
import com.example.finalprojectbinaracademy_secondhandapp.utils.rupiah
import org.koin.androidx.viewmodel.ext.android.viewModel

class PreviewPostProduct : Fragment() {
    private var _binding: FragmentPreviewPostProductBinding? = null
    private val binding get() = _binding!!
    private val args: PreviewPostProductArgs by navArgs()
    private val sellViewModel: SellViewModel by viewModel()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPreviewPostProductBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPreview()

        binding.btnBackPreview.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnPostProductPreview.setOnClickListener {
            binding.loadingPreviewPost.visibility = View.VISIBLE
            postProduct()
        }
        checkPostProduct()
    }

    private fun postProduct() {
        val product = args.requestProduct
        sellViewModel.postProduct(product.name,product.description,product.basePrice,product.categoryIds,product.location,product.image)
    }

    private fun checkPostProduct() {
        sellViewModel.postProduct.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(),"Posting product successfully..", Toast.LENGTH_SHORT).show()
                    binding.loadingPreviewPost.visibility = View.GONE
                    val action = PreviewPostProductDirections.actionPreviewPostProductToDaftarJual()
                    findNavController().navigate(action,NavOptions.Builder().setPopUpTo(R.id.seller_post_product, true).build())
                }
                Status.ERROR -> {
                    binding.loadingPreviewPost.visibility = View.GONE
                    Toast.makeText(requireContext(),"Oppss.. Failed posting product", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupPreview() {
        val productToPost = args.requestProduct

        sellViewModel.getCategoryById(productToPost.categoryIds[0])
        sellViewModel.category.observe(viewLifecycleOwner) { city ->
            binding.tvCategoryPreview.text = city.data?.name
        }
        binding.tvNameProductPreview.text = productToPost.name
        binding.tvCitySeller.text = productToPost.location
        binding.tvDescriptionPreview.text = productToPost.description
        binding.tvBasePricePreview.text = rupiah(productToPost.basePrice.toDouble())

        Glide.with(requireActivity())
            .load(productToPost.image)
            .centerCrop()
            .into(binding.ivProductPreview)

        sellViewModel.getUserByAccessToken()
        sellViewModel.user.observe(viewLifecycleOwner) {
            binding.tvNameSellerPreview.text = it.data?.fullName

            Glide.with(this)
                .load(it.data?.imageUrl)
                .centerCrop()
                .into(binding.ivSellerPreview)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelStore.clear()
        _binding = null
    }
}