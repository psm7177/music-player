package com.example.psm71.musicplayer.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.psm71.musicplayer.R;
import com.example.psm71.musicplayer.model.Music_info;
import com.example.psm71.musicplayer.widget.CircleImageView;

import java.util.ArrayList;

/**
 * Created by psm71 on 2017-08-03.
 */

public class MusicAdapter extends BaseAdapter {
    private ArrayList<Music_info> MusicList;

    public MusicAdapter(ArrayList<Music_info> list) {
        MusicList = new ArrayList<>(list);
    }

    @Override
    public int getCount() {
        return MusicList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return MusicList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Music_info data = MusicList.get(position);
        final int pos = position;
        final Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.music_list_view_box, parent, false);

            //convertView = inflater.inflate(R.layout.list_itemview, parent, false);
        }
        CircleImageView AlumbView = (CircleImageView) convertView.findViewById(R.id.music_cover);
        TextView titleView = (TextView) convertView.findViewById(R.id.music_title);
        TextView artistView = (TextView) convertView.findViewById(R.id.music_artist);

        titleView.setPaintFlags(titleView.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        titleView.setText(data.getName());
        artistView.setText(data.getArtist());
        Glide.with(context).load(data.getAlbum_img()).into(AlumbView);

        return convertView;
    }
}
