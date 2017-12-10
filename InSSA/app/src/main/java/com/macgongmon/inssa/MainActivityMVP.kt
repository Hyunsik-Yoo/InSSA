package com.macgongmon.inssa

import android.content.Context
import android.content.res.AssetManager
import android.view.MenuItem
import android.widget.TextView
import com.macgongmon.inssa.adapter.MainListAdapter

/**
 * Created by hyunsikyoo on 09/12/2017.
 */
interface MainActivityMVP{
    interface Model{
        fun createDayDate(date: String, score: Int)
    }

    interface View{
        fun setTotalScore(score: Int)

        fun setTextViewNotoFont(textView: TextView)

        fun getAssets(): AssetManager

        fun getContext(): Context

        fun showPopupMenu(view: android.view.View)

        fun getTotalPoint(): Int

        fun showDeleteDialog()

        fun setAdapter(adapter: MainListAdapter)

        fun startSettingActivity()
    }

    interface Presenter{
        fun menuOnClicked(v: android.view.View)

        fun onMenuItemClick(item: MenuItem)

        fun openDBHelper(context: Context)

        fun onClickedMenuDelete()

        fun refreshData()
    }
}