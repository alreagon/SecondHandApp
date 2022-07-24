package com.example.finalprojectbinaracademy_secondhandapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.daftarJualvp.Diminati
import com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.daftarJualvp.Produk
import com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.daftarJualvp.Terjual

class MyPagerAdapter(fm: FragmentManager,lifecycle: Lifecycle) : FragmentStateAdapter(fm,lifecycle) {

    //list Object fragment
    private val pages = listOf(
        Produk(),
        Diminati(),
        Terjual()
    )

    override fun getItemCount(): Int {
        return pages.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> Produk()
            1 -> Diminati()
            2 -> Terjual()
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