package com.macgongmon.inssa.activity

import android.app.Activity
import android.os.Bundle

/**
 * Created by hyunsikyoo on 09/12/2017.
 */
abstract class BaseActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
    }
}