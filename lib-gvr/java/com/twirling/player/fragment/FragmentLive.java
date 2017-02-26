package com.twirling.player.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twirling.player.R;
import com.twirling.player.activity.HLSActivity;

/**
 * Created by 谢秋鹏 on 2016/5/27.
 */
public class FragmentLive extends Fragment {
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_live, null);
		CardView cv = (CardView) view.findViewById(R.id.cv);
		cv.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), HLSActivity.class);
				startActivity(intent);
			}
		});
		return view;
	}
}