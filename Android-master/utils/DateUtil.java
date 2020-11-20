package com.megain.nfctemp.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期类工具
 * Created by Simon on 2016/5/6.
 */
public class DateUtil {
    public static final String FORMAT_STR = "yyyy-MM-dd HH:mm";

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     */
    @Nullable
    public static Date parse(String strDate, String pattern) {
        if (TextUtils.isEmpty(strDate)) {
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.getDefault());
            return df.parse(strDate);
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    public static String getDateString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * @param time     字符串时间,注意:格式要与template定义的一样
     * @param template 要格式化的格式:如time为09:21:12那么template为"HH:mm:ss"
     * @return
     */
    public static long formatToLong(String time, String template) {
        SimpleDateFormat sdf = new SimpleDateFormat(template, Locale.getDefault());
        try {
            Date d = sdf.parse(time);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            long l = c.getTimeInMillis();
            return l;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        int curYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH) + 1;
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);
        int curHour = calendar.get(Calendar.HOUR_OF_DAY);
        int curMoment = calendar.get(Calendar.MINUTE);
        String s = String.valueOf(curMoment);
        if (s.length() == 1) {
            return curYear + "-" + curMonth + "-" + curDay + "  " + curHour + ":0" + curMoment;
        } else {
            return curYear + "-" + curMonth + "-" + curDay + "  " + curHour + ":" + curMoment;
        }
    }

    public static String formatTime(@NonNull Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int curYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH) + 1;
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);
        int curHour = calendar.get(Calendar.HOUR_OF_DAY);
        int curMoment = calendar.get(Calendar.MINUTE);
        String s = String.valueOf(curMoment);
        if (s.length() == 1) {
            return curYear + "-" + curMonth + "-" + curDay + "  " + curHour + ":0" + curMoment;
        } else {
            return curYear + "-" + curMonth + "-" + curDay + "  " + curHour + ":" + curMoment;
        }
    }
}

