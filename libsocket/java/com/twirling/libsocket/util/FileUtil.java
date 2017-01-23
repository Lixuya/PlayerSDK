package com.twirling.libsocket.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.twirling.libsocket.Constants;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Target: 基本文件操作
 */
public class FileUtil {
	//
	public static File file = null;

	public static File readFromOculus(String fileName) {
		return readFromPath(fileName, Constants.PAPH_OCULUS);
	}

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

	//
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