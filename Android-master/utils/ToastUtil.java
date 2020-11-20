package com.megain.nfctemp.utils;

import androidx.annotation.NonNull;

import android.widget.Toast;

import com.megain.nfctemp.main.CurApp;

/**
 * Created by apex103036 on 2017/7/7.
 */

public class ToastUtil {
//    private static Toast toast;  //用于判断是否已有Toast执行

//    public static void init(Context context) {
//        toast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_SHORT);
////        toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
//    }

    /**
     * 华为不能复用toast
     * @param text
     */
    public static void toastShow(@NonNull String text) {
        Toast.makeText(CurApp.getAppContext(), text, Toast.LENGTH_SHORT).show();
//        if (toast != null) {
//            toast.setText(text);  //用于覆盖前面未消失的提示信息
//            toast.show();
//        } else {
//            init(CurApp.getAppContext());
//            toast.setText(text);  //用于覆盖前面未消失的提示信息
//            toast.show();
//        }
    }

    public static void toastShowLong(@NonNull String text) {
        Toast.makeText(CurApp.getAppContext(), text, Toast.LENGTH_LONG).show();

    }

    public static void toastShow(int resourceId) {
        Toast.makeText(CurApp.getAppContext(), resourceId, Toast.LENGTH_SHORT).show();
//        if (toast != null) {
//            toast.setText(resourceId);  //用于覆盖前面未消失的提示信息
//            toast.show();
//        } else {
//            init(CurApp.getAppContext());
//            toast.setText(resourceId);  //用于覆盖前面未消失的提示信息
//            toast.show();
//        }
    }
}
