package com.jn.kiku.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jn.kiku.R;
import com.jn.kiku.common.api.IBaseView;
import com.jn.kiku.common.api.IImageView;
import com.jn.kiku.common.api.ILazyFragmentView;
import com.jn.kiku.common.api.ILogToastView;
import com.jn.kiku.common.api.IRouteView;
import com.jn.kiku.common.api.IUtilsView;
import com.jn.kiku.dialog.ProgressDialogFragment;
import com.jn.kiku.utils.LogUtils;
import com.jn.kiku.utils.QMUtil;
import com.jn.kiku.utils.ToastUtil;
import com.jn.kiku.utils.manager.GlideManage;
import com.jn.kiku.utils.manager.IntentManager;
import com.jn.kiku.utils.manager.RxPermissionsManager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
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
 * @Description: (根Fragment)
 * @create by: chenwei
 * @date 2018/5/10 16:56
 */
public abstract class RootFragment extends Fragment implements LifecycleProvider<FragmentEvent>,
        ILazyFragmentView, IRouteView, IUtilsView, IImageView, IBaseView, ILogToastView, View.OnClickListener {

    //Fragment声明周期管理,解决RxJava内存泄漏的问题
    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    protected Activity mActivity = null;
    protected AppCompatActivity mAppCompatActivity = null;
    protected Context mContext = null;
    protected Fragment mFragment = null;
    /**
     * fragment布局
     */
    protected View mView;
    protected Unbinder mRootUnbinder = null;
    protected RxPermissions mRxPermissions = null;
    protected ProgressDialogFragment mProgressDialog = null;
    /**
     * 标志位，标志已经初始化完成，因为setUserVisibleHint是在onCreateView之前调用的，
     * 在视图未初始化的时候，在lazyLoad当中就使用的话，就会有空指针的异常
     */
    protected boolean mIsFragmentViewCreated;
    protected boolean mIsFragmentVisible;//标志当前页面是否可见

    /**
     * 布局资源
     *
     * @return
     */
    protected abstract @LayoutRes
    int getLayoutResourceId();

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }

    @Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(getLayoutResourceId(), container, false);
        mActivity = getActivity();
        if (getActivity() instanceof AppCompatActivity)
            mAppCompatActivity = (AppCompatActivity) getActivity();
        mContext = getActivity().getApplicationContext();
        mFragment = this;
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initButterKnife();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
        mIsFragmentViewCreated = true;
        onFragmentLazyLoad();
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        unbindButterKnife();
        unregisterEventBus();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
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
    public boolean isLazyLoadFragment() {
        return false;
    }

    @Override
    public void onFragmentVisible() {
        onFragmentLazyLoad();
    }

    @Override
    public void onFragmentInvisible() {

    }

    @Override
    public void onFragmentLazyLoad() {
        if (!mIsFragmentVisible || !mIsFragmentViewCreated || !isLazyLoadFragment())
            return;
        sendRequest();//数据请求
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static <T extends Fragment> T newInstance(@NonNull Class<T> tClass) {
        return IntentManager.newInstance(tClass, null);
    }

    public static <T extends Fragment> T newInstance(@NonNull Class<T> tClass, @Nullable Object param) {
        return IntentManager.newInstance(tClass, param);
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
        IntentManager.startActivity(this, cls, null, requestCode);
    }

    @Override
    public Object getParam(Object defaultObject) {
        return IntentManager.getParam(this, getClass(), defaultObject);
    }

    @Override
    public Object getParam(String key, Object defaultObject) {
        return IntentManager.getParam(getArguments(), key, defaultObject);
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
        mRootUnbinder = ButterKnife.bind(this, mView);
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
            mRxPermissions = new RxPermissions(mActivity);
    }

    @Override
    public void requestPermission(int permissionType, Consumer<Boolean> consumer) {
        initRxPermissions();
        RxPermissionsManager.requestPermission(mAppCompatActivity, mRxPermissions, permissionType, getResources().getString(R.string.app_name), consumer);
    }

    @Override
    public void setStatusBar() {

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
