package com.macgongmon.inssa.util

import android.content.Context

open class SharedPreferenceDataStore(val context: Context) {
    private val sharedPreferences = context.getSharedPreferences(context.packageName , Context.MODE_PRIVATE)

    fun putBoolean(key: String, boolean: Boolean) {
        sharedPreferences.edit().putBoolean(key, boolean).apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }
}