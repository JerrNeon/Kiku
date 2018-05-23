package com.jn.kiku.ttp.analysis;

import android.content.Context;

import com.baidu.mobstat.StatService;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (百度统计)
 * @create by: chenwei
 * @date 2018/3/23 10:20
 */
public class BaiduMobManage {

    private static BaiduMobManage instance = null;

    private BaiduMobManage() {
    }

    public static synchronized BaiduMobManage getInstance() {
        if (instance == null)
            instance = new BaiduMobManage();
        return instance;
    }

    /**
     * 由于多进程等可能造成Application多次执行，建议此代码不要埋点在Application中，否则可能造成启动次数偏高
     * 建议此代码埋点在统计路径触发的第一个页面中，若可能存在多个则建议都埋点
     *
     * @param context
     */
    public void start(Context context) {
        StatService.start(context);
    }

    /**
     * 开发时调用，建议上线前关闭，以免影响性能
     *
     * @param isDebugEnable
     */
    public void setDebugOn(boolean isDebugEnable) {
        StatService.setDebugOn(isDebugEnable);
    }

    /**
     * 获取测试设备ID
     *
     * @param context
     * @return
     */
    public String getTestDeviceId(Context context) {
        return StatService.getTestDeviceId(context);
    }
}
