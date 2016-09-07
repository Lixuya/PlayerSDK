package com.twirling.player;

import android.os.Environment;

import java.io.File;

/**
 * Created by xieqi on 2016/8/11.
 */
public class Constants {
    //
    public static boolean isDownload = false;
    public static final String PAPH_DOWNLOAD = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/";
    public static final String FILE_NAME = "jiaoyu.mp4";
    public static final String FILE_PATH = PAPH_DOWNLOAD + FILE_NAME;

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
