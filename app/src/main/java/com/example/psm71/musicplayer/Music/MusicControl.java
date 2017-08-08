package com.example.psm71.musicplayer.Music;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

import com.example.psm71.musicplayer.model.Music_info;

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
    Intent Service;
    Context context;
    public MusicControl(Context context, MusicManager manger)
    {
        this.context = context;
        manger = new MusicManager(context);
        manger.FileSearch();
        Service = new Intent(context,MusicPlay.class);
        musiclist = manger.getList();
    }
    void setMode()
    {

    }
    public void Play(int position)
    {
        Service.putExtra("Music", musiclist.get(position));
        context.startService(Service);
    }
    void Stop()
    {

    }
}
