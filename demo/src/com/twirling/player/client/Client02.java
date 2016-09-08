package com.twirling.player.client;

import android.content.Context;
import android.util.Log;

import com.twirling.player.util.NetUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by xieqi on 2016/9/6.
 */
public class Client02 {
    private String ip = "";
    private int port = 10002;
    int index = 0;

    public String sendMessage(Context context) {
        String inputMsg = "";
        Socket socket = null;
        try {
            socket = new Socket(ip, 10002);
            Log.i("www", ip);
            String mac = NetUtil.getMacAddress(context);
            mac = mac.replace(":", "-");
            //构建IO
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            //向服务器端发送一条消息
            Writer writer = new OutputStreamWriter(os);
            writer.write(mac);
            writer.flush();
            Log.e("www", mac);
            //读取服务器返回的消息
            Reader reader = new InputStreamReader(is, "UTF-8");
//            BufferedReader br = new BufferedReader(reader);
//            Log.i("mess", br.readLine());
            while ((index = reader.read()) != 0) {
                inputMsg += (char) index;
                Log.w("mess", index + " " + inputMsg + " " + Thread.currentThread().getName());
            }
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Log.e("mess2", inputMsg);
            return inputMsg;
        }
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
