package com.twirling.player.client;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;

/**
 * Created by xieqi on 2016/9/13.
 */
public class Client04 {
    private int port;
    private String host;
    byte[] data = new byte[256];
    String[] command = new String[]{"COMMAND_PLAY", "COMMAND_PAUSE", "COMMAND_STOP", "COMMAND_REPLAY", "COMMAND_SEEK_"};

    public Client04(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String listen() {
        String message = "";
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(port);
            DatagramPacket packet = new DatagramPacket(data, data.length);
            socket.receive(packet);
            boolean breakFlag = false;
            while (true) {
                Thread.currentThread().join(500);
                message = new String(packet.getData(), 0, packet.getLength());
                System.out.println(host + " " + port + " " + message);
                for (String str : command) {
                    if (str.equals(message)) {
                        breakFlag = true;
                    }
                }
                if (breakFlag) {
                    break;
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.disconnect();
            socket.close();
            Log.d(Client01.class.getSimpleName(), message);
            return message;
        }
    }

}
