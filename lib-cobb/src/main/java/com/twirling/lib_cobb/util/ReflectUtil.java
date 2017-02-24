package com.twirling.lib_cobb.util;

import android.media.AudioTrack;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by xieqi on 2017/2/22.
 */

public class ReflectUtil {
	public static void getHideFunction(AudioTrack audioplayer){
		Class<?> clazz = null;
		try {
			clazz = Class.forName("android.media.AudioTrack");
			Method method = clazz.getMethod("getLatency");
			method.setAccessible(true);
			Log.w("getLatency", method.invoke(audioplayer) + "");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
