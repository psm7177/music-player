package com.example.psm71.musicplayer.activity;

/**
 * Created by ssjj5 on 2017-08-05.
 */

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.psm71.musicplayer.Music.MusicService;
import com.example.psm71.musicplayer.R;
import com.example.psm71.musicplayer.model.MediaType;
import com.example.psm71.musicplayer.model.Music_info;
import com.example.psm71.musicplayer.utils.Config;
import com.example.psm71.musicplayer.Music.MusicService.Music_Binder;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;

import static com.example.psm71.musicplayer.Music.MusicService.ACTION_PLAY;
import static com.example.psm71.musicplayer.Music.MusicService.ACTION_START_FOREGROUND;

/**
 *
 * 플레이어 컨트롤러
 * 여기서 서비스 연결
 * 재생은 서비스에서
 *
 */

public class PlayerControllerActivity extends AppCompatActivity implements Runnable, View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private Button play_bt;
    private Button pause_bt;
    private Button prev_bt;
    private Button next_bt;

    private TextView current_time_text;
    private TextView end_time_text;

    private SeekBar seekBar;

    private ArrayList<Music_info> musicList = null;
    private int position = 0;

    private MusicService musicService; //서비스 객체

    boolean isService = false; //서비스 생존 여부

    private Thread thread;
    private Handler handler = new Handler();
    private Formatter mFormatter;
    private StringBuilder mFormatBuilder;

    private final String TAG = PlayerControllerActivity.class.getSimpleName();

    ServiceConnection conn = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 서비스와 연결되었을 때 호출되는 메서드
            // 서비스 객체를 전역변수로 저장
            Music_Binder mb = (Music_Binder) service;
            musicService = mb.getService(); // 서비스가 제공하는 메소드 호출하여
            musicService.registerCallback(mCallback);
            // 서비스쪽 객체를 전달받을수 있슴
            isService = true;

        }
        public void onServiceDisconnected(ComponentName name) {
            // 서비스와 연결이 끊겼을 때 호출되는 메서드
            isService = false;
        }
    };

    private MusicService.ICallback mCallback = new MusicService.ICallback() {
        public void preparedPlayer() {
            int duration = musicList.get(position).getDuration(); //총 재생시간을 가져옴
            seekBar.setMax(duration);
            end_time_text.setText(stringForTime(duration));
            //thread.start(); <=== 쓰레드 오류 남
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_layout);

        musicList = (ArrayList<Music_info>) getIntent().getSerializableExtra(Config.AllMusicList);
        position = getIntent().getIntExtra(Config.startPosition, 0);

        init();
        startService();
    }

    private void init() {
        play_bt = (Button) findViewById(R.id.play_bt);
        pause_bt = (Button) findViewById(R.id.pause_bt);
        prev_bt = (Button) findViewById(R.id.prev_bt);
        next_bt = (Button) findViewById(R.id.next_bt);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        current_time_text = (TextView) findViewById(R.id.time_current);
        end_time_text = (TextView) findViewById(R.id.end_time);

        play_bt.setOnClickListener(this);
        pause_bt.setOnClickListener(this);
        prev_bt.setOnClickListener(this);
        next_bt.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);

        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    }

    private void startService() {
        thread = new Thread(null, PlayerControllerActivity.this);

        Intent service = new Intent(this, MusicService.class);
        service.setAction(ACTION_PLAY);
        service.putExtra(Config.TYPE, MediaType.MEDIA_TYPE_MUSIC);
        service.putExtra(Config.AllMusicList, musicList);
        service.putExtra(Config.startPosition, position);
        bindService(service, conn, Context.BIND_AUTO_CREATE);
        startService(service);
    }

    private String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0)
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        else
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.play_bt:
                musicService.start();
                break;
            case R.id.pause_bt:
                musicService.pause();
                break;
            case R.id.prev_bt:
                if (position > 0) {
                    position--;
                }

                musicService.prevSong();
                break;
            case R.id.next_bt:
                if (position < musicList.size() - 1) {
                    position++;
                }

                musicService.nextSong();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        musicService.seekTo(progress);
        musicService.setCurrentPosition(progress);
    }

    //1초 마다 seekBar 에 현재 재생시간을 업데이트 해줌

    private Runnable updateBar = new Runnable() {

        public void run() {
            int currentPosition = musicService.getCurrentPosition();
            seekBar.setProgress(currentPosition);
            current_time_text.setText(stringForTime(currentPosition));
        }
    };

    @Override
    public void run() {
        while(true) {
            handler.post(updateBar);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        /*try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        unbindService(conn);
    }
}
