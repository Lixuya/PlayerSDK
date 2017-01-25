package com.twirling.player.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import java.io.File;

/**
 * Created by xieqi on 2017/1/23.
 */

public class OfflineModel extends BaseObservable {
	private int position = -1;
	private String name = "";
	private Drawable icon = null;
	private File stagePhoto = null;

	public OfflineModel(Context context) {
		icon = new IconicsDrawable(context)
				.icon(FontAwesome.Icon.faw_trash_o)
				.color(Color.parseColor("#FFFFFF"))
				.sizeDp(30);
	}

	public int getPosition() {
		return position;
	}

	public String getName() {
		return name;
	}

	public Drawable getIcon() {
		return icon;
	}

	public File getStagePhoto() {
		return stagePhoto;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setName(String name) {
		this.name = name;
		if (name.endsWith("asset")) {
			icon = null;
		}
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public void setStagePhoto(File stagePhoto) {
		this.stagePhoto = stagePhoto;
	}
}
