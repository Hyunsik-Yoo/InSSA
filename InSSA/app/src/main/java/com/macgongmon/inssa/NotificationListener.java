package com.macgongmon.inssa;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.IntDef;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by hyunsikyoo on 20/08/2017.
 */

public class NotificationListener extends NotificationListenerService {
    public final String TAG = getClass().getSimpleName();
    public final String KAKAO_PACKAGE = "com.kakao.talk";
    public static Integer messageCount;
    public static String timeIndex;

    /**
     * 알림이 왔을 때 카톡인지 확인한 뒤 카운팅
     * @param sbn
     */
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();

        if(packageName.equals(KAKAO_PACKAGE)){
            refresh();
            messageCount += 1;
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }


    /**
     * 서비스가 처음에 생성될때 한번 실행된다.(디바이스가 처음으로 켜질때에도 실행됨)
     * DB오픈하고, timeIndex 초기화
     */
    @Override
    public void onCreate() {
        MainActivity.dbOpenHelper = new DBOpenHelper(getApplicationContext()).open();
        messageCount = MainActivity.dbOpenHelper.getTodayCount(getTimeNow());
        timeIndex = getTimeNow();
        Log.d(TAG,"NotificationListener created!");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"NotificationListener destroyed");
        super.onDestroy();
    }

    /**
     * 현재시간을 ISO format형태로 반환해줌
     * yyyy-MM-ddTHH:mmZ format
     * @return ISO format의 날짜 문자열
     */
    public static String getTimeNow(){
        TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());

        return nowAsISO;
    }

    /**
     * DB새로고침
     * DB에 최신정보로 업데이트 한뒤, index날짜와 현재날짜가 다르다면 초기화시켜줌
     */
    public static void refresh(){
        String now = getTimeNow();
        MainActivity.dbOpenHelper.updateTodayCount(timeIndex,messageCount.toString());

        if(!timeIndex.equals(now)){
            // 날짜라 다르단 소리는 DB에 업데이트를 하고 timeIndex 변경하고, message count 초기화시켜야함
            timeIndex = getTimeNow();
            messageCount = 0;
        }
    }
}
