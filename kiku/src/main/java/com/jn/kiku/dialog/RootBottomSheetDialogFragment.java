package com.jn.kiku.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jn.kiku.R;
import com.jn.kiku.common.api.IBaseView;
import com.jn.kiku.common.api.IImageView;
import com.jn.kiku.common.api.ILogToastView;
import com.jn.kiku.common.api.IRouteView;
import com.jn.kiku.common.api.IUtilsView;
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
 * @Description: (RootBottomSheetDialogFragment)
 * @create by: chenwei
 * @date 2018/5/10 17:04
 */
public abstract class RootBottomSheetDialogFragment extends BottomSheetDialogFragment implements LifecycleProvider<FragmentEvent>,
        DialogInterface.OnKeyListener, IRouteView, IUtilsView, IImageView, IBaseView, ILogToastView, View.OnClickListener {

    //DialogFragment声明周期管理,解决RxJava内存泄漏的问题
    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    protected Activity mActivity = null;
    protected AppCompatActivity mAppCompatActivity = null;
    protected Context mContext = null;
    protected Fragment mFragment = null;
    protected Window mWindow = null;
    /**
     * fragment布局
     */
    protected View mView;
    protected Unbinder mRootUnbinder = null;
    protected RxPermissions mRxPermissions = null;

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
    @CallSuper
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @Override
    @CallSuper
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
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
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    @CallSuper
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        unbindButterKnife();
        unregisterEventBus();
        super.onDestroyView();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    @CallSuper
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉默认标题
        getDialog().setCanceledOnTouchOutside(!getCanceledOnTouchOutsideEnable());//点击边际是否可消失
        if (getAnimationStyle() != 0)
            getDialog().getWindow().getAttributes().windowAnimations = getAnimationStyle();
        mView = inflater.inflate(getLayoutResourceId(), container, false);
        mActivity = getActivity();
        if (getActivity() instanceof AppCompatActivity)
            mAppCompatActivity = (AppCompatActivity) getActivity();
        mContext = getActivity().getApplicationContext();
        mFragment = this;
        initButterKnife();
        initView();
        initData();
        return mView;
    }

    @Override
    public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == keyEvent.KEYCODE_SEARCH)
            return true;//不执行父类点击事件
        return false;
    }

    /**
     * 布局资源
     *
     * @return
     */
    protected abstract @LayoutRes
    int getLayoutResourceId();

    /**
     * 动画
     *
     * @return
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
     *
     * @return
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
     *
     * @return
     */
    public boolean isShowing() {
        if (getDialog() != null)
            return getDialog().isShowing();
        return false;
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
    public void openActivity(@NonNull Class<?> cls, @Nullable Object param, @NonNull int requestCode) {
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
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
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

    }

    @Override
    public void dismissProgressDialog() {

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
