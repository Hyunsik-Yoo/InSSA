package com.macgongmon.inssa

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.macgongmon.inssa.db.DBOpenHelper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

/**
 * Created by hyunsikyoo on 20/08/2017.
 */

class NotificationListener : NotificationListenerService() {
    val TAG = javaClass.simpleName
    val KAKAO_PACKAGE = "com.kakao.talk"

    /**
     * 알림이 왔을 때 카톡인지 확인한 뒤 카운팅
     * @param sbn
     */
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val packageName = sbn.packageName
        Log.d(TAG, packageName)

        if (packageName == KAKAO_PACKAGE) {
            refresh()
            messageCount += 1
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        super.onNotificationRemoved(sbn)
    }


    /**
     * 서비스가 처음에 생성될때 한번 실행된다.(디바이스가 처음으로 켜질때에도 실행됨)
     * DB오픈하고, timeIndex 초기화
     */
    override fun onCreate() {
        DBOpenHelper.dbOpenHelper = DBOpenHelper(applicationContext).open()
        timeIndex = getTimeNow()
        messageCount = DBOpenHelper.dbOpenHelper.getTodayCount(timeIndex)
        Log.d(TAG, "NotificationListener created!")
        super.onCreate()
    }

    override fun onDestroy() {
        Log.d(TAG, "NotificationListener destroyed")
        super.onDestroy()
    }

    companion object {
        var messageCount = 0
        lateinit var timeIndex: String

        /**
         * 현재시간을 ISO format형태로 반환해줌
         * yyyy-MM-ddTHH:mmZ format
         * @return ISO format의 날짜 문자열
         */
        fun getTimeNow():String{
            val tz = TimeZone.getTimeZone("Asia/Seoul")
            val df = SimpleDateFormat("yyyy-MM-dd")
            df.timeZone = tz

            return df.format(Date())
        }

        /**
         * DB새로고침
         * DB에 최신정보로 업데이트 한뒤, index날짜와 현재날짜가 다르다면 초기화시켜줌
         */
        fun refresh() {
            val now = getTimeNow()
            messageCount = DBOpenHelper.dbOpenHelper.getTodayCount(now)
            timeIndex = now

            DBOpenHelper.dbOpenHelper.updateTodayCount(timeIndex, messageCount.toString())

            if (timeIndex != now) {
                // 날짜라 다르단 소리는 DB에 업데이트를 하고 timeIndex 변경하고, message count 초기화시켜야함
                timeIndex = now
                messageCount = 0
            }
        }

    }







}
