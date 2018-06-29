package com.jn.kiku.utils.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.jn.kiku.utils.SoftKeyBoardUtils;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (软键盘)
 * @create by: chenwei
 * @date 2018/6/29 16:21
 */
public class SoftKeyBoardManager {

    private static SoftKeyBoardManager instance = null;
    private int sContentViewInvisibleHeightPre;//窗口可视区域大小
    private OnSoftInputChangedListener onSoftInputChangedListener;
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;

    private SoftKeyBoardManager() {
    }

    public static synchronized SoftKeyBoardManager getInstance() {
        if (instance == null)
            instance = new SoftKeyBoardManager();
        return instance;
    }

    /**
     * Register soft input changed listener.
     *
     * @param activity The activity.
     * @param listener The soft input changed listener.
     */
    public void registerSoftInputChangedListener(final Activity activity,
                                                 final OnSoftInputChangedListener listener) {
        final int flags = activity.getWindow().getAttributes().flags;
        if ((flags & WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) != 0) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final View contentView = activity.findViewById(android.R.id.content);
        sContentViewInvisibleHeightPre = SoftKeyBoardUtils.getContentViewInvisibleHeight(activity);
        onSoftInputChangedListener = listener;
        onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (onSoftInputChangedListener != null) {
                    int height = SoftKeyBoardUtils.getContentViewInvisibleHeight(activity);
                    if (sContentViewInvisibleHeightPre != height) {
                        onSoftInputChangedListener.onSoftInputChanged(height);
                        sContentViewInvisibleHeightPre = height;
                    }
                }
            }
        };
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    /**
     * unRegister soft input changed listener.
     *
     * @param activity The activity.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void unregisterSoftInputChangedListener(final Activity activity) {
        final View contentView = activity.findViewById(android.R.id.content);
        contentView.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        onSoftInputChangedListener = null;
        onGlobalLayoutListener = null;
    }

    public interface OnSoftInputChangedListener {
        void onSoftInputChanged(int height);
    }
}
