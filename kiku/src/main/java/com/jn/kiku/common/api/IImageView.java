package com.jn.kiku.common.api;

import android.widget.ImageView;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (图片加载)
 * @create by: chenwei
 * @date 2018/5/11 17:50
 */
public interface IImageView {

    /**
     * 加载普通图片
     *
     * @param iv
     * @param url
     */
    void displayImage(ImageView iv, Object url);

    /**
     * 加载圆角图片
     *
     * @param iv
     * @param url
     */
    void displayRoundImage(ImageView iv, Object url);

    /**
     * 加载普通头像
     *
     * @param iv
     * @param url
     */
    void displayAvatar(ImageView iv, Object url);

    /**
     * 加载圆形头像
     *
     * @param iv
     * @param url
     */
    void displayRoundAvatar(ImageView iv, Object url);
}
