package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.UpdateProfileRequest
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentEditProfileBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.EditProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val editProfileViewModel: EditProfileViewModel by viewModel()
    private var imageProfile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUser()

        binding.btnSave.setOnClickListener {
            updateProfile()
        }

        binding.cardProfilePict.setOnClickListener {
            checkingPermission()
        }
    }

    private fun getUser() {
        editProfileViewModel.getAccessToken().observe(viewLifecycleOwner, Observer { accessToken ->
            editProfileViewModel.getUserByAccessToken(accessToken)
        })

        editProfileViewModel.detailUser.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                binding.etName.setText(user.fullName)
                binding.etAddress.setText(user.address)
                binding.etPhone.setText(user.phoneNumber)

                if (user.city != "jakarta") {
                    binding.etCity.setText(user.city)
                }

                user.imageUrl?.let {
                    Glide.with(requireContext())
                        .load(user.imageUrl)
                        .centerCrop()
                        .into(binding.ivProfilePict)

                    binding.ivProfilePict.alpha = 1F
                }
            }
        })
    }

    private fun checkingPermission() {
        if (isGranted(
                requireActivity(),
                android.Manifest.permission.CAMERA,
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION )
        ) {
            chooseImageDialog()
        }
    }

    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("permission denied")
            .setMessage("please allow the permission")
            .setPositiveButton("App Setting") { _,_ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", activity?.packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("cancle") { dialog,_ -> dialog.cancel()}
            .show()
    }

    private fun chooseImageDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("Pilih Gambar")
            .setPositiveButton("Gallery") {_,_ -> openGallery() }
            .setNegativeButton("Camera") {_,_ -> openCamera() }
            .show()
    }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            val toBitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, Uri.parse(result.toString()))
//            val bitmapToString = bitmapToString(toBitmap)
//            imageProfile = toBitmap
            editProfileViewModel.detailUser.observe(viewLifecycleOwner, Observer {
                imageProfile = bitmapToFile(toBitmap,"profile-pict-${it.id}.png")
            })

            binding.ivProfilePict.alpha = 1F

            Glide.with(this)
                .load(result)
                .centerCrop()
                .into(binding.ivProfilePict)
        }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryResult.launch("image/*")
    }

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                handleCameraImage(result.data)
            }
        }

    private fun handleCameraImage(intent: Intent?) {
        val bitmap = intent?.extras?.get("data") as Bitmap
//        imageProfile = bitmapToString(bitmap)
        editProfileViewModel.detailUser.observe(viewLifecycleOwner, Observer {
            imageProfile = bitmapToFile(bitmap,"profile-pict-${it.id}.png")
        })

        binding.ivProfilePict.alpha = 1F

        Glide.with(this)
            .load(bitmap)
            .centerCrop()
            .into(binding.ivProfilePict)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResult.launch(cameraIntent)
    }

    private fun bitmapToString(bitmap: Bitmap): String {
        val byteArrayStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayStream)
        Bitmap.createScaledBitmap(bitmap, 150,150,false)
        val toByteArray = byteArrayStream.toByteArray()

        return Base64.encodeToString(toByteArray, Base64.DEFAULT)
    }

    fun bitmapToFile(bitmap: Bitmap, fileNameToSave: String): File? { // File name like "image.png"
        //create a file to write bitmap data
        var file: File? = null
        return try {
            file = File(Environment.getExternalStorageDirectory().toString() + File.separator + fileNameToSave)
            file.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos) // YOU can also save it in JPEG
            val bitmapdata = bos.toByteArray()

            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }

    private fun updateProfile() {
        val name = binding.etName.text.toString()
        val address = binding.etAddress.text.toString()
        val city = binding.etCity.text.toString()
        val phone = binding.etPhone.text.toString()

        if (inputCheck(name,city, address, phone)) {
            val request = UpdateProfileRequest(address,city,name,imageProfile,phone.toLong())

            editProfileViewModel.getAccessToken().observe(viewLifecycleOwner, Observer {
                editProfileViewModel.updateProfile(it,request)
            })

            editProfileViewModel.updateProfile.observe(viewLifecycleOwner, Observer {
                it?.let {
                    Toast.makeText(requireContext(),it.city,Toast.LENGTH_SHORT).show()
                }
            })
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

        if (imageProfile == null) {
            Toast.makeText(requireContext(),"Photo profile tidak boleh kosong",Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(name:String,city: String,address:String,phone:String): Boolean {
        return !(TextUtils.isEmpty(name) || TextUtils.isEmpty(city) || TextUtils.isEmpty(address)
                || TextUtils.isEmpty(phone))
    }

    companion object {
        val REQUEST_CODE_PERMISSION = 99
    }
}