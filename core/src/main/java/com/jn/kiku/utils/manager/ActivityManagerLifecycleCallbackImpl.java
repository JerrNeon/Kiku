package com.jn.kiku.utils.manager;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Activity统一管理)
 * @create by: chenwei
 * @date 2018/5/10 17:12
 */
public class ActivityManagerLifecycleCallbackImpl implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ActivityManager.getInstance().addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityManager.getInstance().removeActivity(activity);
    }
}
