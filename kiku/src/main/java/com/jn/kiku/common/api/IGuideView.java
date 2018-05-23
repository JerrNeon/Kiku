package com.jn.kiku.common.api;

import android.support.annotation.DrawableRes;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (引导页)
 * @create by: chenwei
 * @date 2018/5/17 17:29
 */
public interface IGuideView {

    /**
     * 获取引导页图片资源
     *
     * @return
     */
    @DrawableRes
    int[] getImgResourceIds();

    /**
     * 打开首页
     */
    void openMainActivity();
}
