package com.macgongmon.inssa.Presenter
import android.view.MenuItem
import android.view.View
import com.macgongmon.inssa.adapter.MainListAdapter
import com.macgongmon.inssa.NotificationListener
import com.macgongmon.inssa.R
import com.macgongmon.inssa.MainActivityMVP
import com.macgongmon.inssa.db.RealmHelper


/**
 * Created by hyunsikyoo on 08/12/2017.
 */

class MainActivityPresenter(view: MainActivityMVP.View) : MainActivityMVP.Presenter {
    var view: MainActivityMVP.View = view
    lateinit var realmHelper: RealmHelper

    override fun initRealm(realmHelper: RealmHelper) {
        this.realmHelper = realmHelper
    }

    override fun menuOnClicked(v: View) {
        view.showPopupMenu(v)
    }

    override fun onClickedMenuDelete(){
        realmHelper.deleteAll()
        NotificationListener.messageCount = 0
        refreshData()
    }

    override fun refreshData(){
        NotificationListener.refresh()

        val listViewAdapter = MainListAdapter(realmHelper.getAllData())
        view.setAdapter(listViewAdapter)
        view.setTotalScore(realmHelper.myPoint())
    }

    override fun onMenuItemClick(item: MenuItem) {
        when (item.itemId) {
            R.id.action_mypoint -> view.showReadyDialog()
            R.id.action_delete_all -> view.showDeleteDialog()
            R.id.action_refresh -> refreshData()
            R.id.action_auth -> view.startSettingActivity()
        }
    }
}