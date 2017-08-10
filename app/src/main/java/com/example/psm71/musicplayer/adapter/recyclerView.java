package com.example.psm71.musicplayer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.psm71.musicplayer.R;
import com.example.psm71.musicplayer.model.Music_info;

import java.util.ArrayList;

public class recyclerView extends RecyclerView.Adapter<recyclerView.ViewHolder>{

    private ArrayList<Music_info> albumList;
    private int itemLayout;

    private ItemClick itemClick;
    public interface ItemClick {
        public void onClick(View view,int position);
    }

    //아이템 클릭시 실행 함수 등록 함수
    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }
    public recyclerView(ArrayList<Music_info> items , int itemLayout){

        this.albumList = items;
        this.itemLayout = itemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Music_info item = albumList.get(position);
        viewHolder.title.setText(item.getName());
        viewHolder.artist.setText(item.getArtist());
        Glide.with(viewHolder.view.getContext()).load(item.getAlbum_img()).into(viewHolder.img);
        final int Position = position;
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClick != null){
                    itemClick.onClick(v, Position);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return albumList.size();
    }

    /**
     * 뷰 재활용을 위한 viewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img;
        public TextView title;
        public TextView artist;
        public View view;

        public ViewHolder(View itemView){
            super(itemView);
            view = itemView;
            img = (ImageView) itemView.findViewById(R.id.music_cover);
            title = (TextView) itemView.findViewById(R.id.music_title);
            artist = (TextView) itemView.findViewById(R.id.music_artist);
        }

    }
}