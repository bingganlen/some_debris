package com.megain.nfctemp.utils

import java.util.*

object TimeStringUtil {

    fun getDisplayTime(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val curYear = calendar.get(Calendar.YEAR)
        val curMonth = calendar.get(Calendar.MONTH) + 1
        val curDay = calendar.get(Calendar.DAY_OF_MONTH)
        val curHour = calendar.get(Calendar.HOUR_OF_DAY)
        val curMoment = calendar.get(Calendar.MINUTE)
        //val s = curMoment.toString()
        return (curYear.toString() + "-" + fixNum(curMonth) + "-" + fixNum(curDay)
                + "  " + fixNum(curHour) + ":" + fixNum(curMoment))
    }

    @JvmStatic fun getDisplayTimeforJava(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val curYear = calendar.get(Calendar.YEAR)
        val curMonth = calendar.get(Calendar.MONTH) + 1
        val curDay = calendar.get(Calendar.DAY_OF_MONTH)
        val curHour = calendar.get(Calendar.HOUR_OF_DAY)
        val curMoment = calendar.get(Calendar.MINUTE)
        //val s = curMoment.toString()
        return (curYear.toString() + "-" + fixNum(curMonth) + "-" + fixNum(curDay)
                + "  " + fixNum(curHour) + ":" + fixNum(curMoment))
    }

    private val FIX_NUM = arrayListOf("00", "01", "02", "03", "04", "05", "06", "07", "08", "09")

    private fun fixNum(num: Int): String {
        if (num in 0..9) {
            return FIX_NUM[num]
        }
        return num.toString()
    }

    /**
     * 将不是完整的yyyy-MM-dd HH:mm修补完整
     */
    fun fix(dst: String): String {
        if (dst.length < 17) {
            val date = DateUtil.parse(dst, DateUtil.FORMAT_STR)
            return date?.let { TimeStringUtil.getDisplayTime(it) } ?: dst
        }
        return dst//如果已经是完整的，不需要格式化修复
    }
}