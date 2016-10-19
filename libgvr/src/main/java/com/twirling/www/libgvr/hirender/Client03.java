package com.twirling.www.libgvr.hirender;

import android.content.Context;
import android.util.Log;

import com.twirling.www.libgvr.util.NetUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

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
    private final String TIME = "VR_TIME_";

    public String sendMessage(final Context context) {
        String inputMsg = "";
        Socket socket = null;
        try {
            socket = new Socket(ip, 10003);
            // 构建IO
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            // 向服务器端发送一条消息
            final Writer writer = new OutputStreamWriter(os);
            writer.write(NetUtil.getMac());
            writer.flush();
            Observable
                    .interval(1, TimeUnit.SECONDS)
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            index += 1000;
                            String time = TIME + index + ";";
                            try {
                                writer.write(time);
                                writer.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            Observable
                    .interval(5, TimeUnit.SECONDS)
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            Log.e(Client03.class.getSimpleName(), heart);
                            try {
                                writer.write(heart);
                                writer.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
            Observable
                    .interval(60, TimeUnit.SECONDS)
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            String charge = CHARGE + NetUtil.isCharge(context) + ";";
                            String batter = BATTER + NetUtil.getBatter(context) + ";";
                            Log.d(Client03.class.getSimpleName(), charge);
                            Log.w(Client03.class.getSimpleName(), batter);
                            try {
                                writer.write(charge);
                                writer.flush();
                                writer.write(batter);
                                writer.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
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

    private void sendMessage(Writer writer, Context context) throws IOException, InterruptedException {
        int i = 0;
        while (true) {
            Thread.currentThread().join(1000);
            String time = TIME + i * 1000 + ";";
            Log.v(Client03.class.getSimpleName(), time);
            writer.write(time);
            writer.flush();
            if (i % 5 == 0) {
                Log.e(Client03.class.getSimpleName(), heart);
                writer.write(heart);
                writer.flush();
            }
            if ((i + 60) % 60 == 0) {
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
    }
}
