package com.jn.kiku.common.api;

import androidx.annotation.DrawableRes;

/**
 * Author：Stevie.Chen Time：2019/8/13
 * Class Comment：引导页
 */
public interface ISplashView {

    /**
     * 获取引导页图片资源
     */
    @DrawableRes
    int getImgResourceId();

    /**
     * 获取所有需要用到的权限
     */
    String[] getAllPermissions();

    /**
     * 初始化App中需要用到的所有权限
     */
    void requestAllPermission();

    /**
     * 打开引导页
     */
    void openGuideActivity();

    /**
     * 打开首页
     */
    void openMainActivity();
}
