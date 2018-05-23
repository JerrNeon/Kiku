package com.jn.kiku.common.api;

import android.support.annotation.LayoutRes;

import com.jn.kiku.annonation.RefreshOperateType;
import com.jn.kiku.annonation.RefreshViewType;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (刷新和加载更多)
 * @create by: chenwei
 * @date 2018/5/17 14:58
 */
public interface IRefreshView {

    /**
     * 获取主内容区域布局
     *
     * @return
     */
    @LayoutRes
    int getLayoutItemResourceId();

    /**
     * 获取分页索引值
     *
     * @return
     */
    int getPageIndex();

    /**
     * 获取分页大小
     *
     * @return
     */
    int getPageSize();

    /**
     * 获取总大小
     *
     * @return
     */
    int getTotalSize();

    /**
     * 初始化刷新相关控件
     */
    void initRefreshView();

    /**
     * 获取刷新类型
     *
     * @return
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
     * 是否可以加载更多
     *
     * @return true：可以加载更多
     */
    boolean isLoadMoreEnable();
}
