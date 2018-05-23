package com.jn.kiku.common.api;

import android.support.annotation.ColorRes;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (TabLayout)
 * @create by: chenwei
 * @date 2018/5/16 16:27
 */
public interface ITabLayouView {

    /**
     * 初始化TabLayout相关控件
     */
    void initTabView();

    /**
     * 设置TabLayout样式
     */
    void setTabLayoutAttribute();

    /**
     * 设置ViewPager的预加载数量(没有使用懒加载的Fragment无需调用此方法)
     * 注：设置了预加载为总Tab的个数后，每次点击Tab时就不会再去重新请求数据(还没找到更好的方法解决真正的懒加载问题)
     */
    void setOffscreenPageLimit();

    /**
     * 设置TabLayout子项左右margin值
     *
     * @param marginLeft  左margin
     * @param marginRight 右margin
     */
    void setTabLayoutIndicatorMargin(int marginLeft, int marginRight);

    /**
     * 获取指示器颜色
     *
     * @return
     */
    @ColorRes
    int getTabIndicatorColorId();

    /**
     * 获取未选中文字颜色
     *
     * @return
     */
    @ColorRes
    int getTabNormalTextColorId();

    /**
     * 获取选中文字颜色
     *
     * @return
     */
    @ColorRes
    int getTabSelectedTextColorId();

    /**
     * 获取子项左右margin
     *
     * @return
     */
    int getTabItemMargin();
}
