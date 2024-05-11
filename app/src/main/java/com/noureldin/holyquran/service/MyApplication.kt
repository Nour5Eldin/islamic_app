package com.noureldin.holyquran.service

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import com.noureldin.holyquran.R
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication:Application() {
    companion object{
        val RADIO_PLAYER_NOTIFICATION_CHANNEL = "radio_channel"
    }
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val name = getString(R.string.radio_channel)
            val descriptionText = getString(R.string.radio_channel_description)
            val importance = NotificationManager.IMPORTANCE_LOW

            val channel = NotificationChannel(RADIO_PLAYER_NOTIFICATION_CHANNEL,name,importance )
            channel.description = descriptionText
            channel.lightColor = Color.YELLOW
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel )


        }
    }
}