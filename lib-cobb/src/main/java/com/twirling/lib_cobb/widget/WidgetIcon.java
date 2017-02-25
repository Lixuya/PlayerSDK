package com.twirling.lib_cobb.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

/**
 * Created by xieqi on 2017/2/24.
 */
public class WidgetIcon {
	public static Drawable getLeaveIcon(Context context) {
		Drawable icon = new IconicsDrawable(context)
				.icon(FontAwesome.Icon.faw_arrow_circle_left)
				.color(Color.parseColor("#90FFFF"))
				.sizeDp(30);
		return icon;
	}

	public static Drawable getDownloadIcon(Context context) {
		Drawable icon = new IconicsDrawable(context)
				.icon(FontAwesome.Icon.faw_cloud_download)
				.color(Color.parseColor("#F0DDFFFF"))
				.sizeDp(30);
		return icon;
	}

	public static Drawable getSignOutIcon(Context context) {
		Drawable icon = new IconicsDrawable(context)
				.icon(FontAwesome.Icon.faw_street_view)
				.color(Color.parseColor("#FF90FF"))
				.sizeDp(30);
		return icon;
	}

	public static Drawable getTrashIcon(Context context) {
		Drawable icon = new IconicsDrawable(context)
				.icon(FontAwesome.Icon.faw_trash_o)
				.color(Color.parseColor("#FFFFFF"))
				.sizeDp(30);
		return icon;
	}

	public static Drawable getPlayIcon(Context context) {
		Drawable icon = new IconicsDrawable(context)
				.icon(FontAwesome.Icon.faw_play_circle)
				.color(Color.parseColor("#B0FFFFFF"))
				.sizeDp(36);
		return icon;
	}

}
