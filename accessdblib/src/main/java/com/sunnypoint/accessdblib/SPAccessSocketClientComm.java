package com.sunnypoint.accessdblib;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by NhanCao on 19-Oct-15.
 */
public class SPAccessSocketClientComm extends Thread {
    private static final String TAG = SPAccessSocketClientComm.class.getName();
    private static SPAccessSocketClientComm instance;
    private ServerSocket serverSocket;

    public SPAccessSocketClientComm(int port) throws IOException {
        serverSocket = new ServerSocket(port);
//        serverSocket.setSoTimeout(10000);
    }

    private static SPAccessSocketClientComm getInstance() throws IOException {
        if (instance == null) {
            instance = new SPAccessSocketClientComm(SPAccessConfigs.PORT);
        }
        return instance;
    }

    public static void startDiamond(Integer port) {
        if (port != null)
            SPAccessConfigs.PORT = port;
        try {
            getInstance().start();
        } catch (IOException e) {
            e.printStackTrace();
            SPAccessConfigs.loge(TAG, e.toString());
        }
    }

    public void run() {
        while (true) {
            try {
                SPAccessConfigs.loge(TAG, "Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                SPAccessConfigs.loge(TAG, "Server is: " + server.getLocalSocketAddress() + " just connected to "
                        + server.getRemoteSocketAddress());
                DataInputStream in =
                        new DataInputStream(server.getInputStream());
                solveOptions(in.readUTF());
                DataOutputStream out =
                        new DataOutputStream(server.getOutputStream());
                out.writeUTF("Ok - " + server.getLocalSocketAddress());
                server.close();
            } catch (SocketTimeoutException s) {
                SPAccessConfigs.loge(TAG, "Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void solveOptions(String input) {
        SPAccessConfigs.loge(TAG, input);
        if (input.equals(SPAccessConfigs.GETDP_COMMAND) && SPAccessConfigs.isRegisterBackup())
            SPAccessDBUtils.backupDB();
    }

}
