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
        this(null,null,null,null,null);
    }
    Music_info(String path, String name)
    {
        this(path, name, null, null, null);
    }
    Music_info(String path, String name, String singer, String lyricist, String composer)
    {
        this.path = path;
        this.name = name;
        this.singer = singer;
        this.lyricist = lyricist;
        this.composer = composer;
    }


    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }
}
