package com.twirling.demo.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.twirling.demo.Constants;
import com.twirling.demo.R;
import com.twirling.demo.util.FileUtil;
import com.twirling.player.activity.VRPlayerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadStatus;

/**
 * Target: 在线播放页
 */
public class FragmentOnline extends Fragment {
	@BindView(R.id.iv_download)
	ImageView iv_download;
	@BindView(R.id.iv_play)
	ImageView iv_play;
	@BindView(R.id.iv_video)
	ImageView iv_video;
	@BindView(R.id.pb_download)
	ProgressBar pb_download;
	//
	private String url = Constants.REMOTE_URL;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_online, null);
		ButterKnife.bind(this, view);
		//
		Drawable icon = new IconicsDrawable(getActivity())
				.icon(FontAwesome.Icon.faw_cloud_download)
				.color(Color.parseColor("#DDFFFF"))
				.sizeDp(40);
		iv_download.setImageDrawable(icon);
		iv_download.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RxDownload.getInstance()
						.download(url, Constants.FILE_NAME, Constants.PAPH_MOVIES)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Observer<DownloadStatus>() {
							@Override
							public void onSubscribe(Disposable d) {
							}

							@Override
							public void onNext(DownloadStatus value) {
								// 获得下载状态
								pb_download.setMax((int) value.getTotalSize());
								pb_download.setProgress((int) value.getDownloadSize());
//								Log.w("Online", value.getTotalSize() + " " + value.getDownloadSize());
							}

							@Override
							public void onError(Throwable e) {
								// 下载出错
							}

							@Override
							public void onComplete() {
								// 下载完成
							}
						});
			}
		});
		iv_video.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("VideoItem", url);
				intent.setClass(getActivity(), VRPlayerActivity.class);
				startActivity(intent);
			}
		});
		iv_play.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("VideoItem", url);
				intent.setClass(getActivity(), VRPlayerActivity.class);
				startActivity(intent);
			}
		});
		return view;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {
			if (FileUtil.getFileList() == null) {
				pb_download.setProgress(0);
			}
		}
		super.setUserVisibleHint(isVisibleToUser);
	}
}
