package com.twirling.player.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.twirling.player.Constants;
import com.twirling.player.R;
import com.twirling.player.client.Client01;
import com.twirling.player.client.Client01Command;
import com.twirling.player.client.Client02;
import com.twirling.player.client.Client03;

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
        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        getIp();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
//            loadData();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private String ip = "";

    // getIp
    public void getIp() {
        Observable.just(ip)
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String ip) {
                        return TextUtils.isEmpty(ip);
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        Client01 client = new Client01(Constants.DEFAULT_IP, Constants.DEFAULT_PORT);
                        String ip = client.listen();
                        return ip;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String str) {
                        ip = str;
                        getCommand();
                        recieveFile();
                    }
                });
    }

    // command
    public void getCommand() {
        Observable.just(ip)
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String ip) {
                        return !TextUtils.isEmpty(ip);
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        Client01Command client = new Client01Command(Constants.DEFAULT_IP, Constants.DEFAULT_PORT);
                        String message = client.listen();
                        return message;
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String message) {
                        return message.split(".").length != 4;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String message) {
                        // controlVideo
                    }
                });
    }

    // 02 03
    public void recieveFile() {
        Observable.just(ip)
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return !TextUtils.isEmpty(s);
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Func1<String, Observable<Client02>>() {
                    @Override
                    public Observable<Client02> call(String ip) {
                        Client02 client = new Client02();
                        client.setIp(ip);
                        client.getClientNum();
                        sendMessage(ip);
                        client.getFileInfo();
                        client.recieveFile();
                        return Observable.just(client);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Client02>() {
                    @Override
                    public void call(Client02 client02) {
//                        Client03 client = new Client03();
//                        client.setIp(ip);
//                        client.sendMessage(getActivity());
                    }
                });
    }

    public void sendMessage(String ip) {
        Observable.just(ip)
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return !TextUtils.isEmpty(s);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String ip) {
                        Client03 client = new Client03();
                        client.setIp(ip);
                        client.sendMessage(getActivity());
                    }
                });
    }
}