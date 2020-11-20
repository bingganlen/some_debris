package com.megain.nfctemp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.nfc.NfcAdapter;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import com.megain.nfctemp.main.CurApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * 系统版本信息类
 *
 * @author tangjun
 */
public class DeviceUtils {

    /**
     * >=2.2
     */
    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    /**
     * >=2.3
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * >=3.0 LEVEL:11
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * >=3.1
     */
    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * >=4.0 14
     */
    public static boolean hasICS() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * >= 4.1 16
     *
     * @return
     */
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * >= 4.2 17
     */
    public static boolean hasJellyBeanMr1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * >= 4.3 18
     */
    public static boolean hasJellyBeanMr2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    /**
     * >=4.4 19
     */
    public static boolean hasKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * >5.1
     */
    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    public static int getSDKVersionInt() {
        return Build.VERSION.SDK_INT;
    }

    @SuppressWarnings("deprecation")
    public static String getSDKVersion() {
        return Build.VERSION.SDK;
    }

    /**
     * 获得设备的固件版本号
     */
    public static String getReleaseVersion() {
        return StringUtils.makeSafe(Build.VERSION.RELEASE);
    }

    /**
     * 检测是否是中兴机器
     */
    public static boolean isZte() {
        return getDeviceModel().toLowerCase().indexOf("zte") != -1;
    }

    /**
     * 判断是否是三星的手机
     */
    public static boolean isSamsung() {
        return getManufacturer().toLowerCase().indexOf("samsung") != -1;
    }

    /**
     * 检测是否HTC手机
     */
    public static boolean isHTC() {
        return getManufacturer().toLowerCase().indexOf("htc") != -1;
    }

    /**
     * 检测当前设备是否是特定的设备
     *
     * @param devices
     * @return
     */
    public static boolean isDevice(String... devices) {
        String model = DeviceUtils.getDeviceModel();
        if (devices != null && model != null) {
            for (String device : devices) {
                if (model.indexOf(device) != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获得设备型号
     *
     * @return
     */
    public static String getDeviceModel() {
        return StringUtils.trim(Build.MODEL);
    }

    /**
     * 获取厂商信息
     */
    public static String getManufacturer() {
        return StringUtils.trim(Build.MANUFACTURER);
    }

    /**
     * 判断是否是平板电脑
     *
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 检测是否是平板电脑
     *
     * @param context
     * @return
     */
    public static boolean isHoneycombTablet(Context context) {
        return hasHoneycomb() && isTablet(context);
    }

    public static int dipToPX(final Context ctx, float dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, ctx.getResources().getDisplayMetrics());
    }

    /**
     * 获取CPU的信息
     *
     * @return
     */
    public static String getCpuInfo() {
        String cpuInfo = "";
        try {
            if (new File("/proc/cpuinfo").exists()) {
                FileReader fr = new FileReader("/proc/cpuinfo");
                BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
                cpuInfo = localBufferedReader.readLine();
                localBufferedReader.close();

                if (cpuInfo != null) {
                    cpuInfo = cpuInfo.split(":")[1].trim().split(" ")[0];
                }
            }
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return cpuInfo;
    }

    /**
     * 判断是否支持闪光灯
     */
    public static boolean isSupportCameraLedFlash(PackageManager pm) {
        if (pm != null) {
            FeatureInfo[] features = pm.getSystemAvailableFeatures();
            if (features != null) {
                for (FeatureInfo f : features) {
                    if (f != null && PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) //判断设备是否支持闪光灯
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * 检测设备是否支持相机
     */
    public static boolean isSupportCameraHardware(Context context) {
        // this device has a camera
// no camera on this device
        return context != null && context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * 获取屏幕宽度
     */
    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        return display.getWidth();
    }

    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        return display.getHeight();
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 判断手机是否支持蓝牙
     *
     * @param context
     * @return
     */
    public static boolean isSupportBle(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return false;
        }
        PackageManager pm = context.getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);

    }

    /**
     * 判断手机是否支持NFC
     *
     * @param context
     * @return
     */
    public static boolean isSupportNfc(Context context) {
        if (context == null) {
            return false;
        }
        NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(context);
        /* 手机不支持NFC功能 **/
        return mNfcAdapter != null;
    }

    /**
     * 判断手机是否支持WiFi
     *
     * @param context
     * @return
     */
    public static boolean isSupportWiFi(Context context) {
        if (context == null) {
            return false;
        }
        PackageManager pm = context.getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_WIFI);
    }

    /**
     * 判断{@link Activity}是否有权限
     *
     * @param activity，申请权限是针对{@link Activity}的
     * @return true, 如果已经成功权限，否则返回false
     */
    public static boolean requestPermission(Activity activity, int permissionType) {
        if (!hasLollipop()) {
            return true;//如果手机系统小于6.0
        }
        if (permissionType == CurApp.PERMISSION_WRITE_READ) {
            if (ContextCompat.checkSelfPermission(activity, READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE}, CurApp.PERMISSION_WRITE_READ);
                return false;
            }
        } else if (permissionType == CurApp.PERMISSION_PHOTO) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
                        CurApp.PERMISSION_CAMERA);
                return false;
            }
        } else if (permissionType == CurApp.PERMISSION_CAMERA) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.RECORD_AUDIO},
                        CurApp.PERMISSION_CAMERA);
                return false;
            }
        } else if (permissionType == CurApp.PERMISSION_RECORD) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO},
                        CurApp.PERMISSION_RECORD);
                return false;
            }
        } else if (permissionType == CurApp.PERMISSION_BLUETOOTH) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH,
                                Manifest.permission.BLUETOOTH_ADMIN, ACCESS_COARSE_LOCATION},
                        CurApp.PERMISSION_BLUETOOTH);
                return false;
            }
        }
        return true;
    }

    private static final String[] LOCATE_PERMISSION = {
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION,
            WRITE_EXTERNAL_STORAGE,
            READ_EXTERNAL_STORAGE,
            READ_PHONE_STATE
    };

    /**
     * @param activity
     * @return true, 如果已经成功权限，否则返回false
     */
    public static boolean requestLocatePermission(Activity activity) {
        boolean isACCESS_COARSE_LOCATION = ContextCompat.checkSelfPermission(activity, ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED;
        boolean isACCESS_FINE_LOCATION = ContextCompat.checkSelfPermission(activity, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED;
        boolean isWRITE_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED;
        boolean isREAD_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(activity, READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED;
        boolean isREAD_PHONE_STATE = ContextCompat.checkSelfPermission(activity, READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED;
        if (isACCESS_COARSE_LOCATION || isACCESS_FINE_LOCATION || isWRITE_EXTERNAL_STORAGE || isREAD_EXTERNAL_STORAGE
                || isREAD_PHONE_STATE) {
            ActivityCompat.requestPermissions(activity, LOCATE_PERMISSION,
                    CurApp.PERMISSION_LOCATE);
            return false;
        }
        return true;
    }

    /**
     * 如果手机的NFC权限没有打开，需要手动申请
     * <p>
     * 这个主要是在小米5s这个手机，其他手机没有发现这个情况。小米手机5s在第一次启动的时候有10s的选择时间，如果一次拒绝使用NFC就一直不能用NFC
     * </p>
     *
     * @param activity
     */
    public static void requestNfcPermission(Activity activity) {
        System.out.println(ActivityCompat.checkSelfPermission(activity, Manifest.permission.NFC) != PackageManager.PERMISSION_GRANTED);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.NFC) != PackageManager.PERMISSION_GRANTED) {
//            System.out.println("request NFC permission");
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.NFC,
                    Manifest.permission.NFC}, CurApp.PERMISSION_NFC);
        }
    }

    public static void requestStorageReadWrite(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{READ_EXTERNAL_STORAGE,
                            WRITE_EXTERNAL_STORAGE},
                    CurApp.PERMISSION_WRITE_READ);

        }
    }

    public static boolean isZh(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        return language.endsWith("zh");
    }

    /**
     * 测试当前录音能否被使用
     *
     * @return
     */
    public static boolean hasAudioPermision() {
        int bufferRead;
        int bufferSize = AudioRecord.getMinBufferSize(16000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        short[] tempBuffer = new short[bufferSize];

        AudioRecord recordInstance = new AudioRecord(MediaRecorder.AudioSource.MIC, 16000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT,
                bufferSize);
        try {
            recordInstance.startRecording();
        } catch (Exception e) {
            recordInstance.stop();
            recordInstance.release();
            e.printStackTrace();
            return false;
        }

        bufferRead = recordInstance.read(tempBuffer, 0, bufferSize);
        Lg.i("-------read size--->" + bufferRead + "---recordInstance.getState()--->" + recordInstance.getState());
        if (bufferRead < 0) {
            //录音出现异常
            recordInstance.stop();
            recordInstance.release();
            return false;
        }
        if ("Meizu".equals(getManufacturer())) {
            boolean allZero = true;
            for (int i = 0; i < tempBuffer.length; i++) {
                Lg.d(tempBuffer[i] + "");
                if (tempBuffer[i] != 0) {
                    allZero = false;
                    break;
                }
            }
            if (allZero) {
                //录音出现异常
                recordInstance.stop();
                recordInstance.release();
                return false;
            }
        }
        recordInstance.stop();
        recordInstance.release();
        return true;
    }

    /**
     * 测试当前摄像头能否被使用
     *
     * @return
     */
    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open(0);
            mCamera.setDisplayOrientation(90);
        } catch (Exception e) {
            canUse = false;
        }
        if (canUse) {
            mCamera.release();
        }
        return canUse;
    }

}
