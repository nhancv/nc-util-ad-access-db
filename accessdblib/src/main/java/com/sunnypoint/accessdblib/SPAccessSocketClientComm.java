package com.sunnypoint.accessdblib;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by NhanCao on 19-Oct-15.
 */
public class SPAccessSocketClientComm extends Thread {
    private ServerSocket serverSocket;

    public SPAccessSocketClientComm(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }

    public void run() {
        String serverName = "192.168.1.18";
        int port = Integer.parseInt("123456");
        try {
            System.out.println("Connecting to " + serverName +
                    " on port " + port);
            Socket client = new Socket(serverName, port);
            System.out.println("Just connected to "
                    + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF("Hello from "
                    + client.getLocalSocketAddress());
            InputStream inFromServer = client.getInputStream();
            DataInputStream in =
                    new DataInputStream(inFromServer);
            System.out.println("Server says " + in.readUTF());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
