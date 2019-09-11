package com.jn.kiku.utils.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jn.kiku.R;

/**
 * Author：Stevie.Chen Time：2019/7/31
 * Class Comment：Glide管理
 */
public class GlideManage {

    private static int defaultErrorImageResourceId = 0;//默认错误图片
    private static int defaultPlaceHolderImageResourceId = R.drawable.ic_default_placeholder;//默认占位图
    private static final int defaultPlaceHolderImageResourceId1 = R.drawable.ic_default_placeholder_black;//默认占位图
    private static int defaultErrorAvatarResourceId = 0;//默认错误头像
    private static int defaultPlaceHolderAvatarResourceId = 0;//默认占位头像

    /**
     * 初始化
     *
     * @param defaultPlaceHolderImageResourceIds  默认占位图片
     * @param defaultErrorImageResourceIds        默认错误图片
     * @param defaultPlaceHolderAvatarResourceIds 默认占位头像图片
     * @param defaultErrorAvatarResourceIds       默认错误头像图片
     */
    public static void init(@DrawableRes int defaultPlaceHolderImageResourceIds, @DrawableRes int defaultErrorImageResourceIds,
                            @DrawableRes int defaultPlaceHolderAvatarResourceIds, @DrawableRes int defaultErrorAvatarResourceIds) {
        if (defaultPlaceHolderImageResourceIds != 0)
            defaultPlaceHolderImageResourceId = defaultPlaceHolderImageResourceIds;
        if (defaultErrorImageResourceIds != 0)
            defaultErrorImageResourceId = defaultErrorImageResourceIds;
        if (defaultPlaceHolderAvatarResourceIds != 0)
            defaultPlaceHolderAvatarResourceId = defaultPlaceHolderAvatarResourceIds;
        if (defaultErrorAvatarResourceIds != 0)
            defaultErrorAvatarResourceId = defaultErrorAvatarResourceIds;
    }

    /**
     * 加载图片
     *
     * @param context   context资源对象
     * @param url       加载资源路径
     * @param imageView ImageView控件
     */
    public static void displayImage(final Object context, final Object url, final ImageView imageView) {
        displayImage(context, url, imageView, false, false, false, true);
    }

    /**
     * 加载图片
     *
     * @param context   context资源对象
     * @param url       加载资源路径
     * @param imageView ImageView控件
     * @param isCircle  是否是圆形图片
     * @param isAvatar  是否是头像
     */
    public static void displayImage(final Object context, final Object url, final ImageView imageView, final boolean isCircle, final boolean isAvatar) {
        displayImage(context, url, imageView, isCircle, isAvatar, false, true);
    }

    /**
     * 加载图片
     *
     * @param context   context资源对象
     * @param url       加载资源路径
     * @param imageView ImageView控件
     * @param radius    圆角弧度
     */
    public static void displayImage(final Object context, final Object url, final ImageView imageView, final int radius) {
        displayImage(context, url, imageView, radius, false, true);
    }


    /**
     * 加载图片
     *
     * @param context   context资源对象
     * @param url       加载资源路径
     * @param imageView ImageView控件
     * @param isCircle  是否是圆形图片
     * @param isAvatar  是否是头像
     * @param isGif     是否是GIf
     * @param isCache   是否缓存
     */
    public static void displayImage(final Object context, final Object url, final ImageView imageView, final boolean isCircle, final boolean isAvatar, boolean isGif, boolean isCache) {
        if (!isCircle && !isAvatar)
            GlideUtil.displayImage(context, url, imageView, defaultPlaceHolderImageResourceId, defaultErrorImageResourceId, isGif, isCache);
        else if (isCircle && !isAvatar)
            GlideUtil.displayImageWithCircle(context, url, imageView, defaultPlaceHolderImageResourceId, defaultErrorImageResourceId, isGif, isCache);
        else if (!isCircle)
            GlideUtil.displayImage(context, url, imageView, defaultPlaceHolderAvatarResourceId, defaultErrorAvatarResourceId, isGif, isCache);
        else
            GlideUtil.displayImageWithCircle(context, url, imageView, defaultPlaceHolderAvatarResourceId, defaultErrorAvatarResourceId, isGif, isCache);
    }

    /**
     * 加载圆角图片
     *
     * @param context   context资源对象
     * @param url       加载资源路径
     * @param imageView ImageView控件
     * @param isGif     是否是GIf
     * @param isCache   是否缓存
     */
    public static void displayImage(final Object context, final Object url, final ImageView imageView, final int radius, boolean isGif, boolean isCache) {
        GlideUtil.displayImageWithRound(context, url, imageView, defaultPlaceHolderImageResourceId, defaultErrorImageResourceId, radius, isGif, isCache);
    }

    /**
     * 加载大图片(用于全屏看图时用)
     *
     * @param context   context资源对象
     * @param url       加载资源路径
     * @param imageView ImageView控件
     */
    public static void displayImageBig(final Object context, final Object url, final ImageView imageView) {
        GlideUtil.displayImage(context, url, imageView, defaultPlaceHolderImageResourceId1, defaultErrorImageResourceId, false, false);
    }

    /**
     * 加载图片(获取图片的尺寸信息)
     *
     * @param context context资源对象
     * @param url     加载资源路径
     * @param target  目标
     */
    public static void displayImage(final Object context, final Object url, final SimpleTarget<Bitmap> target) {
        GlideUtil.displayImage(context, url, target);
    }

    /**
     * 加载图片(监听图片的加载进度)
     *
     * @param context context资源对象
     * @param url     加载资源路径
     * @param target  目标
     *                <p>
     *                onLoadStarted() - 开始加载
     *                onResourceReady() - 加载成功
     *                onLoadFailed() - 加载失败
     *                </P>
     */
    public static void displayImage(final Object context, final Object url, final DrawableImageViewTarget target) {
        GlideUtil.displayImage(context, url, defaultPlaceHolderImageResourceId, defaultErrorImageResourceId, target);
    }

    /**
     * 清除内存
     *
     * @param context Context
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    /**
     * 交给 Glide 处理内存情况。
     *
     * @param context Context
     */
    public static void trimMemory(Context context, int level) {
        Glide.get(context).trimMemory(level);
    }

    /**
     * 恢复加载
     */
    public static void resumeRequests(final Object context) {
        GlideUtil.resumeRequests(context);
    }

    /**
     * 停止加载
     */
    public static void pauseRequests(final Object context) {
        GlideUtil.pauseRequests(context);
    }
}
