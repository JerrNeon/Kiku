package com.jn.kiku.adapter;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.chad.library.adapter.base.util.MultiTypeDelegate;

/**
 * Author：Stevie.Chen Time：2019/10/10
 * Class Comment：多类型
 */
public abstract class BaseRvMultiItemDelegateAdapter<T> extends BaseRvAdapter<T> {

    public BaseRvMultiItemDelegateAdapter(Activity activity) {
        super(activity);
        setItemType();
    }

    public BaseRvMultiItemDelegateAdapter(Fragment fragment) {
        super(fragment);
        setItemType();
    }

    private void setItemType() {
        int[] itemViewTypes = getItemViewTypes();
        int[] layoutResourceIds = getLayoutResourceIds();
        if (itemViewTypes != null && layoutResourceIds != null && itemViewTypes.length > 0
                && itemViewTypes.length == layoutResourceIds.length) {
            setMultiTypeDelegate(new MultiTypeDelegate<T>() {
                @Override
                protected int getItemType(T t) {
                    return getItemViewType(t);
                }
            });
            for (int i = 0; i < itemViewTypes.length; i++) {
                getMultiTypeDelegate().registerItemType(itemViewTypes[i], layoutResourceIds[i]);
            }
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return 0;
    }

    protected abstract int getItemViewType(T item);

    protected abstract int[] getItemViewTypes();

    protected abstract int[] getLayoutResourceIds();
}
