package com.jn.kiku.utils.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.jn.kiku.dialog.TokenInvalidDialogFragment;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (对话框工具类)
 * @create by: chenwei
 * @date 2018/5/30 15:43
 */
public class DialogFragmentUtils {

    private static TokenInvalidDialogFragment mTokenInvalidDialog = null;

    /**
     * 显示Token失效对话框
     *
     * @param context
     */
    public static void showTokenValidDialog(Context context) {
        if (mTokenInvalidDialog == null && context instanceof AppCompatActivity) {
            mTokenInvalidDialog = TokenInvalidDialogFragment.newInstance(TokenInvalidDialogFragment.class);
            mTokenInvalidDialog.show(((AppCompatActivity) context).getSupportFragmentManager(), TokenInvalidDialogFragment.class.getSimpleName());
        }
    }

    /**
     * 释放Token失效对话框资源
     */
    public static void onDestroyTokenValidDialog() {
        if (mTokenInvalidDialog != null)
            mTokenInvalidDialog = null;
    }
}
