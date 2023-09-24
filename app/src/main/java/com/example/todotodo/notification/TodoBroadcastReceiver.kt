package com.example.todotodo.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import com.example.todotodo.MainActivity
import com.example.todotodo.R
import com.example.todotodo.notification.NotificationConstant.CHANNEL_DESCRIPTION
import com.example.todotodo.notification.NotificationConstant.CHANNEL_ID
import com.example.todotodo.notification.NotificationConstant.CHANNEL_NAME
import com.example.todotodo.notification.NotificationConstant.NOTIFICATION_ID
import java.util.*

class TodoBroadcastReceiver : BroadcastReceiver() {
    private lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel()
        deliverNotification(context)
    }

    private fun createNotificationChannel() {
        val notificationChannel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
                .apply {
                    enableLights(true)
                    lightColor = Color.RED
                    enableVibration(true)
                    description = CHANNEL_DESCRIPTION
                }

        notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun deliverNotification(context: Context) {
        val intent = Intent(context, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.resources.getString(R.string.app_name))
            .setContentText(context.resources.getString(R.string.noti_text))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

}