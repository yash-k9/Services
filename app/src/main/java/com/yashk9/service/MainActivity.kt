package com.yashk9.service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.yashk9.service.databinding.ActivityMainBinding
import com.yashk9.service.services.BackgroundService
import com.yashk9.service.services.ForegroundService
import android.app.ActivityManager
import android.content.Context


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var serviceIntent: Intent
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        with(binding){
            backgroundService.setOnClickListener { startAppBackgroundService() }
            foregroundService.setOnClickListener { startAppForegroundService() }
            boundService.setOnClickListener { startAppBoundedActivity() }
        }
    }

    private fun startAppBackgroundService() {
        Log.d(TAG, "startAppBackgroundService: Service Start")
        serviceIntent = Intent(applicationContext, BackgroundService::class.java)
        startService(serviceIntent)
    }

    private fun startAppForegroundService() {
        Log.d(TAG, "startAppForegroundService: Service Start")
        ForegroundService.startService(this, "Foreground Service is running...")
    }

    private fun startAppBoundedActivity() {
        Intent(this, BoundedActivity::class.java).apply {
            startActivity(this)
        }
    }

}