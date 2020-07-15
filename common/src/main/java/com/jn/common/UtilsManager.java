package com.jn.common;

import android.app.Application;

import com.jn.common.util.ContextUtils;
import com.jn.common.util.LogUtils;

/**
 * Author：Stevie.Chen Time：2019/8/2
 * Class Comment：工具管理
 */
public class UtilsManager {

    /**
     * 初始化Log
     *
     * @param tagName   Tag名称
     */
    public static void initLogUtils(String tagName) {
        LogUtils.init(tagName);
    }

    /**
     * 初始化ContextUtils
     *
     * @param application Application
     */
    public static void initContextUtils(Application application) {
        ContextUtils.getInstance().init(application);
    }


}
