package com.jn.kiku.common.api;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (主界面)
 * @create by: chenwei
 * @date 2018/5/17 15:54
 */
public interface IMainView {

    /**
     * 获取主界面模块需要显示的所有菜单选中时的文字颜色资源
     *
     * @return
     */
    @ColorRes
    int[] getMenuSelectedTextColorResources();

    /**
     * 获取主界面模块需要显示的所有菜单选中时的图标资源
     *
     * @return
     */
    @DrawableRes
    int[] getMenuSelectedImgResources();

    /**
     * 获取主界面模块需要显示的所有菜单未选中时的图标资源
     *
     * @return
     */
    @DrawableRes
    int[] getMenuUnSelectedImgResources();

    /**
     * 获取主界面模块需要显示的所有菜单标题
     *
     * @return
     */
    String[] getMenuTitles();

    /**
     * 获取主界面模块需要显示所有Fragment
     *
     * @return
     */
    Fragment[] getMenuFragments();

    /**
     * 初始化主界面View
     */
    void initMainView();

    /**
     * 切换菜单时需切换Fragment
     *
     * @param position
     */
    void changeFragment(@IntRange(from = 0) int position);

    /**
     * 隐藏所有Fragment,切换Fragment时会用到
     *
     * @param fragmentTransaction FragmentTransaction
     */
    void hideAllFragment(FragmentTransaction fragmentTransaction);

    /**
     * 是否退出主界面
     *
     * @return
     */
    boolean isExit();

    /**
     * 注册版本更新广播
     */
    void registerVersionUpdateReceiver();

    /**
     * 取消注册版本更新广播
     */
    void unregisterVersionUpdateReceiver();

    /**
     * 显示版本更新对话框
     */
    void showVersionUpdateDialog();

}
