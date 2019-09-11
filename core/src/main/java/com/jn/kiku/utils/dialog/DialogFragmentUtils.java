package com.jn.kiku.utils.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.jn.kiku.dialog.TokenInvalidDialogFragment;

import java.lang.ref.WeakReference;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (对话框工具类)
 * @create by: chenwei
 * @date 2018/5/30 15:43
 */
public class DialogFragmentUtils {

    private static WeakReference<TokenInvalidDialogFragment> mTokenInvalidDialog = null;

    /**
     * 显示Token失效对话框
     */
    public static void showTokenValidDialog(Context context, TokenInvalidDialogFragment.IReLoginListener iReLoginListener) {
        if (mTokenInvalidDialog == null && context instanceof AppCompatActivity) {
            mTokenInvalidDialog = new WeakReference<>(TokenInvalidDialogFragment.newInstance());
            TokenInvalidDialogFragment fragment = mTokenInvalidDialog.get();
            if (fragment != null) {
                fragment.show(((AppCompatActivity) context).getSupportFragmentManager(), iReLoginListener);
            }
        }
    }

    /**
     * 释放Token失效对话框资源
     */
    public static void onDestroyTokenValidDialog() {
        if (mTokenInvalidDialog != null) {
            mTokenInvalidDialog.clear();
            mTokenInvalidDialog = null;
        }
    }
}
