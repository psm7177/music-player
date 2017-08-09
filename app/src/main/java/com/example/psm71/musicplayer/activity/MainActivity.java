package com.example.psm71.musicplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import com.example.psm71.musicplayer.Music.MusicControl;
import com.example.psm71.musicplayer.R;
import com.example.psm71.musicplayer.adapter.pagerAdapter;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    DrawerLayout layout;
    Intent Service;
    public static MusicControl control;
    TabLayout tabLayout;
    ArrayList<String> pagetitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager pagerView =(ViewPager) findViewById(R.id.viewpaper);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        final MusicControl control = new MusicControl(getApplicationContext());

        tabLayout.addTab(tabLayout.newTab().setText("노래"));
        tabLayout.addTab(tabLayout.newTab().setText("아티스트"));
        tabLayout.addTab(tabLayout.newTab().setText("앨범"));
        tabLayout.addTab(tabLayout.newTab().setText("재생목록"));

        pagerAdapter adapter = new pagerAdapter(getSupportFragmentManager(),control,tabLayout.getTabCount());
        pagerView.setAdapter(adapter);
        pagerView.setCurrentItem(0);
        pagerView.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagerView.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}
