package com.example.finalprojectbinaracademy_secondhandapp.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.databinding.ActivityMainBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val homeFragment = Home()
    private val notificationFragment = Buyer_Notification()
    private val detailProduk = Seller_DetailProduk()
    private val daftarJual = DaftarJual()
    private val profile = Profile()


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        replaceFragmnet(homeFragment)
//
//        val navigationBaten = findViewById<BottomNavigationView>(R.id.nav_host_fragment_activity_main)

//        navigationBaten.setOnItemSelectedListener { item->
//            when(item.itemId){
//                R.id.bottom_nav_home -> replaceFragmnet(homeFragment)
//                R.id.bottom_nav_notif -> replaceFragmnet(notificationFragment)
//                R.id.bottom_nav_listsell -> replaceFragmnet(detailProduk)
//                R.id.bottom_nav_sell -> replaceFragmnet(daftarJual)
//                R.id.bottom_nav_user -> replaceFragmnet(profile)
//            }
//            true
//        }

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.botomnav.setupWithNavController(navController)

    }

//    private fun replaceFragmnet(fragment: Fragment) {
//        if (fragment != null){
//            val mindahin = supportFragmentManager.beginTransaction()
//            mindahin.replace(R.id.nav_host_fragment_activity_main,fragment)
//            mindahin.commit()
//        }
//
//    }


}


















