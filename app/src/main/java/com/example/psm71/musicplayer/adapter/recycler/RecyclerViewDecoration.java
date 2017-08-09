package com.example.psm71.musicplayer.adapter.recycler;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by psm71 on 2017-08-09.
 */

public class RecyclerViewDecoration extends RecyclerView.ItemDecoration{
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        //단수,패딩값,
        public RecyclerViewDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
/* if(view instanceof ViewModel == false){ //홈 그리드 아이템이 아닌경우 패스
return;
}*/
            int position = parent.getChildAdapterPosition(view); // item position

            int spanIndex = ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();

            if (includeEdge) {


                if (spanIndex == 0) {
//좌측 아이템이며 우측 패딩을 설정한 패딩의 1/2로 설정
                    outRect.left = spacing;
                    outRect.right = spacing/2;
                }
                else if(spanIndex < spanCount)
                {
                    outRect.left = spacing/2;
                    outRect.right = spacing/2;
                }
                else {
                    outRect.left = spacing/2;
                    outRect.right = spacing;
                }

//상단 패딩
                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }

//하단 패딩
                outRect.bottom = spacing; // item bottom
            }
        }
}
