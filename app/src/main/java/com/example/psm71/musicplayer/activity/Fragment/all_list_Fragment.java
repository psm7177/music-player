package com.example.psm71.musicplayer.activity.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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
 * Created by psm71 on 2017-08-05.
 */

public class all_list_Fragment extends  android.support.v4.app.Fragment{

    ArrayList<Music_info> musiclist;
    MusicControl listcontrol;
    MusicAdapter adapter;
    ListView listView;
    Intent Service;
    MusicControl control;

    public all_list_Fragment()
    {

    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final MusicManager manager = new MusicManager(getContext());
        manager.FileSearch();
        musiclist = manager.getList();
        Service = new Intent(getContext(), MusicPlay.class);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.music_all_list,container,false);
        listView = (ListView) layout.findViewById(R.id.all_list);
        adapter = new MusicAdapter(musiclist);
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

}
