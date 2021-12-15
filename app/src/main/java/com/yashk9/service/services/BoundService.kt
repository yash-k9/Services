package com.yashk9.service.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BoundService: Service() {

    private val TAG = "BoundService"

    private val localBinder: IBinder = LocalBinder()

    override fun onBind(intent: Intent?): IBinder {
        return localBinder
    }

    fun getRandomNumber(): Flow<Int> = flow{
        for(value in 0..100){
            delay(700)
            val num = (0..4000).random()
            Log.d(TAG, "getRandomNumber -> $value: $num")
            emit(num)
        }
    }

    inner class LocalBinder(): Binder(){
        fun getService(): BoundService = this@BoundService
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: Service Stopped")
        super.onDestroy()
    }

}