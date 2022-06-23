package com.example.finalprojectbinaracademy_secondhandapp.ui.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import com.example.finalprojectbinaracademy_secondhandapp.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val handlerThread = HandlerThread("HandlerThread")
        handlerThread.start()
        val backgroundHandler = Handler(handlerThread.looper).postDelayed ({
            println("Thread : " + Thread.currentThread().name)

            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
            handlerThread.quitSafely();
        }, 3000)

    }
}