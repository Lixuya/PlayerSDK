package com.twirling.demo.fragment;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.twirling.demo.R;
import com.twirling.player.adapter.OffineAdapter;
import com.twirling.player.model.OfflineModel;
import com.twirling.player.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Target: 本地视频页
 */
public class FragmentDownload extends Fragment {
	@BindView(R.id.rv)
	XRecyclerView recyclerView;

	private OffineAdapter adapter = null;
	private List<String> strings = new ArrayList<String>();
	private List<OfflineModel> models = new ArrayList<OfflineModel>();

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_download, container, false);
		ButterKnife.bind(this, rootView);
		//
		GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
//        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(getBaseContext()));
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		recyclerView.setLaodingMoreProgressStyle(ProgressStyle.Pacman);
//        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
//        mRecyclerView.setHasFixedSize(true);
		recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				loadData();
				recyclerView.refreshComplete();
			}

			@Override
			public void onLoadMore() {
				recyclerView.loadMoreComplete();
			}
		});
		adapter = new OffineAdapter(models);
		recyclerView.setAdapter(adapter);
		loadData();
		return rootView;
	}

	private void loadData() {
		strings.clear();
		models.clear();
		new RxPermissions(getActivity())
				.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
				.subscribeOn(AndroidSchedulers.mainThread())
				.observeOn(AndroidSchedulers.mainThread())
				.doOnNext(new Consumer<Boolean>() {
					@Override
					public void accept(Boolean granted) throws Exception {
						if (!granted) {
							Toast.makeText(getActivity(), "请到设置中心打开应用存储权限", Toast.LENGTH_LONG).show();
						}
					}
				})
				.subscribe(new Consumer<Boolean>() {
					@Override
					public void accept(Boolean aBoolean) throws Exception {
						strings.addAll(FileUtil.getFileList());
						strings.addAll(FileUtil.getAssetList(getActivity()));
						//
						for (int i = 0; i < strings.size(); i++) {
							OfflineModel model = new OfflineModel(getActivity());
							model.setName(strings.get(i));
							model.setPosition(i);
							models.add(model);
						}
//						DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallBack<>(oldModels, models), true);
//						result.dispatchUpdatesTo(adapter);
//						adapter.setModels(models);
						adapter.notifyDataSetChanged();
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {
						Toast.makeText(recyclerView.getContext(), "刷新失败", Toast.LENGTH_LONG).show();
					}
				});
	}
}