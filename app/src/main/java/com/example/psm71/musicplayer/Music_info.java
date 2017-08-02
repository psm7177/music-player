package com.example.psm71.musicplayer;


public class Music_info {
    String path;
    String name;
    String singer;
    String lyricist;
    String composer;
    // album cover image
    Music_info()
    {

    }
    Music_info(String path, String name)
    {
        this.path = path;
        this.name = name;
    }


    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }
}
