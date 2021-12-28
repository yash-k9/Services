package com.yashk9.service.services

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.yashk9.service.R

class AlarmService: BroadcastReceiver() {
    companion object{
        const val TAG = "ALARM SERVICE"
        const val CHANNEL_ID = "456"
        const val NOTIFICATION_ID = 999
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive: Alarm Service Started")
        val id = intent?.getIntExtra("id", 0)
        if(id == 123){
            if (context != null) {
                createNotification(context)
            }
        }
    }

    private fun createNotification(context: Context) {
        createNotificationChannel(context)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Alarm Fired")
            .setContentText("Notifying the user")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel(context: Context?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Alarm Channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = context?.getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }
}