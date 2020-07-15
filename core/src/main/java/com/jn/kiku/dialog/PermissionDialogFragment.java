package com.jn.kiku.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.jn.kiku.R;
import com.jn.kiku.utils.IntentUtils;
import com.jn.kiku.utils.ScreenUtils;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (权限对话框)
 * @create by: chenwei
 * @date 2018/5/17 11:57
 */
public class PermissionDialogFragment extends RootDialogFragment {

    public static final String APP_NAME = "appName";
    public static final String PERMISSION_NAME = "permissionName";

    private TextView mTvPermissionMessage;//提示信息
    private TextView mTvPermissionCancel;//取消
    private TextView mTvPermissionSubmit;//确定

    public static PermissionDialogFragment newInstance(Bundle bundle) {
        PermissionDialogFragment dialog = new PermissionDialogFragment();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.dialog_permission;
    }

    @Override
    protected int getAnimationStyle() {
        return 0;
    }

    @Override
    protected boolean getCanceledOnTouchOutsideEnable() {
        return true;
    }

    @Override
    protected WindowManager.LayoutParams getLayoutParams() {
        WindowManager.LayoutParams params = mWindow.getAttributes();
        params.gravity = Gravity.CENTER;//中间显示
        params.width = (int) (ScreenUtils.getScreenWidth(mContext) * 0.9);//宽度为屏幕90%
        return params;
    }

    @Override
    public void initView() {
        mTvPermissionMessage = mView.findViewById(R.id.tv_permissionMessage);
        mTvPermissionCancel = mView.findViewById(R.id.tv_permissionCancel);
        mTvPermissionSubmit = mView.findViewById(R.id.tv_permissionSubmit);
        mTvPermissionCancel.setOnClickListener(this);
        mTvPermissionSubmit.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String appName = bundle.getString(APP_NAME, "");
            String permissionName = bundle.getString(PERMISSION_NAME, "");
            mTvPermissionMessage.setText(String.format(getResources().getString(R.string.permission_content), appName, permissionName, appName));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_permissionCancel) {
            this.dismiss();
        } else if (v.getId() == R.id.tv_permissionSubmit) {
            this.dismiss();
            IntentUtils.openApplicationSetting(mContext);
        }
    }
}
