package com.jn.kiku.common.api;

import android.app.Activity;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jn.kiku.adapter.BaseAdapterViewHolder;

/**
 * Author：Stevie.Chen Time：2019/8/13
 * Class Comment：图片加载
 */
public interface IImageAdapterView extends IRootView {

    /**
     * 设置绑定生命周期的对象
     */
    void bindImageContext(Activity activity);

    /**
     * 设置绑定生命周期的对象
     */
    void bindImageContext(Fragment fragment);

    /**
     * 获取绑定生命周期的对象
     */
    Object getImageContext();

    /**
     * 加载普通图片
     */
    BaseViewHolder displayImage(@IdRes int viewId, Object url);

    /**
     * 加载圆角图片
     */
    BaseAdapterViewHolder displayRoundImage(@IdRes int viewId, Object url, int radius);

    /**
     * 加载圆角图片
     */
    BaseAdapterViewHolder displayCircleImage(@IdRes int viewId, Object url);

    /**
     * 加载普通头像
     */
    BaseAdapterViewHolder displayAvatar(@IdRes int viewId, Object url);

    /**
     * 加载圆形头像
     */
    BaseAdapterViewHolder displayCircleAvatar(@IdRes int viewId, Object url);

    /**
     * 加载自适应的图片
     */
    BaseAdapterViewHolder displayWrapImage(@IdRes int viewId, Object url);

    /**
     * 加载自适应的图片
     */
    BaseAdapterViewHolder displayWrapImage(@IdRes int viewId, Object url, int totalSpace);

    /**
     * 加载自适应的图片
     */
    BaseAdapterViewHolder displayWrapImage(@IdRes int viewId, Object url, int width, int totalSpace);
}
