package com.example.psm71.musicplayer.activity.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.psm71.musicplayer.Music.MusicControl;
import com.example.psm71.musicplayer.Music.MusicManager;
import com.example.psm71.musicplayer.Music.MusicPlay;
import com.example.psm71.musicplayer.R;
import com.example.psm71.musicplayer.activity.MainActivity;
import com.example.psm71.musicplayer.adapter.MusicAdapter;
import com.example.psm71.musicplayer.adapter.recyclerView;
import com.example.psm71.musicplayer.model.Music_info;

import java.util.ArrayList;

/**
 * Created by psm71 on 2017-08-07.
 */

public class MusicListFragment extends android.support.v4.app.Fragment
{
    ArrayList<Music_info> list;
    MusicControl listcontrol;
    MusicAdapter adapter;
    ListView listView;
    Intent Service;
    int max = 4;
    MusicControl control;
    int position;
    MusicManager manager;

    public MusicListFragment()
    {

    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        listcontrol = new MusicControl(getContext(),manager);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        RelativeLayout layout = null;
        if(position == 0)
        {
            layout = (RelativeLayout) inflater.inflate(R.layout.music_all_list,container,false);
            RecyclerView recycler = layout.findViewById(R.id.recyclerView);
            recyclerView adapter = new recyclerView(manager.getList() ,R.layout.music_list_view_box);
            adapter.setItemClick(new recyclerView.ItemClick() {
                @Override
                public void onClick(View view, int position) {
                    listcontrol.Play(position);
                }
            });
            recycler.setAdapter(adapter);
            recycler.setLayoutManager(new GridLayoutManager(getContext(),3));
            recycler.setItemAnimator(new DefaultItemAnimator());
        }
        return layout;
    }
    public void setfragment(int position)
    {
        this.position = position;
    }
    public void setList(MusicManager manager)
    {
       this.manager = manager;
    }
}
