package com.jn.kiku.ttp.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.jn.kiku.common.api.ILogToastView;
import com.jn.kiku.retrofit.RetrofitManage;
import com.jn.kiku.ttp.share.sina.SinaApiService;
import com.jn.kiku.ttp.share.sina.SinaUserInfoVO;
import com.jn.kiku.utils.LogUtils;
import com.jn.kiku.utils.ToastUtil;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (新浪登录 、 分享管理)
 * @create by: chenwei
 * @date 2017/3/9 14:49
 */
public class SinaManage implements WbShareCallback, ILogToastView {

    private static SinaManage sSinaManage = null;
    private Context mContext = null;
    private WeakReference<Activity> mActivity = null;//弱引用，防止内存泄漏
    private SsoHandler mSsoHandler = null;//授权关键类(登录仅用到此类)
    //封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
    private Oauth2AccessToken mAccessToken;
    private WbShareHandler mWbShareHandler = null;//分享关键类
    private SinaResultListener mSinaResultListener = null;//登录、分享结果监听
    private SinaUserInfoResultListener mSinaUserInfoResultListener = null;//用户信息监听

    public static synchronized SinaManage getInstance(Context context) {
        if (sSinaManage == null)
            sSinaManage = new SinaManage(context.getApplicationContext());
        return sSinaManage;
    }

    private SinaManage(Context context) {
        this.mContext = context;
    }

    /**
     * 初始化分享
     *
     * @param activity
     */
    public void initShare(Activity activity) {
        if (mActivity == null)
            mActivity = new WeakReference<>(activity);
        if (mWbShareHandler == null)
            mWbShareHandler = new WbShareHandler(activity);
        mWbShareHandler.registerApp();
    }

    /**
     * 初始化登录
     *
     * @param activity
     */
    private void initLogin(Activity activity) {
        if (mActivity == null)
            mActivity = new WeakReference<>(activity);
        mSsoHandler = new SsoHandler(activity);
    }

    /**
     * 新浪微博登录
     *
     * @param listener 结果监听
     */
    public void login(@NonNull Activity activity, @NonNull SinaResultListener listener) {
        initLogin(activity);
        mSinaResultListener = listener;
        if (mSsoHandler == null && mActivity != null && mActivity.get() != null)
            initLogin(mActivity.get());
        if (mSsoHandler != null)
            mSsoHandler.authorize(new SelfWbAuthListener());
    }

    /**
     * 新浪微博登录
     *
     * @param listener 结果监听
     */
    public void login(@NonNull Activity activity, @NonNull SinaUserInfoResultListener listener) {
        initLogin(activity);
        mSinaUserInfoResultListener = listener;
        if (mSsoHandler == null && mActivity != null && mActivity.get() != null)
            initLogin(mActivity.get());
        if (mSsoHandler != null)
            mSsoHandler.authorize(new SelfWbAuthListener());
    }

    /**
     * 新浪微博退出
     */
    public void logout() {
        AccessTokenKeeper.clear(mContext);
        mAccessToken = new Oauth2AccessToken();
    }

    /**
     * 新浪微博分享
     *
     * @param listener 结果监听
     *                 <p>
     *                 TextObject textObject = new TextObject();
     *                 textObject.title = "";
     *                 textObject.text = "分享内容";
     *                 textObject.actionUrl = "";
     *                 </p>
     */
    public void share(TextObject textObject, ImageObject imageObject, @NonNull SinaResultListener listener) {
        mSinaResultListener = listener;
        WeiboMultiMessage message = new WeiboMultiMessage();
        if (textObject != null)
            message.textObject = textObject;
        if (imageObject != null) {
            message.imageObject = imageObject;
        }
        if (mWbShareHandler == null && mActivity != null && mActivity.get() != null)
            initShare(mActivity.get());
        if (mWbShareHandler != null)
            mWbShareHandler.shareMessage(message, false);
    }

    /**
     * 授权时在Activity中onActivityResult()方法中调用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void authorizeCallBack(int requestCode, int resultCode, Intent data) {
        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    /**
     * 分享时在Activity中onNewIntent()方法中调用
     *
     * @param intent
     */
    public void onNewIntent(Intent intent) {
        if (mWbShareHandler != null)
            mWbShareHandler.doResultIntent(intent, this);
    }

    /**
     * 登录回调
     */
    private class SelfWbAuthListener implements com.sina.weibo.sdk.auth.WbAuthListener {
        @Override
        public void onSuccess(final Oauth2AccessToken token) {
            mAccessToken = token;
            if (mAccessToken.isSessionValid()) {
                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(mContext, mAccessToken);
                //ToastUtil.showToast(mContext, "登录成功");
                if (mSinaResultListener != null)
                    mSinaResultListener.onSuccess(token);
                getUserInfo(token);
            }
        }

        @Override
        public void cancel() {
            ToastUtil.showToast(mContext, "取消登录");
        }

        @Override
        public void onFailure(WbConnectErrorMessage errorMessage) {
            ToastUtil.showToast(mContext, "登录失败");
            if (mSinaResultListener != null)
                mSinaResultListener.onFailure(errorMessage);
        }
    }


    @Override
    public void onWbShareSuccess() {
        if (mSinaResultListener != null)
            mSinaResultListener.onSuccess(null);
        ToastUtil.showToast(mContext, "分享成功");
    }

    @Override
    public void onWbShareCancel() {
        ToastUtil.showToast(mContext, "取消分享");
    }

    @Override
    public void onWbShareFail() {
        if (mSinaResultListener != null)
            mSinaResultListener.onFailure(null);
        ToastUtil.showToast(mContext, "分享失败");
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

    }

    @Override
    public void showToast(String message, int duration) {

    }

    /**
     * 获取用户信息
     *
     * @param token
     */
    public void getUserInfo(final Oauth2AccessToken token) {
        RetrofitManage.getInstance().create(SinaApiService.class)
                .getUserInfo(SinaApiService.GET_USERINFO, token.getToken(), token.getUid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SinaUserInfoVO>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SinaUserInfoVO sinaUserInfoVO) {
                        sinaUserInfoVO.setUid(token.getUid());
                        if (mSinaUserInfoResultListener != null)
                            mSinaUserInfoResultListener.onSuccess(sinaUserInfoVO);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void onDestroy() {
        if (mSsoHandler != null)
            mSsoHandler = null;
        if (mAccessToken != null)
            mAccessToken = null;
        if (mWbShareHandler != null)
            mWbShareHandler = null;
        if (mSinaResultListener != null)
            mSinaResultListener = null;
        if (mSinaUserInfoResultListener != null)
            mSinaUserInfoResultListener = null;
    }

    /**
     * 分享、登录结果回调(分享返回的token和errorMessage都为空)
     */
    public interface SinaResultListener {
        void onSuccess(Oauth2AccessToken token);

        void onFailure(WbConnectErrorMessage errorMessage);
    }

    public interface SinaUserInfoResultListener {
        void onSuccess(SinaUserInfoVO response);
    }
}
