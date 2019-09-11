package com.jn.kiku.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.jn.kiku.utils.ScreenUtils;

/**
 * Author：Stevie.Chen Time：2019/8/28
 * Class Comment：RootPopupWindow
 */
public abstract class RootPopupWindow implements View.OnClickListener {

    protected Activity mActivity;
    protected Fragment mFragment;
    protected Context mContext;
    protected View mView;
    protected PopupWindow mPopupWindow;
    protected Window mWindow;

    /**
     * LayoutResourceId
     */
    protected abstract @LayoutRes
    int getLayoutResourceId();

    /**
     * get popupWindow width
     */
    protected abstract int getWidth();

    /**
     * get popupWindow height
     */
    protected abstract int getHeight();

    public RootPopupWindow(Activity activity) {
        this.mActivity = activity;
        this.mContext = activity.getApplicationContext();
        initView();
        setWindowAttributes();
        initData();
        setListener();
    }

    public RootPopupWindow(Fragment fragment) {
        this.mFragment = fragment;
        this.mActivity = fragment.getActivity();
        if (mActivity != null)
            this.mContext = mActivity.getApplicationContext();
        initView();
        setWindowAttributes();
        initData();
        setListener();
    }

    protected void initView() {
        mView = LayoutInflater.from(mContext).inflate(getLayoutResourceId(), null, false);
        mPopupWindow = new PopupWindow(mView);
        if (getWidth() == 0)
            mPopupWindow.setWidth(ScreenUtils.getScreenWidth(mContext));
        else
            mPopupWindow.setWidth(getWidth());
        if (getHeight() == 0)
            mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        else
            mPopupWindow.setHeight(getHeight());
        mPopupWindow.setOutsideTouchable(false);
    }

    protected void setWindowAttributes() {
        mWindow = mActivity.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.alpha = 0.5f;
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mWindow.setAttributes(lp);
    }

    protected void initData() {

    }

    protected void setListener() {

    }

    @Override
    public void onClick(View view) {

    }
}
