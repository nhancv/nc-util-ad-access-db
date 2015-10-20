package com.sunnypoint.accessdblib;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    public static void startDaemon(Integer port) {
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
                DataOutputStream out =
                        new DataOutputStream(server.getOutputStream());
                solveOptions(server, in, out);
                in.close();
                out.close();
                server.close();
            } catch (SocketTimeoutException s) {
                SPAccessConfigs.loge(TAG, "Socket timed out!");
            } catch (IOException e) {
                e.printStackTrace();
                SPAccessConfigs.loge(TAG, e.toString());
            } catch (Exception e) {
                SPAccessConfigs.loge(TAG, e.toString());
            }
        }
    }

    public synchronized void solveOptions(Socket socket, DataInputStream in, DataOutputStream out) throws IOException {
        String input = in.readUTF();
        SPAccessConfigs.loge(TAG, input);
        try {
            if (SPAccessConfigs.isRegisterBackup()) {
                if (SPAccessConfigs.isRegisterSocketMovesdcard() && input.equals(SPAccessConfigs.GETDP_COMMAND)) {
                    SPAccessDBUtils.backupDB();
                    out.writeUTF("Ok - " + socket.getLocalSocketAddress());

                } else if (SPAccessConfigs.isRegisterSocketSendfile() && input.equals(SPAccessConfigs.GETFILESK_COMMAND)) {
                    sendFile(socket, out);

                } else if (SPAccessConfigs.isRegisterSocketReceivefile() && input.contains(SPAccessConfigs.SETFILESK_COMMAND)) {
                    int fileSize = in.readInt();
                    receiveFile(socket, fileSize);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            SPAccessConfigs.loge(TAG, e.toString());
        }
    }

    private void receiveFile(Socket client, int fileSize) throws IOException {
        InputStream is = client.getInputStream();
        int bytesRead;
        int byteCounts = 0;
        OutputStream output = new FileOutputStream(SPAccessDBUtils.getDBSDCardPath());
        int sizeBuffer = 1024;
        byte[] buffer = new byte[sizeBuffer];
        while ((bytesRead = is.read(buffer, 0, Math.max(sizeBuffer, Math.min(sizeBuffer, fileSize - byteCounts)))) != -1) {
            output.write(buffer, 0, bytesRead);
            byteCounts += bytesRead;
            if (byteCounts >= fileSize) {
                break;
            }
        }
        output.close();
        SPAccessDBUtils.copytoData();
    }

    private void sendFile(Socket client, DataOutputStream out) throws IOException {
//        SPAccessDBUtils.copytoSDCard();
//        File myFile = SPAccessDBUtils.getDBSDCardPath();
        File myFile = SPAccessDBUtils.getDBDataPath();
        out.writeInt((int) myFile.length());
        byte[] mybytearray = new byte[(int) myFile.length()];
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
        bis.read(mybytearray, 0, mybytearray.length);
        OutputStream os = client.getOutputStream();
        os.write(mybytearray, 0, mybytearray.length);
        os.flush();
        SPAccessConfigs.loge(TAG, "send file socket " + myFile.length() + " bytes");
    }
}
