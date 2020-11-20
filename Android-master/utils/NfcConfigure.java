package com.megain.nfctemp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.NfcA;
import android.provider.Settings;

import com.megain.nfctemp.R;


/**
 * Nfc配置类
 */
public class NfcConfigure {
    private NfcAdapter mNfcAdapter;
    private IntentFilter[] mFilters = null;
    private PendingIntent mPendingIntent = null;
    private String[][] mTechLists = null;
    protected Activity mActivity;

    /**
     * initial Activity NFC function
     *
     * @param activity
     */
    public NfcConfigure(Activity activity) {
        this.mActivity = activity;
        mNfcAdapter = NfcAdapter.getDefaultAdapter(mActivity);
        initNFC();
    }

    /**
     * 初始化NFC，创建一个前台调度的过滤器
     *
     * @author Jack
     */
    protected void initNFC() {
        mPendingIntent = PendingIntent.getActivity(mActivity, 0, new Intent(
                mActivity, mActivity.getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        // FLAG_ACTIVITY_SINGLE_TOP: not creating multiple instances of the same
        // application.
        IntentFilter tagDetected = new IntentFilter(
                NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndefDetected = new IntentFilter(
                NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter techDetected = new IntentFilter(
                NfcAdapter.ACTION_TECH_DISCOVERED);
        // ndef.addDataScheme("http");
        // Intent filters for writing to a tag
        mFilters = new IntentFilter[]{tagDetected, ndefDetected, techDetected};
        mTechLists = new String[][]{new String[]{NfcA.class.getName()},
                new String[]{IsoDep.class.getName()}};
    }

    /**
     * Enable foreground dispatch to the given Activity
     *
     * @author Jack
     */
    public void enableForegroundDispatch() {
        if (mNfcAdapter != null) {
            mNfcAdapter.enableForegroundDispatch(mActivity, mPendingIntent,
                    mFilters, mTechLists);
            Lg.i("-----------enableForegroundDispatch------------");
            Lg.i("Activity Name= " + mActivity.getClass().getSimpleName());
        }
    }

    /**
     * Disable foreground dispatch to the given activity.
     *
     * @author Jack
     */
    public void disableForegroundDispatch() {
        if (mNfcAdapter != null) {
            mNfcAdapter.disableForegroundDispatch(mActivity);
            Lg.i("-----------disableForegroundDispatch------------");
            Lg.i("Activity Name= " + mActivity.getClass().getSimpleName());
        }
    }

    public boolean isSupportNfc() {
        return mNfcAdapter != null;
    }

    public boolean isNfcEnable() {
        return mNfcAdapter != null && mNfcAdapter.isEnabled();
    }

    /**
     * 检查NFC功能，如果手机不支持或没打开NFC功能弹出相应的提示
     *
     * @author Jack
     */
    public void checkNFCFunction() {
        /* 手机不支持NFC功能 **/
        if (mNfcAdapter == null) {
//            AlertDialog dialog = new AlertDialog.LabelSettingBuilder(mActivity)
//                    .setTitle(mActivity.getString(R.string.tips))
//                    .setMessage(mActivity.getString(R.string.nonsupport_nfc))
//                    .create();
//            dialog.setCancelable(true);
//            dialog.setCanceledOnTouchOutside(true);
//            dialog.show();
        } else {
            /* 手机支持NFC功能，但未开启 **/
            if (!mNfcAdapter.isEnabled()) {
                AlertDialog dialog = new AlertDialog.Builder(mActivity)
                        .setTitle(mActivity.getString(R.string.tips))
                        .setMessage(mActivity.getString(R.string.open_nfc))
                        .setPositiveButton(
                                mActivity.getString(R.string.confirm),
                                (dialog1, which) -> {
                                    dialog1.dismiss();
                                    mActivity.startActivity(new Intent(
                                            Settings.ACTION_NFC_SETTINGS));
                                }).create();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        }
    }

}