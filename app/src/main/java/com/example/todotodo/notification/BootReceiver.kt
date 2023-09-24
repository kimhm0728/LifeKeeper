package com.example.todotodo.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.todotodo.NotificationActivity.Companion.DEFAULT_KEY
import com.example.todotodo.NotificationActivity.Companion.HOUR_KEY
import com.example.todotodo.NotificationActivity.Companion.MINUTE_KEY
import com.example.todotodo.preference.PreferenceUtil
import com.example.todotodo.preference.TodoApplication

class BootReceiver : BroadcastReceiver() {

    private lateinit var pref: PreferenceUtil

    override fun onReceive(context: Context, intent: Intent) {
        pref = TodoApplication.prefs

        Log.d("BOOT", "start")
        if (intent.action != "android.intent.action.BOOT_COMPLETED") return

        Log.d("BOOT", "endddd")

        val h = pref.getData(HOUR_KEY, DEFAULT_KEY)
        val m = pref.getData(MINUTE_KEY, DEFAULT_KEY)

        if (h == DEFAULT_KEY || m == DEFAULT_KEY) return
        NotificationManager(context).startNotification(h.toInt(), m.toInt())
    }
}