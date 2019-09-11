package com.jn.kiku.dialog;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.jn.kiku.R;
import com.jn.kiku.entiy.VersionUpdateVO;
import com.jn.kiku.service.VersionUpdateService;
import com.jn.kiku.utils.ScreenUtils;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (版本更新对话框)
 * @create by: chenwei
 * @date 2017/5/27 9:32
 */
public class VersionUpdateDialog extends RootDialogFragment {

    private TextView mTvVersionUpdateContent;//更新内容
    private TextView mTvVersionUpdateCancel;//取消
    private TextView mTvVersionUpdateSubmit;//确定

    private VersionUpdateVO mVersionUpdateVO = null;
    private View.OnClickListener mOnClickListener = null;

    public static VersionUpdateDialog newInstance(VersionUpdateVO mVersionUpdateVO) {
        VersionUpdateDialog dialog = new VersionUpdateDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(VersionUpdateVO.class.getSimpleName(), mVersionUpdateVO);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.dialog_versionupdate;
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
        params.width = (int) (ScreenUtils.getScreenWidth(mContext) * 0.8);//宽度为屏幕宽度的80%
        params.height = (int) (ScreenUtils.getScreenWidth(mContext) * 0.8);//高度为屏幕宽度的80%
        return params;
    }

    @Override
    public void initView() {
        super.initView();
        mTvVersionUpdateContent = mView.findViewById(R.id.tv_versionUpdateContent);
        mTvVersionUpdateCancel = mView.findViewById(R.id.tv_versionUpdateCancel);
        mTvVersionUpdateSubmit = mView.findViewById(R.id.tv_versionUpdateSubmit);
        mTvVersionUpdateCancel.setOnClickListener(this);
        mTvVersionUpdateSubmit.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mVersionUpdateVO = bundle.getParcelable(VersionUpdateVO.class.getSimpleName());
            if (!isEmpty(mVersionUpdateVO)) {
                if (mVersionUpdateVO.getForceUpdate() == 0)
                    mTvVersionUpdateCancel.setVisibility(View.VISIBLE);
                else {
                    mTvVersionUpdateCancel.setVisibility(View.GONE);
                    setCanceledOnBackPress();
                }
                mTvVersionUpdateContent.setText(mVersionUpdateVO.getContent());
            }
        }
    }

    public void show(FragmentManager manager, String tag, View.OnClickListener listener) {
        mOnClickListener = listener;
        super.show(manager, tag);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.tv_versionUpdateCancel) {
            this.dismiss();
        } else if (viewId == R.id.tv_versionUpdateSubmit) {
            this.dismiss();
            if (!isEmpty(mVersionUpdateVO)) {
                Intent intent = new Intent(mContext, VersionUpdateService.class);
                intent.putExtra(VersionUpdateVO.class.getSimpleName(), mVersionUpdateVO);
                mActivity.startService(intent);
            }
            if (mOnClickListener != null)
                mOnClickListener.onClick(view);
        }
    }
}
