package com.macgongmon.inssa;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
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
    private Integer messageCount;
    private String timeIndex;

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();

        if(packageName.equals(KAKAO_PACKAGE)){
            String now = getTimeNow();

            if(timeIndex.equals(now)){
                // 같은날짜에 온 카톡알림이면 message count 증가시키기
                messageCount += 1;
            }
            else{
                // 날짜라 다르단 소리는 DB에 업데이트를 하고 timeIndex 변경하고, message count 초기화시켜야함
                MainActivity.dbOpenHelper.insertDateCount(timeIndex,messageCount.toString());
                timeIndex = getTimeNow();
                messageCount = 1;
            }
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }

    @Override
    public void onCreate() {
        messageCount = 0;
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
}
