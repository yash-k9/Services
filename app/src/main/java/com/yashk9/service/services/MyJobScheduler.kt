package com.yashk9.service.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class MyJobScheduler: JobService() {
    companion object{
       const val TAG = "MyJobScheduler"
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(TAG, "onStartJob: ${Thread.currentThread().name}")
        doTask(params)
        return true
    }

    private fun doTask(params: JobParameters?) {
        Thread {  Log.d(TAG, "doTask: ${Thread.currentThread().name}")
            for(i in 1..40){
                Thread.sleep(100)
                Log.d(TAG, "onStartJob: $i")
            }
            jobFinished(params, false)
        }.start()
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG, "onStopJob: Job Stopped")
        return false
    }
}