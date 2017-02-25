package com.twirling.player.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;

import com.twirling.lib_cobb.widget.WidgetIcon;

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
		iconDownload = WidgetIcon.getDownloadIcon(context);
		iconPlay = WidgetIcon.getPlayIcon(context);
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
		notifyPropertyChanged(com.twirling.player.BR.progress);
	}

	@Bindable
	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
		notifyPropertyChanged(com.twirling.player.BR.max);
	}

	public void setUrl(String url) {
		this.url = url;
		notifyPropertyChanged(com.twirling.player.BR.url);
	}
}