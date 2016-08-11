package com.twirling.testcard;

import android.net.Uri;

import com.google.vr.sdk.widgets.video.VrVideoView;

import java.io.IOException;

public class HLSActivity extends SimpleVrVideoActivity {
    //
    private static final String TAG = HLSActivity.class.getSimpleName();

    @Override
    protected void loadUrl(VrVideoView videoWidgetView) {
        String uri = "http://yahooios2-i.akamaihd.net/hls/live/224964/iosstream/adinsert_test/master.m3u8";
        Uri fileUri = Uri.parse(uri);
        try {
            videoWidgetView.setInfoButtonEnabled(false);
            VrVideoView.Options options = new VrVideoView.Options();
            options.inputFormat = VrVideoView.Options.FORMAT_HLS;
            options.inputType = VrVideoView.Options.TYPE_MONO;
            videoWidgetView.loadVideo(fileUri, options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
