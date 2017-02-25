package com.twirling.lib_cobb.adapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Target: 为databinding补充绑定方法
 */
public class DataBindingAdapter {
	@BindingAdapter("stagePhoto")
	public static void setStagePhotoByPath(ImageView view,
	                                       String videoPath) {
		Glide.with(view.getContext())
				.load(videoPath)
				.asBitmap()
				.thumbnail(0.1f)
				.centerCrop()
//				.placeholder(drawable)
				.into(view);
	}
}
