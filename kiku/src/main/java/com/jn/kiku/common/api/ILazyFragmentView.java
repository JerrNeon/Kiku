package com.jn.kiku.common.api;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Fragment懒加载)
 * @create by: chenwei
 * @date 2018/5/16 15:41
 */
public interface ILazyFragmentView {

    /**
     * 是否带懒加载的Fragment
     *
     * @return true：带懒加载 false：不带懒加载
     */
    boolean isLazyLoadFragment();

    /**
     * 页面可见
     */
    void onFragmentVisible();

    /**
     * 页面不可见
     */
    void onFragmentInvisible();

    /**
     * 懒加载
     */
    void onFragmentLazyLoad();
}
