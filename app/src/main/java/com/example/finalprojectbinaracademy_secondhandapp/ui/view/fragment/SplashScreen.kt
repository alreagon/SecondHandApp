package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
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