package com.macgongmon.inssa.activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.text.Html
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.macgongmon.inssa.PrefManager
import com.macgongmon.inssa.Presenter.WelcomeActivityPresenter
import com.macgongmon.inssa.R
import com.macgongmon.inssa.WelcomeActivityMVP
import com.macgongmon.inssa.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : BaseActivity(), WelcomeActivityMVP.View {

    lateinit var dots: ArrayList<TextView>
    lateinit var layouts: IntArray
    lateinit var prefManager: PrefManager
    lateinit var presenter: WelcomeActivityMVP.Presenter

    override fun setNextButtonText(text: String) {
        btn_skip.text = text
    }

    override fun setSkipButtonText(text: String) {
        btn_skip.text = text
    }

    override fun setSkipButtonVisible() {
        btn_skip.visibility = View.VISIBLE
    }

    override fun setSkipListener() {
        btn_skip.setOnClickListener {
            val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
            startActivity(intent)
        }
    }

    override fun addBottomDots(currentPage: Int) {
        layouts = intArrayOf(R.layout.welcome_slide1, R.layout.welcome_slide2, R.layout.welcome_slide3)
        dots = arrayListOf()

        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)

        layoutDots.removeAllViews()
        for (i in layouts.indices) {
            val item = TextView(this)
            item.text = Html.fromHtml("&#8226;")
            item.text = Html.fromHtml("&#8226;")
            item.textSize = 35f
            item.setTextColor(colorsInactive[currentPage])
            dots.add(item)
            layoutDots.addView(item)
        }

        if (dots.size > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage])
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        presenter = WelcomeActivityPresenter(this)

        // Checking for first time launch - before calling setContentView()
        prefManager = PrefManager(this)
        if (!prefManager.isFirstTimeLaunch) {
            launchHomeScreen()
            finish()
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        setContentView(R.layout.activity_welcome)


        // adding bottom dots
        addBottomDots(0)

        // making notification bar transparent
        changeStatusBarColor()

        view_pager.adapter = ViewPagerAdapter(this, layouts)
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
            override fun onPageSelected(position: Int) {
                addBottomDots(position)
                presenter.onPageSelected(position, layouts.size)
            }
        })

        btn_skip.setOnClickListener { launchHomeScreen() }
        btn_next.setOnClickListener {
            // checking for last page
            // if last page home screen will be launched
            val current = getItem(+1)
            if (current < layouts.size) {
                view_pager.currentItem = current
            } else {
                launchHomeScreen()
            }
        }
    }


    private fun getItem(i: Int): Int {
        return view_pager.currentItem + i
    }

    private fun launchHomeScreen() {
        prefManager!!.isFirstTimeLaunch = false
        startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
        finish()
    }

    /**
     * Making notification bar transparent
     */
    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }
}
