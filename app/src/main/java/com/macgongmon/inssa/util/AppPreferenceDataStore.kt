package com.macgongmon.inssa.util

import android.content.Context

const val IS_FIRST_LAUNCH = "IS_FIRST_LAUNCH"

class AppPreferenceDataStore(context: Context): SharedPreferenceDataStore(context) {

    fun putFirstLaunch(boolean: Boolean) {
        putBoolean(IS_FIRST_LAUNCH, boolean)
    }

    fun isFirstLaunch(): Boolean {
        return getBoolean(IS_FIRST_LAUNCH)
    }
}