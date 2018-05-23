package com.jn.kiku.common.api;

import com.jn.kiku.annonation.PermissionType;

import io.reactivex.functions.Consumer;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (基础)
 * @create by: chenwei
 * @date 2018/5/11 18:01
 */
public interface IBaseView {

    /**
     * 初始化ButterKnife
     */
    void initButterKnife();

    /**
     * 解绑ButterKnife
     */
    void unbindButterKnife();

    /**
     * 初始化EventBus
     */
    void initEventBus();

    /**
     * 取消注册EventBus
     */
    void unregisterEventBus();

    /**
     * 初始化RxPermissions
     */
    void initRxPermissions();

    /**
     * 请求权限
     * @param permissionType @PermissionType
     * @param consumer Consumer
     */
    void requestPermission(@PermissionType final int permissionType, final Consumer<Boolean> consumer);

    /**
     * 设置状态栏
     */
    void setStatusBar();

    /**
     * 显示加载框
     */
    void showProgressDialog();

    /**
     * 取消显示加载框
     */
    void dismissProgressDialog();

    /**
     * 初始化View
     */
    void initView();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 发起网络请求
     */
    void sendRequest();

}
