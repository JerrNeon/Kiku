package com.jn.kiku.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jn.kiku.R;
import com.jn.kiku.common.api.IBaseView;
import com.jn.kiku.common.api.IImageView;
import com.jn.kiku.common.api.ILogToastView;
import com.jn.kiku.common.api.IRouteView;
import com.jn.kiku.common.api.IUtilsView;
import com.jn.kiku.dialog.ProgressDialogFragment;
import com.jn.kiku.utils.LogUtils;
import com.jn.kiku.utils.QMUtil;
import com.jn.kiku.utils.StatusBarUtils;
import com.jn.kiku.utils.ToastUtil;
import com.jn.kiku.utils.manager.GlideManage;
import com.jn.kiku.utils.manager.IntentManager;
import com.jn.kiku.utils.manager.RxPermissionsManager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (RootActivity)
 * @create by: chenwei
 * @date 2018/5/10 16:18
 */
public class RootActivity extends AppCompatActivity implements LifecycleProvider<ActivityEvent>,
        IRouteView, IUtilsView, IImageView, IBaseView, ILogToastView, View.OnClickListener {

    //Activity lifecycle manager,fix RxJava memory leak question
    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    protected Activity mActivity = null;
    protected AppCompatActivity mAppCompatActivity = null;
    protected Context mContext = null;
    protected Unbinder mRootUnbinder = null;
    protected RxPermissions mRxPermissions = null;
    protected ProgressDialogFragment mProgressDialog = null;

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        mActivity = this;
        mAppCompatActivity = this;
        mContext = getApplicationContext();
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    @CallSuper
    protected void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        unregisterEventBus();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void openActivity(@NonNull Class<?> cls) {
        IntentManager.startActivity(this, cls, null);
    }

    @Override
    public void openActivity(@NonNull Class<?> cls, @Nullable Object param) {
        IntentManager.startActivity(this, cls, param);
    }

    @Override
    public void openActivity(@NonNull Class<?> cls, @Nullable Object param, int requestCode) {
        IntentManager.startActivity(this, cls, param, requestCode);
    }

    @Override
    public Object getParam(Object defaultObject) {
        return IntentManager.getParam(this, getClass(), defaultObject);
    }

    @Override
    public Object getParam(String key, Object defaultObject) {
        return IntentManager.getParam(getIntent().getExtras(), key, defaultObject);
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
    public void displayImage(ImageView iv, Object url) {
        GlideManage.displayImage(this, url, iv);
    }

    @Override
    public void displayRoundImage(ImageView iv, Object url) {
        GlideManage.displayImage(this, url, iv, true, false);
    }

    @Override
    public void displayAvatar(ImageView iv, Object url) {
        GlideManage.displayImage(this, url, iv, false, true);
    }

    @Override
    public void displayRoundAvatar(ImageView iv, Object url) {
        GlideManage.displayImage(this, url, iv, true, true);
    }

    @Override
    public void initButterKnife() {
        mRootUnbinder = ButterKnife.bind(this);
    }

    @Override
    public void unbindButterKnife() {
        if (mRootUnbinder != null)
            mRootUnbinder.unbind();
    }

    @Override
    public void initEventBus() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void unregisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void initRxPermissions() {
        if (mRxPermissions == null)
            mRxPermissions = new RxPermissions(this);
    }

    @Override
    public void requestPermission(int permissionType, Consumer<Boolean> consumer) {
        initRxPermissions();
        RxPermissionsManager.requestPermission(this, mRxPermissions, permissionType, getResources().getString(R.string.app_name), consumer);
    }

    @Override
    public void setStatusBar() {
        StatusBarUtils.setColor(mActivity, ContextCompat.getColor(mContext, R.color.colorPrimary), 0);
    }

    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialogFragment();
        if (mAppCompatActivity != null)
            mProgressDialog.show(mAppCompatActivity.getSupportFragmentManager(), "");
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null)
            mProgressDialog.cancel();
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
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void sendRequest() {

    }

    @Override
    public void onClick(View view) {

    }
}
