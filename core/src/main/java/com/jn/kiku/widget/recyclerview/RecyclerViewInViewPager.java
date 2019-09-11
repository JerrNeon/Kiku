package com.jn.kiku.widget.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.jn.common.util.LogUtils;


/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (解决水平RecyclerView在ViewPager中滑动到最右侧时ViewPager会滑到下一个界面的问题)
 * @create by: chenwei
 * @date 2017/6/15 15:58
 */
public class RecyclerViewInViewPager extends RecyclerView {

    private int lastX = -1;
    private int lastY = -1;

    public RecyclerViewInViewPager(Context context) {
        super(context);
    }

    public RecyclerViewInViewPager(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewInViewPager(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        int dealtX = 0;
        int dealtY = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 保证子View能够接收到Action_move事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                dealtX += Math.abs(x - lastX);
                dealtY += Math.abs(y - lastY);
                LogUtils.d("dealtX:=" + dealtX);
                LogUtils.d("dealtY:=" + dealtY);
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (dealtX >= dealtY) {//横向滑动时,设置父控件不拦截滑动事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        //getParent().requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(e);
    }

}
