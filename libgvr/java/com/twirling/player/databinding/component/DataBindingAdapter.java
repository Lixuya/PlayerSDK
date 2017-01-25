package com.twirling.player.databinding.component;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

/**
 * Target: 为databinding补充绑定方法
 */
public class DataBindingAdapter {
	@BindingAdapter({"imageUrl", "placeholder"})
	public static void setImageFromUrl(ImageView view,
	                                   String url,
	                                   Drawable drawable) {
		Glide.with(view.getContext())
				.load(url)
				.thumbnail(0.1f)
				.placeholder(drawable)
				.into(view);
	}

	@BindingAdapter({"max"})
	public static void setMax(WidgetComponent component,
	                          ProgressBar view,
	                          int max) {
		view.setMax(max);
	}

	@BindingAdapter({"progress"})
	public static void setProgess(WidgetComponent component,
	                              ProgressBar view,
	                              int progress) {
		view.setProgress(progress);
	}

	@BindingAdapter("android:src")
	public static void loadImage(WidgetComponent component,
	                             ImageView view,
	                             String url) {
		/// ...
	}
}
