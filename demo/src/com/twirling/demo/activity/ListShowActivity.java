package com.twirling.demo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.twirling.demo.R;
import com.twirling.demo.fragment.FragmentDownload;
import com.twirling.demo.fragment.FragmentLive;
import com.twirling.demo.fragment.FragmentOnline;
import com.twirling.player.adapter.ViewPagerAdapter;

public class ListShowActivity extends AppCompatActivity {
	private ViewPager viewPager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_list);
		//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
		//
		viewPager = (ViewPager) findViewById(R.id.vp);
		FragmentManager manager = this.getSupportFragmentManager();
		ViewPagerAdapter adapter = new ViewPagerAdapter(manager);
		adapter.addFragment(new FragmentOnline(), "在线");
		adapter.addFragment(new FragmentDownload(), "本地");
		adapter.addFragment(new FragmentLive(), "直播");
//        adapter.addFragment(new FragmentSocket(), "广播组");
		viewPager.setOffscreenPageLimit(2);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(0);
		//
		TabLayout tabLayout = (TabLayout) findViewById(R.id.tl);
		for (int i = 0; i < adapter.getCount(); i++) {
			tabLayout.addTab(tabLayout.newTab().setText(adapter.getPageTitle(i)));
		}
		tabLayout.setupWithViewPager(viewPager);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}
