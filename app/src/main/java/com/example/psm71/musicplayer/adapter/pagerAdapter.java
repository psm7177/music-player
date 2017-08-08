package com.example.psm71.musicplayer.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.psm71.musicplayer.Music.MusicManager;
import com.example.psm71.musicplayer.activity.Fragment.MusicListFragment;
import com.example.psm71.musicplayer.model.Music_info;

import java.util.ArrayList;

/**
 * Created by psm71 on 2017-08-05.
 */

public class pagerAdapter extends FragmentStatePagerAdapter
{
    ArrayList<String> pagetitle;
    ArrayList<MusicListFragment> fragments;
    MusicManager manager;

    Context context;
    public pagerAdapter(android.support.v4.app.FragmentManager fm, MusicManager musicManager)
    {
        super(fm);
        this.manager = musicManager;
        pagetitle = new ArrayList<String>();
        pagetitle.add("노래");
        pagetitle.add("아티스트");
        pagetitle.add("앨범");
        pagetitle.add("재생 목록");
    }
    @Override
    public android.support.v4.app.Fragment getItem(int position)
    {
        MusicListFragment fragment = new MusicListFragment();
        fragment.setList(manager);
        fragment.setfragment(position);
        return fragment;
    }

    @Override
    public int getCount()
    {
        return 4;
    }
    public CharSequence getPageTitle(int position) {
        return pagetitle.get(position);
    }


}