package com.jn.kiku.adapter;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Author：Stevie.Chen Time：2019/4/20
 * Class Comment：
 */
public abstract class BaseRvMultiItemAdapter<T extends MultiItemEntity> extends BaseMultiItemQuickAdapter<T, BaseAdapterViewHolder> {

    protected Activity mActivity;
    protected Fragment mFragment = null;

    public BaseRvMultiItemAdapter(Activity activity) {
        super(null);
        mActivity = activity;
        setItemType();
    }

    public BaseRvMultiItemAdapter(Fragment fragment) {
        super(null);
        mActivity = fragment.getActivity();
        mFragment = fragment;
        setItemType();
    }

    @SuppressWarnings("WeakerAccess")
    protected void setItemType() {
        int[] itemTypeArray = getItemType();
        int[] layoutResourceIdArray = getLayoutResourceId();
        if (itemTypeArray == null)
            return;
        if (layoutResourceIdArray == null)
            return;
        if (itemTypeArray.length == 0)
            return;
        if (layoutResourceIdArray.length == 0)
            return;
        if (itemTypeArray.length != layoutResourceIdArray.length)
            return;
        for (int i = 0; i < itemTypeArray.length; i++) {
            addItemType(itemTypeArray[i], layoutResourceIdArray[i]);
        }
    }

    protected abstract int[] getItemType();

    protected abstract @LayoutRes
    int[] getLayoutResourceId();
}
