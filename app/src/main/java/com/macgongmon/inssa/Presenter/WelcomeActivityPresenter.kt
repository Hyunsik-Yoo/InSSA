package com.macgongmon.inssa.Presenter

import com.macgongmon.inssa.WelcomeActivityMVP
import com.macgongmon.inssa.activity.WelcomeActivity

/**
 * Created by hyunsikyoo on 28/12/2017.
 */
class WelcomeActivityPresenter(view: WelcomeActivityMVP.View): WelcomeActivityMVP.Presenter{
    var view = view

    override fun onPageSelected(position: Int, length: Int) {
        if(position == length - 1){
            view.setNextButtonText("시작하기")
            view.setSkipButtonText("권한설정")
            view.setSkipListener()
        }
        else{
            view.setNextButtonText("다음")
            view.setSkipButtonVisible()
        }
    }
}