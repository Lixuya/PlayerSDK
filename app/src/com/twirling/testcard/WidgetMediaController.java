package com.twirling.testcard;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by 谢秋鹏 on 2016/7/13.
 */
public class WidgetMediaController extends android.widget.MediaController {
    public WidgetMediaController(Context context) {
        super(context);

    }

    // 横竖屏切换
    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            setFullMode();
        } else {
//            setVerticalMode(inModule);
        }
//        refreshDrawableState();
//        invalidate();
    }

    // 设置竖屏
    public void setVerticalMode(int inModule) {
        // baseModule
//        getQXDListView().setVisibility(View.INVISIBLE);
//        mProgress.setVisibility(View.GONE);
//        mKuodaButton.setImageResource(R.drawable.icon_07bofang_fangda);
//        tv_biaoqing.setVisibility(View.GONE);
//        btn_wutai.setVisibility(View.GONE);
//        lv_wutai.setVisibility(View.GONE);
//        btn_jiemudan.setVisibility(View.GONE);
//        mTanmuButton.setVisibility(View.GONE);
//        mCurrentTime.setVisibility(View.GONE);
//        mShouCangButton.setVisibility(View.GONE);
//        tv_sendmsg.setVisibility(View.GONE);
        //
        switch (inModule) {
            //LiveModule
            case 0:
                break;
            //ShowModule
            case 1:
                break;
            //PlayModule
            case 2:
//                mProgress.setEnabled(true);
//                mProgress.setVisibility(View.VISIBLE);
//                mCurrentTime.setVisibility(View.VISIBLE);
//                mShouCangButton.setVisibility(View.VISIBLE);
                break;
        }
    }

    // 设置横屏
    public void setFullMode() {
//        mKuodaButton.setImageResource(R.drawable.icon_07bofang_suoxiao);
//        tv_biaoqing.setVisibility(View.VISIBLE);
//        //直播
//        if (inModule == 0) {
//            tv_sendmsg.setVisibility(View.VISIBLE);
//            mTanmuButton.setVisibility(View.VISIBLE);
//
//            if (hasFormMenu) {
//                btn_jiemudan.setVisibility(View.VISIBLE);
//            }
//
//            if (isNeedShowWutai) {
//                btn_wutai.setVisibility(View.VISIBLE);
//            }
//        }
//        //秀场
//        if (inModule == 1) {
//            tv_sendmsg.setVisibility(View.VISIBLE);
//            mTanmuButton.setVisibility(View.VISIBLE);
//            if (hasFormMenu) {
//                btn_jiemudan.setVisibility(View.VISIBLE);
//            }
//            if (isNeedShowWutai) {
//                btn_wutai.setVisibility(View.VISIBLE);
//            }
//        }
    }
}
