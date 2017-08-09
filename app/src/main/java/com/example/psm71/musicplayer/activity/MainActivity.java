package com.example.psm71.musicplayer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.psm71.musicplayer.Adapter;
import com.example.psm71.musicplayer.MusicManager;
import com.example.psm71.musicplayer.R;
import com.example.psm71.musicplayer.model.Music_info;
import com.example.psm71.musicplayer.utils.Config;

import java.util.ArrayList;

public class MainActivity extends Activity {

    ListView listView;
    Adapter adapter;
    ArrayList<Music_info> musiclist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }

}
