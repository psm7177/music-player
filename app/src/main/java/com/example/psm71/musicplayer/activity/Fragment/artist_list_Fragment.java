package com.example.psm71.musicplayer.activity.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.psm71.musicplayer.R;

/**
 * Created by psm71 on 2017-08-05.
 */

public class artist_list_Fragment extends android.support.v4.app.Fragment {
    public artist_list_Fragment()
    {

    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.music_artist_list,container,false);
        return layout;
    }
}
