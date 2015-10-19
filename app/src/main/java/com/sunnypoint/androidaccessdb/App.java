package com.sunnypoint.androidaccessdb;

import android.app.Application;

import com.sunnypoint.accessdblib.SPAccessConfigs;
import com.sunnypoint.accessdblib.SPAccessDBHelper;
import com.sunnypoint.accessdblib.SPAccessSocketClientComm;

/**
 * Created by NhanCao on 19-Oct-15.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SPAccessDBHelper.setInstance(getApplicationContext());

        SPAccessConfigs.setupInitFirst(getApplicationContext(), "main.db", null);
        SPAccessConfigs.setEnableLog(true);

        //option 1: move db file to sdcard
//        SPAccessDBUtils.backupDB();

        //option 2: register get db via socket
        SPAccessConfigs.setRegisterBackup(true);
        SPAccessSocketClientComm.startDiamond(1234);


    }
}
