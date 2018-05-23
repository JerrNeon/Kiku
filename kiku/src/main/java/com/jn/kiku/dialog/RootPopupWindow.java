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
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (RootPopupWindow)
 * @create by: chenwei
 * @date 2018/5/23 14:04
 */
public abstract class RootPopupWindow implements View.OnClickListener {

    protected Activity mActivity = null;
    protected Fragment mFragment = null;
    protected Context mContext = null;
    protected View mView = null;
    protected PopupWindow mPopupWindow = null;
    protected Window mWindow = null;

    /**
     * LayoutResourceId
     *
     * @return
     */
    protected abstract @LayoutRes
    int getLayoutResourceId();

    /**
     * get popupWindow width
     *
     * @return
     */
    protected abstract int getWidth();

    /**
     * get popupWindow height
     *
     * @return
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
        this.mContext = fragment.getActivity().getApplicationContext();
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
