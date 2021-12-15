package com.yashk9.service.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.yashk9.service.R
import kotlin.concurrent.thread

class BackgroundService: Service() {
    private val TAG = "BackgroundService"

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        doTask()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun doTask() {
        thread{
            for(value in 1..60){
                Thread.sleep(500)
                Log.d(TAG, "doTask: $value -> ${Thread.currentThread().name}")
            }
            stopSelf()
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: Stopped")
        super.onDestroy()
    }
}