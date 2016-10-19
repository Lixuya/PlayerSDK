package com.twirling.player;

import java.io.File;
import static com.twirling.www.libgvr.Constants.PAPH_DOWNLOAD;

/**
 * Created by xieqi on 2016/8/11.
 */
public class Constants {

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
