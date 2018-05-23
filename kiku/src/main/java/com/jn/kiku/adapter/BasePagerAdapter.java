package com.jn.kiku.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jn.kiku.common.api.IImageView;
import com.jn.kiku.common.api.ILogToastView;
import com.jn.kiku.common.api.IRouteView;
import com.jn.kiku.common.api.IUtilsView;
import com.jn.kiku.utils.LogUtils;
import com.jn.kiku.utils.QMUtil;
import com.jn.kiku.utils.ToastUtil;
import com.jn.kiku.utils.manager.GlideManage;
import com.jn.kiku.utils.manager.IntentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (BasePagerAdapter)
 * @create by: chenwei
 * @date 2017/4/10 10:28
 */
public abstract class BasePagerAdapter<T> extends PagerAdapter implements IImageView, ILogToastView, IUtilsView, IRouteView {

    protected Context mContext;
    protected Activity mActivity;
    protected Fragment mFragment;
    protected Object mImageContext = null;//show network Image context
    protected List<T> mList;
    protected SparseArray<View> mViews;
    protected onItemClickListener mOnItemClickListener = null;
    protected onItemLongClickListener mOnItemLongClickListener = null;

    public BasePagerAdapter(Activity activity) {
        mContext = activity.getApplicationContext();
        mActivity = activity;
        mList = new ArrayList<>();
        mViews = new SparseArray<>();
        mImageContext = activity;
    }

    public BasePagerAdapter(Fragment fragment) {
        mContext = fragment.getContext().getApplicationContext();
        mActivity = fragment.getActivity();
        mList = new ArrayList<>();
        mViews = new SparseArray<>();
        mFragment = fragment;
        mImageContext = fragment;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = mViews.get(position);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(getLayoutResourceId(), null, false);
            mViews.put(position, view);
        }
        getView(view, position, mList.get(position));
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemCLick(position, position >= mList.size() ? null : mList.get(position));
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    mOnItemLongClickListener.onItemLongClick(position, position >= mList.size() ? null : mList.get(position));
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    /**
     * get item LayoutResourceId
     *
     * @return
     */
    protected abstract int getLayoutResourceId();

    public abstract void getView(View view, int position, T bean);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    /**
     * get adapter  list data
     *
     * @return
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
        if (!mList.isEmpty() && mList.size() > 0)
            mList.clear();
    }

    public boolean isEmpty() {
        return mList.isEmpty();
    }

    public boolean isNotEmpty() {
        return !mList.isEmpty();
    }

    public void setOnItemClickListener(@NonNull onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(@NonNull onItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public void displayImage(ImageView iv, Object url) {
        GlideManage.displayImage(mImageContext, url, iv);
    }

    @Override
    public void displayRoundImage(ImageView iv, Object url) {
        GlideManage.displayImage(mImageContext, url, iv, true, false);
    }

    @Override
    public void displayAvatar(ImageView iv, Object url) {
        GlideManage.displayImage(mImageContext, url, iv, false, true);
    }

    @Override
    public void displayRoundAvatar(ImageView iv, Object url) {
        GlideManage.displayImage(mImageContext, url, iv, true, true);
    }

    @Override
    public void logI(String message) {
        LogUtils.i(String.format(messageFormat, getClass().getSimpleName(), message));
    }

    @Override
    public void logE(String message) {
        LogUtils.e(String.format(messageFormat, getClass().getSimpleName(), message));
    }

    @Override
    public void showToast(String message) {
        ToastUtil.showToast(mContext, message);
    }

    @Override
    public void showToast(String message, int duration) {
        ToastUtil.showToast(mContext, message, duration);
    }

    @Override
    public boolean isEmpty(Object obj) {
        return QMUtil.isEmpty(obj);
    }

    @Override
    public String checkStr(String str) {
        return QMUtil.checkStr(str);
    }

    @Override
    public <T extends Number> String objToStr(T object) {
        return String.valueOf(object);
    }

    @Override
    public <T extends Number> T strToObject(String str, Number defaultNumber) {
        return QMUtil.strToObject(str, defaultNumber);
    }

    @Override
    public void openActivity(@NonNull Class<?> cls) {
        IntentManager.startActivity(mActivity, cls, null);
    }

    @Override
    public void openActivity(@NonNull Class<?> cls, @Nullable Object param) {
        IntentManager.startActivity(mActivity, cls, param);
    }

    @Override
    public void openActivity(@NonNull Class<?> cls, @Nullable Object param, int requestCode) {
        IntentManager.startActivity(mActivity, cls, param, requestCode);
    }

    @Override
    public Object getParam(Object defaultObject) {
        return null;
    }

    @Override
    public Object getParam(String key, Object defaultObject) {
        return null;
    }

    public interface onItemClickListener<T> {
        void onItemCLick(int position, T bean);
    }

    public interface onItemLongClickListener<T> {
        void onItemLongClick(int position, T bean);
    }
}
