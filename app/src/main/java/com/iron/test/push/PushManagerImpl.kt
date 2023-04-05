package com.iron.test.push

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.iron.test.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class PushManagerImpl @Inject constructor(@ApplicationContext private val context: Context) : PushManager {

    companion object {
        const val CHANNEL_ID = "MY_CHANNEL_ID"
        const val CHANNEL_NAME = "MY_CHANNEL_NAME"
        const val NOTIF_TITLE = "Hey-ho"
    }

    private val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    override fun showPush(data: String) {
        manager.notify(111, createNotif(data))
    }

    private fun createNotif(data: String): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setColor(ContextCompat.getColor(context, android.R.color.black))
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setContentTitle(NOTIF_TITLE)
            .setContentText(data)
            .build()
    }
}