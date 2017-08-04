package com.example.psm71.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

public class MusicPlay extends Service {
    MediaPlayer music;
    String path;
    public MusicPlay() {

    }
    public MusicPlay(String path)
    {
        this.path = path;
    }
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate() {
        android.util.Log.i("테스트", "onCreate()호출");
        super.onCreate();
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
        try {
            music.setDataSource(path);
            music.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        music.start();
        return super.onStartCommand(intent, flags, startId);
    }
}
