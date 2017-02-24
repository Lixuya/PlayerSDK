package com.twirling.player.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

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
	// 视频路径
	private String videoPath = "";

	public OfflineModel(Context context) {
		iconTrash = new IconicsDrawable(context)
				.icon(FontAwesome.Icon.faw_trash_o)
				.color(Color.parseColor("#FFFFFF"))
				.sizeDp(30);
	}

	@Bindable
	public int getPosition() {
		return position;
	}

	@Bindable
	public String getName() {
		return name;
	}

	@Bindable
	public Drawable getIconTrash() {
		return iconTrash;
	}

	@Bindable
	public String getVideoPath() {
		return videoPath;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setName(String name) {
		this.name = name;
		if (name.endsWith("asset")) {
			iconTrash = null;
		}
//		notifyPropertyChanged();
	}

	public void setIconTrash(Drawable iconTrash) {
		this.iconTrash = iconTrash;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
}
