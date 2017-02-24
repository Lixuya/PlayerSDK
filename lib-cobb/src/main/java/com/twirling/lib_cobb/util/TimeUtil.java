package com.twirling.lib_cobb.util;

import java.text.SimpleDateFormat;

/**
 * Created by xieqi on 2017/2/3.
 */
public class TimeUtil {
	public static String float2time(float timeNumber) {
		SimpleDateFormat formatter = (SimpleDateFormat) SimpleDateFormat.getTimeInstance();
		formatter.applyPattern("mm:ss");
		String hms = formatter.format(timeNumber * 1000);
		return hms;
	}
}
