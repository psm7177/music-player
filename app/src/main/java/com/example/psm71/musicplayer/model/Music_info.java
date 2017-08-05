package com.example.psm71.musicplayer.model;


import android.net.Uri;

import java.io.Serializable;

public class Music_info implements Serializable{
    private String path;
    private String name;
    private String singer;
    private String album;

    // album cover image

    public Music_info(String path, String name, String singer, String album)
    {
        this.path = path;
        this.name = name;
        this.singer = singer;
        this.album = album;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
