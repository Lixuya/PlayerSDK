package com.twirling.libsocket;

import java.io.*;
import java.net.*;

public class TalkClient {
    public static int BufferHasSigCount(byte[] chars, int len) {
        int count = 0;
        byte b;
        for (int i = 0; i < len; i++) {
            b = chars[i];
            if (b == ';') {
                count++;
            }
        }
        return count;
    }

    public static byte[] readStream(InputStream inStream) throws Exception {
        int count = 0;
        while (count == 0) {
            count = inStream.available();
        }
        byte[] b = new byte[count];
        inStream.read(b);
        return b;
    }

    public static void main(String args[]) {
        try {
            Socket socket = new Socket("192.168.0.195", 10002);

            Writer writer = new OutputStreamWriter(socket.getOutputStream());
            writer.write("20-11-01-02-03-04");
            writer.flush();

            DataInputStream reader = new DataInputStream(socket.getInputStream());
            byte[] bytes = new byte[1024 * 100];
            int len;
            int wantFileLen = 0;
            int cnt = 0;
            StringBuilder sb = new StringBuilder();
            int zzz = 0;
            boolean needBreak = false;
            while ((len = reader.read(bytes)) != -1) {
                for (int i = 0; i < len; i++) {
                    zzz = i;
                    if (bytes[i] == ';') {
                        cnt++;
                        System.out.println("cnt = " + cnt);
                    }
                    if (cnt < 5) {
                        sb.append(new String(bytes, i, 1));
                    } else {
                        needBreak = true;
                        break;
                    }
                }
                if (cnt == 5) {
                    String readBuffer = sb.toString();
                    String[] hassig = readBuffer.split(";");
                    if (hassig.length >= 5) {
                        String[] hassig2 = hassig[4].split("_");
                        wantFileLen = Integer.valueOf(hassig2[2]);
                        break;
                    }
                }
                if (needBreak) {
                    break;
                }
            }
            System.out.println("Last Time Get len = " + len);

            String name = "bugs bunny.mp4";
            File download = new File(name);

            FileOutputStream outFile = new FileOutputStream(download);

            //FileWriter fileWritter = new FileWriter(download.getName(),true);
            //BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            //outFile.write(readBuffer.toCharArray());
            //bufferWritter.flush();
            if (len - zzz - 1 > 0) {
                outFile.write(bytes, zzz + 1, len - zzz - 1);
                outFile.flush();
            }


            int fileLen = len - zzz - 1;
            System.out.println("Recv len = " + fileLen);


            while ((len = reader.read(bytes)) != -1)
            //while (true) 
            {
                //len = reader.read(chars, 0, 64);
                //len = tmp.length;

                fileLen += len;


                if (fileLen >= wantFileLen) {
                    outFile.write(bytes, 0, len + wantFileLen - fileLen);
                    break;
                }

                outFile.write(bytes, 0, len);

                outFile.flush();

                System.out.println("Recv len = " + fileLen);
                System.out.println("wanted len = " + wantFileLen);
            }


            outFile.close();

            writer.close(); //�ر�Socket�����
            reader.close(); //�ر�Socket������
            socket.close(); //�ر�Socket
            System.out.println("socket close");
        } catch (Exception e) {
            System.out.println("Error" + e); //�������ӡ������Ϣ
        }
    }
}