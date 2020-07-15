package com.jn.kiku.common.api;

import com.jn.kiku.annonation.PermissionType;
import com.jn.kiku.utils.manager.BaseManager;

import io.reactivex.functions.Consumer;

/**
 * Author：Stevie.Chen Time：2019/8/13
 * Class Comment：基础
 */
public interface IBaseView extends IRootView {

    /**
     * 初始化ButterKnife
     */
    default void initButterKnife() {
        BaseManager manager = getBaseManager();
        if (manager != null)
            manager.initButterKnife();
    }

    /**
     * 解绑ButterKnife
     */
    default void unbindButterKnife() {
        BaseManager manager = getBaseManager();
        if (manager != null)
            manager.unbindButterKnife();
    }

    /**
     * 初始化EventBus
     */
    default void initEventBus() {
        BaseManager manager = getBaseManager();
        if (manager != null)
            manager.initEventBus();
    }

    /**
     * 取消注册EventBus
     */
    default void unregisterEventBus() {
        BaseManager manager = getBaseManager();
        if (manager != null)
            manager.unregisterEventBus();
    }

    /**
     * 初始化RxPermissions
     */
    default void initRxPermissions() {
        BaseManager manager = getBaseManager();
        if (manager != null)
            manager.initRxPermissions();
    }

    /**
     * 请求权限
     *
     * @param permissionType @PermissionType
     * @param consumer       Consumer
     */
    default void requestPermission(@PermissionType final int permissionType, final Consumer<Boolean> consumer) {
        BaseManager manager = getBaseManager();
        if (manager != null)
            manager.requestPermission(permissionType, consumer);
    }

    /**
     * 设置状态栏
     */
    default void setStatusBar() {
        BaseManager manager = getBaseManager();
        if (manager != null)
            manager.setStatusBar();
    }

    /**
     * 显示加载框
     */
    default void showProgressDialog() {
        BaseManager manager = getBaseManager();
        if (manager != null)
            manager.showProgressDialog();
    }

    /**
     * 取消显示加载框
     */
    default void dismissProgressDialog() {
        BaseManager manager = getBaseManager();
        if (manager != null)
            manager.dismissProgressDialog();
    }

    /**
     * 初始化View
     */
    default void initView() {

    }

    /**
     * 初始化数据
     */
    default void initData() {

    }

    /**
     * 发起网络请求
     */
    default void sendRequest() {

    }

}
