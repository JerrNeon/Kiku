package com.jn.kiku.widget.recyclerview.api;

import androidx.annotation.NonNull;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.jn.kiku.adapter.BaseRvAdapter;
import com.jn.kiku.common.api.IRvView;

/**
 * Author：Stevie.Chen Time：2019/8/13
 * Class Comment：
 */
public class RvSimpleClickListener<T> extends SimpleClickListener {

    private IRvView<T> iRvView;
    private BaseRvAdapter<T> mAdapter;

    public RvSimpleClickListener(@NonNull IRvView<T> iRvView, BaseRvAdapter<T> adapter) {
        this.iRvView = iRvView;
        mAdapter = adapter;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (iRvView != null && mAdapter != null)
            iRvView.onItemClick(mAdapter, view, position, mAdapter.getItem(position));
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        if (iRvView != null && mAdapter != null)
            iRvView.onItemLongClick(mAdapter, view, position, mAdapter.getItem(position));
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (iRvView != null && mAdapter != null)
            iRvView.onItemChildClick(mAdapter, view, position, mAdapter.getItem(position));
    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
        if (iRvView != null && mAdapter != null)
            iRvView.onItemChildLongClick(mAdapter, view, position, mAdapter.getItem(position));
    }
}
