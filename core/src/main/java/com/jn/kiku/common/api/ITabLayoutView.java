package com.jn.kiku.common.api;

import androidx.annotation.ColorRes;

/**
 * Author：Stevie.Chen Time：2019/8/13
 * Class Comment：TabLayout
 */
public interface ITabLayoutView {

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
     * TabLayout子项内容宽度是match_parent还是wrap_content
     *
     * @return true：match_parent  false：wrap_content  默认true
     */
    default boolean isTabIndicatorFullWidth() {
        return false;
    }

    /**
     * 获取指示器颜色
     */
    @ColorRes
    int getTabIndicatorColorId();

    /**
     * 获取未选中文字颜色
     */
    @ColorRes
    int getTabNormalTextColorId();

    /**
     * 获取选中文字颜色
     */
    @ColorRes
    int getTabSelectedTextColorId();
}
