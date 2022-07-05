package com.example.finalprojectbinaracademy_secondhandapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.daftarJualVP.diminati
import com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.daftarJualVP.produk
import com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.daftarJualVP.terjual

class MyPagerAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm) {

    //list Object fragment
    private val pages = listOf(
        produk(),
        diminati(),
        terjual()
    )

    //menentukan fragment yang akan dibuka pertama
    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    //judul untuk tabs
    override fun getPageTitle(position: Int): String {
        return when(position) {
            0 -> "produk"
            1 -> "Diminati"
            else -> "Terjual"
        }
    }
}