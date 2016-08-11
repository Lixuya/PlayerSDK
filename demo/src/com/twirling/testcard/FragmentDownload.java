package com.twirling.testcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 谢秋鹏 on 2016/5/27.
 */
public class FragmentDownload extends Fragment {
    private String playuri = "file:///sdcard/test.mp4";
    @BindView(R.id.iv_jiaodu)
    ImageView iv_jiaodu;
    @BindView(R.id.iv_delete)
    ImageView iv_delete;
    @BindView(R.id.fl)
    FrameLayout fl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_download, container, false);
        ButterKnife.bind(this, rootView);
        iv_jiaodu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("uri", playuri);
                intent.setClass(getActivity(), SimpleVrVideoActivity.class);
                startActivity(intent);
            }
        });
        iv_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Constant.isDownload = false;
                fl.setVisibility(View.GONE);
                Constant.deleteFile();
            }
        });
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            if (Constant.isDownload == false) {
                fl.setVisibility(View.GONE);
            } else {
                fl.setVisibility(View.VISIBLE);
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }
}