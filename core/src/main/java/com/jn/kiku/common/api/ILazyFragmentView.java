package com.jn.kiku.common.api;

/**
 * Author：Stevie.Chen Time：2019/8/13
 * Class Comment：Fragment懒加载
 */
public interface ILazyFragmentView {

    /**
     * 是否带懒加载的Fragment
     *
     * @return true：带懒加载 false：不带懒加载
     */
    default boolean isLazyLoadFragment() {
        return false;
    }

    /**
     * 是否只加载一次
     *
     * @return true：是 false：不是 默认：true
     */
    default boolean isLoadOnlyOnce() {
        return true;
    }

    /**
     * 页面可见
     */
    default void onFragmentVisible() {
        onFragmentLazyLoad();
    }

    /**
     * 页面不可见
     */
    default void onFragmentInvisible() {

    }

    /**
     * 懒加载
     */
    void onFragmentLazyLoad();
}
