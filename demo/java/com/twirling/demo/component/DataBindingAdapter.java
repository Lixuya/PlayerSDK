package com.twirling.demo.component;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Target: 为databinding补充绑定方法
 */
public class DataBindingAdapter extends com.twirling.lib_cobb.adapter.DataBindingAdapter {
	@BindingAdapter({"stagePhotoByUrl"})
	public static void setStagePhotoByUrl(ImageView view,
	                                      String url) {
		Glide.with(view.getContext())
				.load(url)
				.asBitmap()
				.centerCrop()
//				.placeholder(drawable)
				.into(view);
	}
}