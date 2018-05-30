package com.jn.kiku.utils.manager;

import android.content.Context;

import com.google.gson.JsonParseException;
import com.jn.kiku.utils.SPUtils;
import com.jn.kiku.utils.gson.JsonUtils;


/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (SharePreference管理)
 * @create by: chenwei
 * @date 2017/3/14 17:43
 */
public class SPManage {

    private static final String USER_INFO = "user_info";//用户信息
    private static final String FIRST_GUIDE = "first_guide";//是否首次进入引导页
    private static final String PUSH_ENABLE = "push_enable";//推送是否可用
    private static final String SOUND_ENABLE = "sound_enable";//声音是否可用
    private static final String VIRBATE_ENABLE = "virbate_enable";//震动是否可用
    private static final String MESSAGE_ENABLE = "message_enable";//消息是否可用
    private static final String ALIAS_VALUE = "alias_value";//别名值
    private static final String EXCEPTION_MESSAGE = "exception_message";//异常信息

    private static SPManage instance = null;
    private Context mContext = null;

    private SPManage(Context context) {
        this.mContext = context;
    }

    public static synchronized SPManage getInstance(Context context) {
        if (instance == null)
            instance = new SPManage(context.getApplicationContext());
        return instance;
    }

    public <T> T getUserInfo(Class<T> tClass) {
        T userInfo = null;
        try {
            userInfo = JsonUtils.toObject((String) SPUtils.get(mContext, USER_INFO, ""), tClass);
        } catch (NullPointerException | JsonParseException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    public void setUserInfo(Object userInfo) {
        SPUtils.put(mContext, USER_INFO, JsonUtils.toJson(userInfo));
    }

    public void clearUserInfo() {
        SPUtils.remove(mContext, USER_INFO);
    }

    public boolean getFirstGuide() {
        return (boolean) SPUtils.get(mContext, FIRST_GUIDE, true);
    }

    public void setFirstGuide(boolean isFirstGuide) {
        SPUtils.put(mContext, FIRST_GUIDE, isFirstGuide);
    }

    public boolean getPushEnable() {
        return (boolean) SPUtils.get(mContext, PUSH_ENABLE, true);
    }

    public void setPushEnable(boolean pushEnable) {
        SPUtils.put(mContext, PUSH_ENABLE, pushEnable);
    }

    public boolean isSoundEnable() {
        return (boolean) SPUtils.get(mContext, SOUND_ENABLE, true);
    }

    public void setSoundEnable(boolean soundEnable) {
        SPUtils.put(mContext, SOUND_ENABLE, soundEnable);
    }

    public boolean isVirbateEnable() {
        return (boolean) SPUtils.get(mContext, VIRBATE_ENABLE, true);
    }

    public void setVirbateEnable(boolean virbateEnable) {
        SPUtils.put(mContext, VIRBATE_ENABLE, virbateEnable);
    }

    public boolean isMessageEnable() {
        return (boolean) SPUtils.get(mContext, MESSAGE_ENABLE, true);
    }

    public void setMessageEnable(boolean messageEnable) {
        SPUtils.put(mContext, MESSAGE_ENABLE, messageEnable);
    }

    public String getAliasValue() {
        return (String) SPUtils.get(mContext, ALIAS_VALUE, "");
    }

    public void setAliasValue(String aliasValue) {
        SPUtils.put(mContext, ALIAS_VALUE, aliasValue);
    }

    public String getExceptionMessage() {
        return (String) SPUtils.get(mContext, EXCEPTION_MESSAGE, "");
    }

    public void setExceptionMessage(String exception_message) {
        SPUtils.put(mContext, EXCEPTION_MESSAGE, exception_message);
    }

    public void clearExceptionMessage() {
        SPUtils.remove(mContext, EXCEPTION_MESSAGE);
    }

}
