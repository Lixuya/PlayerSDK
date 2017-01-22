package com.twirling.player.activity;

import android.net.Uri;
import android.widget.Toast;

import com.google.vr.sdk.widgets.video.VrVideoView;

import java.io.IOException;

public class HLSActivity extends VRPlayerActivity {
	//
	private static final String TAG = HLSActivity.class.getSimpleName();

	@Override
	protected void loadUrl(VrVideoView videoWidgetView) {
		String uri = "http://yahooios2-i.akamaihd.net/hls/live/224964/iosstream/adinsert_test/master.m3u8";
		uri = "http://2997.liveplay.myqcloud.com/2997_9e53593fde2c11e691eae435c87f075e.m3u8";
		uri = "http://2997.liveplay.myqcloud.com/2997_94e96d2ede2c11e691eae435c87f075e.m3u8";
		uri = "http://2997.liveplay.myqcloud.com/2997_cf282c595e0911e6a2cba4dcbef5e35a_550.m3u8";
		Uri fileUri = Uri.parse(uri);
		try {
			videoWidgetView.setInfoButtonEnabled(false);
			VrVideoView.Options options = new VrVideoView.Options();
			options.inputFormat = VrVideoView.Options.FORMAT_HLS;
			options.inputType = VrVideoView.Options.TYPE_MONO;
			videoWidgetView.loadVideo(fileUri, options);
		} catch (IOException e) {
			e.printStackTrace();
			// Since this is a background thread, we need to switch to the main thread to show a toast.
			videoWidgetView.post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(HLSActivity.this, "Error opening file. ", Toast.LENGTH_LONG).show();
				}
			});
		}
	}
}
