package com.twirling.www.libgvr.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.twirling.www.libgvr.Constants;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 谢秋鹏 on 2016/5/27.
 */
public class FileUtil {
    //
    public static File file;

    // 读文件
    public static File readFromOculus(String fileName) {
        return readFromPath(fileName, Constants.PAPH_OCULUS);
    }

    // 读文件
    public static File readFromDownload(String fileName) {
        return readFromPath(fileName, Constants.PAPH_DOWNLOAD);
    }

    // 读文件
    public static File readFromPath(String fileName, String filePath) {
        File file = new File(filePath, fileName);
        try {
            File path = new File(filePath);
            if (!path.exists()) {
                path.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 转换 path路径
     *
     * @param c
     * @param uri
     * @return
     */
    public static String getFilePathFromUri(Context c, Uri uri) {
        String filePath = null;
        if ("content".equals(uri.getScheme())) {
            String[] filePathColumn = {MediaStore.MediaColumns.DATA};
            ContentResolver contentResolver = c.getContentResolver();
            Cursor cursor = contentResolver.query(uri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filePath = cursor.getString(columnIndex);
            cursor.close();
        } else if ("file".equals(uri.getScheme())) {
            filePath = new File(uri.getPath()).getAbsolutePath();
        }
        return filePath;
    }

    public static List<String> getFileList() {
        //new的一个File对象
        List<String> list = new ArrayList<String>();
        File f = new File(Constants.PAPH_OCULUS);
        if (f.isDirectory()) {
            for (File file : f.listFiles()) {
                if (file.getName().endsWith("mp4")) {
                    String name = file.getName().substring(0, file.getName().length() - 4);
                    list.add(name);
                }
            }
        }
        Log.e("getFileList", list.size() + "");
        return list;
    }

    public static List<String> getAssetList(Context context) {
        String[] str = new String[0];
        try {
            str = context.getAssets().list("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> list = new ArrayList<String>();
        for (String name : str) {
            if (name.endsWith("mp4")) {
                name = name.substring(0, name.length() - 4);
                name += "assets";
                list.add(name);
            }
        }
        Log.e("getAssetList", list.size() + "");
        return list;
    }

    public static void delete(Uri uri) {
        File file = null;
        try {
            file = new File(new URI(uri.toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            file.delete();
        }
    }

    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            file.delete();
        }
    }
}