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
	private Drawable iconDownload = null;
	private Drawable iconPlay = null;
	private int progress = 0;
	private int max = 100;
	private String url = null;

	public OnlineModel(Context context) {
		iconDownload = new IconicsDrawable(context)
				.icon(FontAwesome.Icon.faw_cloud_download)
				.color(Color.parseColor("#F0DDFFFF"))
				.sizeDp(40);
		iconPlay = new IconicsDrawable(context)
				.icon(FontAwesome.Icon.faw_play_circle)
				.color(Color.parseColor("#B0FFFFFF"))
				.sizeDp(36);
	}

	public Drawable getIconDownload() {
		return iconDownload;
	}

	public void setIconDownload(Drawable iconDownload) {
		this.iconDownload = iconDownload;
	}

	public Drawable getIconPlay() {
		return iconPlay;
	}

	public void setIconPlay(Drawable iconPlay) {
		this.iconPlay = iconPlay;
	}

	@Bindable
	public String getUrl() {
		return url;
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

	public void setUrl(String url) {
		this.url = url;
		notifyPropertyChanged(com.twirling.demo.BR.url);
	}
}