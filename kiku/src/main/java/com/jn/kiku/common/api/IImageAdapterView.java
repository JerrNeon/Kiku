package com.jn.kiku.common.api;

import android.support.annotation.IdRes;

import com.jn.kiku.adapter.BaseAdapterViewHolder;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (图片加载)
 * @create by: chenwei
 * @date 2018/5/11 17:50
 */
public interface IImageAdapterView {

    /**
     * 加载普通图片
     *
     * @param viewId
     * @param url
     */
    BaseAdapterViewHolder displayImage(final Object context, @IdRes int viewId, Object url);

    /**
     * 加载圆角图片
     *
     * @param viewId
     * @param url
     */
    BaseAdapterViewHolder displayRoundImage(final Object context, @IdRes int viewId, Object url);

    /**
     * 加载普通头像
     *
     * @param viewId
     * @param url
     */
    BaseAdapterViewHolder displayAvatar(final Object context, @IdRes int viewId, Object url);

    /**
     * 加载圆形头像
     *
     * @param viewId
     * @param url
     */
    BaseAdapterViewHolder displayRoundAvatar(final Object context, @IdRes int viewId, Object url);
}
