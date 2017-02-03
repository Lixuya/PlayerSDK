package com.twirling.demo.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.twirling.demo.R;
import com.twirling.player.adapter.OffineAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 谢秋鹏 on 2016/5/27.
 */
public class FragmentSocket extends Fragment {
	@BindView(R.id.rv)
	XRecyclerView recyclerView;

	private OffineAdapter mAdapter = null;
	private List<String> datas = new ArrayList<String>();
	private SharedPreferences settings;

//	private Broadcast broadcast = null;

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
//		mAdapter = new OffineAdapter(datas);
		recyclerView.setAdapter(mAdapter);
		//
//		broadcast = new Broadcast();
		return rootView;
	}

	private void loadData() {
		settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
//		broadcast.getIp();
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {
//            loadData();
		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	public void sendMessage(String ip) {
//		Observable.just(ip)
//				.filter(new Func1<String, Boolean>() {
//					@Override
//					public Boolean call(String s) {
//						return !TextUtils.isEmpty(s);
//					}
//				})
//				.subscribeOn(Schedulers.newThread())
//				.subscribe(new Action1<String>() {
//					@Override
//					public void call(String ip) {
//						Client03 client = new Client03();
//						client.setIp(ip);
//						client.sendMessage(getActivity());
//					}
//				});
	}
}