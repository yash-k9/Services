package com.yashk9.service

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.yashk9.service.databinding.ActivityBoundedBinding
import com.yashk9.service.services.BoundService
import com.yashk9.service.viewModel.ServiceViewModel
import kotlinx.coroutines.flow.collectLatest

class BoundedActivity : AppCompatActivity() {

    private val TAG = "BoundedActivity"
    private lateinit var binding: ActivityBoundedBinding
    private var service: BoundService? = null
    private val viewModel: ServiceViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoundedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeBinderService()
        observeRandomNumber()
    }

    private fun observeRandomNumber() {
        lifecycleScope.launchWhenStarted {
            viewModel.bound.collectLatest{ bound ->
               if(bound){
                    service?.getRandomNumber()?.collectLatest {
                        binding.textView.text = "Generated Number is $it"
                    }
               }
            }
        }
    }


    private fun observeBinderService(){
        lifecycleScope.launchWhenStarted {
           viewModel.binder.collectLatest {
               it?.let{
                   Log.d(TAG, "observeBinder: Got Service")
                   service = it.getService()
               }
           }
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, BoundService::class.java).also { intent ->
            startService(intent)
        }
        bindToService()
    }

    private fun bindToService() {
        Intent(this, BoundService::class.java).also { intent ->
            bindService(intent, viewModel.serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(viewModel.serviceConnection)
    }
}