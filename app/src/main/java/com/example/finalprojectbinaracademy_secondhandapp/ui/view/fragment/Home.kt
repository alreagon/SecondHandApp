package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.app.ProgressDialog.show
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class Home :  Fragment(R.layout.fragment_home) {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply{
            haha.setOnClickListener {

                val dialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialog)
                val view = layoutInflater.inflate(R.layout.bottomsheet,null)
                dialog.setCancelable(true)
                dialog.setContentView(view)
//                dialog.window!!.windowStyle.apply { R.style.BottomSheetDialogTheme }
                dialog.show()
                dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
//                BottomFragment.newInstance(Bundle()).apply { show(it,tag) }
            }
        }


//        var bottomSheetDialog = BottomFragment()
//        bottomSheetDialog.show(BottomSheetDialog,"TAG")

    }

}















