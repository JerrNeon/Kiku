package com.jn.kiku.widget.recyclerview;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Author：Stevie.Chen Time：2019/8/13
 * Class Comment：RecyclerView嵌套-设置子项RecyclerView嵌套不拦截触摸事件
 */
public class RvInRecyclerView extends RecyclerView {

    public RvInRecyclerView(Context context) {
        super(context);
    }

    public RvInRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RvInRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }
}
