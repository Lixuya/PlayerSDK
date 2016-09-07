package com.twirling.player.client;

import android.content.Context;

import com.twirling.player.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by xieqi on 2016/9/5.
 */
public class InfoListener {
    private Context context;
    private Socket socket;
    protected BufferedReader mIn = null;
    //
    private String ip = "";
    private int port = 10002;
    private int timeout = Constants.DEFAULT_TIMEOUT;

    //
    public InfoListener(Context context) {
        this.context = context;
    }

    public Socket listen() {
        socket = new Socket();
        SocketAddress address = new InetSocketAddress(ip, port);
        try {
            socket.connect(address, timeout);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }


    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected() ;
    }
}
