package com.jn.kiku.common.api;

import android.support.annotation.LayoutRes;

import com.jn.kiku.annonation.LoadMoreEnableType;
import com.jn.kiku.annonation.RefreshOperateType;
import com.jn.kiku.annonation.RefreshViewType;
import com.jn.kiku.common.Config;

/**
 * Author：Stevie.Chen Time：2019/8/13
 * Class Comment：刷新和加载更多
 */
public interface IRefreshView {

    /**
     * 获取主内容区域布局
     */
    @LayoutRes
    int getLayoutItemResourceId();

    /**
     * 获取初始分页索引值
     */
    default int getInitPageIndex() {
        return Config.PAGE_INDEX;
    }

    /**
     * 获取初始分页大小
     */
    default int getInitPageSize() {
        return Config.PAGE_SIZE;
    }

    /**
     * 获取分页索引值
     */
    int getPageIndex();

    /**
     * 获取分页大小
     */
    int getPageSize();

    /**
     * 获取总大小
     */
    int getTotalSize();

    /**
     * 获取总页大小
     */
    int getTotalPage();

    /**
     * 初始化刷新相关控件
     */
    void initRefreshView();

    /**
     * 获取刷新类型
     */
    @RefreshViewType
    int getRefreshViewType();

    /**
     * 设置Refresh所在界面当前操作类型
     *
     * @param operateType @RefreshOperateType
     */
    void setRefreshOperateType(@RefreshOperateType int operateType);

    /**
     * 获取是否可以加载更多的判断标志
     */
    @LoadMoreEnableType
    int getLoadMoreEnableType();

    /**
     * 是否可以加载更多
     *
     * @return true：可以加载更多
     */
    boolean isLoadMoreEnable();
}
