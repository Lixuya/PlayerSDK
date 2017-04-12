package com.google.android.exoplayer;

/**
 * Created by xieqi on 2017/1/16.
 */

public class HeadAnglesEvent {
    public static HeadAnglesEvent m_HeadAnglesEvent=new HeadAnglesEvent();
	private float[] yawAndPitch = new float[2];

	public void HeadAnglesEvent(float[] yawAndPitch) {
		this.yawAndPitch = yawAndPitch;
	}

	public float[] getYawAndPitch() {
		return yawAndPitch;
	}
}
