package com.example.psm71.musicplayer.activity.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.example.psm71.musicplayer.Music.MusicControl;
import com.example.psm71.musicplayer.R;
import com.example.psm71.musicplayer.adapter.recycler.RecyclerViewDecoration;
import com.example.psm71.musicplayer.adapter.recyclerView;

/**
 * Created by psm71 on 2017-08-07.
 */

public class MusicListFragment extends android.support.v4.app.Fragment
{
    MusicControl control;
    int position;

    public MusicListFragment()
    {

    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        RelativeLayout layout = null;
        switch (position) {
            case 0:
            layout = (RelativeLayout) inflater.inflate(R.layout.music_all_list, container, false);
            RecyclerView recycler = layout.findViewById(R.id.recyclerView);
            recyclerView adapter = new recyclerView(control.getmanager().getList(), R.layout.music_list_view_box);
            adapter.setItemClick(new recyclerView.ItemClick() {
                @Override
                public void onClick(View view, int position) {
                    control.Play(position);
                }
            });
            recycler.setAdapter(adapter);
            recycler.addItemDecoration((new RecyclerViewDecoration(3, dpToPx(getContext(), 6), true)));
            recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
            recycler.setItemAnimator(new DefaultItemAnimator());
        }
        return layout;
    }
    public void setfragment(int position)
    {
        this.position = position;
    }
    public void setList(MusicControl control)
    {
       this.control= control;
    }
    public int dpToPx(Context context, int dp)
    {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

}
