package com.jn.kiku.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jn.common.api.ILogToastView;
import com.jn.kiku.common.api.IBaseView;
import com.jn.kiku.common.api.IImageView;
import com.jn.kiku.common.api.IUtilsView;
import com.jn.kiku.utils.manager.BaseManager;

/**
 * Author：Stevie.Chen Time：2019/7/31
 * Class Comment：RootBottomSheetDialogFragment
 */
public abstract class RootBottomSheetDialogFragment extends BottomSheetDialogFragment implements DialogInterface.OnKeyListener,
        IUtilsView, IImageView, IBaseView, ILogToastView, View.OnClickListener {

    protected Activity mActivity = null;
    protected AppCompatActivity mAppCompatActivity = null;
    protected Context mContext = null;
    protected Fragment mFragment = null;
    protected Window mWindow = null;
    protected View mView;//fragment布局
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

    @Override
    @CallSuper
    public void onDestroyView() {
        unbindButterKnife();
        unregisterEventBus();
        super.onDestroyView();
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
