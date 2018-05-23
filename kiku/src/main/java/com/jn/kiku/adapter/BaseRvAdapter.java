package com.jn.kiku.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (BaseAdapter)
 * @create by: chenwei
 * @date 2018/5/14 14:21
 */
public abstract class BaseRvAdapter<T> extends BaseQuickAdapter<T, BaseAdapterViewHolder> {

    protected Context mContext = null;
    protected Activity mActivity = null;
    protected Fragment mFragment = null;
    protected Object mImageContext = null;//用于显示图片的context对象

    public BaseRvAdapter(Activity activity) {
        super(0, null);
        this.mLayoutResId = getLayoutResourceId();
        mContext = activity.getApplicationContext();
        mActivity = activity;
        mImageContext = activity;
    }

    public BaseRvAdapter(Fragment fragment) {
        super(0, null);
        this.mLayoutResId = getLayoutResourceId();
        mContext = fragment.getContext().getApplicationContext();
        mActivity = fragment.getActivity();
        mFragment = fragment;
        mImageContext = fragment;
    }

    public abstract @LayoutRes
    int getLayoutResourceId();

}
