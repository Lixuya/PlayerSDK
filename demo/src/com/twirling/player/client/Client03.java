package com.twirling.player.client;

import android.content.Context;
import android.util.Log;

import com.twirling.player.util.NetUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by xieqi on 2016/9/6.
 */
public class Client03 {
    private String ip = "";
    private int port = 10003;
    int index = 0;
    String heart = "HEARTBEAT";
    String charge = "VR_CHARGE_";
    String batter = "VR_BATT_";
    String time = "VR_TIME_";

    public String sendMessage(Context context) {
        String inputMsg = "";
        Socket socket = null;
        try {
            socket = new Socket(ip, 10003);
            //构建IO
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            //向服务器端发送一条消息
            Writer writer = new OutputStreamWriter(os);
            int i = 1;
            while (true) {
                Thread.currentThread().join(1000);
                writer.write(time + i * 1000);
                if (i % 5 == 0) {
                    writer.write(heart);
                }
                if (i % 60 == 0) {
                    writer.write(charge + NetUtil.isCharge(context));
                    writer.write(batter + NetUtil.getBatter(context));
                }
                writer.flush();
                i++;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e(Client03.class.getSimpleName(), inputMsg);
            return inputMsg;
        }
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
