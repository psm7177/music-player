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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pagerView =(ViewPager) findViewById(R.id.viewpaper);
        pagerView.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        pagerView.setCurrentItem(0);
        control = new MusicControl(getApplicationContext());
    }


}
