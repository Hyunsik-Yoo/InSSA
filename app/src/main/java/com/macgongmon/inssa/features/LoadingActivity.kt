package com.macgongmon.inssa.features

import android.content.Intent
import android.os.Bundle
import com.macgongmon.inssa.R
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


class LoadingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        startLoading()
    }

    private fun startLoading() {
        compositeDisposable.add(Observable.just(0).delay(2000, TimeUnit.MILLISECONDS)
                .subscribe {
                    goToWelcomeActivity()
                })
    }

    private fun goToWelcomeActivity() {
        val intent = Intent(baseContext, WelcomeActivity::class.java)

        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        finish()
    }
}
