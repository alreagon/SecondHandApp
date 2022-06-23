package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.annotation.SuppressLint
import android.app.ProgressDialog.show
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentHomeBinding
import com.example.finalprojectbinaracademy_secondhandapp.utils.imageData
import com.example.finalprojectbinaracademy_secondhandapp.utils.imageSliderAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class Home : Fragment(R.layout.fragment_home) {

    private lateinit var adapter: imageSliderAdapter
    private val list = ArrayList<imageData>()
    private lateinit var dots: ArrayList<TextView>
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            haha.setOnClickListener {

                val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
                val view1 = layoutInflater.inflate(R.layout.bottomsheet, null)
                dialog.setCancelable(true)
                dialog.setContentView(view1)
                dialog.show()
                dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
            }
        }


        list.add(
            imageData(
                R.drawable.shopeebanner
            )
        )
        list.add(
            imageData(
                R.drawable.shopeebanner1
            )
        )
        list.add(
            imageData(
                R.drawable.shopeebanner2
            )
        )
        list.add(
            imageData(
                R.drawable.shopeebanner3
            )
        )
        adapter = imageSliderAdapter(list)
        binding.viewPager.adapter = adapter
        dots = ArrayList()

        binding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
    }

}





















