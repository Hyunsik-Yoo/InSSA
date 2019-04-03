package com.macgongmon.inssa

import android.content.Context
import android.content.res.AssetManager
import androidx.recyclerview.widget.RecyclerView
import android.view.MenuItem
import android.widget.TextView
import com.macgongmon.inssa.adapter.MainListAdapter
import com.macgongmon.inssa.db.RealmHelper

/**
 * Created by hyunsikyoo on 09/12/2017.
 */
interface MainActivityMVP{
    interface View{
        fun setTotalScore(score: Int)

        fun getAssets(): AssetManager

        fun getContext(): Context

        fun getTotalPoint(): Int

        fun showDeleteDialog()

        fun setAdapter()

        fun startSettingActivity()

        fun showReadyDialog()
    }

    interface Presenter{

        fun onMenuItemClick(itemID: Int)

        fun onClickedMenuDelete()

        fun refreshData()

        fun initRealm(realmHelper: RealmHelper)
    }
}