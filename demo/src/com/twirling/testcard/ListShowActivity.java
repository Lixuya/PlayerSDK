package com.twirling.testcard;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

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
        adapter.addFragment(new FragmentDownload(), "本地");
        adapter.addFragment(new FragmentLive(), "直播");
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        //
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl);
        tabLayout.addTab(tabLayout.newTab().setText(adapter.getPageTitle(0)));
        tabLayout.addTab(tabLayout.newTab().setText(adapter.getPageTitle(1)));
        tabLayout.addTab(tabLayout.newTab().setText(adapter.getPageTitle(2)));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
