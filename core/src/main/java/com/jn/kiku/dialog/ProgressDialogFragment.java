package com.jn.kiku.dialog;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.jn.kiku.R;
import com.jn.kiku.widget.imageview.SpinBlackView;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (加载对话框)
 * @create by: chenwei
 * @date 2018/5/9 17:26
 */
public class ProgressDialogFragment extends DialogFragment {

    private static final int DELAY_MILLISECOND = 450;
    private static final int SHOW_MIN_MILLISECOND = 300;

    private SpinBlackView mSpinBlackProgressView;
    private boolean startedShowing;
    private long mStartMillisecond;
    private long mStopMillisecond;

    public static ProgressDialogFragment newInstance() {
        return new ProgressDialogFragment();
    }

    // default constructor. Needed so rotation doesn't crash
    public ProgressDialogFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_progress, null, false);
        mSpinBlackProgressView = view.findViewById(R.id.sbv_progressDialog);
        return view;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onStart() {
        super.onStart();
        if (getDialog().getWindow() != null) {
            Window window = getDialog().getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            //去掉背景色
            lp.dimAmount = 0;
            lp.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setAttributes(lp);
            getDialog().setCancelable(true);//点击外部区域隐藏对话框
        }
    }

    @Override
    public void show(final FragmentManager fm, final String tag) {
        mStartMillisecond = System.currentTimeMillis();
        startedShowing = false;
        mStopMillisecond = Long.MAX_VALUE;

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mStopMillisecond > System.currentTimeMillis())
                    showDialogAfterDelay(fm, tag);
            }
        }, DELAY_MILLISECOND);
    }

    private void showDialogAfterDelay(FragmentManager fm, String tag) {
        startedShowing = true;
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

    public void cancel() {
        mStopMillisecond = System.currentTimeMillis();

        if (startedShowing) {
            if (mSpinBlackProgressView != null) {
                cancelWhenShowing();
            } else {
                cancelWhenNotShowing();
            }
        }
    }

    private void cancelWhenShowing() {
        if (mStopMillisecond < mStartMillisecond + DELAY_MILLISECOND + SHOW_MIN_MILLISECOND) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismissAllowingStateLoss();
                }
            }, SHOW_MIN_MILLISECOND);
        } else {
            dismissAllowingStateLoss();
        }
    }

    private void cancelWhenNotShowing() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissAllowingStateLoss();
            }
        }, DELAY_MILLISECOND);
    }

    /**
     * 设置加载图标
     *
     * @param resId
     */
    public ProgressDialogFragment setProgressImageResource(@DrawableRes int resId) {
        if (mSpinBlackProgressView != null)
            mSpinBlackProgressView.setImageResource(resId);
        return this;
    }
}
