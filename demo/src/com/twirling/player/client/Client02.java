package com.twirling.player.client;

import android.content.Context;
import android.util.Log;

import com.twirling.player.Constants;
import com.twirling.player.util.NetUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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
            socket = new Socket(ip, port);
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
            BufferedReader br = new BufferedReader(reader);
            char[] chars = new char[1024];
            reader.read(chars, 0, chars.length);
            //
            inputMsg = br.readLine();
            int index = -1;
            String[] info = inputMsg.split(";");
//            String clientNum = info[index].split("_")[2];
            String sent = info[index + 1].split("_")[2];
            String file = info[index + 2].split("_")[2];
            String media = info[index + 3].split("_")[2];
            String name = info[index + 4].split("_")[2];
            String size = info[index + 5].split("_")[2];
            Log.w(Client02.class.getSimpleName(), inputMsg + " " + info.toString());
            //
            File download = new File(Constants.PAPH_DOWNLOAD + name);
            FileOutputStream outFile = new FileOutputStream(download);
            int length = 0;
            while ((inputMsg = br.readLine()) != null) {
                length += inputMsg.getBytes().length;
                if (length >= Integer.valueOf(size)) {
                    String recieve = "VR_RECEIVE_MEDIA_SIZE_" + size;
                    writer.write(recieve);
                    writer.flush();
                    break;
                }
                Log.w(Client02.class.getSimpleName(), inputMsg);
                outFile.write(inputMsg.getBytes());
            }
            //
            Log.v(Client02.class.getSimpleName(), inputMsg);
            outFile.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Log.e(Client02.class.getSimpleName(), inputMsg);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inputMsg;
        }
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
