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
    private final String heart = "HEARTBEAT;";
    private final String CHARGE = "VR_CHARGE_";
    private final String BATTER = "VR_BATT_";
    private final String time = "VR_TIME_";

    public String sendMessage(Context context) {
        String inputMsg = "";
        Socket socket = null;
        try {
            socket = new Socket(ip, 10003);
            // 构建IO
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            // 向服务器端发送一条消息
            Writer writer = new OutputStreamWriter(os);
            writer.write(NetUtil.getMac());
            writer.flush();
            //
            int i = 1;
            while (true) {
                Thread.currentThread().join(1000);
                Log.v(Client03.class.getSimpleName(), time + i * 1000);
                writer.write(time + i * 1000);
                writer.flush();
                if (i % 5 == 0) {
                    Log.e(Client03.class.getSimpleName(), heart);
                    writer.write(heart);
                    writer.flush();
                }
                if (i % 6 == 0) {
                    String charge = CHARGE + NetUtil.isCharge(context) + ";";
                    String batter = BATTER + NetUtil.getBatter(context) + ";";
                    Log.d(Client03.class.getSimpleName(), charge);
                    Log.w(Client03.class.getSimpleName(), batter);
                    writer.write(charge);
                    writer.flush();
                    writer.write(batter);
                    writer.flush();
                }
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
