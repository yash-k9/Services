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
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import com.yashk9.service.services.AlarmService
import com.yashk9.service.services.MyJobScheduler
import java.util.*


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
            jobScheduler.setOnClickListener { startJobService() }
            alarmManager.setOnClickListener { startAlarmManager() }
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

    //Fires Alarm in 1min
    private fun startAlarmManager() {
        Log.d(TAG, "startAlarmManager: Starting Alarm Manager...")
        val alarmManager = this.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmService::class.java).apply {
            putExtra("id", 123)
        }
        val pendingIntent = PendingIntent.getBroadcast(this, 123,  intent, PendingIntent.FLAG_NO_CREATE)
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis()+10000,
            pendingIntent
        )
    }

    private fun startJobService() {
        val jobInfo = JobInfo.Builder(12121, ComponentName(this, MyJobScheduler::class.java)).build()
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        val scheduleRes = jobScheduler.schedule(jobInfo)
        if(scheduleRes == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "startJobService: Success")
        }else{
            Log.d(TAG, "startJobService: Failed")
        }
    }

}