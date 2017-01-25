package com.twirling.demo.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

/**
 * Created by xieqi on 2017/1/24.
 */
public class OnlineModel extends BaseObservable {
	private Drawable icon = null;
	private int progress = 0;
	private int max = 100;

	public OnlineModel(Context context) {
		icon = new IconicsDrawable(context)
				.icon(FontAwesome.Icon.faw_cloud_download)
				.color(Color.parseColor("#DDFFFF"))
				.sizeDp(40);
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	@Bindable
	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
		notifyPropertyChanged(com.twirling.demo.BR.progress);
	}

	@Bindable
	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
		notifyPropertyChanged(com.twirling.demo.BR.max);
	}
}
