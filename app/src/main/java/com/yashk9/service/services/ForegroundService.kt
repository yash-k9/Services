package com.yashk9.service.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.yashk9.service.MainActivity
import com.yashk9.service.R
import kotlin.concurrent.thread

class ForegroundService: Service() {
    private val TAG = "ForegroundService"
    private val CHANNEL_ID = "ForegroundService"
    private val SERVICE_ID = 1001

    companion object {
        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, ForegroundService::class.java)
            ContextCompat.startForegroundService(context, startIntent)
        }

        fun stopService(context: Context) {
            val stopIntent = Intent(context, ForegroundService::class.java)
            context.stopService(stopIntent)
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        doTask()

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service Running...")
            .setContentText("Performing the task")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        startForeground(SERVICE_ID, notification)

        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    private fun doTask() {
        thread {
            for(value in 1..100){
                Thread.sleep(500)
                Log.d(TAG, "doTask: $value -> ${Thread.currentThread().name}")
            }
            stopSelf()
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: Service Stopped")
        super.onDestroy()
    }
}