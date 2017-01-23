package com.twirling.demo.fragment;

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
import com.twirling.demo.R;
import com.twirling.demo.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Target: 下载界面
 */
public class FragmentDownload extends Fragment {
	@BindView(R.id.rv)
	XRecyclerView recyclerView;

	private OffineAdapter mAdapter = null;
	private List<String> datas = new ArrayList<String>();

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
		mAdapter = new OffineAdapter(datas);
		recyclerView.setAdapter(mAdapter);
		return rootView;
	}

	private void loadData() {
		datas.clear();
		try {
			datas.addAll(FileUtil.getFileList());
//			datas.addAll(FileUtil.getAssetList(getActivity()));
		} catch (Exception e) {
			Toast.makeText(recyclerView.getContext(), "请到设置中心打开应用存储权限", Toast.LENGTH_LONG).show();
		}
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {
			loadData();
		}
		super.setUserVisibleHint(isVisibleToUser);
	}
}