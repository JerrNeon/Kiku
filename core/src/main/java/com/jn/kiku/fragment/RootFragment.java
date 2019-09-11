package com.jn.kiku.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.jn.common.api.ILogToastView;
import com.jn.kiku.common.api.IBaseView;
import com.jn.kiku.common.api.IDisposableView;
import com.jn.kiku.common.api.IImageView;
import com.jn.kiku.common.api.ILazyFragmentView;
import com.jn.kiku.common.api.IMvpView;
import com.jn.kiku.common.api.IUtilsView;
import com.jn.kiku.mvp.IBPresenter;
import com.jn.kiku.mvp.IBView;
import com.jn.kiku.utils.manager.BaseManager;

/**
 * Author：Stevie.Chen Time：2019/7/31
 * Class Comment：根Fragment
 */
public abstract class RootFragment<P extends IBPresenter> extends Fragment implements ILazyFragmentView, IUtilsView,
        IImageView, IBaseView, ILogToastView, IDisposableView, IMvpView<P>, View.OnClickListener {

    protected Activity mActivity = null;
    protected AppCompatActivity mAppCompatActivity = null;
    protected Context mContext = null;
    protected Fragment mFragment = null;
    protected View mView;
    protected BaseManager mBaseManager;

    protected P mPresenter;

    /**
     * 标志位，标志已经初始化完成，因为setUserVisibleHint是在onCreateView之前调用的，
     * 在视图未初始化的时候，在lazyLoad当中就使用的话，就会有空指针的异常
     */
    protected boolean mIsFragmentViewCreated;
    protected boolean mIsFragmentVisible;//标志当前页面是否可见

    /**
     * 布局资源
     */
    protected abstract @LayoutRes
    int getLayoutResourceId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(getLayoutResourceId(), container, false);
        mActivity = getActivity();
        if (getActivity() instanceof AppCompatActivity)
            mAppCompatActivity = (AppCompatActivity) getActivity();
        if (mActivity != null)
            mContext = mActivity.getApplicationContext();
        mFragment = this;
        mBaseManager = new BaseManager(this, mView);
        getLifecycle().addObserver(mBaseManager);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initButterKnife();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsFragmentViewCreated = true;
        onFragmentLazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //懒加载
        if (getUserVisibleHint()) {
            mIsFragmentVisible = true;
            onFragmentVisible();
        } else {
            mIsFragmentVisible = false;
            onFragmentInvisible();
        }
    }

    @Override
    public void onFragmentLazyLoad() {
        if (!mIsFragmentVisible || !mIsFragmentViewCreated || !isLazyLoadFragment())
            return;
        sendRequest();//数据请求
        mIsFragmentViewCreated = !isLoadOnlyOnce();
    }

    @Override
    public BaseManager getBaseManager() {
        return mBaseManager;
    }

    @Override
    public void initPresenter() {
        if (mPresenter == null)
            mPresenter = createPresenter();
        if (mPresenter != null && this instanceof IBView) {
            mPresenter.attachView((IBView) this);
            getLifecycle().addObserver(mPresenter);
        }
    }

    @Override
    public void onClick(View view) {

    }
}
