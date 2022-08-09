package com.example.thingstodo.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import com.example.thingstodo.other.Constants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ThingToDoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(Constants.NOTIFICATIONS_CHANNEL_ID, Constants.NOTIFICATIONS_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
                .apply {
                    lightColor = Color.GREEN
                    enableLights(true)
                    description = "Used to show tasks due in 30 minutes or less"
                }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}