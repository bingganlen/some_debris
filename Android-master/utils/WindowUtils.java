package com.megain.nfctemp.utils;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by apex014417 on 2018/1/18.
 */

public class WindowUtils {
    public static void keepScreenOn(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public static void clsKeepScreenOn(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
