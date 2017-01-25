package com.twirling.demo.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.twirling.demo.Constants;
import com.twirling.demo.R;
import com.twirling.demo.fragment.FragmentDownload;
import com.twirling.demo.fragment.FragmentLive;
import com.twirling.demo.fragment.FragmentOnline;
import com.twirling.player.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
	private ViewPager viewPager = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 侧边栏
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
//        adapter.addFragment(new FragmentSocket(), "远程");
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
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		MenuItem menuItem = menu.findItem(R.id.action_edit);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Constants.IS3D = !Constants.IS3D;
		Drawable icon = new IconicsDrawable(this)
				.icon(MaterialDesignIconic.Icon.gmi_3d_rotation)
				.color(Color.parseColor("#DDFFFF"))
				.sizeDp(33);
		if (Constants.IS3D) {
			item.setTitle(null);
			item.setIcon(icon);
		} else {
			item.setTitle(R.string.menu_item);
			item.setIcon(null);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}