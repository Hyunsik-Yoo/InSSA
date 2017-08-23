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

    private static final String DB_NAME = "kakao_info.db";
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
        String tableName = "kakao_info";

        Cursor cursor = DB.query(tableName,null,null,null,null,null,null);
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

    public void insertDateCount(String date, String count){
        /**
         * 자정이 지난경우 전날 날짜에 대한 카카오톡 개수를 파악하여 DB에 저장
         */
        DB = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date",date);
        values.put("count",count);

        DB.insert("kakao_info",null,values);
    }


    // 가게이름 받아서 해당 가게를 DB에서 삭제
    public void deleteAll(){
        DB = DBHelper.getWritableDatabase();
        DB.delete("kakao_info",null,null);
    }

}

