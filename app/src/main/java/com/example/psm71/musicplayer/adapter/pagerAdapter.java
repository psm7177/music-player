package com.example.psm71.musicplayer.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.psm71.musicplayer.Music.MusicControl;
import com.example.psm71.musicplayer.Music.MusicManager;
import com.example.psm71.musicplayer.activity.Fragment.MusicListFragment;
import com.example.psm71.musicplayer.model.Music_info;

import java.util.ArrayList;

/**
 * Created by psm71 on 2017-08-05.
 */

public class pagerAdapter extends FragmentStatePagerAdapter
{
    MusicControl control;
    int viewCount;
    public pagerAdapter(android.support.v4.app.FragmentManager fm, MusicControl control, int viewCount)
    {
        super(fm);
        this.control = control;
        this.viewCount = viewCount;
    }
    @Override
    public android.support.v4.app.Fragment getItem(int position)
    {
        MusicListFragment fragment = new MusicListFragment();
        fragment.setList(control);
        fragment.setfragment(position);
        return fragment;
    }

    @Override
    public int getCount()
    {
        return viewCount;
    }

}