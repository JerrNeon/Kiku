package com.jn.kiku.dialog;

import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.jn.kiku.R;
import com.jn.kiku.utils.ScreenUtils;
import com.jn.kiku.utils.dialog.DialogFragmentUtils;
import com.jn.kiku.utils.manager.SPManage;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Token失效或被顶号对话框)
 * @create by: chenwei
 * @date 2018/5/30 15:18
 */
public class TokenInvalidDialogFragment extends RootDialogFragment {

    protected TextView mTvCommit;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.dialog_tokeninvalid;
    }

    @Override
    protected int getAnimationStyle() {
        return 0;
    }

    @Override
    protected boolean getCanceledOnTouchOutsideEnable() {
        return false;
    }

    @Override
    protected WindowManager.LayoutParams getLayoutParams() {
        WindowManager.LayoutParams params = mWindow.getAttributes();
        params.gravity = Gravity.CENTER;//居中显示
        params.width = (int) (ScreenUtils.getScreenWidth(mContext) * 0.7);//宽度为全屏的70%
        return params;
    }

    @Override
    public void initView() {
        super.initView();
        mTvCommit = mView.findViewById(R.id.tv_commit);
        mTvCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.tv_commit) {
            SPManage.getInstance(mContext).clearUserInfo();
            openLoginActivity();
            this.dismiss();
            DialogFragmentUtils.onDestroyTokenValidDialog();
        }
    }

    /**
     * 打开登录界面并更新用户信息
     * <p>
     * 更新用户可以用EventBus或BroadCastReceiver,是具体情况而定
     * </P>
     */
    protected void openLoginActivity() {

    }

    @Override
    public void onDestroyView() {
        DialogFragmentUtils.onDestroyTokenValidDialog();
        super.onDestroyView();
    }
}
