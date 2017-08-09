package com.example.psm71.musicplayer.Music;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

import com.example.psm71.musicplayer.activity.PlayerControllerActivity;
import com.example.psm71.musicplayer.model.Music_info;
import com.example.psm71.musicplayer.utils.Config;

import java.util.ArrayList;

/**
 * Created by psm71 on 2017-08-05.
 */

public class MusicControl
{
    String mode;//OneLoop:한곡 반복
                //One: 한곡
                //All: 전체
                //AllLoop: 전체 반복
    ArrayList<Music_info> musiclist;
    Context context;
    MusicManager manager;
    public MusicControl(Context context)
    {
        this.context = context;

        manager = new MusicManager(context);
        manager.FileSearch();
        musiclist = manager.getList();
    }
    void setMode()
    {

    }
    public void Play(int position)
    {
        Intent intent = new Intent(context, PlayerControllerActivity.class);
        intent.putExtra(Config.AllMusicList, musiclist);
        intent.putExtra(Config.startPosition, position);
    }
    void Stop()
    {

    }
    public MusicManager getmanager()
    {
        return manager;
    }
}
