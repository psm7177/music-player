package com.example.psm71.musicplayer.Music;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.psm71.musicplayer.R;
import com.example.psm71.musicplayer.activity.PlayerControllerActivity;
import com.example.psm71.musicplayer.model.MediaType;
import com.example.psm71.musicplayer.model.Music_info;
import com.example.psm71.musicplayer.receiver.MediaButtonIntentReceiver;
import com.example.psm71.musicplayer.utils.Config;

import java.io.IOException;
import java.util.ArrayList;

public class MusicService extends Service implements Runnable, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    private final String TAG = MusicService.class.getSimpleName();

    public static final String ACTION_PLAY = "action_play";
    public static final String ACTION_PAUSE = "action_pause";
    public static final String ACTION_PREVIOUS = "action_pr";
    public static final String ACTION_NEXT = "action_next";
    public static final String ACTION_CLOSE = "action_close";
    public static final String ACTION_START_FOREGROUND = "action_start_foreground";

    private MediaPlayer mediaPlayer = null;
    private MediaSessionCompat mSession;
    private MediaControllerCompat mController;

    private NotificationCompat.Builder builder = null;

    private Thread thread;

    private Context context;

    private ArrayList<Music_info> musicList = null;

    int CurrentPosition = 0;
    int CurrentSongIndex = 0;

    boolean isStopService = false;

    IBinder binder = new Music_Binder();

    public class Music_Binder extends Binder {
        public MusicService getService() { // 서비스 객체를 리턴
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public interface ICallback {
        void preparedPlayer(); //플레이어가 준비가 다 되었을때 액티비티에서 호출될 함수
    }

    private ICallback mCallback;

    //액티비티에서 콜백 함수를 등록하기 위함.
    public void registerCallback(ICallback cb) {
        mCallback = cb;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
        }

        initMediaSessions();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void handleIntent(Intent intent) {
        if (intent == null || intent.getAction() == null)
            return;
        String action = intent.getAction();
        if (action.equalsIgnoreCase(ACTION_PLAY)) {
            handleMedia(intent);
            mController.getTransportControls().play();
        } else if (action.equalsIgnoreCase(ACTION_PAUSE)) {
            mController.getTransportControls().pause();
        } else if (action.equalsIgnoreCase(ACTION_PREVIOUS)) {
            mController.getTransportControls().skipToPrevious();
        } else if (action.equalsIgnoreCase(ACTION_NEXT)) {
            mController.getTransportControls().skipToNext();
        } else if (action.equalsIgnoreCase(ACTION_CLOSE)) {
            mController.getTransportControls().stop();
        }
    }

    private void handleMedia(Intent intent) {
        MediaType type = MediaType.MEDIA_NONE;
        if (intent.getSerializableExtra(Config.TYPE) != null)
            type = (MediaType) intent.getSerializableExtra(Config.TYPE);

        switch (type) {
            case MEDIA_NONE:
                mediaPlayer.start();
                break;
            case MEDIA_TYPE_MUSIC:
                musicList = (ArrayList<Music_info>) intent.getSerializableExtra(Config.AllMusicList);
                int startPosition = intent.getIntExtra(Config.startPosition, 0);
                CurrentSongIndex = startPosition;

                initPlayer();
                buildNotification(generateAction(R.drawable.ic_pause_teal_800_24dp, "일시정지", ACTION_PAUSE));
                startForeground(1, builder.build());
                break;
            case MEDIA_TYPE_PLAYLIST:
                break;
        }
    }

    private void initMediaSessions() {
        //PendingIntent buttonReceiverIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(Intent.ACTION_MEDIA_BUTTON), PendingIntent.FLAG_UPDATE_CURRENT);
        ComponentName mediaButtonReceiver = new ComponentName(getApplicationContext(), MediaButtonIntentReceiver.class);
        //mSession = new MediaSessionCompat(getApplicationContext(), "simple player session", null, buttonReceiverIntent);
        mSession = new MediaSessionCompat(getApplicationContext(), "simple", mediaButtonReceiver, null);

        try {
            mController = new MediaControllerCompat(getApplicationContext(), mSession.getSessionToken());

            mSession.setCallback(
                    new MediaSessionCompat.Callback() {
                        @Override
                        public void onPlay() {
                            super.onPlay();
                            buildNotification(generateAction(R.drawable.ic_pause_teal_800_24dp, "일시정지", ACTION_PAUSE));
                        }

                        @Override
                        public void onPause() {
                            super.onPause();
                            pause();
                            buildNotification(generateAction(R.drawable.ic_play_arrow_teal_800_24dp, "재생", ACTION_PLAY));
                        }

                        @Override
                        public void onSkipToNext() {
                            super.onSkipToNext();
                            nextSong();
                        }

                        @Override
                        public void onSkipToPrevious() {
                            super.onSkipToPrevious();
                            prevSong();
                        }

                        @Override
                        public void onStop() {
                            super.onStop();

                            isStopService = true;

                            if (thread != null && thread.isAlive())
                                thread.interrupt(); //쓰레드를 종료시켜줌

                            stopSelf();
                            stopForeground(true);
                        }
                    }
            );
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }

    private void initPlayer() {
        try {
            if (thread != null && thread.isAlive())
                thread.interrupt();

            mediaPlayer.reset();
            mediaPlayer.setDataSource(context, Uri.parse(musicList.get(CurrentSongIndex).getPath()));
            mediaPlayer.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playerRelease() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void setCurrentPosition(int currentPosition) {
        CurrentPosition = currentPosition;
    }

    public int getCurrentPosition() {
        return CurrentPosition;
    }

    public void start() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void seekTo(int i) {
        mediaPlayer.seekTo(i);

    }

    public void prevSong() {
        if (CurrentSongIndex > 0) {
            CurrentSongIndex--;
            initPlayer();
            buildNotification(generateAction(R.drawable.ic_pause_teal_800_24dp, "일시정지", ACTION_PAUSE));
        }
    }

    public void nextSong() {
        if (CurrentSongIndex < musicList.size() - 1) {
            CurrentSongIndex++;
            initPlayer();
            buildNotification(generateAction(R.drawable.ic_pause_teal_800_24dp, "일시정지", ACTION_PAUSE));
        }
    }

    //포그라운드에서 보여줄 노티피케이션

    private NotificationCompat.Action generateAction(int icon, String title, String intentAction) {
        Intent intent = new Intent(context, MusicService.class);
        intent.setAction(intentAction);
        PendingIntent pendingIntent = PendingIntent.getService(context, 1, intent, 0);
        return new NotificationCompat.Action.Builder(icon, title, pendingIntent).build();
    }

    private void buildNotification(NotificationCompat.Action action) {
        final NotificationCompat.MediaStyle style = new NotificationCompat.MediaStyle().setShowCancelButton(true);

        Intent intent = new Intent(this, PlayerControllerActivity.class);
        intent.putExtra(Config.AllMusicList, musicList);
        intent.putExtra(Config.startPosition, CurrentSongIndex);
        PendingIntent clickPendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        String title = musicList.get(CurrentSongIndex).getName();
        String artist = musicList.get(CurrentSongIndex).getArtist();
        String album = musicList.get(CurrentSongIndex).getAlbum();
        String album_img = musicList.get(CurrentSongIndex).getAlbum_img();

        if (artist == null)
            artist = getApplicationContext().getString(R.string.no_artist);
        if (album == null)
            album = getApplicationContext().getString(R.string.no_alumb);

        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_headset_teal_800_24dp);
        builder.setContentTitle(title);
        builder.setContentInfo((CurrentSongIndex + 1) + "/" + musicList.size());
        builder.setContentText(artist);
        builder.setSubText(album);
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        builder.setShowWhen(false);
        builder.setOngoing(true);
        builder.setContentIntent(clickPendingIntent);
        builder.setStyle(style);


        builder.addAction(generateAction(R.drawable.ic_skip_previous_teal_800_24dp, "전", ACTION_PREVIOUS));
        builder.addAction(action);
        builder.addAction(generateAction(R.drawable.ic_skip_next_teal_800_24dp, "다음", ACTION_NEXT));
        builder.addAction(generateAction(R.drawable.ic_close_teal_800_24dp, "닫기", ACTION_CLOSE));
        style.setShowActionsInCompactView(0, 1, 2, 3);

        Drawable errorImg = context.getResources().getDrawable(R.drawable.ic_headset_teal_800_24dp);
        Glide.with(this).asBitmap().load(album_img).into(target).onLoadFailed(errorImg);
    }

    private SimpleTarget target = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
            builder.setLargeIcon(bitmap);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, builder.build());
        }

        @Override
        public void onLoadFailed(@Nullable Drawable errorDrawable) {
            super.onLoadFailed(errorDrawable);

            //이미지가 없을 때 호출

            if (errorDrawable != null) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) errorDrawable;
                Bitmap bitmap = bitmapDrawable.getBitmap();

                builder.setLargeIcon(bitmap);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, builder.build());
            }

        }
    };

    //1초마다 현재 영상 재생 위치를 가지고옴
    @Override
    public void run() {
        while (!isStopService) { //서비스가 종료되면 쓰레드 종료
            setCurrentPosition(mediaPlayer.getCurrentPosition());
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        thread = new Thread(null, this);
        thread.start();

        mCallback.preparedPlayer();

        mediaPlayer.start();
    }

    //음악 재생이 완료 되었을때
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
    }

    //재생시 애러가 발생하였을때
    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

}
