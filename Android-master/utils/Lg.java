package com.megain.nfctemp.utils;

import com.megain.nfctemp.BuildConfig;
import com.tencent.bugly.crashreport.BuglyLog;

/**
 * Log统一管理类
 *
 * @author way
 */
public class Lg {

    private Lg() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private final static boolean isDebug = BuildConfig.DEBUG;
    private static final String TAG = "NfcTemp";

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        BuglyLog.i(TAG, msg);
    }

    public static void d(String msg) {
        BuglyLog.d(TAG, msg);
    }

    public static void e(String msg) {
        BuglyLog.e(TAG, msg);
    }

    public static void v(String msg) {
        BuglyLog.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        BuglyLog.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        BuglyLog.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        BuglyLog.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        BuglyLog.i(tag, msg);
    }
}