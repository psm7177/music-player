package com.example.psm71.musicplayer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.psm71.musicplayer.Adapter;
import com.example.psm71.musicplayer.MusicManager;
import com.example.psm71.musicplayer.R;

public class MainActivity extends Activity {

    ListView listView;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MusicManager manager = new MusicManager(this);
        manager.FileSearch();

        listView = findViewById(R.id.music_listview);
        adapter = new Adapter(manager.getList());
        listView.setAdapter(adapter);

    }

}
