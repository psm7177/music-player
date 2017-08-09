package com.example.psm71.musicplayer.Music;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

import com.example.psm71.musicplayer.model.Music_info;

/**
 * Created by psm71 on 2017-08-03.
 */

public class MusicManager {

    private static final String TAG = MusicManager.class.getSimpleName();

    static ArrayList<Music_info> MusicList = new ArrayList<>();
    private Context context;

    public MusicManager(Context context) {
        this.context = context;

        //FileSearch(Adapter, "/storage/external_SD","");

    }

    public void FileSearch() {

        /*String current_path = path+directory;
        File current_file = new File(current_path);
        File[] filelist = current_file.listFiles(new Mp3Filter());
        Log.e(TAG, "" + filelist.length);
        for (int i = 0; i < filelist.length; i++)
        {
            File file = filelist[i];
            Log.d(TAG, file.getName());
            String extension= file.getName().substring(file.getName().lastIndexOf(".")+1,file.getName().length());
            if (file.isDirectory() && !file.isHidden())
            {
                String goto_directory = file.getName();
                FileSearch(Adapter, current_path, "/"+goto_directory);
                Log.d("Directory: ",goto_directory);
            }
            else if(this.extension.contains(extension))
            {
                String fileName = file.getName();
                Log.d("File: ",fileName);
                //Adapter.add(new Music_info(file.getPath(),""));// Music_info 생성자 추가 바람
            }
        }*/

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST
        };

        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null);

        while (cursor.moveToNext()) {
            int Music_ID = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            //String Music_ID = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            int Album_ID = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

            String path = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "" + Music_ID).toString();
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String album_url = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), Album_ID).toString();
            String singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));

            Log.e(TAG, "path : " + path + "\n title : " + title + "\n singer : " + singer
                    + "\n Album_url : " + album_url);

            MusicList.add(new Music_info(path, title, singer, album_url));
        }
    }

    public ArrayList<Music_info> getList() {
        return MusicList;
    }
}
