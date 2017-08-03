package com.example.psm71.musicplayer;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by psm71 on 2017-08-03.
 */

class MusicManager
{

    static ArrayList <Music_info> MusicList = new ArrayList<Music_info>();
    static ArrayList<String> extension = new ArrayList<String>();
    MusicManager()
    {
        extension.add("mp3");
        extension.add("flac");
        FileSearch(MusicList, Environment.getExternalStorageDirectory().getAbsolutePath(),"");
        FileSearch(MusicList, "/storage/external_SD","");
    }
    void FileSearch(ArrayList<Music_info> MusicList, String path, String directory)
    {
        String current_path = path+directory;
        File current_file = new File(current_path);
        File[] filelist = current_file.listFiles();
        Log.d("path: ",current_path);
        for (int i = 0; i < filelist.length; i++)
        {
            File file = filelist[i];
            String extension= file.getName().substring(file.getName().lastIndexOf(".")+1,file.getName().length());
            if (file.isDirectory() && !file.isHidden())
            {
                String goto_directory = file.getName();
                FileSearch(MusicList, current_path, "/"+goto_directory);
                Log.d("Directory: ",goto_directory);
            }
            else if(this.extension.contains(extension))
            {
                String fileName = file.getName();
                Log.d("File: ",fileName);
                //MusicList.add(new Music_info(file.getPath(),""));// Music_info 생성자 추가 바람
            }
        }

        return;
    }
}
