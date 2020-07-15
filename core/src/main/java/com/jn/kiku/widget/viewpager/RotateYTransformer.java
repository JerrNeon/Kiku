package com.jn.kiku.widget.viewpager;

import android.annotation.TargetApi;
import android.os.Build;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2017/4/10 13:53
 */
public class RotateYTransformer extends BasePageTransformer {

    private static final float DEFAULT_MAX_ROTATE = 35f;
    private float mMaxRotate = DEFAULT_MAX_ROTATE;


    public RotateYTransformer() {
    }

    public RotateYTransformer(float maxRotate) {
        this(maxRotate, NonPageTransformer.INSTANCE);
    }

    public RotateYTransformer(ViewPager.PageTransformer pageTransformer) {
        this(DEFAULT_MAX_ROTATE, pageTransformer);
    }

    public RotateYTransformer(float maxRotate, ViewPager.PageTransformer pageTransformer) {
        mMaxRotate = maxRotate;
        mPageTransformer = pageTransformer;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void pageTransform(View view, float position) {
        view.setPivotY(view.getHeight() / 2);

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setRotationY(-1 * mMaxRotate);
            view.setPivotX(view.getWidth());
        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well

            view.setRotationY(position * mMaxRotate);

            if (position < 0)//[0,-1]
            {
                view.setPivotX(view.getWidth() * (DEFAULT_CENTER + DEFAULT_CENTER * (-position)));
                view.setPivotX(view.getWidth());
            } else//[1,0]
            {
                view.setPivotX(view.getWidth() * DEFAULT_CENTER * (1 - position));
                view.setPivotX(0);
            }

            // Scale the page down (between MIN_SCALE and 1)
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setRotationY(1 * mMaxRotate);
            view.setPivotX(0);
        }
    }
}
