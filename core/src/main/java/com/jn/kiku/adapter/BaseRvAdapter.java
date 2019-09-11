package com.jn.kiku.adapter;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * Author：Stevie.Chen Time：2019/8/13
 * Class Comment：BaseAdapter
 */
public abstract class BaseRvAdapter<T> extends BaseQuickAdapter<T, BaseAdapterViewHolder> {

    protected Activity mActivity;
    protected Fragment mFragment;

    public BaseRvAdapter(Activity activity) {
        super(0, null);
        this.mLayoutResId = getLayoutResourceId();
        mActivity = activity;
    }

    public BaseRvAdapter(Fragment fragment) {
        super(0, null);
        this.mLayoutResId = getLayoutResourceId();
        mActivity = fragment.getActivity();
        mFragment = fragment;
    }

    /**
     * If you have added headeview, the notification view refreshes.
     * Do not need to care about the number of headview, only need to pass in the position of the final view
     */
    public final void refreshNotifyItemChanged(int position, @Nullable Object payload) {
        notifyItemChanged(position + getHeaderLayoutCount(), payload);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAdapterViewHolder holder, int position) {
        if (mFragment != null)
            holder.bindImageContext(mFragment);
        else
            holder.bindImageContext(mActivity);
        super.onBindViewHolder(holder, position);
    }

    protected abstract @LayoutRes
    int getLayoutResourceId();

}
