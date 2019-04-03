package com.macgongmon.inssa.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.macgongmon.inssa.R


class LoadingActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        startLoading()
    }

    private fun startLoading() {
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(baseContext, WelcomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
            finish()
        }, 2000)
    }
}
