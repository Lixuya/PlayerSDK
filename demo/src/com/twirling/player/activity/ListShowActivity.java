package com.twirling.player.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.twirling.player.R;
import com.twirling.player.fragment.FragmentDownload;
import com.twirling.player.fragment.FragmentLive;
import com.twirling.player.fragment.FragmentOnline;
import com.twirling.player.fragment.FragmentSocket;
import com.twirling.www.libgvr.adapter.ViewPagerAdapter;

public class ListShowActivity extends AppCompatActivity {
    public static String playuri;
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
        adapter.addFragment(new FragmentLive(), "直播");
        adapter.addFragment(new FragmentSocket(), "广播组");
        adapter.addFragment(new FragmentDownload(), "本地");
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        //
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl);
        tabLayout.addTab(tabLayout.newTab().setText(adapter.getPageTitle(0)));
        tabLayout.addTab(tabLayout.newTab().setText(adapter.getPageTitle(1)));
        tabLayout.addTab(tabLayout.newTab().setText(adapter.getPageTitle(2)));
        tabLayout.addTab(tabLayout.newTab().setText(adapter.getPageTitle(3)));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
