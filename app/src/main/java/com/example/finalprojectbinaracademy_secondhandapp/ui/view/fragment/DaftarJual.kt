package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentDaftarJualBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.MyPagerAdapter
import kotlinx.android.synthetic.main.fragment_daftar_jual.*

class DaftarJual : Fragment(R.layout.fragment_daftar_jual) {
    private var _binding: FragmentDaftarJualBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDaftarJualBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vp_1.adapter = MyPagerAdapter(childFragmentManager)
        tabs_main.setupWithViewPager(vp_1)
    }

}