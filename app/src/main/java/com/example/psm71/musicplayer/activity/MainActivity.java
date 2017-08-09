package com.example.psm71.musicplayer.activity;

<<<<<<< HEAD
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
=======
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
>>>>>>> sky123

import com.example.psm71.musicplayer.Music.MusicControl;
import com.example.psm71.musicplayer.Music.MusicManager;
import com.example.psm71.musicplayer.Music.MusicPlay;
import com.example.psm71.musicplayer.R;
<<<<<<< HEAD
import com.example.psm71.musicplayer.adapter.pagerAdapter;
import com.example.psm71.musicplayer.model.Music_info;
import com.example.psm71.musicplayer.activity.Fragment.*;
=======
import com.example.psm71.musicplayer.model.Music_info;
import com.example.psm71.musicplayer.utils.Config;

import java.util.ArrayList;
>>>>>>> sky123

import java.util.ArrayList;

<<<<<<< HEAD
public class MainActivity extends AppCompatActivity {

    DrawerLayout layout;
    Intent Service;
    public static MusicControl control;
    TabLayout tabLayout;
    ArrayList<String> pagetitle;
=======
    ListView listView;
    Adapter adapter;
    ArrayList<Music_info> musiclist;

>>>>>>> sky123

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
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
=======
        final MusicManager manager = new MusicManager(this);
        manager.FileSearch();

        listView = findViewById(R.id.music_listview);
        musiclist = manager.getList();
        adapter = new Adapter(musiclist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(parent.getContext(), PlayerControllerActivity.class);
                intent.putExtra(Config.AllMusicList, musiclist);
                intent.putExtra(Config.startPosition, position);
                startActivity(intent);

            }
        });
>>>>>>> sky123

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}
