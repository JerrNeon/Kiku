package com.jn.kiku.common.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (界面跳转)
 * @create by: chenwei
 * @date 2018/5/10 17:19
 */
public interface IRouteView {

    /**
     * 通过类名启动Activity
     *
     * @param cls 需要跳转的类
     */
    void openActivity(@NonNull Class<?> cls);

    /**
     * 通过类名启动Activity，并且传递参数
     *
     * @param cls   需要跳转的类
     * @param param 数据
     */
    void openActivity(@NonNull Class<?> cls, @Nullable Object param);

    /**
     * 通过类名启动Activity，并且含有Bundle数据和返回数据
     *
     * @param cls         需要跳转的类
     * @param param       数据
     * @param requestCode 请求标识符
     */
    void openActivity(@NonNull Class<?> cls, @Nullable Object param, int requestCode);

    /**
     * 获取上一个界面传递过来的参数
     *
     * @param defaultObject 默认值
     * @return
     */
    Object getParam(Object defaultObject);

    /**
     * 获取上一个界面传递过来的参数
     *
     * @param key           获取参数值的key
     * @param defaultObject 默认值
     * @return
     */
    Object getParam(String key, Object defaultObject);
}
