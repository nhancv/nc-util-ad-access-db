package com.sunnypoint.accessdblib;

import android.content.Context;
import android.util.Log;

/**
 * Created by NhanCao on 19-Oct-15.
 */
public class SPAccessConfigs {
    public static final String GETDP_COMMAND = "getDB";
    public static final String GETFILESK_COMMAND = "getFilesk";
    public static final String SETFILESK_COMMAND = "setFilesk";
    //config socket comm
    public static int PORT = 1234;
    private static boolean ENABLE_LOG = true;
    //register option
    private static boolean REGISTER_BACKUP = true;
    private static boolean REGISTER_SOCKET_MOVESDCARD = true;
    private static boolean REGISTER_SOCKET_SENDFILE = false;
    private static boolean REGISTER_SOCKET_RECEIVEFILE = false;


    //setup parameter
    private static Context APPLICATIONCONTEXT;
    private static String DBNAME = "main.db";
    private static String PATH;

    public static void loge(String TAG, String msg) {
        if (isEnableLog()) {
            Log.e(TAG, msg);
        }
    }

    public static void setupInitFirst(Context applicationContext, String dbName, String path) {
        APPLICATIONCONTEXT = applicationContext;
        DBNAME = dbName;
        PATH = path;
    }

    /**
     * When have a request from client:
     * - Move to sdcard
     * - Receive file and update to data
     * - Send file to pc
     */
    public static void enableAllSocketFeature(boolean enable) {
        setRegisterSocketMovesdcard(enable);
        setRegisterSocketReceivefile(enable);
        setRegisterSocketSendfile(enable);

    }

    ///GET SET

    public static boolean isEnableLog() {
        return ENABLE_LOG;
    }

    public static void setEnableLog(boolean enableLog) {
        ENABLE_LOG = enableLog;
    }

    public static boolean isRegisterBackup() {
        return REGISTER_BACKUP;
    }

    public static void setRegisterBackup(boolean registerBackup) {
        REGISTER_BACKUP = registerBackup;
    }

    public static boolean isRegisterSocketMovesdcard() {
        return REGISTER_SOCKET_MOVESDCARD;
    }

    public static void setRegisterSocketMovesdcard(boolean registerSocketMovesdcard) {
        REGISTER_SOCKET_MOVESDCARD = registerSocketMovesdcard;
    }

    public static boolean isRegisterSocketSendfile() {
        return REGISTER_SOCKET_SENDFILE;
    }

    public static void setRegisterSocketSendfile(boolean registerSocketSendfile) {
        REGISTER_SOCKET_SENDFILE = registerSocketSendfile;
    }

    public static boolean isRegisterSocketReceivefile() {
        return REGISTER_SOCKET_RECEIVEFILE;
    }

    public static void setRegisterSocketReceivefile(boolean registerSocketReceivefile) {
        REGISTER_SOCKET_RECEIVEFILE = registerSocketReceivefile;
    }

    public static Context getAPPLICATIONCONTEXT() {
        return APPLICATIONCONTEXT;
    }

    public static String getDBNAME() {
        return DBNAME;
    }

    public static String getPATH() {
        return PATH;
    }


}
