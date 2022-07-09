package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentBuyerNotificationBinding
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentChangePasswordBinding
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentProfileBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.AuthViewModel
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.EditProfileViewModel
import com.example.finalprojectbinaracademy_secondhandapp.utils.PasswordUtils
import com.example.finalprojectbinaracademy_secondhandapp.utils.Resource
import com.example.finalprojectbinaracademy_secondhandapp.utils.Status
import kotlinx.android.synthetic.main.fragment_change_password.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePasswordFragment : Fragment() {
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!
    private val editProfileViewModel: EditProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChangePasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnChangePass.setOnClickListener {
            changePassDialog()
        }
        binding.btnBackChangePass.setOnClickListener {
            findNavController().navigateUp()
        }
        checkChangePass()
    }

    private fun checkChangePass() {
        editProfileViewModel.changePass.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    loadingChangePass.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    loadingChangePass.visibility = View.GONE
                    Toast.makeText(requireContext(),"ubah password berhasil",Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
                Status.ERROR -> {
                    loadingChangePass.visibility = View.GONE
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun changePassDialog() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Ubah Password")
        dialog.setMessage("Apakah anda yakin ingin mengubah password anda?")

        dialog.setCancelable(true)
        dialog.setPositiveButton("YES"){dialogInterface, p1 ->
            changePass()
        }

        dialog.setNegativeButton("NO"){dialogInterface, p1->
            dialogInterface.dismiss()
        }

        dialog.show()
    }

    private fun changePass() {
        val currentpass = binding.etCurrentPass.text.toString()
        val newPass = binding.etNewPass.text.toString()
        val confirmPass = binding.etConfirmPass.text.toString()

        if (inputCheck(currentpass, newPass, confirmPass)) {
            editProfileViewModel.changePassword(currentpass,newPass,confirmPass)
        } else {
            validateErrorInput(currentpass, newPass, confirmPass)
        }
    }

    private fun validateErrorInput(currentPass:String, newPass: String, confirmPass: String) {

        if (TextUtils.isEmpty(currentPass)) {
            binding.wrapCurrentPass.error = "Masukkan password anda saat ini"
        } else {
            binding.wrapCurrentPass.error = null
        }

        if (!PasswordUtils().validate(newPass)) {
            binding.wrapNewPass.error = "Harus mengandung upercase, lowercase, angka, dan min. 8 karakter"
        } else if (TextUtils.isEmpty(newPass)) {
            binding.wrapNewPass.error = "Masukkan password baru anda"
        } else {
            binding.wrapNewPass.error = null
        }

        if (confirmPass != newPass) {
            binding.wrapConfirmPass.error = "Konfirmasi password salah"
        } else if (TextUtils.isEmpty(confirmPass)){
            binding.wrapConfirmPass.error = "Ulangi password baru anda"
        } else {
            binding.wrapConfirmPass.error = null
        }
    }

    private fun inputCheck(currentPass:String, newPass: String, confirmPass: String): Boolean {
        return !(TextUtils.isEmpty(currentPass) || TextUtils.isEmpty(newPass) || !PasswordUtils().validate(newPass)
                || TextUtils.isEmpty(confirmPass) || newPass != confirmPass)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelStore.clear()
        _binding = null
    }
}