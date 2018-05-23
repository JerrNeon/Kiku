package com.jn.kiku.widget.recyclerview.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (RecyclerView分割线, 单独的横向和纵向)
 * @create by: chenwei
 * @date 2017/3/30 15:41
 */
public class LinearItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public LinearItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = space;
//        if (parent.getChildAdapterPosition(view) == 0) {
//            outRect.top = space;
//        }
    }
}
