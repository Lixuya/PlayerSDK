package com.twirling.demo.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.twirling.demo.BR;
import com.twirling.demo.Constants;
import com.twirling.demo.R;
import com.twirling.demo.databinding.FragmentOnlineBinding;
import com.twirling.demo.model.OnlineModel;
import com.twirling.player.activity.VRPlayerActivity;

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
	//
	private static final String URL = Constants.REMOTE_URL;
	private OnlineModel onlineModel = null;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		FragmentOnlineBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_online, container, false);
//		DataBindingUtil.setDefaultComponent(new WidgetComponent());
		onlineModel = new OnlineModel(getActivity());
		binding.setVariable(BR.item, onlineModel);
		binding.setVariable(BR.presenter, new Presenter());
		binding.executePendingBindings();
		return binding.getRoot();
	}

	public class Presenter {
		public void onIvDownloadClick(View view) {
			RxDownload.getInstance()
					.download(URL, Constants.FILE_NAME, Constants.PAPH_MOVIES)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(new Observer<DownloadStatus>() {
						@Override
						public void onSubscribe(Disposable d) {
						}

						@Override
						public void onNext(DownloadStatus value) {
							// 获得下载状态
							onlineModel.setMax((int) value.getTotalSize());
							onlineModel.setProgress((int) value.getDownloadSize());
//							pb_download.setMax((int) value.getTotalSize());
//							pb_download.setProgress((int) value.getDownloadSize());
						}

						@Override
						public void onError(Throwable e) {
							// 下载出错
							Toast.makeText(getActivity(), "下载出错", Toast.LENGTH_LONG).show();
						}

						@Override
						public void onComplete() {
							// 下载完成
							Toast.makeText(getActivity(), "下载完成", Toast.LENGTH_LONG).show();
						}
					});
		}

		public void onCvCardClick(View view) {
			Intent intent = new Intent();
			intent.putExtra("VideoItem", URL);
			intent.setClass(getActivity(), VRPlayerActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
//		if (isVisibleToUser && pb_download != null) {
//			if (new File(Constants.FILE_PATH).exists()) {
//				pb_download.setProgress(100);
//			} else {
//				pb_download.setProgress(0);
//			}
//		}
		super.setUserVisibleHint(isVisibleToUser);
	}
}
