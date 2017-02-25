package com.twirling.lib_cobb;

import android.os.Environment;

/**
 * Target: 基本常量
 */
public class Constants {
	//
	public static final String PATH_DOWNLOAD = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/";
	public static final String PATH_MOVIES = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_MOVIES + "/";
	public static final String PATH_MUSIC = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_MUSIC + "/";
	public static final String PATH_OCULUS = Environment.getExternalStorageDirectory() + "/" + "Oculus/360Videos/";
	//
	public static final String FILE_NAME = "GuZheng_2K.mp4";
	public static final String FILE_PATH = PATH_MOVIES + FILE_NAME;
}