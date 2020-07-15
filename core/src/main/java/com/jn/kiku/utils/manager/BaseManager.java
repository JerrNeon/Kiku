package com.jn.kiku.utils.manager;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.jn.kiku.R;
import com.jn.kiku.common.api.IBaseView;
import com.jn.kiku.common.api.IDisposableView;
import com.jn.kiku.common.api.IImageView;
import com.jn.kiku.dialog.ProgressDialogFragment;
import com.jn.kiku.utils.StatusBarUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Author：Stevie.Chen Time：2019/8/28
 * Class Comment：
 */
public class BaseManager implements IImageView, IBaseView, IDisposableView, DefaultLifecycleObserver {

    private Activity mActivity;
    private Fragment mFragment;
    private View mView;
    private AppCompatActivity mAppCompatActivity;
    private Context mContext;
    private Unbinder mRootUnBinder;
    private RxPermissions mRxPermissions;
    private ProgressDialogFragment mProgressDialog;
    private CompositeDisposable mCompositeDisposable;

    public BaseManager(@NonNull Activity activity) {
        mActivity = activity;
        if (activity instanceof AppCompatActivity)
            mAppCompatActivity = (AppCompatActivity) activity;
        mContext = activity.getApplicationContext();
    }

    public BaseManager(@NonNull Fragment fragment, @Nullable View view) {
        mFragment = fragment;
        mView = view;
        mActivity = fragment.getActivity();
        if (mActivity instanceof AppCompatActivity)
            mAppCompatActivity = (AppCompatActivity) mActivity;
        if (mActivity != null) {
            mContext = mActivity.getApplicationContext();
        }
    }

    @Override
    public void initButterKnife() {
        if (mFragment != null && mView != null)
            mRootUnBinder = ButterKnife.bind(mFragment, mView);
        else
            mRootUnBinder = ButterKnife.bind(mActivity);
    }

    @Override
    public void unbindButterKnife() {
        if (mRootUnBinder != null)
            mRootUnBinder.unbind();
    }

    @Override
    public void initEventBus() {
        Object subscriber = mFragment != null ? mFragment : mActivity;
        EventBus.getDefault().register(subscriber);
    }

    @Override
    public void unregisterEventBus() {
        Object subscriber = mFragment != null ? mFragment : mActivity;
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber);
        }
    }

    @Override
    public void initRxPermissions() {
        if (mRxPermissions == null)
            mRxPermissions = new RxPermissions(mActivity);
    }

    @Override
    public void requestPermission(int permissionType, Consumer<Boolean> consumer) {
        initRxPermissions();
        RxPermissionsManager.requestPermission(mActivity, mRxPermissions, permissionType, mContext.getResources().getString(R.string.app_name), consumer);
    }

    public void requestAllPermissions(Consumer<Boolean> consumer, String... permissions) {
        initRxPermissions();
        RxPermissionsManager.requestAllPermissions(mRxPermissions, consumer, permissions);
    }

    @Override
    public void setStatusBar() {
        StatusBarUtils.StatusBarLightMode(mActivity);
        StatusBarUtils.setColor(mActivity, ContextCompat.getColor(mContext, R.color.white), 0);
    }

    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null)
            mProgressDialog = ProgressDialogFragment.newInstance();
        if (mAppCompatActivity != null)
            mProgressDialog.show(mAppCompatActivity.getSupportFragmentManager(), "");
    }

    public void showProgressDialog(int type) {
        if (mProgressDialog == null)
            mProgressDialog = ProgressDialogFragment.newInstance(type);
        if (mAppCompatActivity != null)
            mProgressDialog.show(mAppCompatActivity.getSupportFragmentManager(), "");
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.cancel();
            mProgressDialog = null;
        }
    }

    @Override
    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void dispose() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    @Override
    public void displayImage(ImageView iv, Object url) {
        Object context = mFragment != null ? mFragment : mActivity;
        GlideManage.displayImage(context, url, iv);
    }

    @Override
    public void displayCircleImage(ImageView iv, Object url) {
        Object context = mFragment != null ? mFragment : mActivity;
        GlideManage.displayImage(context, url, iv, true, false);
    }

    @Override
    public void displayRoundImage(ImageView iv, Object url, int radius) {
        Object context = mFragment != null ? mFragment : mActivity;
        GlideManage.displayImage(context, url, iv, radius);
    }

    @Override
    public void displayAvatar(ImageView iv, Object url) {
        Object context = mFragment != null ? mFragment : mActivity;
        GlideManage.displayImage(context, url, iv, false, true);
    }

    @Override
    public void displayCircleAvatar(ImageView iv, Object url) {
        Object context = mFragment != null ? mFragment : mActivity;
        GlideManage.displayImage(context, url, iv, true, true);
    }

    @Override
    public BaseManager getBaseManager() {
        return null;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        unbindButterKnife();
        unregisterEventBus();
        dispose();
    }
}
