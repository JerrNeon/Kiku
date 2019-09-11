package com.jn.kiku.utils.manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;

import static com.bumptech.glide.Glide.with;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Glide工具类)
 * @create by: chenwei
 * @date 2016/10/9 11:14
 */
public class GlideUtil {

    /**
     * @param placeholderResourceId
     * @param errorResourceId
     * @return <p>
     * DiskCacheStrategy有五个常量：
     * DiskCacheStrategy.ALL 使用DATA和RESOURCE缓存远程数据，仅使用RESOURCE来缓存本地数据。
     * DiskCacheStrategy.NONE 不使用磁盘缓存
     * DiskCacheStrategy.DATA 在资源解码前就将原始数据写入磁盘缓存
     * DiskCacheStrategy.RESOURCE 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
     * DiskCacheStrategy.AUTOMATIC 根据原始图片数据和资源编码策略来自动选择磁盘缓存策略。
     * 默认的策略是DiskCacheStrategy.AUTOMATIC
     * </p>
     */
    @SuppressLint("CheckResult")
    private static RequestOptions getRequestOptions(final int placeholderResourceId, final int errorResourceId, final boolean isCache) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(placeholderResourceId)
                .error(errorResourceId)
                .priority(Priority.HIGH);
        if (!isCache) {
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        }
        return requestOptions;
    }

    /**
     * 获取图片请求管理对象
     *
     * @param context context资源对象
     * @return
     */
    private static RequestManager getRequestManager(Object context) {
        RequestManager requestManager = null;
        if (context instanceof Activity) {
            requestManager = with((Activity) context);
        } else if (context instanceof Fragment) {
            requestManager = with((Fragment) context);
        } else if (context instanceof Context) {
            requestManager = with((Context) context);
        } else
            throw new IllegalArgumentException("context type is no correct");
        return requestManager;
    }

    /**
     * 获取资源对象
     *
     * @param context context资源对象
     * @return
     */
    private static Resources getResources(Object context) {
        Resources resources = null;
        if (context instanceof Activity) {
            resources = ((Activity) context).getResources();
        } else if (context instanceof Fragment) {
            resources = ((Fragment) context).getResources();
        } else if (context instanceof Context) {
            resources = ((Context) context).getResources();
        } else
            throw new IllegalArgumentException("context type is no correct");
        return resources;
    }

    /**
     * 显示图片
     *
     * @param context               context资源对象
     * @param url                   图片地址
     * @param placeholderResourceId 占位图图片
     * @param errorResourceId       加载错误图片
     * @param target                监听图片加载结果
     */
    public static void displayImage(final Object context, final Object url, final int placeholderResourceId, final int errorResourceId, final DrawableImageViewTarget target) {
        RequestOptions requestOptions = getRequestOptions(placeholderResourceId, errorResourceId, false);
        RequestManager requestManager = getRequestManager(context);
        requestManager.load(url).apply(requestOptions).into(target);
    }

    /**
     * 显示图片(使用默认错误图片(有占位图))
     *
     * @param context context资源对象
     * @param url     图片地址
     * @param target  监听图片加载结果
     */
    @SuppressWarnings("unchecked")
    public static void displayImage(final Object context, final Object url, final SimpleTarget target) {
        RequestManager requestManager = getRequestManager(context);
        requestManager.asBitmap().load(url).into(target);
    }

    /**
     * 显示图片
     *
     * @param context               context资源对象
     * @param url                   图片地址
     * @param imageView             imageview
     * @param placeholderResourceId 占位图图片
     * @param errorResourceId       错误图片
     * @param isGif                 是否是Gif图
     * @param isCache               是否缓存
     */
    public static void displayImage(final Object context, final Object url, ImageView imageView, final int placeholderResourceId, final int errorResourceId, boolean isGif, boolean isCache) {
        RequestOptions requestOptions = getRequestOptions(placeholderResourceId, errorResourceId, isCache);
        RequestManager requestManager = getRequestManager(context);
        if (isGif)
            requestManager.asGif().load(url).apply(requestOptions).into(imageView);
        else
            requestManager.load(url).apply(requestOptions).into(imageView);
    }


    /**
     * 显示圆形图片
     *
     * @param context         context资源对象
     * @param url             图片地址
     * @param imageView       imageview
     * @param errorResourceId 错误图片
     */
    public static void displayImageWithCircle(final Object context, final Object url, final ImageView imageView, final int placeholderResourceId, final int errorResourceId, final boolean isGif, final boolean isCache) {
        RequestOptions requestOptions = getRequestOptions(placeholderResourceId, errorResourceId, isCache);
        RequestManager requestManager = getRequestManager(context);
        RequestBuilder requestBuilder = null;
        if (isGif)
            requestBuilder = requestManager.asGif();
        else
            requestBuilder = requestManager.asBitmap();
        requestBuilder.load(url).apply(requestOptions).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(context), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    /**
     * 显示圆形图片
     *
     * @param context         context资源对象
     * @param url             图片地址
     * @param imageView       imageview
     * @param errorResourceId 错误图片
     */
    @SuppressLint("CheckResult")
    @SuppressWarnings("unchecked")
    public static void displayImageWithRound(final Object context, final Object url, final ImageView imageView, final int placeholderResourceId, final int errorResourceId, final int radius, final boolean isGif, final boolean isCache) {
        RequestOptions requestOptions = getRequestOptions(placeholderResourceId, errorResourceId, isCache);
        RequestManager requestManager = getRequestManager(context);
        RequestBuilder requestBuilder = null;
        if (isGif)
            requestBuilder = requestManager.asGif();
        else
            requestBuilder = requestManager.asBitmap();
        requestOptions.transform(GlideTranformUtil.withRadius(context, radius));
        requestBuilder.load(url).apply(requestOptions).into(imageView);
    }

    /**
     * 恢复加载
     */
    public static void resumeRequests(final Object context) {
        RequestManager requestManager = getRequestManager(context);
        requestManager.resumeRequests();
    }

    /**
     * 停止加载
     */
    public static void pauseRequests(final Object context) {
        RequestManager requestManager = getRequestManager(context);
        requestManager.resumeRequests();
    }
}
