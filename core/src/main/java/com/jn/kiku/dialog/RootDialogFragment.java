package com.jn.kiku.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.jn.common.api.ILogToastView;
import com.jn.kiku.common.api.IBaseView;
import com.jn.kiku.common.api.IDisposableView;
import com.jn.kiku.common.api.IImageView;
import com.jn.kiku.common.api.IUtilsView;
import com.jn.kiku.utils.manager.BaseManager;

/**
 * Author：Stevie.Chen Time：2019/7/31
 * Class Comment：根AppCompatDialogFragment
 */
public abstract class RootDialogFragment extends AppCompatDialogFragment implements DialogInterface.OnKeyListener,
        IUtilsView, IImageView, IBaseView, ILogToastView, IDisposableView, View.OnClickListener {

    protected Activity mActivity = null;
    protected AppCompatActivity mAppCompatActivity = null;
    protected Context mContext = null;
    protected Fragment mFragment = null;
    protected Window mWindow = null;
    protected View mView;
    protected BaseManager mBaseManager;

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        mWindow = getDialog().getWindow();
        if (getLayoutParams() == null) {
            WindowManager.LayoutParams params = mWindow.getAttributes();
            params.gravity = Gravity.BOTTOM;//底部显示
            params.width = WindowManager.LayoutParams.MATCH_PARENT;//宽度为全屏
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;//宽度为全屏
            mWindow.setAttributes(params);
        } else
            mWindow.setAttributes(getLayoutParams());
        mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//设置半透明背景
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉默认标题
        getDialog().setCanceledOnTouchOutside(!getCanceledOnTouchOutsideEnable());//点击边际是否可消失
        if (getAnimationStyle() != 0) {
            Window window = getDialog().getWindow();
            if (window != null)
                window.getAttributes().windowAnimations = getAnimationStyle();
        }
        mView = inflater.inflate(getLayoutResourceId(), container, false);
        mActivity = getActivity();
        if (getActivity() instanceof AppCompatActivity)
            mAppCompatActivity = (AppCompatActivity) getActivity();
        if (mActivity != null)
            mContext = mActivity.getApplicationContext();
        mFragment = this;
        mBaseManager = new BaseManager(this, mView);
        getLifecycle().addObserver(mBaseManager);
        initButterKnife();
        initView();
        initData();
        return mView;
    }

    @Override
    public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
        //不执行父类点击事件
        return keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_SEARCH;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (Exception e) {
            //
        }
    }

    /**
     * 布局资源
     */
    protected abstract @LayoutRes
    int getLayoutResourceId();

    /**
     * 动画
     */
    protected abstract int getAnimationStyle();

    /**
     * 点击边际是否可消失
     *
     * @return false可消失
     */
    protected abstract boolean getCanceledOnTouchOutsideEnable();

    /**
     * 对话框布局参数
     */
    protected abstract WindowManager.LayoutParams getLayoutParams();

    /**
     * 宽高自适应且居中
     */
    protected WindowManager.LayoutParams getCenterLayoutParams() {
        WindowManager.LayoutParams params = mWindow.getAttributes();
        params.gravity = Gravity.CENTER;//中显示
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;//宽度为自适应
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;//宽度为自适应
        return params;
    }

    /**
     * 点击物理按键让对话框不消失
     */
    public void setCanceledOnBackPress() {
        getDialog().setOnKeyListener(this);
    }

    /**
     * 对话框是否正在显示
     */
    public boolean isShowing() {
        if (getDialog() != null)
            return getDialog().isShowing();
        return false;
    }

    @Override
    public BaseManager getBaseManager() {
        return mBaseManager;
    }

    @Override
    public void onClick(View view) {

    }

}
