package com.macgongmon.inssa.util

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Created by hyunsikyoo on 10/12/2017.
 */

fun getStringFromInputStream(`is`: InputStream): String {

    lateinit var br: BufferedReader
    lateinit var line: String
    val sb = StringBuilder()

    try {
        br = BufferedReader(InputStreamReader(`is`))
        line = br.readLine()
        while (line != null) {
            sb.append(line)
            line = br.readLine()
        }

    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        if (br != null) {
            try {
                br.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    return sb.toString()

}