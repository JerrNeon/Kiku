package com.jn.kiku.dialog;

import android.content.Intent;
import android.os.Build;
import androidx.fragment.app.FragmentManager;
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
 * @Description: (定位服务对话框)
 * @create by: chenwei
 * @date 2018/5/17 11:57
 */
public class LocationServiceDialogFragment extends RootDialogFragment {

    private static final int REQUESTCODE_LOCATIONSERVICE = 1;
    private ILocationServiceListener mLocationServiceListener = null;

    private TextView mTvPermissionCancel;//取消
    private TextView mTvPermissionSubmit;//确定

    public static LocationServiceDialogFragment newInstance() {
        return new LocationServiceDialogFragment();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.dialog_locationservice;
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
        mTvPermissionCancel = mView.findViewById(R.id.tv_permissionCancel);
        mTvPermissionSubmit = mView.findViewById(R.id.tv_permissionSubmit);
        mTvPermissionCancel.setOnClickListener(this);
        mTvPermissionSubmit.setOnClickListener(this);
    }

    public void show(FragmentManager manager, String tag, ILocationServiceListener listener) {
        super.show(manager, tag);
        mLocationServiceListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_permissionCancel) {
            if (mLocationServiceListener != null)
                mLocationServiceListener.onLocationServiceOpenFailure();
            this.dismiss();
        } else if (v.getId() == R.id.tv_permissionSubmit) {
            IntentUtils.openLocationService(mFragment, REQUESTCODE_LOCATIONSERVICE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //回调再次判断是否开启定位服务
        if (requestCode == REQUESTCODE_LOCATIONSERVICE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (IntentUtils.checkLocationSereviceOPen(mContext)) {
                if (mLocationServiceListener != null)
                    mLocationServiceListener.onLocationServiceOpenSuccess();
            } else {
                if (mLocationServiceListener != null)
                    mLocationServiceListener.onLocationServiceOpenFailure();
            }
            this.dismiss();
        }
    }

    /**
     * 定位服务监听器
     */
    public interface ILocationServiceListener {
        void onLocationServiceOpenSuccess();

        void onLocationServiceOpenFailure();
    }
}
