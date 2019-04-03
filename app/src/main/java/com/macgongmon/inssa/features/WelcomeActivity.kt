package com.macgongmon.inssa.features

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.jakewharton.rxbinding3.view.clicks
import com.macgongmon.inssa.R
import com.macgongmon.inssa.adapter.ViewPagerAdapter
import com.macgongmon.inssa.util.fromHtml
import kotlinx.android.synthetic.main.activity_welcome.*
import java.util.concurrent.TimeUnit

class WelcomeActivity : BaseActivity(), WelcomeView {

    private lateinit var dots: ArrayList<TextView>
    private lateinit var layouts: IntArray
    private lateinit var presenter: WelcomePresenter

    fun setSkipListener() {
        compositeDisposable.add(btn_skip.clicks()
                .throttleFirst(2000, TimeUnit.MILLISECONDS)
                .subscribe {
                    val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
                    startActivity(intent)
                })
    }

    fun addBottomDots(currentPage: Int) {
        layouts = intArrayOf(R.layout.welcome_slide1, R.layout.welcome_slide2, R.layout.welcome_slide3)
        dots = arrayListOf()

        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)

        layoutDots.removeAllViews()
        for (i in layouts.indices) {
            val item = TextView(this)
            item.text = fromHtml("&#8226;")
            item.text = fromHtml("&#8226;")
            item.textSize = 35f
            item.setTextColor(colorsInactive[currentPage])
            dots.add(item)
            layoutDots.addView(item)
        }

        if (dots.size > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage])
    }

    override fun goToMainActivity() {
        startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        presenter = WelcomePresenter(this)
        presenter.onCreate()
        initUi()
    }

    private fun initUi() {
        addBottomDots(0)
        changeStatusBarColor()

        view_pager.adapter = ViewPagerAdapter(this, layouts)
        view_pager.addOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                addBottomDots(position)
                if (position == layouts.size - 1) {
                    btn_next.text = "시작하기"
                    btn_skip.text = "권한설정"
                    setSkipListener()
                } else {
                    btn_next.text = "다음"
                    btn_skip.visibility = View.VISIBLE
                }
            }
        })

        compositeDisposable.add(btn_skip.clicks()
                .throttleFirst(2000, TimeUnit.MILLISECONDS)
                .subscribe { goToMainActivity() })

        compositeDisposable.add(btn_next.clicks()
                .throttleFirst(2000, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (view_pager.currentItem + 1 < layouts.size) {
                        view_pager.currentItem = view_pager.currentItem + 1
                    } else {
                        goToMainActivity()
                    }
                })
    }

    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }
}
