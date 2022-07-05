package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.RequestPostProduct
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentSellerPostProductBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.SpinnerAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.SellViewModel
import com.example.finalprojectbinaracademy_secondhandapp.utils.Status
import com.github.dhaval2404.imagepicker.ImagePicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class SellerPostProduct : Fragment() {
    private var _binding: FragmentSellerPostProductBinding? = null
    private val binding get() = _binding!!
    private val sellViewModel: SellViewModel by viewModel()
    private val idCategory = arrayListOf<Int>()
    private var city: String? = null
    private lateinit var productImage: File

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSellerPostProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCity()
        checkUserLogin()
        setupView()
        binding.ivAddPhoto.setOnClickListener {
            choseImage()
        }
        binding.btnToPreview.setOnClickListener {
            goToPreview()
        }
        binding.button5.setOnClickListener {
            binding.loadingPost.visibility = View.VISIBLE
            postProduct()
        }
        checkPostProduct()
        binding.btnBackPostProduct.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun getCity() {
        sellViewModel.getUserByAccessToken()
        sellViewModel.user.observe(viewLifecycleOwner) {
            city = it.city
        }
    }

    private fun postProduct() {
        val nameProduct = binding.etNameProduct.text.toString()
        val basePrice = binding.etPriceProduct.text.toString()
        val descProduct = binding.etDescriptionProduct.text.toString()

        if (inputCheck(nameProduct,descProduct,basePrice,idCategory)) {
//            sellViewModel.getAccessToken().observe(viewLifecycleOwner) { accessToken ->
//                sellViewModel.getUserByAccessToken(accessToken)
//            }
//            sellViewModel.user.observe(viewLifecycleOwner) {
//                sellViewModel.postProduct(accessToken,nameProduct, descProduct, basePrice.toInt(), idCategory,it.city,productImage)
//            }
            city?.let { cty ->
                sellViewModel.postProduct(nameProduct, descProduct, basePrice.toInt(), idCategory,cty,productImage)
            }
        } else {
            Toast.makeText(requireContext(),"Oppss.. silahkan lengkapi input", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPostProduct() {
        sellViewModel.postProduct.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.loadingPost.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.loadingPost.visibility = View.GONE
                    Toast.makeText(requireContext(),"Posting product successfully..", Toast.LENGTH_SHORT).show()
                    val action = SellerPostProductDirections.actionSellerPostProductToDaftarJual()
                    findNavController().navigate(action,NavOptions.Builder().setPopUpTo(R.id.seller_post_product, true).build())
//                    binding.etNameProduct.text?.clear()
//                    binding.etPriceProduct.text?.clear()
//                    binding.ivAddPhoto.setImageResource(R.drawable.rect_add_image)
//                    binding.etDescriptionProduct.text?.clear()
                }
                Status.ERROR -> {
                    binding.loadingPost.visibility = View.GONE
                    Toast.makeText(requireContext(),"Oppss.. Failed posting product", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goToPreview() {
        val nameProduct = binding.etNameProduct.text.toString()
        val basePrice = binding.etPriceProduct.text.toString()
        val descProduct = binding.etDescriptionProduct.text.toString()

        if (inputCheck(nameProduct,descProduct,basePrice,idCategory)) {
//            sellViewModel.getAccessToken().observe(viewLifecycleOwner){ sellViewModel.getUserByAccessToken(it) }
//            sellViewModel.getUserByAccessToken()
            city?.let { cty ->
                val request = RequestPostProduct(basePrice.toInt(),idCategory,descProduct,productImage,cty,nameProduct)

                val action = SellerPostProductDirections.actionSellerPostProductToPreviewPostProduct(request)
                findNavController().navigate(action)
            }
//            sellViewModel.user.observe(viewLifecycleOwner) {
//                val request = RequestPostProduct(basePrice.toInt(),idCategory,descProduct,productImage,it.city,nameProduct)
//
//                val action = SellerPostProductDirections.actionSellerPostProductToPreviewPostProduct(request)
//                findNavController().navigate(action)
//            }
        } else {
            Toast.makeText(requireContext(),"Oppss.. silahkan lengkapi input", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkUserLogin() {
        sellViewModel.getStatusLogin().observe(viewLifecycleOwner) {
            if (!it) {
                val action = SellerPostProductDirections.actionSellerDetailProdukToLoginFragment()
                findNavController().navigate(action, NavOptions.Builder().setPopUpTo(R.id.seller_post_product, true).build())
                Toast.makeText(requireContext(),"Anda belum login, silahkan login...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupView() {
        sellViewModel.getCategory()
        sellViewModel.listCategory.observe(viewLifecycleOwner) { category ->
            val spinner = binding.spinnerCategory
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val selectedItem = p0?.getItemIdAtPosition(p2)?.toInt()
                    selectedItem?.let {
                        idCategory.clear()
                        idCategory.add(category[it].id)
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) { }
            }
            val adapter = SpinnerAdapter(requireContext(),category)
            spinner.adapter = adapter
        }

        if (::productImage.isInitialized) {
            Glide.with(requireContext())
                .load(productImage)
                .centerCrop()
                .into(binding.ivAddPhoto)
        }
    }

    private fun choseImage() {
        ImagePicker.with(this)
            .compress(1024)
            .crop()
            .maxResultSize(1080, 1080)
            .createIntent { intent ->
                pickImageResult.launch(intent)
            }
    }

    private val pickImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when(resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    productImage = File(fileUri.path.toString())
                    binding.ivAddPhoto.setImageURI(fileUri)
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun inputCheck(nameProduct:String,descProduct: String,basePrice:String,category: List<Int>): Boolean {
        return !(TextUtils.isEmpty(nameProduct) || TextUtils.isEmpty(descProduct) || TextUtils.isEmpty(basePrice)
                || !this::productImage.isInitialized || category.isEmpty())
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelStore.clear()
        _binding = null
    }
}