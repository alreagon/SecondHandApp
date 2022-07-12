package com.example.finalprojectbinaracademy_secondhandapp.ui.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.daftarJualvp.Diminati
import com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.daftarJualvp.Produk
import com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.daftarJualvp.terjual

class MyPagerAdapter(fm: FragmentManager,lifecycle: Lifecycle) : FragmentStateAdapter(fm,lifecycle) {

    //list Object fragment
    private val pages = listOf(
        Produk(),
        Diminati(),
        terjual()
    )

    //menentukan fragment yang akan dibuka pertama
//    override fun getItem(position: Int): Fragment {
//        return pages[position]
//    }
//
//    override fun getCount(): Int {
//        return pages.size
//    }
//
//    //judul untuk tabs
//    override fun getPageTitle(position: Int): String {
//        return when(position) {
//            0 -> "produk"
//            1 -> "Diminati"
//            else -> "Terjual"
//        }
//    }

    override fun getItemCount(): Int {
        return pages.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> Produk()
            1 -> Diminati()
            2 -> terjual()
            else -> Produk()
        }
    }

    companion object {
        val tabTitle = arrayListOf(
            "Produk",
            "Diminati",
            "Terjual"
        )
    }

}