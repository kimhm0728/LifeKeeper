package com.example.todotodo.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.icu.util.Calendar
import android.util.Log
import com.example.todotodo.notification.NotificationConstant.ALARM_ID

class AlarmController(private val context: Context) {

    private val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

    fun startNotification(hour: Int, minute: Int) {
        Log.d(TAG, "startNotification()")

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1)
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            getAlarmIntent()
        )
    }

    fun stopNotification() {
        Log.d(TAG, "stopNotification()")

        alarmManager.cancel(getAlarmIntent())
    }

    private fun getAlarmIntent(): PendingIntent {
        return Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, ALARM_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }
    }

    companion object {
        private val TAG = AlarmController::class.simpleName
    }
}