package com.jn.kiku.ttp.map;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.jn.kiku.common.api.ILogToastView;
import com.jn.kiku.ttp.map.callback.LocationResultListener;
import com.jn.kiku.ttp.map.callback.RouteSearchResultListener;
import com.jn.kiku.ttp.map.overlayutil.BikingRouteOverlay;
import com.jn.kiku.ttp.map.overlayutil.DrivingRouteOverlay;
import com.jn.kiku.ttp.map.overlayutil.TransitRouteOverlay;
import com.jn.kiku.ttp.map.overlayutil.WalkingRouteOverlay;
import com.jn.kiku.utils.LogUtils;

import java.util.List;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (百度地图管理类)
 * @create by: chenwei
 * @date 2017/3/13 13:27
 */
public class BaiduMapManage implements ILogToastView {

    private static BaiduMapManage instance = null;

    private LocationClient mLocationClient = null;//定位关键类
    private BDLocationListener myListener = null;//定位监听
    private LocationClientOption mOption = null;//定位参数
    private RoutePlanSearch pSearch = null;//搜索关键类

    private LocationResultListener mLoctionResultListener = null;
    private RouteSearchResultListener mRouteSearchResultListener = null;

    private BaiduMapManage() {
    }

    public static synchronized BaiduMapManage getInstance() {
        if (instance == null)
            instance = new BaiduMapManage();
        return instance;
    }

    /**
     * 初始化定位相关类和参数
     */
    private void initLoc(Context context) {
        mLocationClient = new LocationClient(context.getApplicationContext());
        myListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myListener);

        mOption = new LocationClientOption();
        mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        mOption.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        mOption.setScanSpan(0);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mOption.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        mOption.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        mOption.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        mOption.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        mOption.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mOption.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        mOption.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        mOption.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(mOption);
    }

    /**
     * 开始定位
     *
     * @param context  Context
     * @param listener 定位结果
     */
    public void startLoc(Context context, @NonNull LocationResultListener listener) {
        initLoc(context);
        mLoctionResultListener = listener;
        mLocationClient.start();
    }

    /**
     * 设置地图类型
     *
     * @param baiduMap
     */
    public void setMapType(BaiduMap baiduMap) {
        //普通地图
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //开启交通图
        baiduMap.setTrafficEnabled(true);
    }

    /**
     * 更新定位图层
     *
     * @param baiduMap
     * @param location
     */
    public void updateLocationMapStatus(@NonNull BaiduMap baiduMap, @NonNull BDLocation location) {
        LatLng locLng = new LatLng(location.getLatitude(), location.getLongitude());
        MyLocationData myLocationData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
        baiduMap.setMyLocationData(myLocationData);
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(locLng));
    }

    /**
     * 获取两个位置之间的距离
     *
     * @param from 开始位置
     * @param to   结束位置
     * @return 距离(单位 : m)
     */
    public double getDistance(@NonNull LatLng from, @NonNull LatLng to) {
        return DistanceUtil.getDistance(from, to);
    }

    /**
     * 初始化路线搜索
     *
     * @param listener
     */
    private void initRouteSearch(RouteSearchResultListener listener) {
        mRouteSearchResultListener = listener;
        if (pSearch == null)
            pSearch = RoutePlanSearch.newInstance();
        pSearch.setOnGetRoutePlanResultListener(new MyOnGetRoutePlanResultListener());
    }

    /**
     * 驾车路线搜索
     *
     * @param currentCity 当前城市名
     * @param from        开始位置
     * @param to          结束位置
     * @param policy      策略(默认：最少时间)
     */
    public void drivingSearch(@NonNull String currentCity, @NonNull LatLng from, @NonNull LatLng to, @Nullable DrivingRoutePlanOption.DrivingPolicy policy, RouteSearchResultListener listener) {
        try {
            initRouteSearch(listener);
            PlanNode fromNode = PlanNode.withLocation(from);
            PlanNode toNode = PlanNode.withLocation(to);
            DrivingRoutePlanOption.DrivingPolicy drivingPolicy;
            if (policy == null)
                drivingPolicy = DrivingRoutePlanOption.DrivingPolicy.ECAR_DIS_FIRST;
            else
                drivingPolicy = policy;
            if (pSearch != null)
                pSearch.drivingSearch(new DrivingRoutePlanOption().currentCity(currentCity).from(fromNode).to(toNode).policy(drivingPolicy));
        } catch (Exception e) {
            e.printStackTrace();
            if (mRouteSearchResultListener != null)
                mRouteSearchResultListener.onFailure(RouteSearchResultListener.Type.DRIVING);
        }
    }

    /**
     * 公交路线搜索
     *
     * @param currentCity 当前城市名
     * @param from        开始位置
     * @param to          结束位置
     * @param policy      策略(默认：时间优先)
     */
    public void transitSearch(@NonNull String currentCity, @NonNull LatLng from, @NonNull LatLng to, @Nullable TransitRoutePlanOption.TransitPolicy policy, RouteSearchResultListener listener) {
        try {
            initRouteSearch(listener);
            PlanNode fromNode = PlanNode.withLocation(from);
            PlanNode toNode = PlanNode.withLocation(to);
            TransitRoutePlanOption.TransitPolicy transitPolicy;
            if (policy == null)
                transitPolicy = TransitRoutePlanOption.TransitPolicy.EBUS_TIME_FIRST;
            else
                transitPolicy = policy;
            if (pSearch != null)
                pSearch.transitSearch(new TransitRoutePlanOption().city(currentCity).from(fromNode).to(toNode).policy(transitPolicy));
        } catch (Exception e) {
            e.printStackTrace();
            if (mRouteSearchResultListener != null)
                mRouteSearchResultListener.onFailure(RouteSearchResultListener.Type.TRANSIT);
        }
    }

    /**
     * 骑行路线搜索
     *
     * @param from
     * @param to
     */
    public void bikingSearch(@NonNull LatLng from, @NonNull LatLng to, RouteSearchResultListener listener) {
        try {
            initRouteSearch(listener);
            PlanNode fromNode = PlanNode.withLocation(from);
            PlanNode toNode = PlanNode.withLocation(to);
            if (pSearch != null)
                pSearch.bikingSearch(new BikingRoutePlanOption().from(fromNode).to(toNode));
        } catch (Exception e) {
            e.printStackTrace();
            if (mRouteSearchResultListener != null)
                mRouteSearchResultListener.onFailure(RouteSearchResultListener.Type.BIKING);
        }

    }

    /**
     * 步行路线搜索
     *
     * @param from
     * @param to
     */
    public void walkingSearch(@NonNull LatLng from, @NonNull LatLng to, RouteSearchResultListener listener) {
        try {
            initRouteSearch(listener);
            PlanNode fromNode = PlanNode.withLocation(from);
            PlanNode toNode = PlanNode.withLocation(to);
            if (pSearch != null)
                pSearch.walkingSearch(new WalkingRoutePlanOption().from(fromNode).to(toNode));
        } catch (Exception e) {
            e.printStackTrace();
            if (mRouteSearchResultListener != null)
                mRouteSearchResultListener.onFailure(RouteSearchResultListener.Type.WALKING);
        }
    }

    public void updateDrivingSearchMapStatus(BaiduMap baiduMap, List<DrivingRouteLine> wrLines) {
        baiduMap.clear();
        //创建驾车路线规划线路覆盖物
        DrivingRouteOverlay overlay = new DrivingRouteOverlay(baiduMap);
        //设置驾车路线规划数据
        overlay.setData(wrLines.get(0));
        //将驾车路线规划覆盖物添加到地图中
        overlay.addToMap();
        overlay.zoomToSpan();
    }

    public void updateTransitSearchMapStatus(BaiduMap baiduMap, List<TransitRouteLine> wrLines) {
        baiduMap.clear();
        //创建公交路线规划线路覆盖物
        TransitRouteOverlay overlay = new TransitRouteOverlay(baiduMap);
        //设置公交路线规划数据
        overlay.setData(wrLines.get(0));
        //将公交路线规划覆盖物添加到地图中
        overlay.addToMap();
        overlay.zoomToSpan();
    }

    public void updateBikingSearchMapStatus(BaiduMap baiduMap, List<BikingRouteLine> wrLines) {
        baiduMap.clear();
        //创建骑行路线规划线路覆盖物
        BikingRouteOverlay overlay = new BikingRouteOverlay(baiduMap);
        //设置骑行路线规划数据
        overlay.setData(wrLines.get(0));
        //将骑行路线规划覆盖物添加到地图中
        overlay.addToMap();
        overlay.zoomToSpan();
    }

    public void updateWalkingSearchMapStatus(BaiduMap baiduMap, List<WalkingRouteLine> wrLines) {
        baiduMap.clear();
        //创建步行路线规划线路覆盖物
        WalkingRouteOverlay overlay = new WalkingRouteOverlay(baiduMap);
        //设置步行路线规划数据
        overlay.setData(wrLines.get(0));
        //将步行路线规划覆盖物添加到地图中
        overlay.addToMap();
        overlay.zoomToSpan();
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //获取定位结果
            StringBuffer sb = new StringBuffer(256);

            sb.append("time : ");
            sb.append(location.getTime());    //获取定位时间

            sb.append("\nerror code : ");
            sb.append(location.getLocType());    //获取类型类型

            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());    //获取纬度信息

            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());    //获取经度信息

            sb.append("\nradius : ");
            sb.append(location.getRadius());    //获取定位精准度

            if (location.getLocType() == BDLocation.TypeGpsLocation) {

                // GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());    // 单位：公里每小时

                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());    //获取卫星数

                sb.append("\nheight : ");
                sb.append(location.getAltitude());    //获取海拔高度信息，单位米

                sb.append("\ndirection : ");
                sb.append(location.getDirection());    //获取方向信息，单位度

                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息

                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

                if (mLoctionResultListener != null) {
                    mLoctionResultListener.onSuccess(location);
                }
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {

                // 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息

                sb.append("\noperationers : ");
                sb.append(location.getOperators());    //获取运营商信息

                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
                if (mLoctionResultListener != null) {
                    mLoctionResultListener.onSuccess(location);
                }
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {

                // 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");

                if (mLoctionResultListener != null) {
                    mLoctionResultListener.onSuccess(location);
                }
            } else if (location.getLocType() == BDLocation.TypeServerError) {

                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");

                if (mLoctionResultListener != null)
                    mLoctionResultListener.onFailure(location);
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {

                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");

                if (mLoctionResultListener != null)
                    mLoctionResultListener.onFailure(location);
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");

                if (mLoctionResultListener != null)
                    mLoctionResultListener.onFailure(location);
            }

            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());    //位置语义化信息

            List<Poi> list = location.getPoiList();    // POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            logI(sb.toString());
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    private class MyOnGetRoutePlanResultListener implements OnGetRoutePlanResultListener {

        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
            if (walkingRouteResult == null || walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                //未找到结果
                if (mRouteSearchResultListener != null)
                    mRouteSearchResultListener.onFailure(RouteSearchResultListener.Type.WALKING);
                logE("步行路线获取失败");
                return;
            }
            if (walkingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                //walkingRouteResult.getSuggestAddrInfo()
                logE("步行起终点或途经点地址有岐义");
                return;
            }
            if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                //获取步行线路规划结果
                List<WalkingRouteLine> wrLines = walkingRouteResult.getRouteLines();
                if (null == wrLines) return;
                if (mRouteSearchResultListener != null)
                    mRouteSearchResultListener.onWalkingSuccess(wrLines);
                logI("步行路线获取成功");
            }
        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
            if (transitRouteResult == null || transitRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                //未找到结果
                if (mRouteSearchResultListener != null)
                    mRouteSearchResultListener.onFailure(RouteSearchResultListener.Type.TRANSIT);
                logE("公交路线获取失败");
                return;
            }
            if (transitRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                //transitRouteResult.getSuggestAddrInfo()
                logE("公交起终点或途经点地址有岐义");
                return;
            }
            if (transitRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                //获取公交换乘路径规划结果
                List<TransitRouteLine> wrLines = transitRouteResult.getRouteLines();
                if (null == wrLines) return;
                if (mRouteSearchResultListener != null)
                    mRouteSearchResultListener.onTransitSuccess(wrLines);
                logI("公交路线获取成功");
            }
        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
            if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                //未找到结果
                if (mRouteSearchResultListener != null)
                    mRouteSearchResultListener.onFailure(RouteSearchResultListener.Type.DRIVING);
                logE("驾车路线获取失败");
                return;
            }
            if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                //drivingRouteResult.getSuggestAddrInfo()
                logE("驾车起终点或途经点地址有岐义");
                return;
            }
            if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                //获取驾车线路规划结果
                List<DrivingRouteLine> wrLines = drivingRouteResult.getRouteLines();
                if (null == wrLines) return;
                if (mRouteSearchResultListener != null)
                    mRouteSearchResultListener.onDrivingSuccess(wrLines);
                logI("驾车路线获取成功");
            }
        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
            if (bikingRouteResult == null || bikingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                //未找到结果
                if (mRouteSearchResultListener != null)
                    mRouteSearchResultListener.onFailure(RouteSearchResultListener.Type.BIKING);
                logE("骑行路线获取失败");
                return;
            }
            if (bikingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                //bikingRouteResult.getSuggestAddrInfo()
                logE("骑行起终点或途经点地址有岐义");
                return;
            }
            if (bikingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                List<BikingRouteLine> wrLines = bikingRouteResult.getRouteLines();
                if (null == wrLines) return;
                if (mRouteSearchResultListener != null)
                    mRouteSearchResultListener.onBikingSuccess(wrLines);
                logI("骑行路线获取成功");
            }
        }
    }

    public void onResume(@NonNull MapView mapView) {
        mapView.onResume();
    }

    public void onPause(@NonNull MapView mapView) {
        mapView.onPause();
    }

    public void onDestroy(@Nullable MapView mapView) {
        if (mapView != null)
            mapView.onDestroy();
    }

    public void onDestroy() {
        if (mLocationClient != null)
            mLocationClient = null;
        if (myListener != null)
            myListener = null;
        if (mOption != null)
            mOption = null;
        if (pSearch != null)
            pSearch.destroy();
        if (mLoctionResultListener != null)
            mLoctionResultListener = null;
        if (mRouteSearchResultListener != null)
            mRouteSearchResultListener = null;
    }

    @Override
    public void logI(String message) {
        LogUtils.i(String.format(messageFormat, getClass().getSimpleName(), message));
    }

    @Override
    public void logE(String message) {
        LogUtils.e(String.format(messageFormat, getClass().getSimpleName(), message));
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void showToast(String message, int duration) {

    }
}
