package com.twirling.lib_cobb.util;

import android.content.Context;
import android.util.Log;

import com.twirling.lib_cobb.Constants;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.twirling.lib_cobb.Constants.PATH_DOWNLOAD;

/**
 * Created by xieqi on 2017/1/20.
 */
public class FileUtil {
	public static class Create {
		public static boolean createByPath(String path) {
			return createByFile(new File(path));
		}

		public static boolean createByFile(File file) {
			try {
				if (!file.getParentFile().exists()) {
					mkdir(file.getParentFile());
				}
				return file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}

		public static boolean mkdir(File file) {
			while (!file.getParentFile().exists()) {
				mkdir(file.getParentFile());
			}
			return file.mkdir();
		}
	}

	public static class Get {
		public Get() {
			getFileList();
		}

		public static List<String> getFileList() {
			//new的一个File对象
			List<String> list = new ArrayList<String>();
			File f = new File(Constants.PATH_MOVIES);
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
					list.add(name);
				}
			}
			Log.e("getAssetList", list.size() + "");
			return list;
		}

		// 读取assets文件
		public static String getFromAsset(Context context, String fileName) {
			InputStream is = null;
			BufferedReader br = null;
			try {
				is = context.getAssets().open(fileName);
				br = new BufferedReader(new InputStreamReader(is));
				String addonStr = "";
				String line = br.readLine();
				while (line != null) {
					addonStr = addonStr + line;
					line = br.readLine();
				}
				return addonStr;
			} catch (Exception e) {
				return null;
			} finally {
				try {
					br.close();
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static class Write {
		public static File writeFileFromShort(short[] changeData) {
			File file = new File(Constants.PATH_DOWNLOAD + File.separator + "short.wav");
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FileOutputStream fos = null;
			DataOutputStream dos = null;
			try {
				fos = new FileOutputStream(file);
				dos = new DataOutputStream(fos);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			for (short j : changeData) {
				try {
					dos.writeShort(j);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				dos.flush();
				fos.flush();
				dos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Log.w("fileLength", file.length() + "");
			return file;
		}
	}

	public static class Delete {
		public static void deleteByPath(String path) {
			deleteByFile(new File(path));
		}

		public static void deleteByFile(File file) {
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
					deleteByFile(childFiles[i]);
				}
				file.delete();
			}
		}
	}

	public static class Copy {
		// 都是相对路径，一一对应
		public static String copyAssetFileToFiles(Context context, String filename) {
			copyAssetFileToFiles(context, filename, new File(PATH_DOWNLOAD + "/" + filename));
			return PATH_DOWNLOAD + "/" + filename;
		}

		public static boolean copyAssetFileToFiles(Context context, String filename, File of) {
			InputStream is = null;
			FileOutputStream os = null;
			try {
				is = context.getAssets().open(filename);
				Create.createByFile(of);
				os = new FileOutputStream(of);
				int readedBytes;
				byte[] buf = new byte[1024];
				while ((readedBytes = is.read(buf)) > 0) {
					os.write(buf, 0, readedBytes);
				}
				os.flush();
				is.close();
				os.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
	}
}