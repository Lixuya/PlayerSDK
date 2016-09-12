package com.twirling.player.client;

import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;

/**
 * Created by xieqi on 2016/9/5.
 */
public class Client01 {

    private int port;
    private String host;
    byte[] data = new byte[256];

    public Client01(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String listen() {
        String message = "";
        try {
            DatagramSocket socket = new DatagramSocket(port);
//            InetSocketAddress isa = new InetSocketAddress(host, port);
//            socket.connect(isa);
            DatagramPacket packet = new DatagramPacket(data, data.length);
            //receive()是阻塞方法，会等待客户端发送过来的信息
            socket.receive(packet);
            message = new String(packet.getData(), 0, packet.getLength());
            System.out.println(host + " " + port + " " + message);
            socket.disconnect();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Log.d(Client01.class.getSimpleName(), getIP(message));
            return getIP(message);
        }
    }

    public String getIP(String message) {
        String[] strs = message.split("_");
        return strs[strs.length - 1];
    }
}
