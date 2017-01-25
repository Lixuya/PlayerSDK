package com.twirling.player.util;

import android.content.Context;
import android.util.Log;

import com.twirling.player.Constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieqi on 2017/1/20.
 */

public class FileUtil {
	public static List<String> getFileList() {
		//new的一个File对象
		List<String> list = new ArrayList<String>();
		File f = new File(Constants.PAPH_MOVIES);
		if (!f.exists()) {
			f.mkdirs();
		}
		if (f.isDirectory()) {
			for (File file : f.listFiles()) {
				if (file.getName().endsWith("mp4")) {
					String name = file.getName().substring(0, file.getName().length());
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
				name += "asset";
				list.add(name);
			}
		}
		Log.e("getAssetList", list.size() + "");
		return list;
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
