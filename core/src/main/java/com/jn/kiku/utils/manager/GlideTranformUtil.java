package com.jn.kiku.utils.manager;

import android.app.Activity;
import android.content.Context;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.jn.common.util.DensityUtils;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class GlideTranformUtil {

    public static Context getContext(Object context) {
        if (context instanceof Activity) {
            return ((Activity) context).getApplicationContext();
        } else if (context instanceof Fragment) {
            return ((Fragment) context).getContext();
        } else if (context instanceof Context) {
            return ((Context) context);
        } else
            throw new IllegalArgumentException("context type is no correct");
    }

    /**
     * 加载'圆角'高斯模糊图片
     *
     * @param radius  圆角半径
     * @param blurred 设置模糊度(在0.0到25.0之间)，默认”25"
     */
    @SuppressWarnings("unchecked")
    public static MultiTransformation withRadiusBlurred(Object context, int radius, int blurred) {
        return new MultiTransformation(
                new BlurTransformation(blurred),
                new CenterCrop(),
                new RoundedCornersTransformation(DensityUtils.getIntDip(getContext(context), radius), 0, RoundedCornersTransformation.CornerType.ALL));
    }

    /**
     * 加载高斯模糊图片
     *
     * @param blurred 设置模糊度(在0.0到25.0之间)，默认”25"
     * @param scale   图片缩放比例,默认“1”。
     */
    @SuppressWarnings("unchecked")
    public static MultiTransformation withBlurred(int blurred, int scale) {
        return new MultiTransformation(new BlurTransformation(blurred, scale));
    }

    /**
     * 加载'圆角'图片
     *
     * @param radius 圆角半径
     */
    @SuppressWarnings("unchecked")
    public static MultiTransformation withRadius(Object context, int radius) {
        return new MultiTransformation(new CenterCrop(),
                new RoundedCornersTransformation(DensityUtils.getIntDip(getContext(context), radius), 0, RoundedCornersTransformation.CornerType.ALL));
    }

    /**
     * 加载圆形图片带外框，颜色
     *
     * @param context     上下文
     * @param borderColor 外圈圆颜色
     * @param borderWith  外圈圆宽度
     */
    @SuppressWarnings("unchecked")
    public static MultiTransformation withCircleBorder(Object context, int borderWith, int borderColor) {
        return new MultiTransformation(new GlideCircleBorderTransform(getContext(context), borderWith, borderColor));
    }

    /**
     * 加载'圆角'图片
     *
     * @param radius     圆角半径
     * @param cornerType 圆角位置
     */
    @SuppressWarnings("unchecked")
    public static MultiTransformation withRadiusAndCornerType(Object context, int radius, RoundedCornersTransformation.CornerType cornerType) {
        return new MultiTransformation(new CenterCrop(),
                new RoundedCornersTransformation(DensityUtils.getIntDip(getContext(context), radius), 0, cornerType));
    }

    /**
     * 加载'圆角'图片
     *
     * @param radius     圆角半径
     * @param cornerType 圆角位置
     */
    @SuppressWarnings("unchecked")
    public static MultiTransformation withRadiusAndCornerType_Natural(Object context, int radius, RoundedCornersTransformation.CornerType cornerType) {
        return new MultiTransformation(new RoundedCornersTransformation(DensityUtils.getIntDip(getContext(context), radius), 0, cornerType));
    }
}
