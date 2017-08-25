package com.macgongmon.inssa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyunsikyoo on 23/08/2017.
 */

public class DBOpenHelper {
    public static SQLiteDatabase DB;

    private static final String DB_NAME = "kakao_info";
    private static final int DB_VERSION = 1;

    public static final String CREATE_KAKAO_LIST = "create table kakao_info (_id integer primary key " +
            "autoincrement, date text not null, count text not null);";

    private DatabaseHelper DBHelper;
    private Context context;



    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_KAKAO_LIST);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // version값을 올려주면 onUpgrade가 불림
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
        }
    }


    public DBOpenHelper(Context context) {
        this.context = context;
    }


    public DBOpenHelper open() throws SQLException {
        DBHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
        return this;
    }

    public void close() {
        DB.close();
    }

    public List getAllData(){
        /**
         * DB에 존재하는 날짜별 카톡카운팅 횟수를 모두 가져오기
         */
        DB = DBHelper.getReadableDatabase();

        Cursor cursor = DB.query(DB_NAME,null,null,null,null,null,null);
        List resultList = new ArrayList<>();
        while(cursor.moveToNext()){
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String count = cursor.getString(cursor.getColumnIndex("count"));
            List dataPair = new ArrayList<String>();
            dataPair.add(date);
            dataPair.add(count);
            resultList.add(dataPair);
        }
        cursor.close();
        return resultList;
    }


    public void updateTodayCount(String date, String count){
        // 오늘날짜로 된 데이터가 있는지 확인. 없으면 새로운 row추가 .있으면 기존 count update

        DB = DBHelper.getReadableDatabase();
        Cursor cursor = DB.query(DB_NAME,null,"date=?",new String[]{date},null,null,null);

        DB = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date",date);
        values.put("count",count);

        DB = DBHelper.getWritableDatabase();
        if(cursor.moveToFirst()){ // cursor에 객체가 존재하면 진입.
            DB.update(DB_NAME,values,"date=?",new String[]{date});
        }else{ // 오늘날짜로 된 데이터가 없는경우
            DB.insert(DB_NAME,null,values);
        }
    }


    // 가게이름 받아서 해당 가게를 DB에서 삭제
    public void deleteAll(){
        DB = DBHelper.getWritableDatabase();
        DB.delete("kakao_info",null,null);
    }

    public Integer getTodayCount(String date){
        Integer count = 0;
        DB = DBHelper.getReadableDatabase();
        Cursor cursor = DB.query(DB_NAME,null,"date=?",new String[]{date},null,null,null);

        if(cursor.moveToFirst()){
            count = Integer.parseInt(cursor.getString(cursor.getColumnIndex("count")));
        }
        cursor.close();
        return count;
    }

    public Integer getMyPoint(){
        Integer myPoint = 0;
        DB = DBHelper.getReadableDatabase();

        Cursor cursor = DB.query(DB_NAME,null,null,null,null,null,null);

        while(cursor.moveToNext()){
            Integer count = Integer.parseInt(cursor.getString(cursor.getColumnIndex("count")));
            myPoint +=count;
        }
        myPoint = myPoint/cursor.getCount();
        cursor.close();
        return myPoint;
    }

}

