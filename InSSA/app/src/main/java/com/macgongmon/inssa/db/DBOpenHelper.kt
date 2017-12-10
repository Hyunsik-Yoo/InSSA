package com.macgongmon.inssa.db

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

/**
 * Created by hyunsikyoo on 23/08/2017.
 */

class DBOpenHelper(private val context: Context) {
    companion object {
        lateinit var dbOpenHelper:DBOpenHelper
    }

    private val DB_NAME = "kakao_info"
    private val DB_VERSION = 1
    private val CREATE_KAKAO_LIST = "create table kakao_info (_id integer primary key " + "autoincrement, date text not null, count text not null);"

    private lateinit var DB: SQLiteDatabase
    private lateinit var DBHelper: DatabaseHelper

    /**
     * DB에 존재하는 날짜별 카톡카운팅 횟수를 모두 가져오기
     */
    fun getAllData(): List<*>{
        DB = DBHelper.readableDatabase

        val cursor = DB.query(DB_NAME, null, null, null, null, null, null)
        val resultList = ArrayList<ArrayList<String>>()

        while (cursor.moveToNext()){
            val date = cursor.getString(cursor.getColumnIndex("date"))
            val count = cursor.getString(cursor.getColumnIndex("count"))
            val dataPair = arrayListOf(date, count)

            resultList.add(dataPair)
        }
        cursor.close()
        return resultList
    }


    /**
     * 나의 토탈지수 파악
     * 전체 저장된 지수들의 평균을 구하여 반환
     */
    fun myPoint(): Int{
        DB = DBHelper.readableDatabase

        var myPoint = 0
        val cursor = DB.query(DB_NAME, null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            val count = Integer.parseInt(cursor.getString(cursor.getColumnIndex("count")))
            myPoint += count
        }
        if (cursor.count != 0)
            myPoint = myPoint / cursor.count
        cursor.close()
        return myPoint
    }


    private inner class DatabaseHelper(context: Context, name: String,
                                       factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(CREATE_KAKAO_LIST)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            // version값을 올려주면 onUpgrade가 불림
        }

        override fun onOpen(db: SQLiteDatabase) {
            super.onOpen(db)
        }
    }


    //@Throws(SQLException::class)
    fun open(): DBOpenHelper {
        DBHelper = DatabaseHelper(context, DB_NAME, null, DB_VERSION)
        return this
    }

    fun close() {
        DB.close()
    }


    fun updateTodayCount(date: String, count: String) {
        // 오늘날짜로 된 데이터가 있는지 확인. 없으면 새로운 row추가 .있으면 기존 count update

        DB = DBHelper.readableDatabase
        val cursor = DB.query(DB_NAME, null, "date=?", arrayOf(date), null, null, null)

        DB = DBHelper.writableDatabase
        val values = ContentValues()
        values.put("date", date)
        values.put("count", count)

        if (cursor.moveToFirst()) { // cursor에 객체가 존재하면 진입.
            DB.update(DB_NAME, values, "date=?", arrayOf(date))
        } else { // 오늘날짜로 된 데이터가 없는경우
            DB.insert(DB_NAME, null, values)
        }
    }

    // 가게이름 받아서 해당 가게를 DB에서 삭제
    fun deleteAll() {
        DB = DBHelper.writableDatabase
        DB.delete("kakao_info", null, null)
    }


    /**
     * 오늘날짜의 count가져오기
     * @param date
     * @return
     */
    fun getTodayCount(date: String): Int {
        var count = 0
        DB = DBHelper.readableDatabase
        val cursor = DB.query(DB_NAME, null, "date=?", arrayOf(date), null, null, null)

        // 오늘날짜로 된 데이터가 존재하면 if문으로 들어가고 없으면 안들어가짐
        if (cursor.moveToFirst()) {
            count = Integer.parseInt(cursor.getString(cursor.getColumnIndex("count")))
        }
        cursor.close()
        return count
    }

}

