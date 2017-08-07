package com.example.psm71.musicplayer.activity.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.psm71.musicplayer.Music.MusicControl;
import com.example.psm71.musicplayer.Music.MusicManager;
import com.example.psm71.musicplayer.Music.MusicPlay;
import com.example.psm71.musicplayer.R;
import com.example.psm71.musicplayer.activity.MainActivity;
import com.example.psm71.musicplayer.adapter.MusicAdapter;
import com.example.psm71.musicplayer.model.Music_info;

import java.util.ArrayList;

/**
 * Created by psm71 on 2017-08-07.
 */

public class MusicListFragment extends android.support.v4.app.Fragment
{
    ArrayList<ArrayList<Music_info>> musiclist;
    MusicControl listcontrol;
    MusicAdapter adapter;
    ListView listView;
    Intent Service;
    int max = 4;
    int position;
    MusicControl control;

    public MusicListFragment()
    {

    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final MusicManager manager = new MusicManager(getContext());
        manager.FileSearch();
        musiclist = new ArrayList<ArrayList<Music_info>>();

        musiclist.add(manager.getList());
        musiclist.add(new ArrayList<Music_info>());
        musiclist.add(new ArrayList<Music_info>());
        musiclist.add(new ArrayList<Music_info>());

        Service = new Intent(getContext(), MusicPlay.class);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.music_all_list,container,false);
        listView = (ListView) layout.findViewById(R.id.all_list);
        adapter = new MusicAdapter(musiclist.get(position));
        listView.setAdapter(adapter);
        listcontrol = MainActivity.control;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                listcontrol.Play(position);
            }
        });
        return layout;
    }
    public void setfragment(int position)
    {
        this.position = position;
    }
}
