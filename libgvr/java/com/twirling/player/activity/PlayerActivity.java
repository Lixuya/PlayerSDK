package com.twirling.player.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer.HeadAnglesEvent;
import com.google.vr.sdk.widgets.video.VrVideoEventListener;
import com.google.vr.sdk.widgets.video.VrVideoView;
import com.twirling.player.Constants;
import com.twirling.player.R;
import com.twirling.player.widget.WidgetMediaController;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class PlayerActivity extends Activity {
	private static final String TAG = PlayerActivity.class.getSimpleName();
	private static final String STATE_IS_PAUSED = "isPaused";
	private static final String STATE_PROGRESS_TIME = "progressTime";
	private static final String STATE_VIDEO_DURATION = "videoDuration";
	//
	public static final int LOAD_VIDEO_STATUS_UNKNOWN = 0;
	public static final int LOAD_VIDEO_STATUS_SUCCESS = 1;
	public static final int LOAD_VIDEO_STATUS_ERROR = 2;
	//
	private int loadVideoStatus = LOAD_VIDEO_STATUS_UNKNOWN;
	private Uri fileUri;
	private VrVideoView videoWidgetView;
	private SeekBar seekBar;
	private TextView statusText;
	private boolean isPaused = false;
	private WidgetMediaController wmc;
	private ImageView iv_play;

	//
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//
		initData();
		//
		initView();
		//
		Intent videoIntent = new Intent(Intent.ACTION_VIEW);
		videoIntent.setDataAndType(fileUri, "video/*");
		onNewIntent(videoIntent);
	}

	public void initView() {
		iv_play = (ImageView) findViewById(R.id.iv_play);
		iv_play.setVisibility(View.GONE);
		//
		videoWidgetView = (VrVideoView) findViewById(R.id.video_view);
		videoWidgetView.setFullscreenButtonEnabled(false);
		videoWidgetView.setInfoButtonEnabled(false);
		videoWidgetView.setStereoModeButtonEnabled(true);
		videoWidgetView.setEventListener(new ActivityEventListener());
		//
		wmc = (WidgetMediaController) findViewById(R.id.wmc);
		wmc.setVisibility(View.GONE);
		//
		seekBar = (SeekBar) wmc.findViewById(R.id.sb);
		seekBar.setOnSeekBarChangeListener(new SeekBarListener());
		statusText = (TextView) wmc.findViewById(R.id.tv_load);
	}

	public void initData() {
		loadVideoStatus = LOAD_VIDEO_STATUS_UNKNOWN;
		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			return;
		}
		String uri = bundle.getString("VideoItem");
		Constants.is3D = bundle.getBoolean("Is3D", false);
		if (uri == null) {
			return;
		}
		fileUri = Uri.parse(uri);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		//
		if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			Log.i(TAG, "ACTION_VIEW Intent received");
			fileUri = intent.getData();
			if (fileUri == null) {
				Log.w(TAG, "No data uri specified. Use \"-d /path/filename\".");
			} else {
				Log.i(TAG, "Using file " + fileUri.toString());
			}
		} else {
			Log.i(TAG, "Intent is not ACTION_VIEW. Using the default video.");
			fileUri = null;
		}
		loadUrl(videoWidgetView);
	}

	//
	protected void loadUrl(VrVideoView videoWidgetView) {
		try {
			VrVideoView.Options options = new VrVideoView.Options();
			options.inputFormat = VrVideoView.Options.FORMAT_DEFAULT;
			if (Constants.is3D) {
				options.inputType = VrVideoView.Options.TYPE_STEREO_OVER_UNDER;
			} else {
				options.inputType = VrVideoView.Options.TYPE_MONO;
			}
			if (fileUri == null) {
				videoWidgetView.loadVideoFromAsset("testRoom1_1080Stereo.mp4", options);
			} else {
				videoWidgetView.loadVideo(fileUri, options);
			}
		} catch (IOException e) {
			// An error here is normally due to being unable to locate the file.
			loadVideoStatus = LOAD_VIDEO_STATUS_ERROR;
			// Since this is a background thread, we need to switch to the main thread to show a toast.
			videoWidgetView.post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(PlayerActivity.this, "Error opening file. ", Toast.LENGTH_LONG).show();
				}
			});
		}
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putLong(STATE_PROGRESS_TIME, videoWidgetView.getCurrentPosition());
		savedInstanceState.putLong(STATE_VIDEO_DURATION, videoWidgetView.getDuration());
		savedInstanceState.putBoolean(STATE_IS_PAUSED, isPaused);
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		long progressTime = savedInstanceState.getLong(STATE_PROGRESS_TIME);
		videoWidgetView.seekTo(progressTime);
		seekBar.setMax((int) savedInstanceState.getLong(STATE_VIDEO_DURATION));
		seekBar.setProgress((int) progressTime);
		isPaused = savedInstanceState.getBoolean(STATE_IS_PAUSED);
		if (isPaused) {
			videoWidgetView.pauseVideo();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		videoWidgetView.pauseRendering();
		isPaused = true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		videoWidgetView.resumeRendering();
		updateStatusText();
	}

	@Override
	protected void onDestroy() {
		videoWidgetView.shutdown();
		super.onDestroy();
	}

	private void togglePause() {
		if (isPaused) {
			wmc.setVisibility(View.GONE);
			iv_play.setVisibility(View.GONE);
			videoWidgetView.playVideo();
		} else {
			wmc.setVisibility(View.VISIBLE);
			iv_play.setVisibility(View.VISIBLE);
			videoWidgetView.pauseVideo();
		}
		isPaused = !isPaused;
		updateStatusText();
	}

	private void updateStatusText() {
		StringBuilder status = new StringBuilder();
		status.append(isPaused ? "暂停: " : "播放: ");
		status.append(String.format("%.2f", videoWidgetView.getCurrentPosition() / 1000f));
		status.append(" / ");
		status.append(videoWidgetView.getDuration() / 1000f);
		status.append(" 秒.");
		statusText.setText(status.toString());
	}

	private class SeekBarListener implements SeekBar.OnSeekBarChangeListener {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			if (fromUser) {
				videoWidgetView.seekTo(progress);
				updateStatusText();
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
	}

	private class ActivityEventListener extends VrVideoEventListener {
		@Override
		public void onLoadSuccess() {
			Log.i(TAG, "Sucessfully loaded video " + videoWidgetView.getDuration());
			loadVideoStatus = LOAD_VIDEO_STATUS_SUCCESS;
			seekBar.setMax((int) videoWidgetView.getDuration());
			updateStatusText();
		}

		@Override
		public void onLoadError(String errorMessage) {
			loadVideoStatus = LOAD_VIDEO_STATUS_ERROR;
			Toast.makeText(PlayerActivity.this, "Error loading video: " + errorMessage, Toast.LENGTH_LONG).show();
			Log.e(TAG, "Error loading video: " + errorMessage);
		}

		@Override
		public void onClick() {
			togglePause();
		}

		@Override
		public void onNewFrame() {
			updateStatusText();
			seekBar.setProgress((int) videoWidgetView.getCurrentPosition());
			// TODO
			float[] yawAndPitch = new float[2];
			videoWidgetView.getHeadRotation(yawAndPitch);
			EventBus.getDefault().post(new HeadAnglesEvent(yawAndPitch));
		}

		@Override
		public void onCompletion() {
			videoWidgetView.seekTo(0);
		}
	}

	public int getLoadVideoStatus() {
		return loadVideoStatus;
	}
}