package com.jn.kiku.ttp.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.jn.kiku.ttp.TtpConstants;
import com.jn.kiku.ttp.common.ILogToastView;
import com.jn.kiku.ttp.common.manage.RetrofitTtpManage;
import com.jn.kiku.ttp.common.utils.JsonUtils;
import com.jn.kiku.ttp.common.utils.ToastUtil;
import com.jn.kiku.ttp.share.qq.QqApiService;
import com.jn.kiku.ttp.share.qq.QqShareMessage;
import com.jn.kiku.ttp.share.qq.QqUserInfoVO;
import com.tencent.connect.UserInfo;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.jn.kiku.ttp.share.QqManage.QQType.LOGIN;
import static com.jn.kiku.ttp.share.QqManage.QQType.SHARE;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (QQ登录 、 分享管理)
 * @create by: chenwei
 * @date 2017/3/9 14:20
 */
public class QqManage implements ILogToastView {

    private static QqManage sMQqManage = null;
    private Context mContext = null;
    private Tencent mTencent = null;
    private static final String mScope = "all";//要所有权限，不然会再次申请增量权限，这里不要设置成get_user_info,add_t
    private QqResultListener mQqResultListener = null;//登录、分享结果监听
    private QqUserInfoResultListener mQqUserInfoResultListener = null;//用户信息监听
    private @QQType
    int mQQType;

    @IntDef({SHARE, LOGIN})
    @Retention(RetentionPolicy.SOURCE)
    @interface QQType {
        int SHARE = 1;
        int LOGIN = 2;
    }

    public static synchronized QqManage getInstance(Context context) {
        if (sMQqManage == null)
            sMQqManage = new QqManage(context.getApplicationContext());
        return sMQqManage;
    }


    private QqManage(Context context) {
        this.mContext = context;
    }

    private void init() {
        if (mTencent == null)
            mTencent = Tencent.createInstance(TtpConstants.QQ_APP_ID, mContext);
    }

    /**
     * 登录
     *
     * @param activity activity
     * @param listener 结果监听
     */
    public void login(Activity activity, QqResultListener listener) {
        init();
        if (!checkTencentAvailable())
            return;
        mQQType = LOGIN;
        mQqResultListener = listener;
        mTencent.login(activity, mScope, new IUiListenerIml());
    }

    /**
     * 登录
     *
     * @param activity activity
     * @param listener 用户信息结果监听
     */
    public void login(Activity activity, QqUserInfoResultListener listener) {
        init();
        if (!checkTencentAvailable())
            return;
        mQQType = LOGIN;
        mQqUserInfoResultListener = listener;
        mTencent.login(activity, mScope, new IUiListenerIml());
    }

    /**
     * 登录
     *
     * @param fragment fragment
     * @param listener 结果监听
     */
    public void login(Fragment fragment, QqResultListener listener) {
        init();
        if (!checkTencentAvailable())
            return;
        mQQType = LOGIN;
        mQqResultListener = listener;
        mTencent.login(fragment, mScope, new IUiListenerIml());
    }

    /**
     * 登录
     *
     * @param fragment fragment
     * @param listener 用户信息结果监听
     */
    public void login(Fragment fragment, QqUserInfoResultListener listener) {
        init();
        if (!checkTencentAvailable())
            return;
        mQQType = LOGIN;
        mQqUserInfoResultListener = listener;
        mTencent.login(fragment, mScope, new IUiListenerIml());
    }


    /**
     * 注销
     */
    public void logout() {
        init();
        if (!checkTencentAvailable())
            return;
        mTencent.logout(mContext);
    }

    /**
     * QQ分享
     *
     * @param activity activity
     * @param params   分享参数
     * @param listener 结果监听
     *                 <p>
     *                 Bundle params = new Bundle();
     *                 params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
     *                 //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_SUMMARY不能全为空，最少必须有一个是有值的。
     *                 params.putString(QQShare.SHARE_TO_QQ_TITLE, "我在测试");
     *                 //这条分享消息被好友点击后的跳转URL。
     *                 params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://connect.qq.com/");
     *                 //分享的图片URL
     *                 params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
     *                 "http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg");
     *                 //分享的消息摘要，最长50个字
     *                 params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "测试");
     *                 //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
     *                 params.putString(QQShare.SHARE_TO_QQ_APP_NAME, mContext.getResources().getString(R.string.app_name));
     *                 </p>
     */
    public void shareWithQQ(Activity activity, Bundle params, QqResultListener listener) {
        init();
        if (!checkTencentAvailable())
            return;
        mQQType = SHARE;
        mQqResultListener = listener;
        if (params != null) {
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
            if ("".equals(params.getString(QQShare.SHARE_TO_QQ_APP_NAME, "")))
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "test");
            mTencent.shareToQQ(activity, params, new IUiListenerIml());
        }
    }

    /**
     * QQ分享
     *
     * @param activity       activity
     * @param qqShareMessage 分享参数
     * @param listener       结果监听
     *                       <p>
     *                       Bundle params = new Bundle();
     *                       params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
     *                       //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_SUMMARY不能全为空，最少必须有一个是有值的。
     *                       params.putString(QQShare.SHARE_TO_QQ_TITLE, "我在测试");
     *                       //这条分享消息被好友点击后的跳转URL。
     *                       params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://connect.qq.com/");
     *                       //分享的图片URL
     *                       params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
     *                       "http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg");
     *                       //分享的消息摘要，最长50个字
     *                       params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "测试");
     *                       //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
     *                       params.putString(QQShare.SHARE_TO_QQ_APP_NAME, mContext.getResources().getString(R.string.app_name));
     *                       </p>
     */
    public void shareWithQQ(Activity activity, QqShareMessage qqShareMessage, QqResultListener listener) {
        init();
        if (!checkTencentAvailable())
            return;
        mQQType = SHARE;
        mQqResultListener = listener;
        if (qqShareMessage != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            if (qqShareMessage.getAppName() != null)
                bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, qqShareMessage.getAppName());
            if (qqShareMessage.getTitle() != null)
                bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, qqShareMessage.getTitle());//必填
            if (qqShareMessage.getTargetUrl() != null)
                bundle.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, qqShareMessage.getTargetUrl());//必填
            if (qqShareMessage.getSummary() != null)
                bundle.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, qqShareMessage.getSummary());//选填
            if (qqShareMessage.getImageUrl() != null)
                bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, qqShareMessage.getImageUrl());// 图片地址
            mTencent.shareToQQ(activity, bundle, new IUiListenerIml());
        }
    }

    /**
     * Qzone分享
     *
     * @param activity activity
     * @param params   分享参数
     * @param listener 结果监听
     */
    public void shareWithQzone(Activity activity, Bundle params, QqResultListener listener) {
        init();
        if (!checkTencentAvailable())
            return;
        mQQType = SHARE;
        mQqResultListener = listener;
        if (params == null) {
            Bundle bundle = new Bundle();
            bundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
            bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, "标题");//必填
            bundle.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://connect.qq.com/");//必填
            bundle.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "摘要");//选填
            ArrayList<String> imgUrlList = new ArrayList<>();
            imgUrlList.add("http://f.hiphotos.baidu.com/image/h%3D200/sign=6f05c5f929738bd4db21b531918a876c/6a600c338744ebf8affdde1bdef9d72a6059a702.jpg");
            bundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrlList);// 图片地址
            mTencent.shareToQzone(activity, bundle, new IUiListenerIml());
        } else
            mTencent.shareToQzone(activity, params, new IUiListenerIml());
    }

    /**
     * Qzone分享
     *
     * @param activity       activity
     * @param qqShareMessage 分享参数
     * @param listener       结果监听
     */
    public void shareWithQzone(Activity activity, QqShareMessage qqShareMessage, QqResultListener listener) {
        init();
        if (!checkTencentAvailable())
            return;
        mQQType = SHARE;
        mQqResultListener = listener;
        if (qqShareMessage != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
            if (qqShareMessage.getTitle() != null)
                bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, qqShareMessage.getTitle());//必填
            if (qqShareMessage.getTargetUrl() != null)
                bundle.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, qqShareMessage.getTargetUrl());//必填
            if (qqShareMessage.getSummary() != null)
                bundle.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, qqShareMessage.getSummary());//选填
            if (qqShareMessage.getImageUrl() != null) {
                ArrayList<String> imgUrlList = new ArrayList<>();
                imgUrlList.add(qqShareMessage.getImageUrl());
                bundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrlList);// 图片地址
            }
            mTencent.shareToQzone(activity, bundle, new IUiListenerIml());
        }
    }

    public boolean onActivityResultData(int requestCode, int resultCode, Intent data) {
        return Tencent.onActivityResultData(requestCode, resultCode, data, new IUiListenerIml());
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        if (null == mTencent) return;
        UserInfo userInfo = new UserInfo(mContext, mTencent.getQQToken());
        userInfo.getUserInfo(new IUiUserInfoListenerIml());
    }

    /**
     * 获取UnionID
     * <p>
     * "client_id": "",
     * "openid": "",
     * "unionid": ""
     * </P>
     */
    public void getUnionID(final QqUserInfoVO userInfoVO) {
        if (mTencent == null) return;
        RetrofitTtpManage.getInstance()
                .create(QqApiService.QQBaseUrl, QqApiService.class)
                .getUserInfo(QqApiService.GET_UNIONID, mTencent.getAccessToken(), "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<QqUserInfoVO>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(QqUserInfoVO qqUserInfoVO) {
                        userInfoVO
                                .setClient_id(qqUserInfoVO.getClient_id())
                                .setOpenid(qqUserInfoVO.getOpenid())
                                .setUnionid(qqUserInfoVO.getUnionid());
                        if (mQqUserInfoResultListener != null)
                            mQqUserInfoResultListener.onSuccess(userInfoVO);
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

    private boolean checkTencentAvailable() {
        if (mTencent == null)
            return false;
//        if (mTencent.isSessionValid())
//            return false;
        return true;
    }

    /**
     * 登录、分享回调
     */
    private class IUiListenerIml implements IUiListener {
        @Override
        public void onComplete(Object response) {
            if (null == response) {
                logI("onComplete: 登录失败");
                if (mQQType == LOGIN)
                    ToastUtil.showToast(mContext, "登录失败");
                else if (mQQType == SHARE)
                    ToastUtil.showToast(mContext, "分享失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                logI("onComplete: 登录失败");
                if (mQQType == LOGIN)
                    ToastUtil.showToast(mContext, "登录失败");
                else if (mQQType == SHARE)
                    ToastUtil.showToast(mContext, "分享失败");
                return;
            }
            logI("onComplete: " + JsonUtils.toJson(jsonResponse));
            if (mQQType == LOGIN) {
                //ToastUtil.showToast(mContext, "登录成功");
            } else if (mQQType == SHARE)
                ToastUtil.showToast(mContext, "分享成功");
            int ret = jsonResponse.optInt("ret");
            if (ret == 0) {
                if (mQqResultListener != null)
                    mQqResultListener.onSuccess(jsonResponse);
                if (mTencent != null) {
                    String openId = jsonResponse.optString("openid");
                    String accessToken = jsonResponse.optString("access_token");
                    long expires = jsonResponse.optLong("expires_in");
                    mTencent.setOpenId(openId);
                    mTencent.setAccessToken(accessToken, expires + "");
                    getUserInfo();
                }
            }
        }

        @Override
        public void onError(UiError uiError) {
            logE("onError: " + uiError.errorMessage);
            if (mQQType == LOGIN)
                ToastUtil.showToast(mContext, "登录失败");
            else if (mQQType == SHARE)
                ToastUtil.showToast(mContext, "分享失败");
            if (mQqResultListener != null)
                mQqResultListener.onFailure();
        }

        @Override
        public void onCancel() {
            logI("onCancel: ");
            if (mQQType == LOGIN)
                ToastUtil.showToast(mContext, "取消登录");
            else if (mQQType == SHARE)
                ToastUtil.showToast(mContext, "取消分享");
        }
    }

    /**
     * 用户信息回调
     */
    private class IUiUserInfoListenerIml implements IUiListener {
        @Override
        public void onComplete(Object response) {
            if (null == response) {
                logI("onComplete: 获取用户信息失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                logI("onComplete: 获取用户信息失败");
                return;
            }
            int ret = jsonResponse.optInt("ret");
            if (ret == 0) {
                try {
                    String json = jsonResponse.toString();
                    QqUserInfoVO qqUserInfoVO = JsonUtils.toObject(json, QqUserInfoVO.class);
                    getUnionID(qqUserInfoVO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    }

    @Override
    public void logI(String message) {
        Log.i(getClass().getSimpleName(), String.format(messageFormat, getClass().getSimpleName(), message));
    }

    @Override
    public void logE(String message) {
        Log.i(getClass().getSimpleName(), String.format(messageFormat, getClass().getSimpleName(), message));
    }

    @Override
    public void showToast(String message) {
        ToastUtil.showToast(mContext, message);
    }

    @Override
    public void showToast(String message, int duration) {
        ToastUtil.showToast(mContext, message, duration);
    }

    public void onDestroy() {
        if (mTencent != null)
            mTencent = null;
        if (mQqResultListener != null)
            mQqResultListener = null;
        if (mQqUserInfoResultListener != null)
            mQqUserInfoResultListener = null;
    }

    public interface QqResultListener {
        void onSuccess(JSONObject response);

        void onFailure();
    }

    public interface QqUserInfoResultListener {
        void onSuccess(QqUserInfoVO response);
    }

}
