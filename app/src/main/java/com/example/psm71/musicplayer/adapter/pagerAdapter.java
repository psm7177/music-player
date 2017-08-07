package com.example.psm71.musicplayer.adapter;

import android.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.psm71.musicplayer.activity.Fragment.MusicListFragment;

/**
 * Created by psm71 on 2017-08-05.
 */

public class pagerAdapter extends FragmentStatePagerAdapter
{
    public pagerAdapter(android.support.v4.app.FragmentManager fm)
    {
        super(fm);
    }
    @Override
    public android.support.v4.app.Fragment getItem(int position)
    {
        MusicListFragment fragment= new MusicListFragment();
        fragment.setfragment(position);
        return fragment;
    }
    @Override
    public int getCount()
    {
        return 4;
    }
}