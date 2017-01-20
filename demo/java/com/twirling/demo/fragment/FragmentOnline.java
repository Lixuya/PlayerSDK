package com.twirling.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;

import com.twirling.demo.Constants;
import com.twirling.demo.R;
import com.twirling.player.activity.PlayerActivity;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadStatus;

import static com.twirling.demo.Constants.getFile;

/**
 * Created by 谢秋鹏 on 2016/5/27.
 */
public class FragmentOnline extends Fragment {
	@BindView(R.id.button)
	Button load;
	@BindView(R.id.button2)
	Button play;
	@BindView(R.id.cb_3d)
	CheckBox cb_3d;
	@BindView(R.id.pb_loading)
	ProgressBar pb_loading;
	//
	private String url = "http://yun-twirlingvr.oss-cn-hangzhou.aliyuncs.com/App_Movies/guzheng/video/guzhen_2k.mp4";
	public int contentLength;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_online, null);
		ButterKnife.bind(this, view);
		load.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RxDownload.getInstance()
						.download(url, com.twirling.demo.Constants.FILE_NAME, null)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Observer<DownloadStatus>() {
							@Override
							public void onSubscribe(Disposable d) {
							}

							@Override
							public void onNext(DownloadStatus value) {
								//获得下载状态
								pb_loading.setMax((int) value.getTotalSize());
								pb_loading.setProgress((int) value.getDownloadSize());
							}

							@Override
							public void onError(Throwable e) {
								//下载出错
							}

							@Override
							public void onComplete() {
								//下载完成
							}
						});
			}
		});
		play.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("VideoItem", url);
				Constants.is3D = false;
				//设置跳转新的activity，参数（当前对象，跳转到的class）
				intent.setClass(getActivity(), PlayerActivity.class);
				//启动Activity 没有返回
				startActivity(intent);
			}
		});
		cb_3d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
				if (isChecked) {
					Constants.is3D = true;
				} else {
					Constants.is3D = false;
				}
			}
		});
		return view;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {
			if (getFile() == null) {
				pb_loading.setProgress(0);
			}
		}
		super.setUserVisibleHint(isVisibleToUser);
	}
}
