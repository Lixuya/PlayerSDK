package com.twirling.player.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.twirling.lib_cobb.util.FileUtil;
import com.twirling.player.Constants;
import com.twirling.player.R;
import com.twirling.player.adapter.OffineAdapter;
import com.twirling.player.model.OfflineModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Target: 本地视频页
 */
public class FragmentDownload extends Fragment {
	private View rootView = null;
	private XRecyclerView recyclerView;
	private OffineAdapter adapter = null;
	private List<OfflineModel> models = new ArrayList<OfflineModel>();

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_download, container, false);
		//
		GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
//        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(getBaseContext()));
		recyclerView = (XRecyclerView) rootView.findViewById(R.id.rv);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		recyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
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
		models.clear();
		List<String> strings = FileUtil.Get.getFileList();
		int i = 0;
		OfflineModel model = null;
		for (String name : strings) {
			model = new OfflineModel(getActivity());
			model.setName(name);
			model.setAsset(false);
			model.setVideoPath(Constants.PATH_MOVIES + name);
			models.add(model);
			i++;
		}
		strings = FileUtil.Get.getAssetList(getActivity());
		for (String name : strings) {
			model = new OfflineModel(getActivity());
			model.setName(name);
			model.setAsset(true);
			model.setVideoPath(Constants.PATH_MOVIES + name);
			models.add(model);
			i++;
		}
//		DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallBack<>(oldModels, models), true);
//		result.dispatchUpdatesTo(adapter);
//		adapter.setModels(models);
		adapter.notifyDataSetChanged();
	}
}