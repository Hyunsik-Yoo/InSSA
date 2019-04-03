package com.macgongmon.inssa

import android.app.Application
import io.realm.Realm

class InSSaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        Realm.init(this)
    }

    companion object {
        private lateinit var instance: InSSaApplication

        fun getGlobalApplicationContext(): InSSaApplication = instance
    }
}