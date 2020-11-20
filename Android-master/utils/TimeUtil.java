package com.megain.nfctemp.utils;

import java.util.TimeZone;

public class TimeUtil {

    public static String getCurrentTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        String strTz = tz.getDisplayName(false, TimeZone.SHORT);
        return strTz;
    }

}