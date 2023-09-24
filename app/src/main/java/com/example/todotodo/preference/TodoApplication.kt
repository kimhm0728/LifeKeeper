package com.example.todotodo.preference

import android.app.Application

class TodoApplication : Application() {

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }

    companion object {
        lateinit var prefs: PreferenceUtil
    }
}