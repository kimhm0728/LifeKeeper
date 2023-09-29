package com.example.todotodo.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.todotodo.NotificationActivity.Companion.DEFAULT_KEY
import com.example.todotodo.NotificationActivity.Companion.HOUR_KEY
import com.example.todotodo.NotificationActivity.Companion.MINUTE_KEY
import com.example.todotodo.NotificationActivity.Companion.SWITCH_KEY
import com.example.todotodo.preference.PreferenceUtil
import com.example.todotodo.preference.TodoApplication

class BootReceiver : BroadcastReceiver() {

    private lateinit var pref: PreferenceUtil

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "BOOT_COMPLETED onReceive()")

        pref = TodoApplication.prefs
        if (!pref.getData(SWITCH_KEY, DEFAULT_KEY).toBoolean()) return

        val h = pref.getData(HOUR_KEY, DEFAULT_KEY)
        val m = pref.getData(MINUTE_KEY, DEFAULT_KEY)

        if (h == DEFAULT_KEY || m == DEFAULT_KEY) return
        AlarmController(context).startNotification(h.toInt(), m.toInt())
    }

    companion object {
        private val TAG = BootReceiver::class.simpleName
    }
}