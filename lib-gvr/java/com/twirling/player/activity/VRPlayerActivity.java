package com.twirling.player.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer.HeadAnglesEvent;
import com.google.vr.sdk.widgets.video.VrVideoEventListener;
import com.google.vr.sdk.widgets.video.VrVideoView;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.twirling.lib_cobb.util.TimeUtil;
import com.twirling.player.R;
import com.twirling.player.widget.WidgetMediaController;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class VRPlayerActivity extends Activity {
	private static final String TAG = VRPlayerActivity.class.getSimpleName();
	private static final String STATE_IS_PAUSED = "isPaused";
	private static final String STATE_PROGRESS_TIME = "progressTime";
	private static final String STATE_VIDEO_DURATION = "videoDuration";
	//
	public static final int LOAD_VIDEO_STATUS_UNKNOWN = 0;
	public static final int LOAD_VIDEO_STATUS_SUCCESS = 1;
	public static final int LOAD_VIDEO_STATUS_ERROR = 2;
	//
	private int loadVideoStatus = LOAD_VIDEO_STATUS_UNKNOWN;
	private Uri fileUri = null;
	private VrVideoView videoWidgetView = null;
	private SeekBar seekBar = null;
	private TextView statusText = null;
	private boolean isPaused = false;
	private WidgetMediaController wmc = null;
	private ImageView iv_play = null;
	private boolean asset = false;
	private boolean stereo = false;

	//
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vr_player);
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
		Drawable iconPlay = new IconicsDrawable(this)
				.icon(FontAwesome.Icon.faw_play_circle)
				.color(Color.parseColor("#B0FFFFFF"))
				.sizeDp(64);
		iv_play.setImageDrawable(iconPlay);
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
		wmc.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				togglePause();
				return true;
			}
		});
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
		stereo = bundle.getBoolean("stereo", false);
		asset = bundle.getBoolean("asset", false);
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
			if (stereo) {
				options.inputType = VrVideoView.Options.TYPE_STEREO_OVER_UNDER;
			} else {
				options.inputType = VrVideoView.Options.TYPE_MONO;
			}
			if (asset) {
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
					Toast.makeText(VRPlayerActivity.this, "Error opening file. ", Toast.LENGTH_LONG).show();
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
			videoWidgetView.seekTo(videoWidgetView.getCurrentPosition());
		}
		isPaused = !isPaused;
		updateStatusText();
	}

	private void updateStatusText() {
		StringBuilder status = new StringBuilder();
		status.append(isPaused ? "暂停: " : "播放: ");
		String currentTime = TimeUtil.float2time(videoWidgetView.getCurrentPosition() / 1000f);
		status.append(currentTime);
		status.append(" / ");
		String wholeTime = TimeUtil.float2time(videoWidgetView.getDuration() / 1000f);
		status.append(wholeTime);
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
			Toast.makeText(VRPlayerActivity.this, "Error loading video: " + errorMessage, Toast.LENGTH_LONG).show();
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
			float[] yawAndPitch = new float[2];
			videoWidgetView.getHeadRotation(yawAndPitch);
			//EventBus.getDefault().post(new HeadAnglesEvent(yawAndPitch));
			HeadAnglesEvent.m_HeadAnglesEvent.HeadAnglesEvent(yawAndPitch);
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