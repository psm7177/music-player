package com.example.psm71.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import com.bumptech.glide.load.DataSource;
import com.example.psm71.musicplayer.model.Music_info;

import java.io.File;
import java.io.IOException;

public class MusicPlay extends Service {
    MediaPlayer music;
    Music_info MusicDTO;
    public MusicPlay() {

    }
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate() {
        android.util.Log.i("테스트", "onCreate()호출");
        super.onCreate();
        music = new MediaPlayer();
    }

    @Override
    public void onDestroy() {
        android.util.Log.i("테스트", "onDestory()호출");
        music.stop();
        super.onDestroy();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        android.util.Log.i("테스트", "onStartCommand() 호출");
        MusicDTO = (Music_info) intent.getSerializableExtra("Music");
        try {
            Log.e("path: ",MusicDTO.getPath());
            File mp3 = new File(MusicDTO.getPath());
            if(mp3.exists()){
                Log.i("exist: " ,"true");
            music.setDataSource(MusicDTO.getPath());
            music.prepare();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        music.start();
        return super.onStartCommand(intent, flags, startId);
    }
}
