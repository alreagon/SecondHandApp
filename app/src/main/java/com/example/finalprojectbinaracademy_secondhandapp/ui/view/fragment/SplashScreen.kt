package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.annotation.SuppressLint
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import android.view.View
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentSplashScreenBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.RegisterViewModel

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment(R.layout.fragment_splash_screen) {

    private val viewModel: RegisterViewModel by viewModel()
    private var fragmentSplashBinding: FragmentSplashScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSplashScreenBinding.bind(view)
        fragmentSplashBinding = binding
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getLoginStatus().observe(viewLifecycleOwner) { isLogin ->
                if (isLogin == true) {
                    findNavController().navigate(R.id.action_splashScreen_to_home2)
                } else {

                    findNavController().navigate(R.id.action_splashScreen_to_login)
                }
            }
        }, 4000)
    }


}