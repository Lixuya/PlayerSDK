package com.twirling.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.twirling.demo.R;
import com.twirling.player.Constants;
import com.twirling.player.activity.SimpleVrVideoActivity;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.twirling.demo.Constants.FILE_PATH;
import static com.twirling.demo.Constants.getFile;

/**
 * Created by 谢秋鹏 on 2016/5/27.
 */
public class FragmentOnline extends Fragment {
    @BindView(R.id.button)
    Button load;
    @BindView(R.id.button2)
    Button play;
    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
    //
    private String playuri = "http://www.twirlingvr.com/App_Media/videos/_tianjin_jiaoyu_1920x1080_5mb_a.mp4";
    public int contentLength;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.play_load, null);
        ButterKnife.bind(this, view);
        load.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        download(playuri);
                    }
                }).start();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("VideoItem", playuri);
                Constants.is3D = false;
                //设置跳转新的activity，参数（当前对象，跳转到的class）
                intent.setClass(getActivity(), SimpleVrVideoActivity.class);
                //启动Activity 没有返回
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            if (getFile() == null) {
                mPbLoading.setProgress(0);
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    public void download(String _urlStr) {
        try {
            // 构造URL
            URL url = new URL(_urlStr);
            // 打开连接
            URLConnection con = url.openConnection();
            //获得文件的长度
            contentLength = con.getContentLength();
            mPbLoading.setMax(contentLength);
            System.out.println("长度 :" + contentLength);
            // 输入流
            InputStream is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;

            // 输出的文件流
            FileOutputStream os = new FileOutputStream(FILE_PATH);

            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
                mPbLoading.setProgress((int) getFile().length());
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
