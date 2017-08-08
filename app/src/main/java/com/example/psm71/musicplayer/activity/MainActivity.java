package com.example.psm71.musicplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TableLayout;

import com.example.psm71.musicplayer.Music.MusicControl;
import com.example.psm71.musicplayer.Music.MusicManager;
import com.example.psm71.musicplayer.Music.MusicPlay;
import com.example.psm71.musicplayer.R;
import com.example.psm71.musicplayer.adapter.pagerAdapter;
import com.example.psm71.musicplayer.model.Music_info;
import com.example.psm71.musicplayer.activity.Fragment.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DrawerLayout layout;
    Intent Service;
    public static MusicControl control;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager pagerView =(ViewPager) findViewById(R.id.viewpaper);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        final MusicManager manager = new MusicManager(getApplicationContext());
        manager.FileSearch();
        pagerAdapter adapter = new pagerAdapter(getSupportFragmentManager(),manager);
        pagerView.setAdapter(adapter);
        pagerView.setCurrentItem(0);
        pagerView.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setupWithViewPager(pagerView);

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
