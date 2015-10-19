package com.sunnypoint.accessdblib;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by NhanCao on 19-Oct-15.
 */
public class SPAccessDBUtils {

    private static final String TAG = SPAccessDBUtils.class.getName();

    public static String getPackageName(Context applicationContext) {
        return applicationContext.getPackageName();
    }

    /**
     * Backup db to sdcard
     *
     * @param applicationContext
     * @param dbName
     * @param path               == null ? "/data/" + getPackageName(applicationContext) + "/databases/" + dbName : path
     */
    public static void backupDB(Context applicationContext, String dbName, String path) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = (path == null) ? "/data/" + getPackageName(applicationContext) + "/databases/" + dbName : path;
                String backupDBPath = "main.db";
                SPAccessConfigs.loge(TAG, "Backup: " + data.getAbsolutePath() + currentDBPath + " to " + sd.getAbsolutePath() + currentDBPath);
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    SPAccessConfigs.loge(TAG, "Backup: successfully");
                } else {
                    SPAccessConfigs.loge(TAG, "Backup: failed - currentDB not exists");
                }

            }
        } catch (Exception e) {
            SPAccessConfigs.loge(TAG, "Backup: " + e.toString());
        }
    }

    private static void copyFile(File src, File dst) throws IOException {
        FileInputStream in = new FileInputStream(src);
        FileOutputStream out = new FileOutputStream(dst);
        FileChannel fromChannel = null, toChannel = null;
        try {
            fromChannel = in.getChannel();
            toChannel = out.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } finally {
            if (fromChannel != null)
                fromChannel.close();
            if (toChannel != null)
                toChannel.close();
        }
    }
}
