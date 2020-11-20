package com.megain.nfctemp.utils;

import androidx.annotation.NonNull;

import com.megain.label.driver.label.LabelSetting;
import com.megain.label.driver.label.ThValues;

/**
 * Created by Administrator on 2017/10/24.
 */

public class TemRhUtil {

    public static int getDownTempTotalTime(LabelSetting setting) {
        if (setting.getThValues() == null) {
            return 0;
        }
        if (setting.getThValues().getTempValueAmount() == 0) {
            return 0;
        }
        int sum = 0;
        float[] temp = setting.getThValues().getTempValues(ThValues.Type.CENTIGRADE);
        if (temp != null && temp.length > 0) {
            for (float f : temp) {
                if (f < setting.getMinTemp()) {
                    sum++;
                }
            }
        }
        return sum * setting.getSampleInterval();
    }

    public static int getUpTempTotalTime(LabelSetting setting) {
        if (setting.getThValues() == null) {
            return 0;
        }
        if (setting.getThValues().getTempValueAmount() == 0) {
            return 0;
        }
        int sum = 0;
        float[] temp = setting.getThValues().getTempValues(ThValues.Type.CENTIGRADE);
        if (temp != null && temp.length > 0) {
            for (float f : temp) {
                if (f > setting.getMaxTemp()) {
                    sum++;
                }
            }
        }
        return sum * setting.getSampleInterval();
    }

    public static int getDownRhTotalTime(LabelSetting setting) {
        if (setting.getThValues() == null) {
            return 0;
        }
        if (setting.getThValues().getHumidityValueAmount() == 0) {
            return 0;
        }
        int sum = 0;
        int[] temp = setting.getThValues().getHumidityValues();
        if (temp != null && temp.length > 0) {
            for (float f : temp) {
                if (f < setting.getMinHumidity()) {
                    sum++;
                }
            }
        }
        return sum * setting.getSampleInterval();
    }

    public static int getUpRhTotalTime(LabelSetting setting) {
        if (setting.getThValues() == null) {
            return 0;
        }
        if (setting.getThValues().getHumidityValueAmount() == 0) {
            return 0;
        }
        int sum = 0;
        int[] temp = setting.getThValues().getHumidityValues();
        if (temp != null && temp.length > 0) {
            for (float f : temp) {
                if (f > setting.getMaxHumidity()) {
                    sum++;
                }
            }
        }
        return sum * setting.getSampleInterval();
    }

    public static float getMax(float[] arr) {
        if (arr != null) {
            float max = arr[0];
            for (int x = 1; x < arr.length; x++) {
                if (arr[x] > max)
                    max = arr[x];
            }
            return max;
        } else {
            return 0;
        }
    }

    public static float[] getTem(@NonNull LabelSetting thSettingAll) {
        int a = 0;
        if (thSettingAll.getThValues().getTempValueAmount() > 0) {
            a = thSettingAll.getThValues().getTempValueAmount();
        }
        float[] tem = null;
        if (a != 0) {
            Lg.i("a----" + a);
            tem = thSettingAll.getThValues().getTempValues(ThValues.Type.CENTIGRADE);
        }
        return tem;
    }

    public static int[] getRh(@NonNull LabelSetting thSettingAll) {
        int b = 0;
        if (thSettingAll.getThValues().getHumidityValueAmount() > 0) {
            b = thSettingAll.getThValues().getHumidityValueAmount();
        }
        int[] rh = null;
        if (b != 0) {
            Lg.i("a----" + b);
            rh = thSettingAll.getThValues().getHumidityValues();
        }
        return rh;
    }

    public static int getMax(int[] arr) {
        if (arr != null) {
            int max = arr[0];
            for (int x = 1; x < arr.length; x++) {
                if (arr[x] > max)
                    max = arr[x];
            }
            return max;
        } else {
            return 0;
        }
    }

    public static int getMin(int[] arr) {
        if (arr != null) {
            int min = 0;
            for (int x = 1; x < arr.length; x++) {
                if (arr[x] < arr[min])
                    min = x;
            }
            return arr[min];
        } else {
            return 0;
        }
    }

    public static float getMin(float[] arr) {
        if (arr != null) {
            int min = 0;
            for (int x = 1; x < arr.length; x++) {
                if (arr[x] < arr[min])
                    min = x;
            }
            return arr[min];
        } else {
            return 0;
        }
    }
}
