package com.jn.kiku.common.api;

import android.widget.ImageView;

import com.jn.kiku.utils.manager.BaseManager;

/**
 * Author：Stevie.Chen Time：2019/7/31
 * Class Comment：图片加载
 */
public interface IImageView extends IRootView {

    /**
     * 加载普通图片
     */
    default void displayImage(ImageView iv, Object url) {
        BaseManager manager = getBaseManager();
        if (manager != null)
            manager.displayImage(iv, url);
    }

    /**
     * 加载圆形图片
     */
    default void displayCircleImage(ImageView iv, Object url) {
        BaseManager manager = getBaseManager();
        if (manager != null)
            manager.displayCircleImage(iv, url);
    }

    /**
     * 加载圆角图片
     */
    default void displayRoundImage(ImageView iv, Object url, int radius) {
        BaseManager manager = getBaseManager();
        if (manager != null)
            manager.displayRoundImage(iv, url, radius);
    }

    /**
     * 加载普通头像
     */
    default void displayAvatar(ImageView iv, Object url) {
        BaseManager manager = getBaseManager();
        if (manager != null)
            manager.displayAvatar(iv, url);
    }

    /**
     * 加载圆形头像
     */
    default void displayCircleAvatar(ImageView iv, Object url) {
        BaseManager manager = getBaseManager();
        if (manager != null)
            manager.displayCircleAvatar(iv, url);
    }
}
