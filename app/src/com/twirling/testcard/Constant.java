package com.twirling.testcard;

import java.io.File;

/**
 * Created by xieqi on 2016/8/11.
 */
public class Constant {
    public static boolean isDownload = false;
    private static File file = null;
    public static String savepath = "sdcard/test.mp4";

    public static File getFile() {
        if (file == null) {
            file = new File(savepath);
        }
        return file;
    }

    public static void deleteFile() {
        file.delete();
        file = null;
    }
}
