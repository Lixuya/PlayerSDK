package com.twirling.player.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;

import com.twirling.lib_cobb.widget.WidgetIcon;
import com.twirling.player.BR;

/**
 * Created by xieqi on 2017/1/23.
 */

public class OfflineModel extends BaseObservable {
	// 列表索引
	private int position = -1;
	// 视频名称
	private String name = "";
	// 删除图标
	private Drawable iconTrash = null;
	// 视频下载Url
	private String videoUrl = "";
	// 视频本地存储路径
	private String videoPath = "";
	// assets文件
	private boolean asset = false;

	public OfflineModel(Context context) {
		iconTrash = WidgetIcon.getTrashIcon(context);
	}

	@Bindable
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
		notifyPropertyChanged(BR.position);
	}

	@Bindable
	public String getName() {
		return name;
	}

	@Bindable
	public Drawable getIconTrash() {
		return iconTrash;
	}

	public void setName(String name) {
		this.name = name;
		notifyPropertyChanged(BR.name);
	}

	public void setIconTrash(Drawable iconTrash) {
		this.iconTrash = iconTrash;
		notifyPropertyChanged(BR.iconTrash);
	}

	@Bindable
	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	@Bindable
	public boolean isAsset() {
		return asset;
	}

	public void setAsset(boolean asset) {
		this.asset = asset;
	}

	@Bindable
	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	@Override
	public String toString() {
		return "OfflineModel{" +
				"position=" + position +
				", name='" + name + '\'' +
				", videoUrl='" + videoUrl + '\'' +
				", videoPath='" + videoPath + '\'' +
				", asset='" + asset + '\'' +
				'}';
	}
}
