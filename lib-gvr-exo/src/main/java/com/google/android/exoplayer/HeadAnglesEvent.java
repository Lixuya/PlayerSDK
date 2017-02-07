package com.google.android.exoplayer;

/**
 * Created by xieqi on 2017/1/16.
 */

public class HeadAnglesEvent {

	private float[] yawAndPitch = new float[2];

	public HeadAnglesEvent(float[] yawAndPitch) {
		this.yawAndPitch = yawAndPitch;
	}

	public float[] getYawAndPitch() {
		return yawAndPitch;
	}
}
