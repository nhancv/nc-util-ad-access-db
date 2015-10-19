package com.sunnypoint.accessdblib;

import android.util.Log;

/**
 * Created by NhanCao on 19-Oct-15.
 */
public class SPAccessConfigs {
    private static boolean ENABLE_LOG = true;

    public static boolean isEnableLog() {
        return ENABLE_LOG;
    }

    public static void setEnableLog(boolean enableLog) {
        ENABLE_LOG = enableLog;
    }

    public static void loge(String TAG, String msg) {
        if (isEnableLog()) {
            Log.e(TAG, msg);
        }
    }
}
