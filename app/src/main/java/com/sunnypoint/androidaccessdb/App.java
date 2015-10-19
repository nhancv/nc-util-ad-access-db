package com.sunnypoint.androidaccessdb;

import android.app.Application;

import com.sunnypoint.accessdblib.DBHelper;
import com.sunnypoint.accessdblib.DBUtils;

/**
 * Created by NhanCao on 19-Oct-15.
 */
public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        DBHelper.setInstance(getApplicationContext());

        DBUtils.backupDB(getApplicationContext(), "main.db", null);
    }
}
