package com.macgongmon.inssa.model

import io.realm.RealmObject

/**
 * Created by hyunsikyoo on 12/12/2017.
 */
open class Score(): RealmObject() {
    var date: String? = null
    var count: String? = null

    init {
    }

    constructor(date: String, score: String): this() {
        this.date = date
        this.count = score
    }
}