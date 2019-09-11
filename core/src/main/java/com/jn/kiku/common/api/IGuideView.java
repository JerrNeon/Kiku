package com.jn.kiku.common.api;

import android.support.annotation.DrawableRes;

/**
 * Author：Stevie.Chen Time：2019/8/13
 * Class Comment：引导页
 */
public interface IGuideView {

    /**
     * 获取引导页图片资源
     */
    @DrawableRes
    int[] getImgResourceIds();

    /**
     * 打开首页
     */
    void openMainActivity();
}
