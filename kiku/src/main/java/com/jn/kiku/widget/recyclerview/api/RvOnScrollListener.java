package com.jn.kiku.widget.recyclerview.api;

import android.support.v7.widget.RecyclerView;

import com.jn.kiku.utils.ViewsUtils;


/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (RecyclerView滑动监听器)
 * @create by: chenwei
 * @date 2017/4/20 14:21
 */
public class RvOnScrollListener extends RecyclerView.OnScrollListener {

    private OnScrollListener mOnScrollListener = null;
    private onScrollLastItemListener mOnScrollLastItemListener = null;

    public RvOnScrollListener(OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    public RvOnScrollListener(onScrollLastItemListener onScrollLastItemListener) {
        mOnScrollLastItemListener = onScrollLastItemListener;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (mOnScrollListener != null)
            mOnScrollListener.onScrolled(recyclerView, dx, dy);
        if (mOnScrollLastItemListener != null) {
            if (ViewsUtils.isSlideToBottom(recyclerView) && dy > 0)
                mOnScrollLastItemListener.onScrolledLastItem(recyclerView, dx, dy);
        }
    }

    /**
     * RecyclerView滑动监听器
     */
    public interface OnScrollListener {
        void onScrolled(RecyclerView recyclerView, int dx, int dy);
    }

    /**
     * RecyclerView滑动到最后一个监听器
     */
    public interface onScrollLastItemListener {
        void onScrolledLastItem(RecyclerView recyclerView, int dx, int dy);
    }
}
