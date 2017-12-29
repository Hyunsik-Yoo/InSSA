package com.macgongmon.inssa

/**
 * Created by hyunsikyoo on 28/12/2017.
 */
interface WelcomeActivityMVP {
    interface View{
        fun setNextButtonText(text: String)

        fun setSkipButtonText(text: String)

        fun setSkipButtonVisible()

        fun setSkipListener()

        fun addBottomDots(currentPage: Int)

    }

    interface Presenter{
        fun onPageSelected(position: Int, length: Int)

    }
}