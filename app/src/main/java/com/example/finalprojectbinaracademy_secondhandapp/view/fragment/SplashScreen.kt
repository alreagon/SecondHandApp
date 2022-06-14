package com.example.finalprojectbinaracademy_secondhandapp.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.UserManager
import android.renderscript.ScriptGroup
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.window.SplashScreen
import androidx.navigation.fragment.findNavController
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentSplashScreenBinding

class SplashScreen : Fragment(R.layout.fragment_splash_screen) {

//    private val viewModel: LoginViewModel by viewModel()
//    private var fragmentSplashBinding: FragmentSplashScreenBinding? = null

    private var fragmentSplashBinding : FragmentSplashScreenBinding?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSplashScreenBinding.bind(view)
        fragmentSplashBinding = binding
//        Handler(Looper.getMainLooper()).postDelayed({
//            viewModel.getLoginStatus().observe(viewLifecycleOwner) { isLogin ->
//                if (isLogin == true) {
//                    findNavController().navigate(R.id.action_splashFragment3_to_homeFragment)
//                } else {
//
//                    findNavController().navigate(R.id.action_splashFragment3_to_loginFragment)
//                }
//            }
//        }, 4000)
    }


}