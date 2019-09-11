package com.jn.kiku;

import android.app.Application;
import android.content.Context;

import androidx.annotation.DrawableRes;
import androidx.multidex.MultiDex;

import com.jn.kiku.common.exception.CrashHandler;
import com.jn.kiku.net.RetrofitManage;
import com.jn.kiku.utils.WebViewUtils;
import com.jn.kiku.utils.manager.ActivityManager;
import com.jn.kiku.utils.manager.GlideManage;

/**
 * Author：Stevie.Chen Time：2019/8/6
 * Class Comment：RootApplication
 */
public abstract class RootApplication extends Application {

    protected abstract boolean isLogEnable();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);//突破65536个方法数
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        GlideManage.clearMemory(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level) {
            case TRIM_MEMORY_UI_HIDDEN:
                //表示应用程序的所有UI界面被隐藏了，即用户点击了Home键或者Back键导致应用的UI界面不可见．
                // 这时候应该释放一些资源．
                GlideManage.clearMemory(this);
                break;
            case TRIM_MEMORY_RUNNING_MODERATE:
                // 表示应用程序正常运行，并且不会被杀掉。但是目前手机的内存已经有点低了，
                // 系统可能会开始根据LRU缓存规则来去杀死进程了。
                break;
            case TRIM_MEMORY_RUNNING_LOW:
                //表示应用程序正常运行，并且不会被杀掉。但是目前手机的内存已经非常低了，
                // 我们应该去释放掉一些不必要的资源以提升系统的性能，同时这也会直接影响到我们应用程序的性能。
                break;
            case TRIM_MEMORY_RUNNING_CRITICAL:
                //表示应用程序仍然正常运行，但是系统已经根据LRU缓存规则杀掉了大部分缓存的进程了。
                // 这个时候我们应当尽可能地去释放任何不必要的资源，不然的话系统可能会继续杀掉所有缓存中的进程，
                // 并且开始杀掉一些本来应当保持运行的进程，比如说后台运行的服务。
                break;
            case TRIM_MEMORY_BACKGROUND:
                //表示手机目前内存已经很低了，系统准备开始根据LRU缓存来清理进程。
                // 这个时候我们的程序在LRU缓存列表的最近位置，是不太可能被清理掉的，
                // 但这时去释放掉一些比较容易恢复的资源能够让手机的内存变得比较充足，
                // 从而让我们的程序更长时间地保留在缓存当中，这样当用户返回我们的程序时会感觉非常顺畅，而不是经历了一次重新启动的过程。
                break;
            case TRIM_MEMORY_MODERATE:
                //表示手机目前内存已经很低了，并且我们的程序处于LRU缓存列表的中间位置，
                // 如果手机内存还得不到进一步释放的话，那么我们的程序就有被系统杀掉的风险了。
                break;
            case TRIM_MEMORY_COMPLETE:
                //表示手机目前内存已经很低了，并且我们的程序处于LRU缓存列表的最边缘位置，
                // 系统会最优先考虑杀掉我们的应用程序，在这个时候应当尽可能地把一切可以释放的东西都进行释放。
                break;
            default:
                break;
        }
        GlideManage.trimMemory(this, level);
    }

    /**
     * 初始化ActivityManager
     */
    protected void initActivityManager() {
        ActivityManager.getInstance().register(this);
    }

    /**
     * 初始化Retrofit
     *
     * @param BASE_URL 服务器域名地址
     */
    protected void initRetrofit(String BASE_URL) {
        RetrofitManage.getInstance().initRetrofit(BASE_URL);
    }

    /**
     * 初始化GlideManager
     */
    protected void initGlideManager(@DrawableRes int defaultPlaceHolderImageResourceIds, @DrawableRes int defaultErrorImageResourceIds,
                                    @DrawableRes int defaultPlaceHolderAvatarResourceIds, @DrawableRes int defaultErrorAvatarResourceIds) {
        GlideManage.init(defaultPlaceHolderImageResourceIds, defaultErrorImageResourceIds, defaultPlaceHolderAvatarResourceIds, defaultErrorAvatarResourceIds);
    }

    /**
     * 初始化工具类的一些相关信息
     */
    protected void initUtilsManager() {

    }

    /**
     * 初始化第三方平台一些相关信息
     */
    protected void initTTpManager() {

    }

    /**
     * 初始化崩溃异常信息
     */
    protected void initCrashHandler() {
        if (!isLogEnable())
            CrashHandler.getInstance().init(this);
    }

    /**
     * 搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
     */
    protected void initTencentWebView() {
        WebViewUtils.initX5Environment(getApplicationContext());
    }

    /**
     * 查看App中数据库和sp中的数据及其项目构造
     */
    protected void initStetho() {

    }

    /**
     * 检查内存泄漏
     */
    protected void initCanary() {

    }

}
