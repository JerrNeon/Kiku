package com.jn.kiku.utils.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.jn.kiku.utils.manager.GlideManage;

/**
 * Author：Stevie.Chen Time：2019/04/17
 * Class Comment：RecyclerView中Glide加载优化
 */
public class GlideOnScrollListener extends RecyclerView.OnScrollListener {

    private Object mContext;//Activity或Fragment的实例

    public GlideOnScrollListener(@NonNull Object mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            GlideManage.resumeRequests(mContext);
        } else {
            GlideManage.pauseRequests(mContext);
        }
    }
}
