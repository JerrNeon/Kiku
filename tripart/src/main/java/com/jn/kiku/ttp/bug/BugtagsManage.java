package com.jn.kiku.ttp.bug;

import android.app.Activity;
import android.app.Application;
import android.view.MotionEvent;

import androidx.fragment.app.Fragment;

import com.bugtags.library.Bugtags;
import com.jn.kiku.ttp.TtpConstants;

/**
 * Author：Stevie.Chen Time：2019/9/11
 * Class Comment：Bugtags
 */
public class BugtagsManage {

    private static BugtagsManage instance = null;

    private BugtagsManage() {
    }

    public static synchronized BugtagsManage getInstance() {
        if (instance == null)
            instance = new BugtagsManage();
        return instance;
    }

    /**
     * 在Application初始化
     */
    public void init(Application application) {
        start(TtpConstants.BUGTAGS_APPKEY, application, Bugtags.BTGInvocationEventNone);
    }

    /**
     * 在Application初始化
     *
     * @param event       BTGInvocationEventNone | BTGInvocationEventShake | BTGInvocationEventBubble
     *                    <p>
     *                    BTGInvocationEventNone    // 静默模式，只收集 Crash 信息（如果允许，默认为允许）
     *                    BTGInvocationEventShake   // 通过摇一摇呼出 Bugtags
     *                    BTGInvocationEventBubble  // 通过悬浮小球呼出 Bugtags
     *                    </p>
     */
    public void start(String appKey, Application application, int event) {
        Bugtags.start(appKey, application, event);
    }

    /**
     * 回调 1
     */
    public void onResume(Activity activity) {
        Bugtags.onResume(activity);
    }

    /**
     * 回调 2
     */
    public void onPause(Activity activity) {
        Bugtags.onPause(activity);
    }

    /**
     * 回调 3
     */
    public void dispatchTouchEvent(Activity activity, MotionEvent event) {
        Bugtags.onDispatchTouchEvent(activity, event);
    }

    /**
     * 回调 1
     *
     */
    public void onResume(Fragment fragment) {
        Bugtags.onResume(fragment);
    }

    /**
     * 回调 2
     *
     */
    public void onPause(Fragment fragment) {
        Bugtags.onPause(fragment);
    }

}
