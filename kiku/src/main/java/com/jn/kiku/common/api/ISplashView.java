package com.jn.kiku.common.api;

import android.support.annotation.DrawableRes;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (引导页)
 * @create by: chenwei
 * @date 2018/5/17 16:57
 */
public interface ISplashView {

    /**
     * 获取引导页图片资源
     *
     * @return
     */
    @DrawableRes
    int getImgResourceId();

    /**
     * 获取所有需要用到的权限
     *
     * @return
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
