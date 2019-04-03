package com.macgongmon.inssa.features

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by hyunsikyoo on 09/12/2017.
 */
open class BaseActivity : AppCompatActivity() {
    val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}