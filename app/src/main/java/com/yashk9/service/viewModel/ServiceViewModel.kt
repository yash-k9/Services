package com.yashk9.service.viewModel

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.ViewModel
import com.yashk9.service.services.BoundService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

class ServiceViewModel: ViewModel() {
    private val TAG = "DownloadViewModel"

    private val _binder = MutableStateFlow<BoundService.LocalBinder?>(null)
    private val _bound = MutableStateFlow(false)

    val binder = _binder.asStateFlow()
    val bound = _bound.asStateFlow()


    val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(component: ComponentName?, iBinder: IBinder?){
            Log.d(TAG, "onServiceConnected: Service Connected")
            val binder = iBinder as BoundService.LocalBinder
            _binder.value = binder
            _bound.value = true
        }

        override fun onServiceDisconnected(component: ComponentName?){
            Log.d(TAG, "onServiceConnected: Service Disconnected")
            _binder.value = null
            _bound.value = false
        }
    }

}