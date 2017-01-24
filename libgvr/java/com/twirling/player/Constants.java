package com.twirling.player;

import android.os.Environment;

/**
 * Target: 基本常量
 */
public class Constants {
	//
	public static final String PAPH_DOWNLOAD = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/";
	public static final String PAPH_MOVIES = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_MOVIES + "/";
	public static final String PAPH_OCULUS = Environment.getExternalStorageDirectory() + "/" + "Oculus/360Videos/";
	//
	public static final String FILE_NAME = "GuZheng_2K.mp4";
	public static final String FILE_PATH = PAPH_MOVIES + FILE_NAME;
	//
	public static boolean IS3D = false;
}