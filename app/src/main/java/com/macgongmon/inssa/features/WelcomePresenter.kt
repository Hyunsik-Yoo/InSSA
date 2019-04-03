package com.macgongmon.inssa.features

import com.macgongmon.inssa.InSSaApplication
import com.macgongmon.inssa.util.AppPreferenceDataStore

/**
 * Created by hyunsikyoo on 28/12/2017.
 */
class WelcomePresenter(private val view: WelcomeView): BasePresenter{
    private val appPreferenceDataStore = AppPreferenceDataStore(InSSaApplication.getGlobalApplicationContext())

    fun onCreate() {
        if(appPreferenceDataStore.isFirstLaunch()) {

        } else {
            appPreferenceDataStore.putFirstLaunch(false)
            view.goToMainActivity()
        }
    }
}