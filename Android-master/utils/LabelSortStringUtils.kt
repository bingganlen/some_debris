package com.megain.nfctemp.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object LabelSortStringUtils {
    /**
     * 最多15个有效的hex，如果超过15个去前15个转换成long
     */
    fun resolveHex2Long(src: String): Long {
        val str = src.trim()
        var rawHex = ""
        for (i in 0 until str.length) {
            val isNum = str[i].toInt() in 48..57
            val isAUntilF = str[i].toInt() in 65..70//大写A-F
            val isaUntilf = str[i].toInt() in 97..102//小写a-f
            if (isNum || isAUntilF || isaUntilf) {
                rawHex += str[i]
            }
        }
        val hex = if (rawHex.length > 15) {
            rawHex.subSequence(0, 15).toString()
        } else {
            rawHex
        }
        return java.lang.Long.parseLong(hex, 16)
    }

    /**
     * 2019-12-19 12:12
     * 将特定格式的时间转换成long，不适用SimpleDateFormat之类的解析是因为性能太差，
     * 而且在按时间排序上不需要转换成那样的格式
     *
     * fixme 解析数据有错，不能使用
     */
    fun resolveSpecifyTime2Long(src: String): Long {
        val str = src.trim()
        val year = getTimeSubString(str, 0, 3)
        val month = getTimeSubString(str, 5, 6)
        val day = getTimeSubString(str, 8, 9)
        val hour = getTimeSubString(str, 10, 11)
        val minute = getTimeSubString(str, 12, 13)
        val hex = year + month + day + hour + minute
        println("时间解析结果=$hex")
        return java.lang.Long.parseLong(hex, 10)
    }

    fun resolveSpecifyTime2Long2(src: String): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return try {
            val date = dateFormat.parse(src)
            date.time
        } catch (e: Exception) {
            0
        }
    }

    private fun replaceBlank(str: String?): String {
        var dest = ""
        if (str != null) {
            val p = Pattern.compile("\\s*|\t|\r|\n|")
            val m = p.matcher(str)
            dest = m.replaceAll("")
        }
        return dest
    }

    private fun getTimeSubString(src: String, start: Int, end: Int): String {
        var dst = ""
        for (j in start..end) {
            if (src[j].toInt() in 48..57) {
                dst += src[j]
            }
        }
        if (dst.length < 2) {
            dst = getFixTime(Integer.decode(dst))
        }
        return dst
    }

    private val TIME_NUM = arrayOf("00", "01", "02", "03", "04", "05", "06", "07", "08", "09")

    private fun getFixTime(timeNum: Int): String {
        return if (timeNum in 0..9)
            TIME_NUM[timeNum]
        else
            timeNum.toString()
    }
}