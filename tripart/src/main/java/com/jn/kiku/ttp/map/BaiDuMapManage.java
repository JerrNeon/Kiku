package com.jn.kiku.ttp.map;


import androidx.annotation.NonNull;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.jn.common.util.ContextUtils;
import com.jn.kiku.ttp.map.callback.BDLocationListenerIml;
import com.jn.kiku.ttp.map.callback.LocationResultListener;

/**
 * Author：Stevie.Chen Time：2019/8/2
 * Class Comment：百度地图管理类
 */
public class BaiDuMapManage {

    private LocationClient mLocationClient = null;//定位关键类
    private LocationClient mServiceLocationClient = null;//定位关键类

    private BaiDuMapManage() {
    }

    private static class INSTANCE {
        private static BaiDuMapManage instance = new BaiDuMapManage();
    }

    public static synchronized BaiDuMapManage getInstance() {
        return INSTANCE.instance;
    }

    /**
     * 初始化定位相关类和参数
     */
    private void initLoc(final LocationResultListener listener) {
        if (mLocationClient == null)
            mLocationClient = new LocationClient(ContextUtils.getInstance().getContext());
        final BDLocationListenerIml mBDLocationListener = new BDLocationListenerIml();
        mBDLocationListener.setLocationResultListener(new LocationResultListener() {
            @Override
            public void onSuccess(BDLocation location) {
                if (listener != null)
                    listener.onSuccess(location);
                if (mLocationClient != null)
                    mLocationClient.unRegisterLocationListener(mBDLocationListener);
            }

            @Override
            public void onFailure(BDLocation location) {
                if (listener != null)
                    listener.onFailure(location);
                if (mLocationClient != null)
                    mLocationClient.unRegisterLocationListener(mBDLocationListener);
            }
        });
        mLocationClient.registerLocationListener(mBDLocationListener);
        //定位参数
        LocationClientOption mOption = getLocationClientOption(0);
        mLocationClient.setLocOption(mOption);
    }

    /**
     * 初始化定位相关类和参数
     */
    private void initLoc(final int scanSpanTime, final LocationResultListener listener) {
        if (mServiceLocationClient == null)
            mServiceLocationClient = new LocationClient(ContextUtils.getInstance().getContext());
        final BDLocationListenerIml mBDLocationListener = new BDLocationListenerIml();
        mBDLocationListener.setLocationResultListener(listener);
        mServiceLocationClient.registerLocationListener(mBDLocationListener);
        //定位参数
        LocationClientOption mOption = getLocationClientOption(scanSpanTime);
        mServiceLocationClient.setLocOption(mOption);
    }

    /**
     * 获取定位参数
     *
     * @param scanSpanTime 设置发起定位请求的间隔，需要大于等于1000ms才是有效的
     * @return LocationClientOption
     */
    private LocationClientOption getLocationClientOption(int scanSpanTime) {
        LocationClientOption mOption = new LocationClientOption();
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系
        mOption.setCoorType("bd09ll");
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mOption.setScanSpan(scanSpanTime);
        //可选，设置是否需要地址信息，默认不需要
        mOption.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        mOption.setOpenGps(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        mOption.setLocationNotify(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        mOption.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mOption.setIsNeedLocationPoiList(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        mOption.setIgnoreKillProcess(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        mOption.SetIgnoreCacheException(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mOption.setEnableSimulateGps(false);
        return mOption;
    }

    /**
     * 开始定位
     *
     * @param listener 定位结果
     */
    public void startLoc(@NonNull LocationResultListener listener) {
        initLoc(listener);
        startLoc();
    }

    /**
     * 开始定位
     *
     * @param scanSpanTime 定位间隔时间点，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
     * @param listener     定位结果
     */
    public void startLoc(int scanSpanTime, @NonNull LocationResultListener listener) {
        initLoc(scanSpanTime, listener);
        startServiceLoc();
    }

    /**
     * 开始定位
     */
    private void startLoc() {
        if (mLocationClient != null)
            mLocationClient.start();
    }

    /**
     * 停止定位
     */
    public void stopLoc() {
        if (mLocationClient != null)
            mLocationClient.stop();
    }

    /**
     * 开始定位
     */
    private void startServiceLoc() {
        if (mServiceLocationClient != null)
            mServiceLocationClient.start();
    }

    /**
     * 停止定位
     */
    public void stopServiceLoc() {
        if (mServiceLocationClient != null)
            mServiceLocationClient.stop();
    }


    public void onDestroy() {
        if (mLocationClient != null)
            mLocationClient = null;
    }

    public void onDestroyService() {
        if (mServiceLocationClient != null)
            mServiceLocationClient = null;
    }
}
