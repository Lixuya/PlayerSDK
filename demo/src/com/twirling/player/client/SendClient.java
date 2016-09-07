package com.twirling.player.client;

import android.content.Context;

import com.twirling.player.util.NetUtil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by xieqi on 2016/9/6.
 */
public class SendClient {
    public void setIp(String ip) {
        this.ip = ip;
    }

    private String ip = "";
    private int port = 10002;

    public SendClient() {

    }

    public void sendMessage(Context context) {
        try {
            Socket socket = new Socket(ip, 10002);
            String mac = NetUtil.getMacAddress(context);
            mac = mac.replace(":", "-");
            System.out.println("mac " + mac);
            //构建IO
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            //
            DataOutputStream writer = new DataOutputStream(os);
            writer.writeChars(mac); // 写一个UTF-8的信息
            //
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
//            //向服务器端发送一条消息
//            bw.write(mac);
//            bw.flush();
            //读取服务器返回的消息
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String mess = br.readLine();
            System.out.println("mess：" + mess);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
