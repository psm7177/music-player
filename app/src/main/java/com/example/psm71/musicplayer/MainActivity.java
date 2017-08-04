package com.example.psm71.musicplayer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {

    ListView listView;
    MusicList adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new MusicList();
        MusicManager manager = new MusicManager(adapter);
        listView = (ListView) findViewById(R.id.music_listview);

    }

}
