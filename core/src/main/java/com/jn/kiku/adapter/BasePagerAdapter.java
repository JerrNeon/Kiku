package com.jn.kiku.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jn.common.api.ILogToastView;
import com.jn.kiku.common.api.IImageView;
import com.jn.kiku.common.api.IUtilsView;
import com.jn.kiku.utils.manager.BaseManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Stevie.Chen Time：2019/7/31
 * Class Comment：BasePagerAdapter
 */
public abstract class BasePagerAdapter<T> extends PagerAdapter implements IImageView, ILogToastView, IUtilsView {

    protected Context mContext;
    protected Activity mActivity;
    protected Fragment mFragment;
    private BaseManager mBaseManager;
    private List<T> mList;
    private SparseArray<View> mViews;
    private OnItemClickListener<T> mOnItemClickListener = null;
    private OnItemLongClickListener<T> mOnItemLongClickListener = null;

    public BasePagerAdapter(Activity activity) {
        mContext = activity.getApplicationContext();
        mActivity = activity;
        mList = new ArrayList<>();
        mViews = new SparseArray<>();
        mBaseManager = new BaseManager(activity);
    }

    public BasePagerAdapter(Fragment fragment) {
        mFragment = fragment;
        mActivity = fragment.getActivity();
        if (mActivity != null) {
            mContext = mActivity.getApplicationContext();
        }
        mList = new ArrayList<>();
        mViews = new SparseArray<>();
        mBaseManager = new BaseManager(fragment, null);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = mViews.get(position);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(getLayoutResourceId(), null, false);
            mViews.put(position, view);
        }
        getView(view, position, mList.get(position));
        container.addView(view);
        view.setOnClickListener(v -> {
            if (mOnItemClickListener != null)
                mOnItemClickListener.onItemCLick(position, position >= mList.size() ? null : mList.get(position));
        });
        view.setOnLongClickListener(v -> {
            if (mOnItemLongClickListener != null) {
                mOnItemLongClickListener.onItemLongClick(position, position >= mList.size() ? null : mList.get(position));
                return true;
            }
            return false;
        });
        return view;
    }

    /**
     * get item LayoutResourceId
     */
    protected abstract int getLayoutResourceId();

    public abstract void getView(View view, int position, T bean);

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mViews.get(position));
    }

    /**
     * get adapter  list data
     */
    public List<T> getData() {
        return mList;
    }

    public void add(T bean) {
        if (bean != null) {
            mList.add(bean);
        }
    }

    public void addAll(List<T> beans) {
        if (beans != null) {
            mList.addAll(beans);
        }
    }

    public T remove(int position) {
        if (position >= 0 && position < mList.size()) {
            return mList.remove(position);
        }
        return null;
    }

    public void clear() {
        if (!mList.isEmpty())
            mList.clear();
    }

    public boolean isEmpty() {
        return mList.isEmpty();
    }

    public boolean isNotEmpty() {
        return !mList.isEmpty();
    }

    public void setOnItemClickListener(@NonNull OnItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(@NonNull OnItemLongClickListener<T> onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public BaseManager getBaseManager() {
        return mBaseManager;
    }

    public interface OnItemClickListener<T> {
        void onItemCLick(int position, T bean);
    }

    public interface OnItemLongClickListener<T> {
        void onItemLongClick(int position, T bean);
    }
}
