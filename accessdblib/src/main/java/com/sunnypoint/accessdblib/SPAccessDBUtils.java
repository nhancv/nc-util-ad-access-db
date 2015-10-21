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

    public static String getPackageName() {
        return SPAccessConfigs.getAPPLICATIONCONTEXT().getPackageName();
    }

    public static void backupDB() {
        Context applicationContext = SPAccessConfigs.getAPPLICATIONCONTEXT();
        String dbName = SPAccessConfigs.getDBNAME();
        String path = SPAccessConfigs.getPATH();
        try {
            if (applicationContext != null) {
                File sd = Environment.getExternalStorageDirectory();
                File data = Environment.getDataDirectory();

                if (sd.canWrite()) {
                    dbName = (dbName == null) ? "main.db" : dbName;
                    String currentDBPath = (path == null) ? "/data/" + getPackageName() + "/databases/" + dbName : path;
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
                        SPAccessConfigs.loge(TAG, "Backup to sdcard: successfully - " + dbName + " " + currentDB.length() + " bytes");
                    } else {
                        SPAccessConfigs.loge(TAG, "Backup to sdcard: failed - " + dbName + " not exists");
                    }
                }
            }
        } catch (Exception e) {
            SPAccessConfigs.loge(TAG, "Backup to sdcard: " + e.toString());
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

    public static void copytoSDCardandZip() {
        backupDB();
        GZipFile.getInstance().gzipIt(getDBSDCardPath().getPath(),getDBSDCardPath().getPath()+".zip");
    }

    public static void unZipandCopytoData() {
        GZipFile.getInstance().gunzipIt(getDBSDCardPath().getPath()+".zip",getDBSDCardPath().getPath());
        Context applicationContext = SPAccessConfigs.getAPPLICATIONCONTEXT();
        String dbName = SPAccessConfigs.getDBNAME();
        String path = SPAccessConfigs.getPATH();
        try {
            if (applicationContext != null) {
                File sd = Environment.getExternalStorageDirectory();
                File data = Environment.getDataDirectory();

                if (sd.canWrite()) {
                    dbName = (dbName == null) ? "main.db" : dbName;
                    String currentDBPath = (path == null) ? "/data/" + getPackageName() + "/databases/" + dbName : path;
                    String backupDBPath = "main.db";
                    SPAccessConfigs.loge(TAG, "Backup: " + data.getAbsolutePath() + currentDBPath + " to " + sd.getAbsolutePath() + currentDBPath);
                    File currentDB = new File(sd, backupDBPath);
                    File backupDB = new File(data, currentDBPath);

                    if (currentDB.exists()) {
                        FileChannel src = new FileInputStream(currentDB).getChannel();
                        FileChannel dst = new FileOutputStream(backupDB).getChannel();
                        dst.transferFrom(src, 0, src.size());
                        src.close();
                        dst.close();
                        SPAccessConfigs.loge(TAG, "Backup to data: successfully - " + dbName + " " + currentDB.length() + " bytes");
                    } else {
                        SPAccessConfigs.loge(TAG, "Backup to data: failed - " + dbName + " not exists");
                    }
                }
            }
        } catch (Exception e) {
            SPAccessConfigs.loge(TAG, "Backup to data: " + e.toString());
        }
    }

    public static File getDBSDCardPath() {
        File sd = Environment.getExternalStorageDirectory();
        String backupDBPath = "main.db";
        File sdcardPath = new File(sd, backupDBPath);
        return sdcardPath;
    }

    public static File getDBDataPath() {
        String dbName = SPAccessConfigs.getDBNAME();
        String path = SPAccessConfigs.getPATH();
        File data = Environment.getDataDirectory();
        dbName = (dbName == null) ? "main.db" : dbName;
        String currentDBPath = (path == null) ? "/data/" + getPackageName() + "/databases/" + dbName : path;
        File dataPath = new File(data, currentDBPath);
        return dataPath;
    }
}
