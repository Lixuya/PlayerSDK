package com.twirling.testcard;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by 谢秋鹏 on 2016/7/13.
 */
public class WidgetMediaController extends android.widget.MediaController {


    public WidgetMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
        View mRoot = LayoutInflater.from(context).inflate(R.layout.widget_media_controller, this);
    }

    // 横竖屏切换
    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setFullMode();
        } else {
            setVerticalMode();
        }
        refreshDrawableState();
        invalidate();
    }

    // 设置竖屏
    public void setVerticalMode() {

    }

    @Override
    public void hide() {
        super.hide();
    }

    // 设置横屏
    public void setFullMode() {

    }
}
