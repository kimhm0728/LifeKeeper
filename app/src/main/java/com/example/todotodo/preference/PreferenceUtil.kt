package com.example.todotodo.preference

import android.content.Context

class PreferenceUtil(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_TAG, Context.MODE_PRIVATE)

    fun getData(key: String, value: String): String {
        return prefs.getString(key, value).toString()
    }

    fun setData(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    companion object {
        private const val PREFS_TAG = "prefs"
    }
}