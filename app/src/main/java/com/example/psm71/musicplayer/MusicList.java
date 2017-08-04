package com.example.psm71.musicplayer;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by psm71 on 2017-08-03.
 */

public class MusicList extends BaseAdapter
{
    private ArrayList<Music_info> listViewMusicList = new ArrayList<Music_info>();

    @Override
    public int getCount() {
        return listViewMusicList.size();
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewMusicList.get(position) ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.music_list_view, parent, false);
        }
        CircleImageView cover = (CircleImageView) convertView.findViewById(R.id.music_cover);
        TextView title = (TextView) convertView.findViewById(R.id.music_title);
        TextView artist = (TextView) convertView.findViewById(R.id.music_artist);

        title.setPaintFlags(title.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);


        return convertView;
    }
}
