package com.sunnypoint.androidaccessdb;

import android.app.Application;

import com.sunnypoint.accessdblib.SPAccessConfigs;
import com.sunnypoint.accessdblib.SPAccessDBHelper;
import com.sunnypoint.accessdblib.SPAccessDBUtils;

/**
 * Created by NhanCao on 19-Oct-15.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SPAccessDBHelper.setInstance(getApplicationContext());

        SPAccessConfigs.setEnableLog(true);
        SPAccessDBUtils.backupDB(getApplicationContext(), "main.db", null);
    }
}
