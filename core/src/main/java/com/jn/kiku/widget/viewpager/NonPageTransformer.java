package com.jn.kiku.widget.viewpager;

import androidx.viewpager.widget.ViewPager;
import android.view.View;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2017/4/10 13:47
 */
public class NonPageTransformer implements ViewPager.PageTransformer {

    public static final ViewPager.PageTransformer INSTANCE = new NonPageTransformer();

    @Override
    public void transformPage(View page, float position) {
        page.setScaleX(0.999f);//hack
    }

}
