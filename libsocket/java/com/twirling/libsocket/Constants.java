package com.twirling.libsocket;

import android.os.Environment;

/**
 * Created by xieqi on 2016/8/11.
 */
public class Constants {
	//
	public static final String PAPH_DOWNLOAD = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/";
	public static final String PAPH_OCULUS = Environment.getExternalStorageDirectory() + "/" + "Oculus/360Videos/";
	// HIRENDER
	public static final String DEFAULT_IP = "255.255.255.255";
	public static final int DEFAULT_PORT = 10001;
	public static final int DEFAULT_TIMEOUT = 5000;
	//
	public static boolean is3D = false;
	// 0 play 1 pause 2 Stop 3 replay
	public static int state = 2;
}
