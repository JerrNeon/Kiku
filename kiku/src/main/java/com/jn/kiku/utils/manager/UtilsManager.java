package com.jn.kiku.utils.manager;

import android.app.Application;

import com.jn.kiku.utils.ImageUtil;
import com.jn.kiku.utils.LogUtils;
import com.jn.kiku.utils.UriUtils;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (工具管理)
 * @create by: chenwei
 * @date 2018/5/30 14:24
 */
public class UtilsManager {

    /**
     * 初始化Log
     *
     * @param logEnable 是否开启日志，建议debug开启日志信息，release关闭日志信息
     * @param tagName   Tag名称
     */
    public static void initLogUtils(boolean logEnable, String tagName) {
        LogUtils.init(logEnable, tagName);
    }

    /**
     * 初始化ImageUtils
     *
     * @param application Application
     */
    public static void initImageUtils(Application application) {
        ImageUtil.init(application);
    }

    /**
     * 初始化UriUtils
     */
    public static void initUriUtils(String packageName) {
        UriUtils.init(packageName);
    }

}
