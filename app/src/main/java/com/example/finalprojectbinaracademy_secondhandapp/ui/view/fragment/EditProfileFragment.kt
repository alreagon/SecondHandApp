package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentEditProfileBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.EditProfileViewModel
import com.example.finalprojectbinaracademy_secondhandapp.utils.Status
import com.example.finalprojectbinaracademy_secondhandapp.utils.errorToast
import com.example.finalprojectbinaracademy_secondhandapp.utils.successToast
import com.github.dhaval2404.imagepicker.ImagePicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val editProfileViewModel: EditProfileViewModel by viewModel()
    private lateinit var imageProfile: File

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUser()
        checkProfile()

        binding.btnSave.setOnClickListener {
            updateProfile()
        }

        binding.cardProfilePict.setOnClickListener {
            choseImage()
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun getUser() {
        editProfileViewModel.getUserByAccessToken()
        editProfileViewModel.detailUser.observe(viewLifecycleOwner) {
            when(it.status) {
                Status.SUCCESS -> {
                    it.data?.let { user ->
                        binding.etName.setText(user.fullName)

                        if (user.address != "DEFAULT_ADDRESS") {
                            binding.etAddress.setText(user.address)
                        }
                        if (user.phoneNumber != "000") {
                            binding.etPhone.setText(user.phoneNumber)
                        }

                        if (user.city != "DEFAULT_CITY") {
                            binding.etCity.setText(user.city)
                        }

                        user.imageUrl?.let {
                            Glide.with(requireContext())
                                .load(user.imageUrl)
                                .centerCrop()
                                .into(binding.ivProfilePict)

                            binding.ivProfilePict.alpha = 1F
                        }
                        binding.pbProfile.visibility = View.GONE
                    }
                }
                Status.ERROR -> {
                    Toast(requireContext()).errorToast(it.message.toString(),requireContext())
                    binding.pbProfile.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.pbProfile.visibility = View.VISIBLE
                }
            }
        }

//        editProfileViewModel.detailUser.observe(viewLifecycleOwner) { user ->
//            if (user != null) {
//                binding.etName.setText(user.fullName)
//
//                if (user.address != "DEFAULT_ADDRESS") {
//                    binding.etAddress.setText(user.address)
//                }
//                if (user.phoneNumber != "000") {
//                    binding.etPhone.setText(user.phoneNumber)
//                }
//
//                if (user.city != "DEFAULT_CITY") {
//                    binding.etCity.setText(user.city)
//                }
//
//                user.imageUrl?.let {
//                    Glide.with(requireContext())
//                        .load(user.imageUrl)
//                        .centerCrop()
//                        .into(binding.ivProfilePict)
//
//                    binding.ivProfilePict.alpha = 1F
//                }
//                binding.pbProfile.visibility = View.GONE
//            }
//        }
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
                    imageProfile = File(fileUri.path.toString())
                    binding.ivProfilePict.setImageURI(fileUri)
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun checkProfile() {
        editProfileViewModel.updateProfile.observe(viewLifecycleOwner) {
            when(it.status) {
                Status.SUCCESS -> {
                    Toast(requireContext()).successToast("update profile successfully..",requireContext())
                    binding.pbProfile.visibility = View.GONE
                }
                Status.ERROR -> {
                    Toast(requireContext()).errorToast(it.message.toString(),requireContext())
                    binding.pbProfile.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.pbProfile.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun updateProfile() {
        val name = binding.etName.text.toString()
        val address = binding.etAddress.text.toString()
        val city = binding.etCity.text.toString()
        val phone = binding.etPhone.text.toString()

        if (inputCheck(name,city, address, phone)) {
            if (!::imageProfile.isInitialized) {
                Toast.makeText(requireContext(),"Photo profile tidak boleh kosong",Toast.LENGTH_SHORT).show()
            } else {
                editProfileViewModel.updateProfile(imageProfile,name,phone, address, city)
            }
        } else {
            validateErrorInput(name, address, city, phone)
        }
    }

    private fun validateErrorInput(name: String, address: String, city: String, phone: String) {

        if (TextUtils.isEmpty(name)) {
            binding.wrapName.error = "Nama tidak boleh kosong"
        } else {
            binding.wrapName.error = null
        }

        if (TextUtils.isEmpty(address)) {
            binding.wrapAddress.error = "Alamat tidak boleh kosong"
        } else {
            binding.wrapAddress.error = null
        }

        if (TextUtils.isEmpty(city)) {
            binding.wrapCity.error = "Kota tidak boleh kosong"
        } else {
            binding.wrapCity.error = null
        }

        if (TextUtils.isEmpty(phone)) {
            binding.wrapPhone.error = "Nomor telepon tidak boleh kosong"
        } else {
            binding.wrapPhone.error = null
        }
    }

    private fun inputCheck(name:String,city: String,address:String,phone:String): Boolean {
        return !(TextUtils.isEmpty(name) || TextUtils.isEmpty(city) || TextUtils.isEmpty(address)
                || TextUtils.isEmpty(phone))
    }
}