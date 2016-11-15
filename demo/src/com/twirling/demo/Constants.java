package com.twirling.demo;

import java.io.File;

import static com.twirling.player.Constants.PAPH_DOWNLOAD;

/**
 * Created by xieqi on 2016/8/11.
 */
public class Constants {

    public static final String FILE_NAME = "jiaoyu.mp4";
    public static final String FILE_PATH = PAPH_DOWNLOAD + FILE_NAME;

    public static boolean is3D = false;

    private static File file = null;

    public static File getFile() {
        if (file == null) {
            file = new File(FILE_PATH);
        }
        return file;
    }

    public static void deleteFile() {
        file.delete();
        file = null;
    }
}
