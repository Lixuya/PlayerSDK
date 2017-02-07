package com.twirling.player.databinding.component;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

/**
 * Target: 为databinding补充绑定方法
 */
public class DataBindingAdapter {
	@BindingAdapter("stagePhoto")
	public static void setStagePhotoByFile(ImageView view,
	                                 File video) {
		Glide.with(view.getContext())
				.load(video)
				.asBitmap()
				.thumbnail(0.1f)
				.centerCrop()
//				.placeholder(drawable)
				.into(view);
	}
}
