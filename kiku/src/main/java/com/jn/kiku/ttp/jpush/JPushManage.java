package com.jn.kiku.ttp.jpush;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.jn.kiku.common.api.ILogToastView;
import com.jn.kiku.utils.LogUtils;
import com.jn.kiku.utils.ToastUtil;
import com.jn.kiku.utils.manager.SPManage;

import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.DefaultPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Jpush管理类)
 * @create by: chenwei
 * @date 2017/3/14 10:08
 */
public class JPushManage implements ILogToastView {

    private static final int MSG_SET_ALIAS = 1001;
    private static JPushManage instance = null;
    private Context mContext = null;

    public enum JpushType {
        SOUND, VIBRATE, All, NONE
    }

    private JPushManage(Context context) {
        this.mContext = context;
    }

    public static synchronized JPushManage getInstance(Context context) {
        if (instance == null)
            instance = new JPushManage(context.getApplicationContext());
        return instance;
    }

    /**
     * 设置别名
     *
     * @param alias 别名
     */
    public void setAlias(@NonNull String alias) {
        if (alias.equals(SPManage.getInstance(mContext).getAliasValue())) {
            logI("当前用户已设置过别名");
            return;
        }
        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    /**
     * @param context
     * @param alias    null 此次调用不设置此值。（注：不是指的字符串"null"）"" （空字符串）表示取消之前的设置。
     *                 每次调用设置有效的别名，覆盖之前的设置。
     *                 有效的别名组成：字母（区分大小写）、数字、下划线、汉字、特殊字符(v2.1.6支持)@!#$&*+=.|。
     *                 限制：alias 命名长度限制为 40 字节。（判断长度需采用UTF-8编码）
     * @param tags     null 此次调用不设置此值。（注：不是指的字符串"null")空数组或列表表示取消之前的设置。
     *                 每次调用至少设置一个 tag，覆盖之前的设置，不是新增。
     *                 有效的标签组成：字母（区分大小写）、数字、下划线、汉字、特殊字符(v2.1.6支持)@!#$&*+=.|。
     *                 限制：每个 tag 命名长度限制为 40 字节，最多支持设置 1000 个 tag，但总长度不得超过7K字节。
     *                 （判断长度需采用UTF-8编码）
     * @param callback 在TagAliasCallback 的 gotResult 方法，返回对应的参数 alias, tags。
     *                 并返回对应的状态码：0为成功，其他返回码请参考错误码定义。
     */
    public void setAliasAndTags(Context context, String alias, Set<String> tags, TagAliasCallback callback) {
        JPushInterface.setAliasAndTags(context, alias, tags, callback);
    }

    /**
     * 过滤掉无效的 tags，得到有效的 tags
     *
     * @param tags
     * @return
     */
    public Set<String> filterValidTag(Set<String> tags) {
        return JPushInterface.filterValidTags(tags);
    }

    /**
     * 取得应用程序对应的 RegistrationID。 只有当应用程序成功注册到 JPush 的服务器时才返回对应的值，否则返回空字符串
     *
     * @return
     */
    public String getRegistrationID() {
        return JPushInterface.getRegistrationID(mContext);
    }

    /**
     * 清除所有通知
     */
    public void clearAllNotifications() {
        JPushInterface.clearAllNotifications(mContext);
    }

    /**
     * 通过通知ID清除通知
     */
    public void clearNotificationById(int notificationId) {
        JPushInterface.clearNotificationById(mContext, notificationId);
    }

    /**
     * 设置允许推送时间
     *
     * @param weekDays  0表示星期天，1表示星期一，以此类推。 （7天制，Set集合里面的int范围为0到6）
     *                  新功能:set的值为null,则任何时间都可以收到消息和通知，set的size为0，则表示任何时间都收不到消息和通知.
     * @param startHour 允许推送的开始时间 （24小时制：startHour的范围为0到23）
     * @param endHour   允许推送的结束时间 （24小时制：endHour的范围为0到23）
     */
    public void setPushTime(Set<Integer> weekDays, int startHour, int endHour) {
        JPushInterface.setPushTime(mContext, weekDays, startHour, endHour);
    }

    /**
     * 设置通知静默时间
     *
     * @param startHour   静音时段的开始时间 - 小时 （24小时制，范围：0~23 ）
     * @param startMinute 静音时段的开始时间 - 分钟（范围：0~59 ）
     * @param endHour     静音时段的结束时间 - 小时 （24小时制，范围：0~23 ）
     * @param endMinute   静音时段的结束时间 - 分钟（范围：0~59 ）
     */
    public void setSilenceTime(int startHour, int startMinute, int endHour, int endMinute) {
        JPushInterface.setSilenceTime(mContext, startHour, startMinute, endHour, endMinute);
    }

    /**
     * 设置保留最近通知条数
     * 默认为保留最近 5 条通知
     *
     * @param maxNum 最多显示的条数
     */
    public void setLatestNotificationNumber(int maxNum) {
        JPushInterface.setLatestNotificationNumber(mContext, maxNum);
    }

    /* ==================================静态方法================================== */

    /**
     * 在 Android 6.0 及以上的系统上，需要去请求一些用到的权限，
     * JPush SDK 用到的一些需要请求如下权限，因为需要这些权限使统计更加精准，
     * 功能更加丰富，建议开发者调用
     * <p>
     * "android.permission.READ_PHONE_STATE"
     * "android.permission.WRITE_EXTERNAL_STORAGE"
     * "android.permission.READ_EXTERNAL_STORAGE"
     * "android.permission.ACCESS_FINE_LOCATION"
     *
     * @param activity 当前应用的 Activity 的上下文
     */
    public void requestPermission(Activity activity) {
        JPushInterface.requestPermission(activity);
    }

    /**
     * 恢复推送服务
     */
    public void resumePush() {
        if (JPushInterface.isPushStopped(mContext))
            JPushInterface.resumePush(mContext);
    }

    /**
     * 停止推送服务
     */
    public void stopPush() {
        if (!JPushInterface.isPushStopped(mContext))
            JPushInterface.stopPush(mContext);
    }

    /**
     * 定制通知栏样式
     *
     * @param integer 样式编号
     * @param builder
     */
    public static void setPushNotificationBuilder(Integer integer, DefaultPushNotificationBuilder builder) {
        JPushInterface.setPushNotificationBuilder(integer, builder);
    }

    /**
     * 推送声音、震动相关设置
     *
     * @param activity
     * @param type     SOUND：只有声音,VIBRATE:只有震动,All:声音震动都有,NONE:声音震动都没有
     */
    public static void setPushHardware(Activity activity, JpushType type) {
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(activity);
        switch (type) {
            case SOUND:
                builder.notificationDefaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS;
                break;
            case VIBRATE:
                builder.notificationDefaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS;
                break;
            case All:
                builder.notificationDefaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS;
                break;
            case NONE:
                builder.notificationDefaults = Notification.DEFAULT_LIGHTS;
                break;
            default:
                break;
        }
        setPushNotificationBuilder(1, builder);
    }

    public static void onResume(Activity activity) {
        JPushInterface.onResume(activity);
    }

    public static void onPause(Activity activity) {
        JPushInterface.onPause(activity);
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code) {
                case 0:
                    logI("Set tag and alias success");
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    SPManage.getInstance(mContext).setAliasValue(alias);
                    break;
                case 6002:
                    logI("Failed to set alias and tags due to timeout. Try again after 60s.");
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logE("Failed with errorCode = " + code);
            }
        }
    };

    private final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    logI("Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    setAliasAndTags(mContext, (String) msg.obj, null, mAliasCallback);
                    break;
                default:
                    logI("Unhandled msg - " + msg.what);
            }
            return false;
        }
    });

    @Override
    public void logI(String message) {
        LogUtils.i(String.format(messageFormat, getClass().getSimpleName(), message));
    }

    @Override
    public void logE(String message) {
        LogUtils.e(String.format(messageFormat, getClass().getSimpleName(), message));
    }

    @Override
    public void showToast(String message) {
        ToastUtil.showToast(mContext, message);
    }

    @Override
    public void showToast(String message, int duration) {
        ToastUtil.showToast(mContext, message, duration);
    }
}
