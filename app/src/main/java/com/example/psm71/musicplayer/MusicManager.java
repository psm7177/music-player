package com.example.psm71.musicplayer;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by psm71 on 2017-08-03.
 */

class MusicManager
{
    ArrayList <Music_info> MusicList = new ArrayList<Music_info>();

    static  void FileSearch(ArrayList<Music_info> MusicList, String path, String directory)
    {
        String current_path = path+"/"+directory;
        File current_file = new File(current_path);
        File[] list = current_file.listFiles();
        for (int i = 0; i < list.length; i++)
        {
            File file = list[i];
            if (file.isDirectory())
            {
                String goto_directory = file.getName();
                FileSearch(MusicList, current_path, goto_directory);
            }
            else
            {
                MusicList.add(new Music_info(file.getPath(),""));// Music_info 생성자 추가 바람
            }
        }
        return;
    }
}
