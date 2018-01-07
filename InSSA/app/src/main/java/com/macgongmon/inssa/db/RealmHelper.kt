package com.macgongmon.inssa.db

import com.macgongmon.inssa.model.Score
import io.realm.Realm
import io.realm.RealmResults


/**
 * Created by hyunsikyoo on 23/08/2017.
 */

class RealmHelper{
    private val realm = Realm.getDefaultInstance()

    /**
     * DB에 존재하는 날짜별 카톡카운팅 횟수를 모두 가져오기
     */
    fun getAllData(): List<Score>{
        return realm.where(Score::class.java).findAll().toList()
    }

    /**
     * 나의 토탈지수 파악
     * 전체 저장된 지수들의 평균을 구하여 반환
     */
    fun myPoint(): Int{
        var myPoint = 0
        val result = getAllData()

        for (item: Score in result){
            val count = Integer.parseInt(item.count)
            myPoint += count
        }

        if (result.isNotEmpty())
            myPoint /= result.size
        return myPoint
    }

    fun updateTodayCount(score: Score) {
        // 오늘날짜로 된 데이터가 있는지 확인. 없으면 새로운 row추가 .있으면 기존 count update
        realm.beginTransaction()
        val result = realm.where(Score::class.java)
                .equalTo("date", score.date)
                .findFirst()

        if(result == null){
            realm.insert(score)
        }else{
            result.count = score.count
        }
        realm.commitTransaction()
    }

    // 가게이름 받아서 해당 가게를 DB에서 삭제
    fun deleteAll() {
        realm.beginTransaction()
        realm.delete(Score::class.java)
        realm.commitTransaction()
    }


    /**
     * 오늘날짜의 count가져오기
     * @param date
     * @return
     */
    fun getTodayCount(date: String): Int {
        var count = 0

        val result = realm.where(Score::class.java)
                .equalTo("date",date)
                .findFirst()

        result?.let { score->
            count = Integer.parseInt(score.count)
        }

        return count
    }

}

