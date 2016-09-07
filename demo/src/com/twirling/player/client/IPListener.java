package com.twirling.player.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;

/**
 * Created by xieqi on 2016/9/5.
 */
public class IPListener {

    private int port;
    private String host;
    byte[] data = new byte[256];

    public IPListener(String host, int port) {
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
            System.out.println(message);
            socket.disconnect();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return getIP(message);
        }
    }

    public String getIP(String message) {
        String[] strs = message.split("_");
        return strs[strs.length - 1];
    }

    public static void main(String[] args) {
        int port = 10002;
        String host = "192.168.1.118";
        IPListener ml = new IPListener(host, port);
        while (true) {
            ml.listen();
        }
    }
}
