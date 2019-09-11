package com.jn.common;

import com.google.gson.JsonParseException;
import com.jn.common.util.gson.JsonUtils;
import com.jn.common.util.SPUtils;

/**
 * Author：Stevie.Chen Time：2019/8/2
 * Class Comment：SharePreference管理
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
    private static final String VIDEO_AUTO_PLAY = "video_auto_play";//视频自动播放 0:关闭 1:使用移动流量和WIFI 2:仅WIFI
    private static final String IS_WIFI_UPLOAD_LARGE_FILE = "is_wifi_upload_large_file";//只使用WIFI上传大文件

    private SPManage() {
    }

    private static class INSTANCE {
        private static SPManage instance = new SPManage();
    }

    public static SPManage getInstance() {
        return INSTANCE.instance;
    }

    public <T> T getUserInfo(Class<T> tClass) {
        T userInfo = null;
        try {
            userInfo = JsonUtils.getInstance().toObject((String) SPUtils.get(USER_INFO, ""), tClass);
        } catch (NullPointerException | JsonParseException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    public void setUserInfo(Object userInfo) {
        SPUtils.put(USER_INFO, JsonUtils.getInstance().toJson(userInfo));
    }

    public void clearUserInfo() {
        SPUtils.remove(USER_INFO);
    }

    public boolean getFirstGuide() {
        return (boolean) SPUtils.get(FIRST_GUIDE, true);
    }

    public void setFirstGuide(boolean isFirstGuide) {
        SPUtils.put(FIRST_GUIDE, isFirstGuide);
    }

    public boolean getPushEnable() {
        return (boolean) SPUtils.get(PUSH_ENABLE, true);
    }

    public void setPushEnable(boolean pushEnable) {
        SPUtils.put(PUSH_ENABLE, pushEnable);
    }

    public boolean isSoundEnable() {
        return (boolean) SPUtils.get(SOUND_ENABLE, true);
    }

    public void setSoundEnable(boolean soundEnable) {
        SPUtils.put(SOUND_ENABLE, soundEnable);
    }

    public boolean isVirbateEnable() {
        return (boolean) SPUtils.get(VIRBATE_ENABLE, true);
    }

    public void setVirbateEnable(boolean virbateEnable) {
        SPUtils.put(VIRBATE_ENABLE, virbateEnable);
    }

    public boolean isMessageEnable() {
        return (boolean) SPUtils.get(MESSAGE_ENABLE, true);
    }

    public void setMessageEnable(boolean messageEnable) {
        SPUtils.put(MESSAGE_ENABLE, messageEnable);
    }

    public String getAliasValue() {
        return (String) SPUtils.get(ALIAS_VALUE, "");
    }

    public void setAliasValue(String aliasValue) {
        SPUtils.put(ALIAS_VALUE, aliasValue);
    }

    public String getExceptionMessage() {
        return (String) SPUtils.get(EXCEPTION_MESSAGE, "");
    }

    public void setExceptionMessage(String exception_message) {
        SPUtils.put(EXCEPTION_MESSAGE, exception_message);
    }

    public void clearExceptionMessage() {
        SPUtils.remove(EXCEPTION_MESSAGE);
    }

    public int getVideoAutoPlay() {
        return (int) SPUtils.get(VIDEO_AUTO_PLAY, 2);
    }

    public void setVideoAutoPlay(int videoAutoPlay) {
        SPUtils.put(VIDEO_AUTO_PLAY, videoAutoPlay);
    }

    public boolean isWifiUploadLargeFile() {
        return (boolean) SPUtils.get(IS_WIFI_UPLOAD_LARGE_FILE, true);
    }

    public void setWifiUploadLargeFile(boolean isWifiUploadLargeFile) {
        SPUtils.put(IS_WIFI_UPLOAD_LARGE_FILE, isWifiUploadLargeFile);
    }
}
