package com.twirling.player.client;

import android.content.Context;
import android.util.Log;

import com.twirling.player.Constants;
import com.twirling.player.util.NetUtil;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
public class Client02 {
    private String ip = "";
    private int port = 10002;
    int index = 0;

    public String sendMessage(Context context) {
        String readBuffer = "";
        String name = "";
        int size = 0;
        String content = "";
        Socket socket = null;
        DataInputStream reader = null;
        Writer writer = null;
        byte[] bytes = new byte[1024 * 1024];
        int len = 0;
        int videoIndex = 0;
        int sperate = 0;
        //
        try {
            socket = new Socket(ip, port);
//            socket.setReceiveBufferSize();
//            socket.setSoTimeout(5000);-
            String mac = NetUtil.getMac();
            //构建IO
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            //向服务器端发送一条消息
            writer = new OutputStreamWriter(os);
            writer.write(mac);
            writer.flush();
            Log.e("www", mac);
//            Toast.makeText(context, mac, Toast.LENGTH_LONG).show();
            //读取服务器返回的消息
            reader = new DataInputStream(is);
            len = reader.read(bytes);
            String client = new String(bytes, 0, len);
            Log.w(Client02.class.getSimpleName(), client);
//            Toast.makeText(context, client, Toast.LENGTH_LONG).show();
            //
            StringBuilder stringBuilder = new StringBuilder();
            while ((len = reader.read(bytes)) != -1) {
                for (int i = 0; i < len; i++) {
                    videoIndex = i;
                    if (bytes[videoIndex] == ';') {
                        sperate++;
                    }
                    if (sperate < 5) {
                        stringBuilder.append(new String(bytes, videoIndex, 1));
                    } else if (sperate == 5) {
                        break;
                    }
                }
                // buffer 太长
                if (sperate == 5) {
                    break;
                }
            }
            readBuffer = stringBuilder.toString();
            String[] info = readBuffer.split(";");
            String sent = info[0].split("_")[2];
            String file = info[1].split("_")[2];
            String media = info[2].split("_")[2];
            name = info[3].split("_")[2];
            size = Integer.valueOf(info[4].split("_")[2]);
            String tag = "sent " + sent + "\n " +
                    "file " + file + "\n " +
                    "media " + media + "\n " +
                    "name " + name + "\n " +
                    "size " + size;
            Log.w(Client02.class.getSimpleName(), tag);
//            Toast.makeText(context, tag, Toast.LENGTH_LONG).show();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        File download = new File(Constants.PAPH_DOWNLOAD + name);
        FileOutputStream outFile = null;
        try {
            outFile = new FileOutputStream(download);
            if (len - (videoIndex + 1) > 0) {
                outFile.write(bytes, videoIndex + 1, len - videoIndex - 1);
                outFile.flush();
            }
            int fileLen = len - videoIndex - 1;
            while ((len = reader.read(bytes)) != -1) {
                fileLen += len;
                outFile.write(bytes, 0, len);
                outFile.flush();
                if (fileLen >= size) {
                    outFile.write(bytes, 0, len - (fileLen - size));
                    break;
                }
            }
            Log.d(Client02.class.getSimpleName(), new String(bytes));
            Log.e(Client02.class.getSimpleName(), "socket close");
            int length = ";VR_MEDIA_OVER".length();
            StringBuilder stringBuilder2 = new StringBuilder();
            for (int i = 0; i < length; i++) {
                stringBuilder2.append(new String(bytes, len - (fileLen - size) + i, 1));
            }
            Log.w(Client02.class.getSimpleName(), stringBuilder2.toString());
            //
            String back = "VR_RECEIVE_MEDIA_SIZE_%" + size;
            writer.write(back);
            writer.flush();
            //
            back = "VR_READY";
            writer.write(back);
            writer.flush();
            //
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            Log.e(Client02.class.getSimpleName(), readBuffer);
            try {
                outFile.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return readBuffer;
        }

    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
