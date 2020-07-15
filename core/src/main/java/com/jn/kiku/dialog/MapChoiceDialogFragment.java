package com.jn.kiku.dialog;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.jn.kiku.R;
import com.jn.kiku.utils.MapUtils;

/**
 * Author：Stevie.Chen Time：2019/4/18
 * Class Comment：地图选择对话框
 */
public class MapChoiceDialogFragment extends RootDialogFragment {

    private static final String ROUTE_ORIGINLATITUDE = "originLatitude";
    private static final String ROUTE_ORIGINLONGITUDE = "originLongitude";
    private static final String ROUTE_DESTINATIONLATITUDE = "destinationLatitude";
    private static final String ROUTE_DESTINATIONLONGITUDE = "destinationLongitude";

    private TextView mTvGoogle;//Google
    private TextView mTvBaiduMap;//百度地图
    private TextView mTvAutoNavi;//高德地图
    private TextView mTvCancel;//取消

    private MapUtils.LatLng originLl;//初始地
    private MapUtils.LatLng destinationLl;//目的地

    public static MapChoiceDialogFragment newInstance(double originLatitude, double originLongitude,
                                                      double destinationLatitude, double destinationLongitude) {
        MapChoiceDialogFragment fragment = new MapChoiceDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble(ROUTE_ORIGINLATITUDE, originLatitude);
        bundle.putDouble(ROUTE_ORIGINLONGITUDE, originLongitude);
        bundle.putDouble(ROUTE_DESTINATIONLATITUDE, destinationLatitude);
        bundle.putDouble(ROUTE_DESTINATIONLONGITUDE, destinationLongitude);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.dialog_mapchoice;
    }

    @Override
    protected int getAnimationStyle() {
        return R.style.bottom_in_out;
    }

    @Override
    protected boolean getCanceledOnTouchOutsideEnable() {
        return false;
    }

    @Override
    protected WindowManager.LayoutParams getLayoutParams() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        mTvGoogle = mView.findViewById(R.id.tv_google);
        mTvBaiduMap = mView.findViewById(R.id.tv_baiduMap);
        mTvAutoNavi = mView.findViewById(R.id.tv_autoNavi);
        mTvCancel = mView.findViewById(R.id.tv_cancel);

        mTvGoogle.setOnClickListener(this);
        mTvBaiduMap.setOnClickListener(this);
        mTvAutoNavi.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            double originLatitude = bundle.getDouble(ROUTE_ORIGINLATITUDE, 0);
            double originLongitude = bundle.getDouble(ROUTE_ORIGINLONGITUDE, 0);
            double destinationLatitude = bundle.getDouble(ROUTE_DESTINATIONLATITUDE, 0);
            double destinationLongitude = bundle.getDouble(ROUTE_DESTINATIONLONGITUDE, 0);
            originLl = new MapUtils.LatLng(originLatitude, originLongitude);
            destinationLl = new MapUtils.LatLng(destinationLatitude, destinationLongitude);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int i = view.getId();
        if (i == R.id.tv_google) {
            if (originLl != null && destinationLl != null) {
                MapUtils.openGoogleMap(mActivity, MapUtils.MapType.DRIVING, originLl, destinationLl);
            }
        } else if (i == R.id.tv_baiduMap) {
            if (originLl != null && destinationLl != null) {
                MapUtils.openBaiduMap(mActivity, MapUtils.MapType.DRIVING, originLl, destinationLl);
            }
        } else if (i == R.id.tv_autoNavi) {
            if (originLl != null && destinationLl != null) {
                MapUtils.openAutoNaviMap(mActivity, MapUtils.MapType.DRIVING, originLl, destinationLl);
            }
        } else if (i == R.id.tv_cancel) {
            //
        }
        dismissAllowingStateLoss();
    }
}
