package com.twirling.player.fragment;

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
import com.twirling.player.Constants;
import com.twirling.player.OffineAdapter;
import com.twirling.player.R;
import com.twirling.player.client.Client01;
import com.twirling.player.client.Client02;
import com.twirling.player.client.Client03;
import com.twirling.player.config.PreferenceKeys;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 谢秋鹏 on 2016/5/27.
 */
public class FragmentSocket extends Fragment {
    @BindView(R.id.rv)
    XRecyclerView recyclerView;

    private OffineAdapter mAdapter = null;
    private List<String> datas = new ArrayList<String>();
    private SharedPreferences settings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_download, container, false);
        ButterKnife.bind(this, rootView);
        //
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
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
        init();
//        datas.clear();
//        datas.addAll();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
//            loadData();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    public void init() {
        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Observable.just("")
                .observeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        String ip = settings.getString(PreferenceKeys.HOST_IP, Constants.DEFAULT_IP);
                        int port = settings.getInt(PreferenceKeys.HOST_PORT, Constants.DEFAULT_PORT);
                        Client01 listener1 = new Client01(ip, port);
                        ip = listener1.listen();
//                        Toast.makeText(getActivity(), ip, Toast.LENGTH_LONG).show();
                        return ip;
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String ip) {
                        Client02 sendClient = new Client02();
                        sendClient.setIp(ip);
                        String clientID = sendClient.sendMessage(getActivity());
                        return ip;
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String ip) {
                        Client03 sendClient = new Client03();
                        sendClient.setIp(ip);
                        String clientID = sendClient.sendMessage(getActivity());
                        return clientID;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        // 1
                    }
                });
    }
}