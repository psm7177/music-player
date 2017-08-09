package com.example.psm71.musicplayer.model;


<<<<<<< HEAD
=======
import java.io.Serializable;
>>>>>>> dec79f5fb3921121088567d0f43c2cf3cb7eedda
import android.net.Uri;

import java.io.Serializable;

<<<<<<< HEAD
public class Music_info implements Serializable{
=======
public class Music_info implements Serializable {
>>>>>>> dec79f5fb3921121088567d0f43c2cf3cb7eedda
    private String path;
    private String name;
    private String artist;
    private String album;
    private String album_img;
    private int duration;

    // album cover image
    public Music_info()
    {
    }

    public Music_info(String path, String name, String artist, String album, String album_img, int duration)
    {
        this.path = path;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.album_img = album_img;
        this.duration = duration;
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setAlbum_img(String album_img) {
        this.album_img = album_img;
    }

    public String getAlbum_img() {
        return album_img;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
